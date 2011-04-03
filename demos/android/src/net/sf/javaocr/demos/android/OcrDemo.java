package net.sf.javaocr.demos.android;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.cluster.FeatureExtractor;
import net.sourceforge.javaocr.filter.MedianFilter;
import net.sourceforge.javaocr.filter.SauvolaBinarisationFilter;
import net.sourceforge.javaocr.filter.ThresholdFilter;
import net.sourceforge.javaocr.ocr.*;
import net.sourceforge.javaocr.plugin.cluster.Cluster;
import net.sourceforge.javaocr.plugin.cluster.MahalanobisDistanceCluster;
import net.sourceforge.javaocr.plugin.cluster.Match;
import net.sourceforge.javaocr.plugin.cluster.MetricMatcher;
import net.sourceforge.javaocr.plugin.moment.HuMoments;

import java.io.IOException;
import java.util.*;

/**
 * Simple OCR demonstrator. Just tries to match recognised text.
 *
 * @author Konstantin Pribluda
 */
public class OcrDemo extends Activity implements SurfaceHolder.Callback, Camera.AutoFocusCallback, Camera.PreviewCallback, View.OnClickListener {
    // black and white pixels
    public static final int WHITE = 0xFFFFFFFF;
    public static final int BLACK = 0xff000000;

    // filter window size
    public static final int WINDOW_SIZE = 50;

    // sauvola filter weight - higher values lover threshold,
    // practical - 0.2 to 0.5
    // 0 means just mean value
    public static final double SAUVOLA_WEIGHT = 0.20;
    private static final String LOG_TAG = "ocrdemo";
    private SauvolaBinarisationFilter sauvolaBinarisationFilter;


    // window size to be ued by median filter
    private static final int MEDIAN_WINDOW = 3;

    // median filter will be used to kill saltand pepper inside images
    // to allow crisper setting of sauvoval filter
    private MedianFilter medianFilter;

    // drawing surface for entire image
    private SurfaceView surfaceView;
    // hosts preview image
    private SurfaceHolder preview;


    Camera camera;
    private Camera.Parameters cameraParameters;
    private Camera.Size previewSize;

    boolean previewActive = false;


    // viewfinder area
    private View scanArea;
    // work area displays processed image and glyph borders
    private ImageView workArea;


    // coordinates for and viewfinder for extraction of subimage
    private int overlayH;
    private int overlayW;
    private int viewfinderW;
    private int viewfinderH;
    private int viewfinderOriginY;
    private int viewfinderOriginX;

    private float scaleW;
    private float scaleH;

    private int bitmapW;
    private int bitmapH;

    private PixelImage processImage;

    private Bitmap backBuffer;


    private Button snap;
    private Button save;
    private EditText expected;

    private StringBuilder recognitionResult;
    private TextView resultText;

    private MediaPlayer mediaPlayer;
    private static final float BEEP_VOLUME = 0.10f;

    // moments from last recogition attempt, use them to train
    private ArrayList<double[]> moments;

    // paints to be used in drawing borders over the found glyphs
    private Paint redPaint;
    private Paint greenPaint;

    // matcher will be used to train  and recognise
    private MetricMatcher matcher;
    // feature extractor
    private FeatureExtractor extractor;


    //whether surface size was already set
    private boolean surfaceSizeSet = false;
    // whether applucation active - guards against strange racing confitions
    // set to true upon completion og onResume() and to false on onPause()
    private boolean active = false;

    //  we will use it for recognition. clusters will be teached on save
    Map<Cluster, Character> characterMap = new HashMap<Cluster, Character>();
    Map<Character, Cluster> clusterMap = new HashMap<Character, Cluster>();

    /**
     * create actvity and initalise interface elements
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        surfaceView = (SurfaceView) findViewById(R.id.preview);

        preview = surfaceView.getHolder();
        preview.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        preview.addCallback(this);

        scanArea = findViewById(R.id.scanarea);
        workArea = (ImageView) findViewById(R.id.workarea);

        // make it stay on
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        snap = (Button) findViewById(R.id.snap);
        snap.setOnClickListener(this);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);

        expected = (EditText) findViewById(R.id.expectedText);
        resultText = (TextView) findViewById(R.id.recognitionResult);


        redPaint = new Paint();
        redPaint.setColor(0xffFF0000);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(2);

        greenPaint = new Paint();
        greenPaint.setColor(0xffFF0000);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(2);

        matcher = new MetricMatcher();

        extractor = new HuMoments();
    }


    /**
     * activated again
     * <p/>
     * - register callbacks
     * - start preview
     */
    @Override
    protected void onResume() {
        super.onResume();
        // init beep sound
        initBeepSound();

        // init buttons
        snap.setEnabled(false);
        save.setEnabled(false);

        // activate camera
        camera = Camera.open();


        // restart preview is already set surface size
        if (surfaceSizeSet) {
            Log.d(LOG_TAG, "surface size was already set, start preview ");
            // set  preview if already created
            setPreviewDisplay();
            startPreview();
        }

        // say  that we are active now
        active = true;
    }


    /**
     * set preview display properly, quarding and releasing
     * camera in case of emergency
     */
    private void setPreviewDisplay() {
        if (preview != null) {
            try {
                camera.setPreviewDisplay(preview);
            } catch (IOException e) {
                camera.release();
                camera = null;
                System.err.println("****************** could not set preview on resume");
            }
        }
    }

    /**
     * deactivating activity
     * - stop preview
     * - release camera
     * - disable callbacks
     */
    @Override
    protected void onPause() {
        super.onPause();
        active = false;

        Log.d(LOG_TAG, "paused ");
        if (camera != null) {
            // avoid race condition
            synchronized (camera) {
                camera.stopPreview();
                previewActive = false;
                camera.release();
                camera = null;
            }

        }
    }

    /**
     * ack camera in surface creation
     *
     * @param surfaceHolder
     */
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(LOG_TAG, "surface created");
        if (active) {
            Log.d(LOG_TAG, "active - proceed");
            if (surfaceHolder == preview) {
                setPreviewDisplay();
            }
        } else {
            Log.d(LOG_TAG, "inactive - ignore");
        }
    }

    /**
     * suradce was changed means that we  are up and ready to display - time to start camera
     *
     * @param surfaceHolder
     * @param i
     * @param width
     * @param height
     */
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int width, int height) {
        // only if we are active
        Log.d(LOG_TAG, " surface changed");
        if (active) {
            Log.d(LOG_TAG, " as we are active, activate preview etc");
            if (surfaceHolder == preview) {
                surfaceSizeSet = true;
                overlayW = width;
                overlayH = height;
                if (camera != null) {
                    startPreview();
                }
            }
        } else {
            Log.d(LOG_TAG, " inactive - ignore for good");
        }
    }


    /**
     * need this because wake lock sometimes forces application in portaint mode.
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (previewActive && newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(LOG_TAG, "reconfiguring for landscape layout");
            setPreviewDisplay();
            setUpImagesAndBitmaps();
        }
        Log.d(LOG_TAG, " configuration was changed:" + newConfig);

    }


    private void startPreview() {

        cameraParameters = camera.getParameters();


        // activate auto focus


        // as we know,  that big preview size can produce RE on HTC Hero,
        // we just iterate through allowed preview sizes until we find something proper
        //  go for maximal allowed preview size
        previewSize = cameraParameters.getPreviewSize();
        if (previewSize.width <= 480) {
            Log.d(LOG_TAG, "preview size is too small:" + previewSize.width + "x" + previewSize.height);
            final List<Camera.Size> sizes = cameraParameters.getSupportedPreviewSizes();
            Collections.sort(sizes, new Comparator<Camera.Size>() {
                public int compare(Camera.Size o1, Camera.Size o2) {
                    return new Integer(o2.width).compareTo(o1.width);
                }
            });

            for (Camera.Size size : sizes) {
                cameraParameters.setPreviewSize(size.width, size.height);
                camera.setParameters(cameraParameters);
                Log.d(LOG_TAG, "attempt preview size:" + size.width + "x" + size.height);
                try {
                    camera.startPreview();
                    Log.d(LOG_TAG, "...accepted - go along");
                    //  ok, camera accepted out settings.  since we know,
                    //  ok, camera accepted out settings.  since we know,
                    // that some implementations may choose different preview format,
                    // we retrieve parameters again. just to be sure
                    cameraParameters = camera.getParameters();
                    break;
                } catch (RuntimeException rx) {
                    // ups, camera did not like this size
                    Log.d(LOG_TAG, "...barfed, try next");
                }

            }
        } else {
            Log.d(LOG_TAG, " accepted preview size on the spot:" + previewSize.width + "x" + previewSize.height);
            camera.startPreview();
        }

        previewActive = true;

        setUpImagesAndBitmaps();
        // activate snap button
        snap.setEnabled(true);
    }


    private void computeViewfinderOrigin() {

        int[] absPos = new int[2];
        scanArea.getLocationOnScreen(absPos);


        viewfinderOriginX = absPos[0];
        viewfinderOriginY = absPos[1];

        // subtract origin of preview view
        surfaceView.getLocationOnScreen(absPos);

        viewfinderOriginX -= absPos[0];
        viewfinderOriginY -= absPos[1];
    }


    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
            preview = null;
        }
    }

    public void onAutoFocus(boolean status, Camera camera) {
        System.err.println("********* autofocus:" + status);
        // do we have focus? do we care at all?

        if (this.camera != null && previewActive && status == true) {
            // prevent another scan
            // perform scan
            camera.setOneShotPreviewCallback(this);
        } else {
            // request autofocus again if not successfull
            requestAutofocus();
        }


    }


    private void requestAutofocus() {
        if (this.camera != null && previewActive) {
            camera.autoFocus(this);
        }
    }

    /**
     * here we receive one shot preview callback, and perform OCR of the
     * image
     */
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        // play beep
        mediaPlayer.start();

        //just in case - readjust position og viewfinder,
        // in case layout was shifted by ad views (if any)
        computeViewfinderOrigin();

        Log.d(LOG_TAG, "received preview frame:" + bytes.length);

        // create sub-image containing preview area,  note that we
        // use offset of WINDOW_SIZE to add borders to the image
        // those borders are invalid, because local thresholding produces
        // invalid data there
        ByteImage image = new ByteImage(bytes, previewSize.width, previewSize.height, (int) ((float) viewfinderOriginX / scaleW) - WINDOW_SIZE / 2, (int) ((float) viewfinderOriginY / scaleH) - WINDOW_SIZE / 2, bitmapW + WINDOW_SIZE, bitmapH + WINDOW_SIZE);
        Log.d(LOG_TAG, "image:" + image);

        // copy image data into out process image expanding it to int
        // original image data is mmapped and will be destroyed by next frame
        image.copy(processImage);

        Log.d(LOG_TAG, "pixel image:" + processImage);

        // perform processing  in  separate thread
        (new Thread(new Runnable() {
            public void run() {

                //  eliminate salt and pepper
                medianFilter.process(processImage);
                // perform adaptive thresholding
                sauvolaBinarisationFilter.process(processImage);

                // since borders of sauvola processed image image are unusable,
                // extract subimage for further processing
                Image binarzedImage = processImage.chisel(WINDOW_SIZE / 2, WINDOW_SIZE / 2, bitmapW, bitmapH);


                // after sauvola processing,  dark pixels are set to 1
                // while empty pixels are set to 0

                //shrinker to sjring glyphs
                Shrinker shrinker = new Shrinker(0);


                // slice image
                List<List<Image>> rows = new ArrayList();
                SlicerH hslicer = new SlicerH(binarzedImage, 0);

                hslicer.slice(0);
                while (hslicer.hasNext()) {
                    Image row = hslicer.next();
                    // if there is a row and it is smaller than process image
                    if (row != null && row.getHeight() < binarzedImage.getHeight()) {
                        Log.d(LOG_TAG, "row: " + row);
                        List<Image> cells = new ArrayList<Image>();
                        rows.add(cells);
                        // slice V
                        SlicerV vslicer = new SlicerV(row, 0);
                        vslicer.slice(0);
                        Image glyph;
                        int j = 1;
                        while (vslicer.hasNext()) {
                            // shrinkwrap glyph
                            glyph = shrinker.shrink(vslicer.next());
                            // is it valid glyph?
                            if (glyph != null && glyph.getWidth() < row.getWidth()) {
                                cells.add(glyph);
                                Log.d(LOG_TAG, "glyph " + (j++) + ":" + glyph);
                            }
                        }
                    }
                }

                // ok,   we have segmented image - which row do we select for further processing
                // this has to contain the same amount of characters as expected text
                String expectedText = expected.getText().toString();

                Log.d(LOG_TAG, "expected size:" + expectedText.length());
                List<Image> recognition = new ArrayList<Image>();
                for (List<Image> row : rows) {
                    Log.d(LOG_TAG, "row length:" + row.size());
                    if (row.size() == expectedText.length()) {
                        Log.d(LOG_TAG, "recognise this one");
                        recognition = row;
                    }
                }

                moments = new ArrayList<double[]>();
                // compute moments  and store them into array
                for (Image glyph : recognition) {
                    final double[] features = extractor.extract(glyph);
                    moments.add(features);
                    StringBuilder builder = new StringBuilder();

                    for (int i = 0; i < 7; i++) {
                        builder.append(features[i] + "|");
                    }
                    Log.d(LOG_TAG, "**************");
                    Log.d(LOG_TAG, builder.toString());
                    Log.d(LOG_TAG, "**************");
                }

                // perform recognition attempt
                recognitionResult = new StringBuilder();

                int i = 0;
                for (double[] moment : moments) {
                    StringBuilder mb = new StringBuilder();
                    for (double m : moment)
                        mb.append(m).append(':');
                    Log.d(LOG_TAG, "expecting " + expectedText.charAt(i++));
                    Log.d(LOG_TAG, "matching with" + mb.toString());

                    List<Match> matches = matcher.match(moment);
                    // TODO: append it here
                    // recognitionResult.append(c);
                }


                // transfer image to B&W ARGS
                final ThresholdFilter argbFilter = new ThresholdFilter(0, BLACK, WHITE);
                argbFilter.process(processImage);


                // create canvas to draw borders to bitmap
                Canvas canvas = new Canvas(backBuffer);
                // offset , stride, width, height
                canvas.drawBitmap(
                        Bitmap.createBitmap(processImage.pixels,
                                (processImage.getWidth() + 1) * WINDOW_SIZE / 2,  // offset
                                processImage.getWidth(), // stride
                                bitmapW, // width
                                bitmapH, //height
                                Bitmap.Config.ARGB_8888), 0, 0, null);
                // draw glyph borders on image
                for (List<Image> row : rows) {
                    // ... but row under recognition has to be highlighted green
                    Paint paint = row == recognition ? greenPaint : redPaint;
                    for (Image glyph : row) {
                        canvas.drawRect(glyph.getOriginX() - WINDOW_SIZE / 2, glyph.getOriginY() - WINDOW_SIZE / 2, glyph.getOriginX() + glyph.getWidth() - WINDOW_SIZE / 2, glyph.getOriginY() + glyph.getHeight() - WINDOW_SIZE / 2, paint);
                    }
                }

                // draw back buffer ASAP
                final List<Image> finalRecognition = recognition;
                runOnUiThread(new Runnable() {
                    public void run() {
                        workArea.setImageBitmap(backBuffer);
                        workArea.invalidate();

                        // also set recognised text
                        resultText.setText(recognitionResult.toString());
                        // if we have something to recognise,  we may as well enable learning
                        if (finalRecognition.size() > 0) {
                            save.setEnabled(true);
                        }
                        // now we are ready with recognition, reenable snap
                        snap.setEnabled(true);
                    }
                });

            }
        })).start();

        Log.d(LOG_TAG, "snapshot ready processing ready");
    }

    public void onClick(View view) {
        if (view == snap) {
            // start snapping picrture
            snap.setEnabled(false);
            save.setEnabled(false);
            requestAutofocus();
        } else if (view == save) {
            // only once
            save.setEnabled(false);
            // save result coefficients
            final String exp = expected.getText().toString();
            if (moments.size() == exp.length()) {
                Log.d(LOG_TAG, "teaching:" + exp);


                // process for every character
                final char[] chars = exp.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    // train individual clusters
                    Cluster cluster = clusterMap.get(chars[i]);
                    if (cluster == null) {

                        // we see this character for the first time,
                        // create and register clusters
                        cluster = new MahalanobisDistanceCluster(extractor.getSize());

                        clusterMap.put(chars[i], cluster);
                        characterMap.put(cluster, chars[i]);

                        // also register it into matcher
                        matcher.getClusters().add(cluster);

                    }
                    // ok, now we have cluster, it is registered, so we can train it.
                    //TODO: complete me - keep list of glzph features, and use it to train cluster
                    // and also save sample with timestamp & text
                }
            }
        }
    }

    /**
     * set up images and bitmaps to adjust for change in screen size
     */
    private void setUpImagesAndBitmaps() {
        previewSize = cameraParameters.getPreviewSize();

        Log.d(LOG_TAG, "preview width:" + previewSize.width + " preview height:" + previewSize.height);
        // compute and prepare working images

        // size of preview area in screen coordinates
        int viewfinderH = scanArea.getBottom() - scanArea.getTop();
        int viewfinderW = scanArea.getRight() - scanArea.getLeft();

        // scaling factor between preview image and screen coordinates
        scaleW = (float) overlayW / (float) previewSize.width;
        scaleH = (float) overlayH / (float) previewSize.height;

        // bitmap size
        bitmapW = (int) ((float) viewfinderW / scaleW);
        bitmapH = (int) ((float) viewfinderH / scaleH);

        // and now create byte image
        // image to hold copy to be processed  - allow for borders
        processImage = new PixelImage(bitmapW + WINDOW_SIZE, bitmapH + WINDOW_SIZE);
        Log.d(LOG_TAG, "image width:" + processImage.getWidth() + " height:" + processImage.getHeight());

        // median filter for preprocessing
        medianFilter = new MedianFilter(processImage, MEDIAN_WINDOW);

        // and local threshold for it  - note that we like to have dark as 1 and lite as 0
        sauvolaBinarisationFilter = new SauvolaBinarisationFilter(0, 1, processImage, 256, SAUVOLA_WEIGHT, WINDOW_SIZE);


        // bitmap to draw information
        backBuffer = Bitmap.createBitmap(bitmapW, bitmapH, Bitmap.Config.ARGB_8888);
    }

    /**
     * Creates the beep MediaPlayer in advance so that the sound can be triggered with the least
     * latency possible.
     */
    private void initBeepSound() {
        if (mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                        file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };
}

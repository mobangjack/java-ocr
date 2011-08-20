package net.sf.javaocr.demos.android.recognizer;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.*;
import android.widget.*;
import net.sf.javaocr.demos.android.R;
import net.sf.javaocr.demos.android.utils.camera.CameraManager;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.cluster.FeatureExtractor;
import net.sourceforge.javaocr.filter.SauvolaBinarisationFilter;
import net.sourceforge.javaocr.filter.ThresholdFilter;
import net.sourceforge.javaocr.matcher.Match;
import net.sourceforge.javaocr.matcher.MetricMatcher;
import net.sourceforge.javaocr.ocr.*;
import net.sourceforge.javaocr.plugin.moment.HuMoments;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple OCR demonstrator. Just tries to match recognised text.
 *
 * @author Konstantin Pribluda
 */
public class Recognizer extends Activity implements SurfaceHolder.Callback, View.OnClickListener {
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


    // drawing surface for entire image
    private SurfaceView surfaceView;
    // hosts preview image
    private SurfaceHolder preview;


    // viewfinder area
    private View scanArea;
    // work area displays processed image and glyph borders
    private ImageView workArea;


    // coordinates for and viewfinder for extraction of subimage
    private int overlayH;
    private int overlayW;
    private int viewfinderOriginY;
    private int viewfinderOriginX;

    private float scaleW;
    private float scaleH;

    private int bitmapW;
    private int bitmapH;

    private PixelImage processImage;

    private Bitmap backBuffer;


    private Button snap;


    private StringBuilder recognitionResult;
    private TextView resultText;

    private MediaPlayer mediaPlayer;
    private static final float BEEP_VOLUME = 0.10f;

    // paints to be used in drawing borders over the found glyphs
    private Paint redPaint;
    private Paint greenPaint;

    // matcher will be used to recognise image sampler
    private MetricMatcher matcher;

    // feature extractor
    private FeatureExtractor extractor;


    //whether surface size was already set
    private boolean haveSurface = false;
    //  encapsulated camera management logic
    private CameraManager cameraManager;


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


        // create camera manager
        cameraManager = new CameraManager();

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

        resultText = (TextView) findViewById(R.id.recognitionResult);


        redPaint = new Paint();
        redPaint.setColor(0xffFF0000);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(2);

        greenPaint = new Paint();
        greenPaint.setColor(0xff00FF00);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(2);

        //  as cluster data is potentially big, and may take long time to load
        // we read them in separate thread

        Thread thr = new Thread(new Runnable() {
            public void run() {
                readClusterData();
            }
        });
        thr.start();

        matcher = new MetricMatcher();

        extractor = new HuMoments();
    }

    /**
     * read cluster data  from storage
     */
    private void readClusterData() {
        
    }


    /**
     * open camera and activate preview display
     */
    @Override
    protected void onResume() {
        super.onResume();
        // init beep sound
        initBeepSound();

        // init buttons
        snap.setEnabled(false);      

        // in case we already have surface, we can start camera ASAP
        if (haveSurface) {
            try {
                Log.d(LOG_TAG, "existing surface - start camera now");
                startCamera();
            } catch (IOException e) {
                Log.e(LOG_TAG, "error starting preview in on resume", e);
            }
        }

    }


    /**
     * perform necessary operations to start camera
     *
     * @throws IOException
     */
    private void startCamera() throws IOException {
        cameraManager.start(preview);
        setUpImagesAndBitmaps();
    }


    /**
     * deactivate camera manager on activity stop
     */
    @Override
    protected void onPause() {
        super.onPause();
        cameraManager.stop();
    }

    /**
     * ack camera in surface creation
     *
     * @param surfaceHolder
     */
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(LOG_TAG, "surface created");
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
        Log.d(LOG_TAG, " surface changed, initialize camera");
        haveSurface = true;

        Log.d(LOG_TAG, "surface changed " + width + "x" + height);
        overlayW = width;
        overlayH = height;
        try {
            startCamera();
        } catch (IOException e) {
            Log.e(LOG_TAG, "error starting preview", e);
        }
    }

    /**
     * when surface if being destroyed, we just stop camera
     *
     * @param surfaceHolder
     */
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(LOG_TAG, " surface destroyed");

        haveSurface = false;
        cameraManager.stop();
    }



    /**
     * as image processing is long running task ( obtain camera focus,
     * retrieve preview image, and finally process it ) it us launched in a separate thread
     */
    private void startProcessing() {
        Thread worker = new Thread(new Runnable() {
            public void run() {
                acquireAndProcess();
            }
        });

        worker.start();
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


    public void onClick(View view) {
        if (view == snap) {
            // start snapping picrture
            snap.setEnabled(false);
            startProcessing();
        }
    }

    /**
     * set up images and bitmaps to adjust for change in screen size
     */
    private void setUpImagesAndBitmaps() {
        previewSize = cameraParameters.getPreviewSize();

        Log.d(LOG_TAG, "preview width: " + previewSize.width + " preview height: " + previewSize.height);
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
        Log.d(LOG_TAG, "image width: " + processImage.getWidth() + " height: " + processImage.getHeight());


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


    /**
     * save image for later processing. image name contains expected text,
     * and   timestamp.    image size and window are saved first to allow easier
     * processing of sampler afterwards
     */
    public void saveTrainingImage(String expected, int[] data, int w, int h) throws IOException {
        File outdir = new File(Environment.getExternalStorageDirectory() + "/" + LOG_TAG);
        outdir.mkdirs();

        final DataOutputStream dos = new DataOutputStream(new FileOutputStream(outdir.getAbsolutePath() + "/" + expected + "_" + (System.currentTimeMillis() / 1000) + ".dat"));


        // save size
        dos.writeInt(w);
        dos.writeInt(h);
        dos.writeInt(WINDOW_SIZE);


        for (int value : data)
            dos.writeInt(value);
        dos.close();
    }


    /**
     * displays toast with failure message
     */
    private void displayFailure() {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(scanArea.getContext(), scanArea.getContext().getString(R.string.recognitionFailed), Toast.LENGTH_SHORT).show();
            }
        }
        );
    }

    /**
     * acquire and process frame
     * TODO: maybe it shall be guarded against parallel execution?
     */
    public void acquireAndProcess() {
        Log.d(LOG_TAG, "autofocus requested");
        if (!cameraManager.doAutofocus()) {
            Log.d(LOG_TAG, "autofocus failed");
            return;
        }
        Log.d(LOG_TAG, "autofocus obtained");
        byte[] previewFrame = cameraManager.getPreviewFrame();
        if (previewFrame == null) {
            // we were interrupted,  signal to user and bail out
            displayFailure();
            return;
        }
        Log.d(LOG_TAG, "got preview frame" + previewFrame);

        scanArea.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING | HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING);


        // recompute origin in case ad was displayed and shifted  layout
        computeViewfinderOrigin();

        PixelImage processedImage = imageProcessor.prepareImage(previewFrame, (int) ((float) viewfinderOriginX / scaleW), (int) ((float) viewfinderOriginY / scaleH));
        Log.d(LOG_TAG, "frame processed" + processedImage);


        // slice into glyphs, take last row
        // glyphs are shrink wrapped
        final List<List<Image>> glyphs = slicer.sliceUp(processedImage);


        final SpannableStringBuilder result = new SpannableStringBuilder();
        int redColor = Color.rgb(255, 0, 0);

        // perform recognition  and draw borders with results
        ArrayList<Paint> paints = new ArrayList();

        // guard against nothing available
        List<Image> row = glyphs.size() > 0 ? glyphs.get(glyphs.size() - 1) : Collections.EMPTY_LIST;

        // shorten to max 20
        if (row.size() > 20) {
            row = row.subList(0, 20);
        }

        for (Image glyph : row) {
            Log.d(LOG_TAG, "glyph:" + glyph);
            double[] features = extractor.extract(glyph);
            // cluster mathcer
            List<Match> matches = metricMatcher.classify(features);
            // perform matching of free spaces
            List<Match> freeSpaceMatches = freeSpaceMatcher.classify(new double[]{features[1]});

            // ... and bayes it

            List<Match> mergedMatches = merge(matches, freeSpaceMatches);


            // proceed to bext glyph on empty matches
            // decide which border color to draw
            Paint paint = redPaint;
            if (!mergedMatches.isEmpty()) {

                final Match match = mergedMatches.get(0);
                Character character = match.getChr();
                Log.d(LOG_TAG, "recognised:" + character + " dist: " + match.getDistance());
                Log.d(LOG_TAG, "yellow:" + match.getYellow() + " red:" + match.getRed());
                result.append(character);
                if (match.getDistance() > match.getRed()) {
                    result.setSpan(new ForegroundColorSpan(redColor), result.length() - 1, result.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    result.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), result.length() - 1, result.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    Log.d(LOG_TAG, "poor quality - notify " + (result.length() - 1));
                }
                /*
              for (Match m : mergedMatches) {
                  Log.d(LOG_TAG, m.getChr() + "\t" + m.getDistance() + "\t");
              }
              Log.d(LOG_TAG, "--------------------------------");
                */
                if (match.getDistance() < match.getYellow()) {
                    // green area
                    paint = borderPaint;
                } else if (match.getDistance() < match.getRed()) {
                    paint = yellowPaint;
                }
            } else {
                Log.d(LOG_TAG, ".... ignore because merged list is empty");
                for (Match m : matches) {
                    Log.d(LOG_TAG, m.getChr() + "\t" + m.getDistance() + "\t");
                }
            }

            paints.add(paint);
        }

        //  draw result image with borders

        // transfer image to B&W ARGB
        final ThresholdFilter argbFilter = new ThresholdFilter(0, BLACK, WHITE);
        argbFilter.process(processedImage);

        // create canvas to draw borders to bitmap
        Canvas canvas = new Canvas(backBuffer);
        // offset , stride, width, height
        canvas.drawBitmap(Bitmap.createBitmap(processedImage.pixels,
                // initial pixel offset
                (processedImage.getArrayWidth() + 1) * (processedImage.getArrayWidth() - processedImage.getWidth()) / 2,  // offset
                processedImage.getArrayWidth(), // stride
                bitmapW, // width
                bitmapH, //height
                Bitmap.Config.ARGB_8888), 0, 0, null);

        for (int i = 0; i < row.size(); i++) {
            final Image glyph = row.get(i);
            // draw borders on back buffer
            // as origin is counted from origin bordered array, subtract half of window size
            canvas.drawRect(glyph.getOriginX() - WINDOW_SIZE / 2, glyph.getOriginY() - WINDOW_SIZE / 2, glyph.getOriginX() + glyph.getWidth() - WINDOW_SIZE / 2, glyph.getOriginY() + glyph.getHeight() - WINDOW_SIZE / 2, paints.get(i));

        }

        //  final calls to interface on UI thread, show recognition result
        runOnUiThread(new Runnable() {
            public void run() {
                // display recognition result
                scanArea.setImageBitmap(backBuffer);
                scanArea.invalidate();
                recognition.setText(result);
                recogntionPanel.setVisibility(View.VISIBLE);

            }
        }
        );
    }


}

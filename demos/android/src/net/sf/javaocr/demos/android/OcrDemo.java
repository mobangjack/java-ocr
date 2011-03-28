package net.sf.javaocr.demos.android;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.filter.HistogramFilter;
import net.sourceforge.javaocr.filter.ThresholdFilter;
import net.sourceforge.javaocr.ocr.*;
import net.sourceforge.javaocr.plugin.moment.HuMoments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple OCR demonstrator. Just tries to match recognised text.
 * @author Konstantin Pribluda
 */
public class OcrDemo extends Activity implements SurfaceHolder.Callback, Camera.AutoFocusCallback, Camera.PreviewCallback, View.OnClickListener {

        // hosts preview image
    private SurfaceHolder preview;


    Camera camera;
    private Camera.Parameters cameraParameters;

    boolean previewActive = false;


    private View scanArea;
    private View overlay;
    private Camera.Size previewSize;
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
    private SurfaceView surfaceView;
    private Bitmap backBuffer;
    private ImageView workArea;
    public static final int WHITE = 0xFFFFFFFF;
    public static final int BLACK = 0xff000000;
    private Button snap;
    private Button save;
    private EditText expected;

    private StringBuilder recognitionResult;
    private TextView resultText;

    private MediaPlayer mediaPlayer;
    private static final float BEEP_VOLUME = 0.10f;
    // moments from last recogition attempt, use them to train
    private ArrayList<double[]> moments;

    // performs matching
    Matcher matcher = new Matcher();

    /**
     *
     * create actvity and initalise interface elements 
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main);

        surfaceView = (SurfaceView) findViewById(R.id.preview);

        preview = surfaceView.getHolder();
        preview.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        preview.addCallback(this);

        scanArea = findViewById(R.id.scanarea);
        overlay = findViewById(R.id.overlay);

        workArea = (ImageView) findViewById(R.id.workarea);

        // make it stay on
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        snap = (Button) findViewById(R.id.snap);
        snap.setOnClickListener(this);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);

        expected = (EditText) findViewById(R.id.expectedText);
        resultText = (TextView) findViewById(R.id.recognitionResult);


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
        // set  preview if already created
        setPreviewDisplay();
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
        System.err.println("****************** paused ");
        if (camera != null) {
            // avoid race condition
            synchronized (camera) {
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
        System.err.println("********************* surface created");
        if (surfaceHolder == preview) {
            setPreviewDisplay();
        } else if (surfaceHolder == workArea) {
            System.err.println("************** work area created ");
        }

    }

    /**
     * suradce was changed means that we  are up and ready to display - time to start camera
     *
     * @param surfaceHolder
     * @param i
     * @param w
     * @param h
     */
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int w, int h) {

        // NOTE:  we switch coordinates, to camera space!!!!!!
        overlayH = w;
        overlayW = h;
        System.err.println("************************* surface changed:" + overlayH + ":" + overlayW);
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        System.err.println("***************** overlay area:" + overlay.getLeft() + "/" + overlay.getTop() + "-" + overlay.getRight() + "/" + overlay.getBottom());
        System.err.println("***************** scan area:" + scanArea.getLeft() + "/" + scanArea.getTop() + "-" + scanArea.getRight() + "/" + scanArea.getBottom());

        startPreview();

    }

    private void startPreview() {
        cameraParameters = camera.getParameters();


        previewSize = cameraParameters.getPreviewSize();


        System.err.println("***************  preview size:" + previewSize.width + "x" + cameraParameters.getPreviewSize().height);

        // store viewfinder params (in PREVIEW!!!! CAMERA!!!!! coordinates)
        viewfinderW = scanArea.getBottom() - scanArea.getTop();
        viewfinderH = scanArea.getRight() - scanArea.getLeft();
        viewfinderOriginY = overlayH - scanArea.getRight();

        computeViewfinderOrigin();

        scaleW = (float) overlayW / (float) previewSize.width;
        scaleH = (float) overlayH / (float) previewSize.height;

        System.err.println("************ viewfinder scale:" + scaleW + "/" + scaleH);

        bitmapW = (int) ((float) viewfinderW / scaleW);
        bitmapH = (int) ((float) viewfinderH / scaleH);
        // image to hold copy to be processed
        processImage = new PixelImage(bitmapH, bitmapW);

        // back buffer to be drawn to surface holder
        backBuffer = Bitmap.createBitmap(bitmapH, bitmapW, Bitmap.Config.ARGB_8888);

        camera.startPreview();
        // activate auto focus
        previewActive = true;

        // activate snap button
        snap.setEnabled(true);
    }

    /**
     * encapsulate logic to compute viewfinder origin - as viewfinder may be shifted
     * because of ad display
     */
    private void computeViewfinderOrigin() {
        int[] absPos = new int[2];
        scanArea.getLocationOnScreen(absPos);
        System.err.println("***************** abs position:" + absPos[1]);

        viewfinderOriginX = absPos[1];
        // subtract origin of preview view
        surfaceView.getLocationOnScreen(absPos);
        viewfinderOriginX -= absPos[1];
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        System.err.println("*******************  surface destroyed");
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
        // beep
        mediaPlayer.start();
        //just in case - readjust
        computeViewfinderOrigin();
        System.err.println("************* received preview frame:" + bytes.length);
        // create subimage containing preview area


        ByteImage image = new ByteImage(bytes, previewSize.width, previewSize.height, (int) ((float) viewfinderOriginX / scaleW), (int) ((float) viewfinderOriginY / scaleH), bitmapW, bitmapH);
        System.err.println("****************** image:" + image);


        image.flip(processImage);
        System.err.println("************ pixel image:" + processImage);

        // fire into separate thread
        (new Thread(new Runnable() {
            public void run() {
                // 1 is white and thus empty
                Shrinker shrinker = new Shrinker(1);
                // compute histogram
                final HistogramFilter histogramFilter = new HistogramFilter();
                histogramFilter.process(processImage);
                // extract adaptive threshold
                final int threshold = histogramFilter.adaptiveThreshold();
                System.err.println("******************** threshold: " + threshold);

                // make image binary
                final ThresholdFilter thresholdFilter = new ThresholdFilter(threshold, 1, 0);
                thresholdFilter.process(processImage);

                List<List<Image>> rows = new ArrayList();
                SlicerH hslicer = new SlicerH(processImage, 1);
                hslicer.slice(0);
                while (hslicer.hasNext()) {
                    Image row = hslicer.next();
                    // if there is a row and it is smaller than process image
                    if (row != null && row.getHeight() < processImage.getHeight()) {
                        System.err.println("************** row: " + row);
                        List<Image> cells = new ArrayList<Image>();
                        rows.add(cells);
                        // slice V
                        SlicerV vslicer = new SlicerV(row, 1);
                        vslicer.slice(0);
                        Image glyph;
                        int j = 1;
                        while (vslicer.hasNext()) {
                            // shrinkwrap glyph
                            glyph = shrinker.shrink(vslicer.next());
                            // is it valid glyph?
                            if (glyph != null && glyph.getWidth() < row.getWidth()) {
                                cells.add(glyph);
                                System.err.println("************* glyph " + (j++) + ":" + glyph);
                            }
                        }
                    }
                }
                // ok,   we have segmented image - which row do we select for further processing
                // this has to contain the same amount of characters as expected text
                String expectedText = expected.getText().toString();

                System.err.println("*************expected size:" + expectedText.length());
                List<Image> recognition = new ArrayList<Image>();
                for (List<Image> row : rows) {
                    System.err.println("*************** row length:" + row.size());
                    if (row.size() == expectedText.length()) {
                        System.err.println("************recognise this one");
                        recognition = row;
                    }
                }
                // reverse it, because of coordinate switch
                Collections.reverse(recognition);
                moments = new ArrayList<double[]>();
                // compute moments  and store them into array
                for (Image glyph : recognition) {
                    final double[] hu = (new HuMoments()).extract(glyph);
                    moments.add(hu);
                    StringBuilder builder = new StringBuilder();

                    for (int i = 0; i < 7; i++) {
                        builder.append(hu[i] + "|");
                    }
                    System.err.println("**************");
                    System.err.println("************" + builder.toString());
                    System.err.println("**************");
                }

                // perform recognition attempt
                recognitionResult = new StringBuilder();

                int i = 0;
                for (double[] moment : moments) {
                    StringBuilder mb = new StringBuilder();
                    for (double m : moment)
                        mb.append(m).append(':');
                    System.err.println("*********** expecting " + expectedText.charAt(i++));
                    System.err.println("*********** matching " + mb.toString());

                    char c = matcher.match(moment);
                    recognitionResult.append(c);
                }


                // transfer image to B&W ARGS
                final ThresholdFilter argbFilter = new ThresholdFilter(0, WHITE, BLACK);
                argbFilter.process(processImage);


                // create canvas to draw borders to bitmap
                Canvas canvas = new Canvas(backBuffer);

                Paint paint = new Paint();
                paint.setColor(0xffff0000);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);

                Paint activePaint = new Paint();
                activePaint.setColor(0xff00FF00);
                activePaint.setStyle(Paint.Style.STROKE);
                activePaint.setStrokeWidth(1);

                canvas.drawBitmap(Bitmap.createBitmap(processImage.pixels, bitmapH, bitmapW, Bitmap.Config.ARGB_8888), 0, 0, null);

                // draw glyph borders on image
                for (List<Image> row : rows)
                    for (Image glyph : row) {
                        // ... but row under recognition has to be highlighted green
                        canvas.drawRect(glyph.getOriginX(), glyph.getOriginY(), glyph.getOriginX() + glyph.getWidth(), glyph.getOriginY() + glyph.getHeight(), row == recognition ? activePaint : paint);
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

        System.err.println("***************** snapshot ready");
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
                System.err.println("************* teaching:" + exp);


                // process for every character
                final char[] chars = exp.toCharArray();
                for (int i = 0; i < chars.length; i++) {


                    matcher.train(chars[i], moments.get(i));

                }
            }
        }
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

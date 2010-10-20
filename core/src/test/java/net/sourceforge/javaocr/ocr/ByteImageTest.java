package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;
import net.sourceforge.javaocr.filter.GrayscaleToRGBA;
import net.sourceforge.javaocr.filter.HistogramFilter;
import net.sourceforge.javaocr.filter.ThresholdFilter;

/**
 * test capabilities of byte images
 * TODO: implement testing of byte to int conversion,  shall be able to set and retrieve values from 0 to 255 properly, maybe bigger
 */
public class ByteImageTest extends TestCase {

    public void testValuesAreSetAndRetrievedProperlyAsUnsigned() {
        ByteImage image = new ByteImage(1, 1);

        image.setCurrentIndex(0, 0);
        image.put(255);
        assertEquals(255, image.get());

        image.put(129);
        assertEquals(129, image.get());
    }


    public void testPerformance() {
        byte[] pixels = new byte[720 * 480];

        ByteImage image = new ByteImage(pixels, 720, 480, 360, 0, 133, 480);
        PixelImage dest = new PixelImage(480, 133);

        HistogramFilter histogramm = new HistogramFilter();
        for (int i = 0; i < 100; i++) {
            long start = System.nanoTime();
            image.flip(dest);
            long flip = System.nanoTime();
            histogramm.process(dest);
            long hist = System.nanoTime();
            final int thr = histogramm.adaptiveThreshold();

            (new ThresholdFilter(thr)).process(dest);
            long threshold = System.nanoTime();
            (new GrayscaleToRGBA()).process(dest);
            long gtrgb = System.nanoTime();

            System.err.println("start:" + start);
            System.err.println("flip:" + (flip - start));
            System.err.println("hist:" + (hist - flip));
            System.err.println("thr:" + (threshold - hist));
            System.err.println("to rgb:" + (gtrgb - threshold));
            System.err.println("=================================");
        }
    }
}

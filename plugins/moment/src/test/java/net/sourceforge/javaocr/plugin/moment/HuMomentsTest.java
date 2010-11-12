package net.sourceforge.javaocr.plugin.moment;

import junit.framework.TestCase;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ocr.ByteImage;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * ensure proper function of hu moment calculations
 */
public class HuMomentsTest extends TestCase {


    /**
     * shall return 7 moments
     */
    public void testThat7MomentsAreDelivered() {
        byte[] data = new byte[]{0, 1};
        Image image = new ByteImage(data, 1, 2);
        final double[] moments = HuMoments.compute(image);
        assertEquals(7, moments.length);
        for (double i : moments) {
            System.err.println(i);
        }
    }


    public void testHTranslationInvariance() {
        int[] data = new int[]
                {
                        0, 0, 0, 0,
                        0, 0, 1, 0,
                        0, 0, 1, 0,
                        0, 1, 1, 0,
                };

        Image full = new PixelImage(data, 4, 4);
        Image left = new PixelImage(data, 4, 4, 0, 0, 3, 4);
        Image right = new PixelImage(data, 4, 4, 1, 0, 3, 4);
        Image wrap = new PixelImage(data, 4, 4, 1, 0, 2, 4);

        double[] momentsFull = HuMoments.compute(full);
        double[] momentsLeft = HuMoments.compute(left);
        double[] momentsRight = HuMoments.compute(right);
        double[] momentsWrap = HuMoments.compute(wrap);

        printMoment("full :\t", momentsFull);
        printMoment("left :\t", momentsLeft);
        printMoment("right :\t", momentsRight);
        printMoment("wrap :\t", momentsWrap);

        for (int i = 0; i < 7; i++) {
            assertEquals("left " + i, momentsFull[i], momentsLeft[i]);
            assertEquals("right " + i, momentsFull[i], momentsRight[i]);
            assertEquals("wrap " + i, momentsFull[i], momentsWrap[i]);

        }
    }


    public void testVTranslationInvariance() {
              int[] data = new int[]
                {
                        0, 0, 0, 0,
                        0, 1, 1, 1,
                        0, 0, 0, 1,
                        0, 0, 0, 0,
                };

        Image full = new PixelImage(data, 4, 4);
        Image top = new PixelImage(data, 4, 4, 0, 0, 4, 3);
        Image bottom = new PixelImage(data, 4, 4, 0, 1, 4, 3);
        Image wrap = new PixelImage(data, 4, 4, 0, 1, 4, 2);

        double[] momentsFull = HuMoments.compute(full);
        double[] momentsTop = HuMoments.compute(top);
        double[] momentsBottom = HuMoments.compute(bottom);
        double[] momentsWrap = HuMoments.compute(wrap);

        printMoment("full:\t", momentsFull);
        printMoment("top:\t", momentsTop);
        printMoment("bottom:\t", momentsBottom);
        printMoment("wrap:\t", momentsWrap);

        for (int i = 0; i < 7; i++) {
            assertEquals("top " + i, momentsFull[i], momentsTop[i]);
            assertEquals("bottom " + i, momentsFull[i], momentsBottom[i]);
            assertEquals("wrap " + i, momentsFull[i], momentsWrap[i]);

        }
    }


    private void printMoment(final String label, double[] momentsFull) {
        StringBuilder builder = new StringBuilder();
        for (double moment : momentsFull)
            builder.append(moment).append("\t");
        System.err.println(label + " " + builder.toString());
    }

}

package net.sourceforge.javaocr.plugin.moment;

import junit.framework.TestCase;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ocr.ByteImage;

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
        for(double i:moments) {
            System.err.println(i);
        }
    }
}

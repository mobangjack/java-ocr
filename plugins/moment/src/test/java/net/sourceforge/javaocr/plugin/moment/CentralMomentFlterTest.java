package net.sourceforge.javaocr.plugin.moment;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * test capability of central moment filter (computation)
 */
public class CentralMomentFlterTest extends TestCase {

    /**
     * shal compute proper value
     */
    public void testMomentComputing() {
        int[] data = new int[]{
                1, 2,
                3, 4
        };
        final CentralMomentFilter rawMomentFilter = new CentralMomentFilter(2, 3, 4, 5);
        rawMomentFilter.process(new PixelImage(data, 2, 2));
        assertEquals(rawMomentFilter.getMoment(),
                Math.pow(0 - 4, 2) * Math.pow(0 - 5, 3) * 1 + Math.pow(1 - 4, 2) * Math.pow(0 - 5, 3) * 2 +
                Math.pow(0 - 4, 2) * Math.pow(1 - 5, 3) * 3 + Math.pow(1 - 4, 2) * Math.pow(1 - 5, 3) * 4
        );


    }
}

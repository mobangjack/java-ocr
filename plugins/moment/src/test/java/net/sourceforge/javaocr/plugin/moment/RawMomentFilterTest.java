package net.sourceforge.javaocr.plugin.moment;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * test capability of raw moment filter (computation)
 */
public class RawMomentFilterTest extends TestCase {

    /**
     * shal compute proper value
     */
    public void testMomentComputing() {
        int[] data = new int[]{
                1, 2,
                3, 4
        };
        final RawMomentFilter rawMomentFilter = new RawMomentFilter(2, 3);
        rawMomentFilter.process(new PixelImage(data, 2, 2));
        assertEquals(rawMomentFilter.getMoment(),
                Math.pow(0, 2) * Math.pow(0, 3) * 1 + Math.pow(1, 2) * Math.pow(0, 3) * 2 +
                Math.pow(0, 2) * Math.pow(1, 3) * 3 + Math.pow(1, 2) * Math.pow(1, 3) * 4
        );


    }
}
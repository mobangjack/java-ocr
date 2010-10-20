package net.sourceforge.javaocr.filter;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * test threshold computation
 */
public class ThresholdFilterTest extends TestCase {

    public void testThreasholdProcessing() {
        ThresholdFilter filter = new ThresholdFilter(150);
        int[] data = new int[]{3, 150, 151};

        filter.process(new PixelImage(data, 1, 3));
        assertEquals(0, data[0]);
        assertEquals(0, data[1]);
        assertEquals(255, data[2]);
    }
}

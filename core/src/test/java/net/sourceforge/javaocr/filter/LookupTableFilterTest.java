package net.sourceforge.javaocr.filter;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * test capability of lookup  table filter
 */
public class LookupTableFilterTest extends TestCase {
    public void testLookupIsPerformedProperly() {
        int[] lut = new int[]{255, 0};
        int[] data = new int[]{0, 1};
        
        PixelImage image = new PixelImage(data, 2, 1);
        LookupTableFilter filter = new LookupTableFilter(lut);

        filter.process(image);

        assertEquals(255, data[0]);
        assertEquals(0, data[1]);
    }
}

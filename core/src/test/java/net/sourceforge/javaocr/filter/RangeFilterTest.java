package net.sourceforge.javaocr.filter;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * test range filter capabilities
 */
public class RangeFilterTest extends TestCase {
     /**
      * shall calculate proper range    
      */
    public void testPixelComputation() {
        RangeFilter filter = new RangeFilter();
        int[] data = new int[]{3, 20, 210};

        filter.process(new PixelImage(data, 1, 3));
        assertEquals(3, filter.getMin());
        assertEquals(210, filter.getMax());

    }
}

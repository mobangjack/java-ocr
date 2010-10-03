package net.sourceforge.javaocr.filter;


import junit.framework.TestCase;
import net.sourceforge.javaocr.ImageFilter;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * test capabilities of single pixel filters (scanning)
 *
 * @author Konstantin Pribluda
 */
public class AbstractSinglePixelFilterTest extends TestCase {
    int[] data = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * test order of pixel processing
     */
    public void testFullImageScanning() {

        final ArrayList pixels = new ArrayList();
        ImageFilter filter = new AbstractSinglePixelFilter() {
            protected int convert(int pixel) {
                pixels.add(pixel);
                return 0;
            }
        };

        filter.process(data, 3, 3);
        assertEquals(9,pixels.size());
    }
}

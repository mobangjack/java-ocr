package net.sourceforge.javaocr.filter;


import junit.framework.TestCase;
import net.sourceforge.javaocr.ImageFilter;

import java.util.ArrayList;

/**
 * test capabilities of single pixel filters (scanning)
 *
 * @author Konstantin Pribluda
 */
public class AbstractSinglePixelFilterTest extends TestCase {
    private int[] data;
    private ArrayList pixels;
    private ImageFilter filter;

    public void setUp() {
        data = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        pixels = new ArrayList();

        filter = new AbstractSinglePixelFilter() {
            protected int convert(int pixel) {
                pixels.add(pixel);
                return 0;
            }
        };
    }

    /**
     * test order of pixel processing
     */
    public void testFullImageScanning() {

        filter.process(data, 3, 3);
        assertEquals(9, pixels.size());
        for (int i = 0; i < 9; i++) {
            assertEquals(i, pixels.get(i));
        }
    }

    /**
     * 1x1 inside, no border contact
     */
    public void testSubimageInside() {
        filter.process(data, 3, 3, 1, 1, 1, 1);
        assertEquals(1, pixels.size());
        assertEquals(4, pixels.get(0));
    }

    /**
     * contact with left and top
     */
    public void testTopLeft() {
        filter.process(data, 3, 3, 0, 0, 2, 2);


        assertEquals(4, pixels.size());

        assertEquals(0, pixels.get(0));
        assertEquals(1, pixels.get(1));
        assertEquals(3, pixels.get(2));
        assertEquals(4, pixels.get(3));
    }

    /**
     * contact with left and top
     */
    public void testBottomRight() {
        filter.process(data, 3, 3, 1, 1, 2, 2);


        assertEquals(4, pixels.size());

        assertEquals(4, pixels.get(0));
        assertEquals(5, pixels.get(1));
        assertEquals(7, pixels.get(2));
        assertEquals(8, pixels.get(3));
    }
}

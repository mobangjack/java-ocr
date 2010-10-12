package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ImageFilter;
import net.sourceforge.javaocr.filter.AbstractSinglePixelFilter;

import java.util.ArrayList;

/**
 * test capabilities of abstract linear image - correct traversal order
 *
 * @author Konstantin Pribluda
 */
public class LinearImageTest extends TestCase {

    private ArrayList pixels;
    private ImageFilter filter;
    AbstractLinearImage image;

    public void setUp() {

        pixels = new ArrayList();

        filter = new AbstractSinglePixelFilter() {

            public int processPixel(int pixel) {
                pixels.add(pixel);
                return 0;
            }
        };
    }

    /**
     * test order of pixel processing
     */
    public void testFullImageScanning() {
        image = new AbstractLinearImage(3, 3) {
            @Override
            protected void processCurrent() {
                pixels.add(currentIndex);
            }

            @Override
            protected int getCurrent() {
                return 0;
            }
        };
        image.filter(filter);

        assertEquals(9, pixels.size());
        for (int i = 0; i < 9; i++) {
            assertEquals(i, pixels.get(i));
        }
    }

    /**
     * 1x1 inside, no border contact
     */
    public void testSubimageInside() {
        image = new AbstractLinearImage(3, 3, 1, 1, 1, 1) {
            @Override
            protected void processCurrent() {
                pixels.add(currentIndex);
            }

            @Override
            protected int getCurrent() {
                return 0;
            }
        };
        image.filter(filter);
        assertEquals(1, pixels.size());
        assertEquals(4, pixels.get(0));
    }

    /**
     * contact with left and top
     */
    public void testTopLeft() {
        image = new AbstractLinearImage(3, 3, 0, 0, 2, 2) {
            @Override
            protected void processCurrent() {
                pixels.add(currentIndex);
            }

            @Override
            protected int getCurrent() {
                return 0;
            }
        };

        image.filter(filter);


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
        image = new AbstractLinearImage(3, 3, 1, 1, 2, 2) {
            @Override
            protected void processCurrent() {
                pixels.add(currentIndex);
            }

            @Override
            protected int getCurrent() {
                return 0;
            }
        };

        image.filter(filter);


        assertEquals(4, pixels.size());

        assertEquals(4, pixels.get(0));
        assertEquals(5, pixels.get(1));
        assertEquals(7, pixels.get(2));
        assertEquals(8, pixels.get(3));
    }
}

package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;

/**
 * test capabilities of abstract linear image - correct traversal order
 *
 * @author Konstantin Pribluda
 */
public class LinearImageTest extends TestCase {


    /**
     * aspect ratio setting shall be set  properly
     */
    public void testAspectRatioSetting() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3);
        assertEquals(4f / 3f, testLinearImage.getAspectRatio());
    }


    /**
     * test cartesian to linear conversion
     * coordinate is processed as: w * (originY+y) + originX + x
     */
    public void testPixelCoordinateProcessing() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3, 2, 1, 2, 2);

        assertEquals(4 * 1 + 2, testLinearImage.get(0, 0));
        assertEquals(4 * (1 + 2) + 2 + 2, testLinearImage.get(2, 2));
    }


    /**
     * assure proper functionality of H-Iterator
     * - step of 1
     * - proper beginning  (before next)
     * - proper termination
     */
    public void testHIteratorSetting() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3);
        testLinearImage.iterateH(1);

        // positioned before first pixel
        assertEquals(3, testLinearImage.get());
        // and one is available
        assertTrue(testLinearImage.hasNext());

        assertEquals(4, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());
        assertEquals(5, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());
        assertEquals(6, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());
        assertEquals(7, testLinearImage.next());
        assertFalse(testLinearImage.hasNext());
    }

    /**
     * assure proper working of H-Iterator
     */
    public void testVIteratorSetting() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3);
        testLinearImage.iterateV(1);
        // before first row
        assertEquals(-3, testLinearImage.get());
        // and available
        assertTrue(testLinearImage.hasNext());

        assertEquals(1, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());

        assertEquals(5, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());

        assertEquals(9, testLinearImage.next());
        assertFalse(testLinearImage.hasNext());
    }


    public void testSinglePixelIteratorH() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3);
        testLinearImage.iterateH(1, 1, 1);
        // before first
        assertEquals(4, testLinearImage.get());
        assertTrue(testLinearImage.hasNext());

        assertEquals(5, testLinearImage.next());
        assertFalse(testLinearImage.hasNext());
    }

    public class TestLinearImage extends AbstractLinearImage {
        protected TestLinearImage(int width, int height) {
            super(width, height);
        }

        protected TestLinearImage(int width, int height, int originX, int originY, int boxW, int boxH) {
            super(width, height, originX, originY, boxW, boxH);
        }

        @Override
        public int get() {
            return currentIndex;
        }

        @Override
        public void put(int value) {

        }
    }
}

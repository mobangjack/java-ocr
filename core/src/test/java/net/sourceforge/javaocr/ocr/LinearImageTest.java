package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;

/**
 * test capabilities of abstract linear image - correct traversal order
 *
 * @author Konstantin Pribluda
 */
public class LinearImageTest extends TestCase {


    /**
     * aspect ratio setting shall be set  properly   and taken from box size
     */
    public void testAspectRatioSetting() {
        final TestLinearImage testLinearImage = new TestLinearImage(20, 30, 7, 8, 4, 3);
        assertEquals(4f / 3f, testLinearImage.getAspectRatio());
    }


    public void testWidthAndHeightAreTakenFromBox() {

        final TestLinearImage testLinearImage = new TestLinearImage(4, 3, 1, 1, 1, 2);
        assertEquals(1, testLinearImage.getWidth());
        assertEquals(2, testLinearImage.getHeight());
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
        final TestLinearImage testLinearImage = new TestLinearImage(20,30,0,0,4, 3);
        testLinearImage.iterateH(1);

        // positioned before first pixel (last ion prev row)
        assertEquals(19, testLinearImage.get());
        // and one is available
        assertTrue(testLinearImage.hasNext());

        assertEquals(20, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());
        assertEquals(21, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());
        assertEquals(22, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());
        assertEquals(23, testLinearImage.next());
        assertFalse(testLinearImage.hasNext());
    }

    /**
     * assure proper working of H-Iterator
     */
    public void testVIteratorSetting() {
        final TestLinearImage testLinearImage = new TestLinearImage(10,12,0,0,4, 3);
        testLinearImage.iterateV(1);
        // before first row
        assertEquals(-9, testLinearImage.get());
        // and available
        assertTrue(testLinearImage.hasNext());

        assertEquals(1, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());

        assertEquals(11, testLinearImage.next());
        assertTrue(testLinearImage.hasNext());

        assertEquals(21, testLinearImage.next());
        assertFalse(testLinearImage.hasNext());
    }

    /**
     * single pixel H-iterator shall work properly
     */
    public void testSinglePixelIteratorH() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3);
        testLinearImage.iterateH(1, 1, 1);
        // before first
        assertEquals(4, testLinearImage.get());
        assertTrue(testLinearImage.hasNext());

        assertEquals(5, testLinearImage.next());
        assertFalse(testLinearImage.hasNext());
    }

    /**
     * single pixel v-interation shall work properly
     */
    public void testSinglePixelIteratorV() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3);
        testLinearImage.iterateV(2, 2, 2);
        // before first
        assertEquals(6, testLinearImage.get());
        // and available
        assertTrue(testLinearImage.hasNext());
        // step
        assertEquals(10, testLinearImage.next());
        // nothing more
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

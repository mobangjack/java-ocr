package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;

/**
 * test capabilities of abstract linear image - correct traversal order
 *
 * @author Konstantin Pribluda
 */
public class LinearImageTest extends TestCase {


    /**
     * aspect ratio setting shall worok properly
     */
    public void testAspectRatioSetting() {
        final TestLinearImage testLinearImage = new TestLinearImage(4, 3);
        assertEquals(4f / 3f, testLinearImage.getAspectRatio());
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

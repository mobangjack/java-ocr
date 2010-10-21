package net.sourceforge.javaocr.filter;

import junit.framework.TestCase;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ocr.AbstractLinearImage;

/**
 * test proper conversion from grayscale to RGBA
 */
public class GrayscaleToRGBATestCase extends TestCase {


    public void testPixelConversion() {
        GrayscaleToRGBA filter = new GrayscaleToRGBA();
        TestLinearImage image = new TestLinearImage(0x15);
        filter.processPixel(image);

        assertEquals(0xff151515, image.get());

        image.put(0xff);
        filter.processPixel(image);
        assertEquals(0xffffffff, image.get());

        image.put(0x00);
        filter.processPixel(image);
        assertEquals(0xff000000, image.get());
    }


    class TestLinearImage extends AbstractLinearImage {
        int pixel;

        protected TestLinearImage(int value) {
            super(1, 1);
            pixel = value;
        }

        @Override
        public int get() {
            return pixel;
        }

        @Override
        public void put(int value) {
            pixel = value;
        }

        public Image chisel(int fromX, int fromY, int width, int height) {
            return null; 
        }
    }

}

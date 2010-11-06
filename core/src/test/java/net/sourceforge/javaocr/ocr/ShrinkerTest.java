package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageShrinker;

/**
 * assure proper function of image shrinkeer
 */
public class ShrinkerTest extends TestCase {

    public void testImageShrinking() {
        byte[] data = new byte[]{0, 0, 0, 0, 1, 0, 0, 0, 0};

        Image image = new ByteImage(data, 3, 3);
        ImageShrinker shrinker = new Shrinker(0);

        Image result = shrinker.shrink(image);

        assertEquals(1, result.getWidth());
        assertEquals(1, result.getHeight());
        assertEquals(1, result.get(0, 0));
        assertEquals(1, result.getOriginX());
        assertEquals(1, result.getOriginY());
    }
}

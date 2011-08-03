package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ocr.PixelImage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test capability and proper functions of median filter
 * phantom pixels outside image treated as empty
 */
public class MedianFilterTest {

    /**
     * shall write proper values into destination image
     */
    @Test
    public void testFunctionality() {
        int[] data = new int[]{
                0,0,0,0,0,
                0,1,2,3,0,
                0,1,2,3,0,
                0,1,2,3,0,
                0,0,0,0,0
        };

        Image sourceImage = new PixelImage(data, 5, 5);

        PixelImage destination = new PixelImage(5, 5);

        // results in 3x3 kernel
        MedianFilter filter = new MedianFilter(destination, 3);

        filter.process(sourceImage);

        int[] pixels = destination.pixels;

        // top row
        // top left  - 6/9
        assertEquals(0, pixels[6]);
        // 12/9 -> 1
        assertEquals(1, pixels[7]);
        // 10/9 -> 1
        assertEquals(1, pixels[8]);

        // middle row
        // 9/9
        assertEquals(1, pixels[11]);
        // 18/9 -> 2
        assertEquals(2, pixels[12]);
        // 15/9 -> 1
        assertEquals(1, pixels[13]);

        // bottom row
        // 6/9
        assertEquals(0, pixels[16]);
        // 12/9 -> 1
        assertEquals(1, pixels[17]);
        // 10/9 -> 1
        assertEquals(1, pixels[18]);
    }
}

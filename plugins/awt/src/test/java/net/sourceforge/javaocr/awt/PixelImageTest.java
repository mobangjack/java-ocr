package net.sourceforge.javaocr.awt;

import junit.framework.TestCase;

/**
 * test capabilities of pixel image
 */
public class PixelImageTest extends TestCase {


    /**
     * shall return
     */
    public void testEmptyRowComesOutAsEmpty() {

        int data[] = {0, 0, 0, 0, 0, 100};
        PixelImage image = new PixelImage(data, 6, 1);

        // nothing there
        assertTrue(image.emptyHorizontal(0, 0, 4));
        // one pixel on the right border
        assertFalse(image.emptyHorizontal(0, 0, 5));
        // single pixel shall be catched
        assertFalse(image.emptyHorizontal(0, 5, 5));
        // test right border

        data = new int[]{0, 0, 0, 100, 0, 0};
        image = new PixelImage(data, 6, 1);

        // left border shall be catched
        assertFalse(image.emptyHorizontal(0, 3, 5));
        // middle shall be catched
        assertFalse(image.emptyHorizontal(0, 2, 5));
        
    }

}

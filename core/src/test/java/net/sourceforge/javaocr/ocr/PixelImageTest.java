package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;


/**
 * test capabilities of pixel image
 */
public class PixelImageTest extends TestCase {


    /**
     * assure proper functionality of empty row checking.  for our test data
     * wee assume that black, non-empty is 0, everything else is  empty or white
     */
    public void testEmptyRowProcessing() {

        int data[] = {0, 1, 1, 1, 0, 1, 1, 1, 1};
        PixelImage image = new PixelImage(data, 9, 1);

        // just one pixel
        assertFalse(image.emptyHorizontal(0, 0, 0));
        // 2 pixel,  left border is filled
        assertFalse(image.emptyHorizontal(0, 0, 0));

        // 1 pixel, empty
        assertTrue(image.emptyHorizontal(0, 1, 1));

        // pixel span empty
        assertTrue(image.emptyHorizontal(0, 1, 3));

        // one pixel on the right border
        assertFalse(image.emptyHorizontal(0, 1, 4));

        // pixel in the middle
        assertFalse(image.emptyHorizontal(0, 3, 5));

        // empty span to the end
        assertTrue(image.emptyHorizontal(0, 5, 8));

    }


    /**
     * assure proper finctionality of empty column testing
     */
    public void testEmptyColumnProcessing() {
        int data[] = {0, 1, 1, 1, 0, 1, 1, 1, 1};
        PixelImage image = new PixelImage(data, 1, 9);

        // just one pixel
        assertFalse(image.emptyVertical(0, 0, 0));
        // 2 pixel,  left border is filled
        assertFalse(image.emptyVertical(0, 0, 0));

        // 1 pixel, empty
        assertTrue(image.emptyVertical(0, 1, 1));

        // pixel span empty
        assertTrue(image.emptyVertical(0, 1, 3));

        // one pixel on the right border
        assertFalse(image.emptyVertical(0, 1, 4));

        // pixel in the middle
        assertFalse(image.emptyVertical(0, 3, 5));

        // empty span to the end
        assertTrue(image.emptyVertical(0, 5, 8));
    }


    /**
     * pixel image shall take full area if not specified
     * explicitely
     */
    public void testBorderProcessing() {
        int data[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        PixelImage image = new PixelImage(data, 3, 3);

        assertEquals(3, image.getWidth());
        assertEquals(3, image.getHeight());
        assertEquals(8, image.getPixel(2, 2));
    }


    /**
     * box setting shall be honored.
     */
    public void testBoxSettingAreHonored() {
        int data[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        PixelImage image = new PixelImage(data, 3, 3, 1, 1, 1, 1);

        assertEquals(4,image.getPixel(0,0));
    }

}

package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;
import net.sourceforge.javaocr.Image;

/**
 * test capabilities of image slicing with vertical lines
 * since most features are tested by slicerHTest, we check only differing ones here
 *
 * @author Konstantin Pribluda
 */
public class SlicerVTest extends TestCase {
    /**
     * test image on end is recognised properly
     */
    public void testSubimageOnEnd() {
        byte[] data = new byte[]{0, 0, 1};
        Image image = new ByteImage(data, 3, 1);

        SlicerV slicer = new SlicerV(image, 0);

        slicer.slice(0);
        // must have image
        assertTrue(slicer.hasNext());


        Image slice = slicer.next();

        assertNotNull(slice);
        assertEquals(1, slice.getHeight());
        assertEquals(1, slice.getWidth());
        assertEquals(1, slice.get(0, 0));

        // no image anymore
        assertFalse(slicer.hasNext());
    }

}

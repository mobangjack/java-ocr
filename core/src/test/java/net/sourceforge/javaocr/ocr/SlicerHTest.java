package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;
import net.sourceforge.javaocr.Image;

/**
 * test capabilities of slicer
 *
 * @author Konstantin Pribluda
 */
public class SlicerHTest extends TestCase {

    /**
     * empty image shall produce no slices
     */
    public void testThatEmptyImageHasNothing() {
        byte[] data = new byte[]{0, 0, 0};
        Image image = new ByteImage(data, 1, 3);
        SlicerH slicer = new SlicerH(image, 0);

        slicer.slice(0);
        // must have no image
        assertFalse(slicer.hasNext());
    }

    /**
     * vertical iteration test
     */
    public void testIteration() {
        byte[] data = new byte[]{0, 0, 1};
        Image image = new ByteImage(data, 1, 3);

        SlicerH slicer = new SlicerH(image, 0);

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

    public void testThatImageOnStartIsRecognisedProperly() {
        byte[] data = new byte[]{1, 0, 0};
        Image image = new ByteImage(data, 1, 3);

        SlicerH slicer = new SlicerH(image, 0);

        slicer.slice(0);
        // must have image
        assertTrue(slicer.hasNext());

        Image slice = slicer.next();
        System.err.println("slice:" + slice);
        assertNotNull(slice);
        assertEquals(1, slice.getHeight());
        assertEquals(1, slice.getWidth());
        assertEquals(1, slice.get(0, 0));
        // no image anymore
        assertFalse(slicer.hasNext());
    }

    /**
     * gap below tolerance shall not produce another slice
     */
    public void testTolerance() {
        byte[] data = new byte[]{0, 0, 1};
    }
}

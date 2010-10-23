package net.sourceforge.javaocr.plugin.moment;

import junit.framework.TestCase;
import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ocr.AbstractLinearImage;

import java.util.ArrayList;


/**
 * test image traversal by abstract filter
 */
public class ImageTraversalTest extends TestCase {

    /**
     * shall traverse entire image defined over subregion
     */
    public void testImageTraversal() {
        final ArrayList pixels = new ArrayList();
        Image image = new AbstractLinearImage(5, 5, 2, 2, 3, 3) {
            @Override
            public int get() {
                System.err.println("retrieve");
                pixels.add(currentIndex);
                return 0;
            }

            @Override
            public void put(int value) {
                // no op here
            }

            public Image chisel(int fromX, int fromY, int width, int height) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        AbstractMomentFilter filter = new AbstractMomentFilter(0, 0) {
            @Override
            protected void computeIndividualMoment(int pixel, int currentX, int currentY) {
                // do nothing here as we harvest pixels in ALI
            }
        };

        filter.process(image);
        
        assertEquals(9, pixels.size());
        assertEquals(12, pixels.get(0));
        assertEquals(13, pixels.get(1));
        assertEquals(14, pixels.get(2));
        assertEquals(17, pixels.get(3));
        assertEquals(18, pixels.get(4));
        assertEquals(19, pixels.get(5));
        assertEquals(22, pixels.get(6));
        assertEquals(23, pixels.get(7));
        assertEquals(24, pixels.get(8));
    }
}

package net.sourceforge.javaocr.filter;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * assure proper functionlaity of histogramm filter
 */
public class HistogrammTest extends TestCase {
    int[] values = new int[]{0, 0, 1, 1, 2, 2, 255, 255, 254, 254, 253, 253};

    /**
     * histogramm values shall be collected properly
     */
    public void testHistogrammCollection() {
        PixelImage image = new PixelImage(values, 1, 12);

        HistogramFilter filter = new HistogramFilter();
        filter.process(image);

        assertEquals(12, filter.getTotalCount());
        assertEquals(2, filter.getHistogramm()[0]);
        assertEquals(2, filter.getHistogramm()[1]);
        assertEquals(2, filter.getHistogramm()[2]);
        for (int i = 3; i < 253; i++)
            assertEquals(0, filter.getHistogramm()[i]);
        assertEquals(2, filter.getHistogramm()[253]);
        assertEquals(2, filter.getHistogramm()[254]);
        assertEquals(2, filter.getHistogramm()[255]);

    }

    /**
     * shall perform folding properly
     *    - clamp values on border to 0
     *    - perform proper computations of values
     */
    public void testFolding() {
        int[] folder = new int[] { 1,0,1};
        PixelImage image = new PixelImage(values, 1, 12);
        HistogramFilter filter = new HistogramFilter();
        filter.process(image);


    }
}

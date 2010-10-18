package net.sourceforge.javaocr.filter;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * assure proper functionality of histogram filter
 */
public class HistogrammTest extends TestCase {
    int[] values = new int[]{0, 1, 1, 2, 2, 2, 253, 253, 253, 254, 254, 255};

    /**
     * histogramm values shall be collected properly
     */
    public void testHistogrammCollection() {
        PixelImage image = new PixelImage(values, 1, 12);

        HistogramFilter filter = new HistogramFilter();
        filter.process(image);

        assertEquals(12, filter.getTotalCount());
        assertEquals(1, filter.getHistogramm()[0]);
        assertEquals(2, filter.getHistogramm()[1]);
        assertEquals(3, filter.getHistogramm()[2]);
        for (int i = 3; i < 253; i++)
            assertEquals(0, filter.getHistogramm()[i]);
        assertEquals(3, filter.getHistogramm()[253]);
        assertEquals(2, filter.getHistogramm()[254]);
        assertEquals(1, filter.getHistogramm()[255]);

    }

    /**
     * shall perform folding properly
     * - clamp values on border to 0
     * - perform proper computations of values
     */
    public void testFolding() {
        int[] folder = new int[]{1, 1, 1};
        PixelImage image = new PixelImage(values, 1, 12);
        HistogramFilter filter = new HistogramFilter();
        filter.process(image);
        // fold it
        filter.fold(folder);

        //first one shall be 0   / clamped
        assertEquals(0, filter.getHistogramm()[0]);

        // (1 * 1 + 1 * 2 + 1 * 3)/3 -> 2
        assertEquals(2, filter.getHistogramm()[1]);
        // (1 * 2 + 1* 3+ 1 * 0)/3 -> 1
        assertEquals(1, filter.getHistogramm()[2]);
        // (1 * 3 + 1* 0+ 1 * 0)/3 -> 1
        assertEquals(1, filter.getHistogramm()[3]);
        for (int i = 4; i < 252; i++)
            assertEquals(0, filter.getHistogramm()[i]);

        // reverse
        assertEquals(1, filter.getHistogramm()[252]);
        assertEquals(1, filter.getHistogramm()[253]);
        assertEquals(2, filter.getHistogramm()[254]);
        assertEquals(0, filter.getHistogramm()[255]);
    }


    public void testEvenSizeProducesIAE() {
        int[] folder = new int[]{1, 1, 1, 1};
        HistogramFilter filter = new HistogramFilter();
        try {
            filter.fold(folder);
            fail("no exception thrown on even golding array size");
        } catch (IllegalArgumentException e) {
            // thar's ok, we await this one
        }
    }
}

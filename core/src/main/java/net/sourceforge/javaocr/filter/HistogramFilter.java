package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

import java.util.Arrays;

/**
 * collect image histogram and perform useful calculations
 *
 * @author Konstantin Pribluda
 */
public class HistogramFilter extends AbstractSinglePixelFilter {

    int[] histogramm = new int[256];
    int totalCount;

    public int[] getHistogramm() {
        return histogramm;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void reset() {
        Arrays.fill(histogramm, 0);
        totalCount = 0;
    }

    @Override
    protected void processPixel(Image image) {
        totalCount++;
        histogramm[image.next()]++;
    }
}

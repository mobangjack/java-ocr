package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

import java.util.Arrays;

/**
 * collect image histogram and perform useful calculations on it
 *
 * @author Konstantin Pribluda
 */
public class HistogramFilter extends AbstractSinglePixelFilter {
    int[] defaultSmooth = {1, 2, 1};
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

    /**
     * perform historgramm smoothing woth default coeefficitns { 1,2,1}
     */
    public void smooth() {
        fold(defaultSmooth);
    }

    /**
     * perform folding (smoothing, sharpening... depending on coeff array).
     * TODO:  what do we do with border conditions?  zero them? let in peace? assume folding coefficients outside border to be 0?
     *
     * @param folder array contaning folding coefficients. must have odd length
     */
    public void fold(int[] folder) throws IllegalArgumentException {
        if (folder.length % 2 == 0)
            throw new IllegalArgumentException("folding array must have odd length");
        int[] result = new int[256];
        final int start = folder.length / 2;
        final int end = histogramm.length - start;
        for (int i = start; i < end; i++) {
            int sum = 0;
            for (int j = 0; j < folder.length; j++) {
                 sum += histogramm[i - start + j] * folder[j];
            }
            result[i] = sum / folder.length;
        }
        histogramm = result;
    }
}

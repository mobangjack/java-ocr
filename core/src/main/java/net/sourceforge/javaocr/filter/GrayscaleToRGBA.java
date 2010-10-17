package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * transform pixel from grayscale to RGBA  (well,  actually ARGB)
 */
public class GrayscaleToRGBA extends AbstractSinglePixelFilter {

    final int cR;
    final int cG;
    final int cB;

    /**
     * construct with default coefficients (306,601,117)
     */
    public GrayscaleToRGBA() {
        this(306, 601, 117);
    }

    /**
     * construct with weight coefficients. multiplication results are divided by 1024
     *
     * @param cR
     * @param cG
     * @param cB
     */
    public GrayscaleToRGBA(int cR, int cG, int cB) {
        this.cB = cB;
        this.cG = cG;
        this.cR = cR;
    }

    @Override
    protected void processPixel(Image iterator) {
        final int pixel = iterator.next();
        iterator.put((pixel * cB >> 10) & 0xff | (((pixel * cG >> 10) & 0xff) << 8) | (((pixel * cR >> 10) & 0xff) << 16) | 0xff000000);
    }
}

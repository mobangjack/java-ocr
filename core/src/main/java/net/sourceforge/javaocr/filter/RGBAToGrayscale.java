package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * converts RGBA image to grayscale
 *
 * @author Konstantin Pribluda
 */
public class RGBAToGrayscale extends AbstractSinglePixelFilter {

    final int cR;
    final int cG;
    final int cB;

    /**
     * construct with default coefficients (306,601,117)
     */
    public RGBAToGrayscale() {
        this(306, 601, 117);
    }

    /**
     * construct with weight coefficients. multiplication results are divided by 1024
     *
     * @param cR
     * @param cG
     * @param cB
     */
    public RGBAToGrayscale(int cR, int cG, int cB) {
        this.cB = cB;
        this.cG = cG;
        this.cR = cR;
    }

    /**
     * convert RGBA to grayscale
     *
     * @param image to be processed
     */
    @Override
    protected void processPixel(Image image) {
        final int pixel = image.next();
        final int r = (pixel >> 16) & 0xff;
        final int g = (pixel >> 8) & 0xff;
        final int b = pixel & 0xff;
        int Y = ((r * cR) + (g * cG) + (b * cB)) >> 10;
        if (Y < 0) {
            Y = 0;
        } else if (Y > 255) {
            Y = 255;
        }
        image.put(Y);
    }
}

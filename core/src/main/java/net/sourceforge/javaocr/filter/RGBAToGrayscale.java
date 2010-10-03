package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.ImageFilter;

/**
 * converts RGBA image to grayscale
 *
 * @author Konstantin Pribluda
 */
public class RGBAToGrayscale extends AbstractSinglePixelFilter {

    /**
     * convert RGBA to grayscale
     *
     * @param pixel
     * @return
     */
    @Override
    protected int convert(int pixel) {
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;
        int Y = ((r * 306) + (g * 601) + (b * 117)) >> 10;
        if (Y < 0) {
            Y = 0;
        } else if (Y > 255) {
            Y = 255;
        }
        return Y;
    }
}

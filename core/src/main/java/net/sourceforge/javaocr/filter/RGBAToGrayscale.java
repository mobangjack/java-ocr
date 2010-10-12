package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.PixelIterator;

/**
 * converts RGBA image to grayscale
 *
 * @author Konstantin Pribluda
 */
public class RGBAToGrayscale extends AbstractSinglePixelFilter {


    /**
     * convert RGBA to grayscale
     * @param iterator
     */
    @Override
    protected void processPixel(PixelIterator iterator) {
        final int pixel = iterator.next();
        final int r = (pixel >> 16) & 0xff;
        final int g = (pixel >> 8) & 0xff;
        final int b = pixel & 0xff;
        int Y = ((r * 306) + (g * 601) + (b * 117)) >> 10;
        if (Y < 0) {
            Y = 0;
        } else if (Y > 255) {
            Y = 255;
        }
        iterator.put(Y);
    }
}

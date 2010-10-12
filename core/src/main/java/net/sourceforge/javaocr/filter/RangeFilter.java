package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.PixelIterator;

/**
 * gather min and max values from supplied pixels
 *
 * @author Konstantin Pribluda
 */
public class RangeFilter extends AbstractSinglePixelFilter {
    int min;
    int max;


    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    @Override
    protected void processPixel(PixelIterator iterator) {
        final int pixel = iterator.next();
        if (pixel < min) min = pixel;
        if (pixel > max) max = pixel;
    }
}

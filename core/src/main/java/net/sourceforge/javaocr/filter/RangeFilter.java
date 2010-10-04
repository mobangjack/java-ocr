package net.sourceforge.javaocr.filter;

/**
 * gather min and max values from supplied pixels
 *
 * @author Konstantin Pribluda
 */
public class RangeFilter extends AbstractSinglePixelFilter {
    int min;
    int max;

    protected int convert(int pixel) {
        if (pixel < min) min = pixel;
        if (pixel > max) max = pixel;
        return pixel;
    }


    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}

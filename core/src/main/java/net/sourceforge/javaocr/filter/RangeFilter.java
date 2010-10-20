package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * gather min and max values from supplied pixels
 *
 * @author Konstantin Pribluda
 */
public class RangeFilter extends AbstractSinglePixelFilter {
    int min = 255;
    int max = 0;

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    @Override
    protected void processPixel(Image image) {
        final int pixel = image.next();        
        if (pixel < min) min = pixel;
        if (pixel > max) max = pixel;      
    }
}

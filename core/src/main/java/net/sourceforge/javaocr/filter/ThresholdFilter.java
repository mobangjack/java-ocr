package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * perform threshold filtering
 * TODO: do we need to make this via threshold? 
 * @author Konstantin Pribluda
 */
public class ThresholdFilter extends AbstractSinglePixelFilter {
    int threshold;

    public ThresholdFilter(int threshold) {
        this.threshold = threshold;
    }

    @Override
    protected void processPixel(Image image) {
        image.put(image.next() > threshold ? 255 : 0);
    }
}

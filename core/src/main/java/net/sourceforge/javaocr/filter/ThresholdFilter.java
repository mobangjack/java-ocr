package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.PixelIterator;

/**
 * perform thershold filtering
 *
 * @author Konstantin Pribluda
 */
public class ThresholdFilter extends AbstractSinglePixelFilter {
    int threshold;

    public ThresholdFilter(int threshold) {
        this.threshold = threshold;
    }

    @Override
    protected void processPixel(PixelIterator iterator) {
        iterator.put(iterator.next() > threshold ? 255 : 0);
    }
}

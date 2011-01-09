package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * perform threshold filtering
 * TODO: do we need to make this via threshold? Yep - 'if' is still faster than lookup in array
 *
 * @author Konstantin Pribluda
 */
public class ThresholdFilter extends AbstractSinglePixelFilter {
    int threshold;
    private int above;
    private int below;

    /**
     * construct with threshold and default values for above / below ( 255 / 0 )
     *
     * @param threshold threshold value
     */
    public ThresholdFilter(int threshold) {
        this(threshold, 255, 0);
    }

    /**
     * construct with specified values for above and below
     *
     * @param threshold threshold value
     * @param above     value to use for values strictly over the threshold
     * @param below     value to substitute for values below the threshold
     */
    public ThresholdFilter(final int threshold, final int above, final int below) {
        this.threshold = threshold;
        this.above = above;
        this.below = below;
    }

    @Override
    protected void processPixel(Image image) {
        image.put(image.next() > threshold ? above : below);
    }
}

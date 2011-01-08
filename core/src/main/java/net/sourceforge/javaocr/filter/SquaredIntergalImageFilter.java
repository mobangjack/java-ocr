package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * like integral image filter, but sum up squares (useful in various calculations)
 *
 * @author Konstantin Pribluda
 */
public class SquaredIntergalImageFilter extends AbstractIntegralImageFilter {

    public SquaredIntergalImageFilter(Image resultImage) {
        super(resultImage);
    }

    @Override
    protected int processPixel(int i) {
        return i * i;
    }
}

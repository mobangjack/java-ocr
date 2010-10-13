package net.sourceforge.javaocr.filter;

/**
 * normalise grayscale pixels linear basing on min/max values
 *
 * @author Konstantin Pribluda
 */
public class NormaliseGrayscaleFilter extends AbstractSinglePixelFilter {
    int min;
    int max;
    int range;

    /**
     * @param max max pixel value in image
     * @param min min pixel value in image
     */
    public NormaliseGrayscaleFilter(int max, int min) {
        this.max = max;
        this.min = min;
        range = max - min;
    }


    @Override
    protected void processPixel(PixelIterator iterator) {
        iterator.put(Math.min(255, Math.max(0, ((iterator.next() - min) * 255) / range)));
    }
}

package net.sourceforge.javaocr.filter;

/**
 * abstract base class for filters operating on single pixels
 */
public abstract class AbstractSinglePixelFilter extends AbstractBaseFilter {

    public void process(int[] data, int width, int height, int originX, int originY, int boxW, int boxH) {
        final int scanWidth = originX + boxW;
        for (int i = originX; i < scanWidth; i++) {
            data[i] = convert(data[i]);
        }
    }

    protected abstract int convert(int pixel);
}

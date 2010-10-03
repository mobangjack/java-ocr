package net.sourceforge.javaocr.filter;

/**
 * abstract base class for filters operating on single pixels
 */
public abstract class AbstractSinglePixelFilter extends AbstractBaseFilter {

    public void process(int[] data, int width, int height, int originX, int originY, int boxW, int boxH) {

        final int maxRow = originY + boxH;
        for (int j = originY; j < maxRow; j++) {
            final int scanStart = originX + j * width;
            final int scanEnd = scanStart + boxW;
            for (int i = scanStart; i < scanEnd; i++) {
                data[i] = convert(data[i]);
            }
        }
    }

    protected abstract int convert(int pixel);
}

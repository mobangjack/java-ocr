package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * applies median filter to image (pixel is Mx from  his surrounding window)
 * result is collected in destination image.  Pixels outside source image are treated as non
 * existing. Border pixels are considered invalid, and their treatment is upon user
 */
public class MedianFilter implements ImageFilter {
    final protected Image destination;
    final protected int halfWindow;
    final protected int squareWindow;

    private final PixelImage meanImage;
    protected final IntegralImageFilter integralImageFilter;

    /**
     * create median filter
     *
     * @param destination overscan image where result is collected.
     * @param window      window size, in case of even value will be treated  as one less
     */
    public MedianFilter(Image destination, int window) {
        this.destination = destination;
        // we need those for all the computations, so precompute them
        this.halfWindow = window / 2;
        this.squareWindow = (halfWindow * 2 + 1) * (halfWindow * 2 + 1);

        meanImage = new PixelImage(destination.getWidth(), destination.getHeight());

        integralImageFilter = new IntegralImageFilter(meanImage);
    }


    public void process(Image image) {

        // calculate means
        integralImageFilter.process(image);

        final int height = image.getHeight();
        final int width = image.getWidth();

        // since processing result on image borders is  invalid,
        // we just ignore them for sake of performance.  caller shall take care about proper padding
        final int maxY = height - halfWindow;
        final int maxX = width - halfWindow;
        for (int y = halfWindow; y < maxY; y++) {
            for (int x = halfWindow; x < maxX; x++) {
                destination.put(x, y, computePixel(image, y, x));
            }
        }
    }

    /**
     * compute median pixel value
     */
    protected int computePixel(Image image, int y, int x) {

        int sum = integralImageFilter.windowValue(x - halfWindow, y - halfWindow, x + halfWindow, y + halfWindow);
        return sum / squareWindow;
    }

    public int getHalfWindow() {
        return halfWindow;
    }

    public int getSquareWindow() {
        return squareWindow;
    }

    public IntegralImageFilter getIntegralImageFilter() {
        return integralImageFilter;
    }
}

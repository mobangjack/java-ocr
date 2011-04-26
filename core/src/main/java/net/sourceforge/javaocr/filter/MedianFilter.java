package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * applies median filter to image ( pixel is Mx from  his window)
 */
public class MedianFilter implements ImageFilter {
    final Image destination;
    final int halfWindow;
    final int squareWindow;
    protected PixelImage augmentedMeanImage;
    private Image meanImage;
    protected IntegralImageFilter integralImageFilter;

    /**
     * create median filter
     *
     * @param destination
     * @param window
     */
    public MedianFilter(Image destination, int window) {
        this.destination = destination;
        // we need those for all the computations, so precompute them
        this.halfWindow = window / 2;
        this.squareWindow = halfWindow * halfWindow * 4;
        // augmented images have empty borders for kernel processing
        augmentedMeanImage = new PixelImage(destination.getWidth() + window, destination.getHeight() + window);
        meanImage = augmentedMeanImage.chisel(halfWindow, halfWindow, destination.getWidth(), destination.getHeight());

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
        return (meanImage.get(x - halfWindow, y - halfWindow) + meanImage.get(x + halfWindow, y + halfWindow) -
                meanImage.get(x + halfWindow, y - halfWindow) - meanImage.get(x - halfWindow, y + halfWindow)) / squareWindow;
    }
}

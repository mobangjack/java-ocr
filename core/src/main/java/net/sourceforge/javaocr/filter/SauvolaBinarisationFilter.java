package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * Performs local image threshloding with Sauvola algorythm.
 * allocates integral images to achieve O(N) performance instead  ow O(N2W2).
 * This filter allocates working images and can  be reused with destination image of same size.
 * Please note,  that  window/2 pixels on borders of image are invalid. It is responsibility of caller
 * to provide suitable padding.
 * <p/>
 * Based on "Efficient Implementation of Local Adaptive Thresholding Techniques Using Integral Images" by
 * Faisal Shafait , Daniel Keysers , Thomas M. Breuel from Image Understanding and Pattern Recognition (IUPR) Research Group
 * German Research Center for Artificial Intelligence (DFKI) GmbH
 * D-67663 Kaiserslautern, Germany
 * and  Thomas M. Breuel from Department of Computer Science, Technical University of Kaiserslautern
 * D-67663 Kaiserslautern, Germany
 *
 * @author Konstantin Pribluda
 */
public class SauvolaBinarisationFilter extends MedianFilter {


    final int above;
    final int below;
    final int range;
    final double weight;

    Image destination;
    private PixelImage augmentedSquaresImage;
    private SquaredIntergalImageFilter squaredIntergalImageFilter;

    private Image squaresImage;

    /**
     * @param above       value to use for pixels above threshold
     * @param below       value to use for pixels below threshold
     * @param destination destination image
     * @param maxValue    maxValue of intensity values
     * @param weight      weight of variance term (determines relation of threshold to local mean)
     * @param window      computing Window size
     */
    public SauvolaBinarisationFilter(int above, int below, Image destination, int maxValue, double weight, int window) {
        super(destination, window);
        this.above = above;
        this.below = below;

        this.range = maxValue / 2;
        this.weight = weight;


        // augmented images have empty borders for kernel processing
        augmentedSquaresImage = new PixelImage(destination.getWidth() + window, destination.getHeight() + window);
        squaresImage = augmentedSquaresImage.chisel(halfWindow, halfWindow, destination.getWidth(), destination.getHeight());
        squaredIntergalImageFilter = new SquaredIntergalImageFilter(squaresImage);
    }

    /**
     * traversal will be done bt median filter, actual processing delegated
     * to derived method
     *
     * @param image
     */
    @Override
    public void process(Image image) {
        // compute squares here
        squaredIntergalImageFilter.process(image);

        super.process(image);
    }

    @Override
    protected int computePixel(Image image, int y, int x) {
        double mean = super.computePixel(image, y, x);

        double meanSquaresSum = (squaresImage.get(x - halfWindow, y - halfWindow) + squaresImage.get(x + halfWindow, y + halfWindow) -
                squaresImage.get(x + halfWindow, y - halfWindow) - squaresImage.get(x - halfWindow, y + halfWindow)) / squareWindow;
        // this is our supercool local variance
        double variance = meanSquaresSum - mean * mean;

        double thr = mean * (1 + weight * (Math.sqrt(variance) / range - 1));

        if (image.get(x, y) > thr) {
            return above;
        } else {
            return below;
        }
    }

    public int getAbove() {
        return above;
    }

    public int getBelow() {
        return below;
    }

    public int getHalfWindow() {
        return halfWindow;
    }
}

package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * Performs local image threshloding with Sauvola algorythm.
 * allocates integral images to achieve O(N) performance instead  ow O(N2W2).
 * This filter allocates working images and shall be reused with destination image of same size
 * <p/>
 * Based on "Efficient Implementation of Local Adaptive Thresholding Techniques Using Integral Images" by
 * Faisal Shafaita , Daniel Keysersa , Thomas M. Breuel from Image Understanding and Pattern Recognition (IUPR) Research Group
 * German Research Center for Artificial Intelligence (DFKI) GmbH
 * D-67663 Kaiserslautern, Germany
 * and  Thomas M. Breuel from Department of Computer Science, Technical University of Kaiserslautern
 * D-67663 Kaiserslautern, Germany
 *
 * @author Konstantin Pribluda
 */
public class SauvolaBinarisationFilter implements ImageFilter {

    final int halfWindow;
    final int above;
    final int below;
    final int range;
    final double weight;
    final int squareWindow;
    Image destination;
    private Image augmentedMeanImage;
    private PixelImage augmentedSquaresImage;
    private IntregralImageFilter integralImageFilter;
    private SquaredIntergalImageFilter squaredIntergalImageFilter;
    private Image meanImage;
    private Image squaresImage;

    /**
     * @param above       value to use for above pixels
     * @param below       value to use for below pixels
     * @param destination destination image
     * @param range       range of intensity values
     * @param weight      weight of variance term (determines relation of threshold to local mean)
     * @param window      computing Window size
     */
    public SauvolaBinarisationFilter(int above, int below, Image destination, int range, double weight, int window) {
        this.above = above;
        this.below = below;
        this.destination = destination;
        this.range = range / 2;
        this.weight = weight;
        // we neew those for all the computations, so precompute them
        this.halfWindow = window / 2;
        this.squareWindow = halfWindow * halfWindow * 4;

        // augmented images have empty borders for kernel processing
        augmentedMeanImage = new PixelImage(destination.getWidth() + window, destination.getHeight() + window);
        augmentedSquaresImage = new PixelImage(destination.getWidth() + window, destination.getHeight() + window);

        meanImage = augmentedMeanImage.chisel(halfWindow, halfWindow, destination.getWidth(), destination.getHeight());
        squaresImage = augmentedSquaresImage.chisel(halfWindow, halfWindow, destination.getWidth(), destination.getHeight());

        integralImageFilter = new IntregralImageFilter(meanImage);
        squaredIntergalImageFilter = new SquaredIntergalImageFilter(squaresImage);
    }


    public void process(Image image) {
        // calculate means
        integralImageFilter.process(image);
        // and squares
        squaredIntergalImageFilter.process(image);

        final int height = image.getHeight();
        final int width = image.getWidth();
        // since our process images are inside of bigger augmented images,
        // we do no care about borders
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double mean = (meanImage.get(x - halfWindow, y - halfWindow) + meanImage.get(x + halfWindow, y + halfWindow) -
                        meanImage.get(x + halfWindow, y - halfWindow) - meanImage.get(x - halfWindow, y + halfWindow)) / squareWindow;

                double meanSquaresSum = (squaresImage.get(x - halfWindow, y - halfWindow) + squaresImage.get(x + halfWindow, y + halfWindow) -
                        squaresImage.get(x + halfWindow, y - halfWindow) - squaresImage.get(x - halfWindow, y + halfWindow)) / squareWindow;
                // thisis our supercool local variance
                double variance = meanSquaresSum - mean * mean;

                double thr = mean * (1 + weight * (Math.sqrt(variance) / range + 1));

                if (image.get(x, y) > thr) {
                    destination.put(x, y, above);
                } else {
                    destination.put(x,y,below);
                }
            }
        }
    }
}

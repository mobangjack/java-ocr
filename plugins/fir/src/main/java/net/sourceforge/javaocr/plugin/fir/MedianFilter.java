package net.sourceforge.javaocr.plugin.fir;

import java.util.Arrays;

import net.sourceforge.javaocr.Image;

/**
 * Apply median filter to image, useful to clean up after thresholding.
 * TODO FIXME currently works only for grayscale images
 * @author Andrea De Pasquale
 */
public class MedianFilter extends AbstractNeighborhoodFilter {
	
	private int[] pixels;
	private int counter;

	/**
	 * Create a <code>MedianFilter</code>, which selects median value from 
	 * pixels inside a given neighborhood.
	 * 
	 * @param width Neighborhood width; if even, filter expands to the right only.
	 * @param height Neighborhood height; if even, filter expands to the bottom only.
	 * @param dest The image to be filled up during processing.
	 */
	public MedianFilter(int width, int height, Image dest) {
		super(width, height, dest);
		pixels = new int[filterW * filterH];
	}

	protected int processNeighborhood(Image nImage) {
		counter = 0;
		
		int nHeight = nImage.getHeight();
        for (int i = 0; i < nHeight; i++) {
            for (nImage.iterateH(i); nImage.hasNext();) {
            	pixels[counter++] = nImage.next();
            }
        }
		
        Arrays.sort(pixels, 0, counter);
        if (counter % 2 == 0) {
        	return (pixels[counter/2] + pixels[(counter/2)-1]) / 2;
        } else {
        	return pixels[(counter-1)/2];
        }
	}

}

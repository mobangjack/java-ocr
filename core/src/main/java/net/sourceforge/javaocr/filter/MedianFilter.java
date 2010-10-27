package net.sourceforge.javaocr.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.javaocr.Image;

/**
 * Apply median filter to image, useful to clean up after thresholding.
 * TODO FIXME currently works only for grayscale images
 * @author Andrea De Pasquale
 */
public class MedianFilter extends AbstractBaseFilter {
	Image filteredImage;
	private List<Integer> pixels;
	private int sizeT, sizeB, sizeL, sizeR;

	/**
	 * Create a <code>MedianFilter</code>, which selects median value from 
	 * pixels inside a given neighbourhood.
	 * 
	 * @param destination The image to be filled up during processing.
	 * @param width
	 *            Pixel neighbourhood width; if it's not odd, 
	 *            filter expands to the right only.
	 * @param height
	 *            Pixel neighbourhood height; if it's not odd, 
	 *            filter expands to the bottom only.
	 */
	public MedianFilter(Image destination, int width, int height) {
		filteredImage = destination;
		
		int filterW = (width  > 0 ? width  : 1);
		int filterH = (height > 0 ? height : 1);
		
		// int array to be sorted
		pixels = new ArrayList<Integer>(filterW * filterH);

		// how much we should expand relative to central pixel
		sizeL = (filterW - 1) / 2;
		sizeR = (filterW - 1) - sizeL;		
		sizeT = (filterH - 1) / 2;
		sizeB = (filterH - 1) - sizeT;
	}

	@Override
	public void process(Image image) {
		final int imageW = image.getWidth();
		final int imageH = image.getHeight();
		
		// for every pixel in the image
		for (int y = 0; y < imageH; ++y) {
			for (int x = 0; x < imageW; ++x) {
				
				pixels.clear();
				
				// get its neighbourhood
				for (int k = y-sizeT; k <= y+sizeB; ++k) {
					if (k < 0 || k >= imageH) continue;
					for (int j = x-sizeL; j <= x+sizeR; ++j) {
						if (j < 0 || j >= imageW) continue;
						
						// and put it into the list
						pixels.add(image.get(j, k));
						
					}
				}
				
				// finally sort the list
				Collections.sort(pixels);
				int median = pixels.size() / 2;
				filteredImage.put(x, y, pixels.get(median));

			}
		}
	}

}

package net.sourceforge.javaocr.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;

/**
 * Apply median filter to image, useful to clean up after thresholding.
 * FIXME currently works only for grayscale images
 * @author Andrea De Pasquale
 */
public class MedianFilter implements ImageFilter {
	private int fSize, fMedian;
	private List<Integer> filter;
	private int fTop, fBottom, fLeft, fRight;

	/**
	 * Create a <code>MedianFilter</code> which select median value from 
	 * pixel inside a given neighbourhood.
	 * 
	 * @param width
	 *            Pixel neighbourhood width; if it's not odd, 
	 *            filter expands to the right only.
	 * @param height
	 *            Pixel neighbourhood height; if it's not odd, 
	 *            filter expands to the bottom only.
	 */
	public MedianFilter(int width, int height) {
		int fWidth  = (width  > 0 ? width  : 1);
		int fHeight = (height > 0 ? height : 1);
		
		// int array to be sorted
		filter = new ArrayList<Integer>(fWidth * fHeight);

		// how much we should expand relative to central pixel
		fLeft   = (fWidth - 1) / 2;
		fRight  = (fWidth - 1) - fLeft;		
		fTop    = (fHeight - 1) / 2;
		fBottom = (fHeight - 1) - fTop;
	}

	@Override
	public void process(Image image) {
		// FIXME this is wrong!!!
		process(image, image);
	}
	
	public void process(Image input, Image output) {
		final int imageW = input.getWidth();
		final int imageH = input.getHeight();
		
		// for every pixel in the image
		for (int y = 0; y < imageH; ++y) {
			for (int x = 0; x < imageW; ++x) {
				
				filter.clear();
				
				// get its neighbourhood
				for (int k = y-fTop; k <= y+fBottom; ++k) {
					if (k < 0 || k >= imageH) continue;
					for (int j = x-fLeft; j <= x+fRight; ++j) {
						if (j < 0 || j >= imageW) continue;
						
						// and put it into the list
						filter.add(input.get(j, k));
						System.out.println("pixel (" + j + ", " + k + ") is "
								+ input.get(j, k));
						
					}
				}
				
				// finally sort the list
				Collections.sort(filter);
				int median = filter.size() / 2;
				output.put(x, y, filter.get(median));
				System.out.println("median for (" + x + ", " + y + ") is "
						+ filter.get(median) + " at " + median);
				
			}
		}
	}

}

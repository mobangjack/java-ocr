package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;

/**
 * Filter used to subtract a previously known background from an image. 
 * @author Andrea De Pasquale
 */
public class BackgroundSubtractionFilter implements ImageFilter {
	
	private Image bgImage;
	private int min, max;

	/**
	 * Create a new filter that subtracts a fixed background.
	 * @param background The image to be used during subtraction
	 */
	public BackgroundSubtractionFilter(Image background) {
		this(background, 0, 255);
	}
	
	/**
	 * Create a new filter that subtracts a fixed background, without filtering
	 * pixels less than minGray or more than maxGray
	 * @param background The image to be used during subtraction
	 * @param minGray Minimum pixel value to be processed
	 * @param maxGray Maximum pixel value to be processed
	 */
	public BackgroundSubtractionFilter(Image background, int minGray, int maxGray) {
		bgImage = background;
		min = minGray;
		max = maxGray;
	}
	
    public void process(Image image) {
        final int height = image.getHeight();
        
        for (int i = 0; i < height; i++) {
            for (image.iterateH(i), bgImage.iterateH(i); 
            	image.hasNext() && bgImage.hasNext();) {
            	
            	int pixel = Math.abs(image.next() - bgImage.next());
                if (image.get() > min && image.get() < max) image.put(pixel);
                
            }
        }
    }
}

package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.ImageFilter;

/**
 * base filter functionality
 */
public  abstract class AbstractBaseFilter implements ImageFilter {
    public void process(int[] data, int width, int height) {
        process(data, width, height, 0, 0, width, height);
    }
}

package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * abstract base class for filters operating on single pixels
 */
public abstract class AbstractSinglePixelFilter extends AbstractBaseFilter {
    public void process(Image image) {
        final int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (image.iterateH(i); image.hasNext();) {
                processPixel(image);
            }
        }
    }

    /**
     * process single image pixel subclass shall retrieve current pixel value with image.next()
     * and store it with image.put() after processing
     * @param image
     */
    protected abstract void processPixel(Image image);
}

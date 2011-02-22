package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * computes integral image.   result is stored in  the allocated image
 * to be retrieved.  Basically it is sum of all pixels from top-left part
 * of image relative to current pixel. this filter can work in place, but
 * intended usage tells us to create and cache destination image
 */
public class IntregralImageFilter extends AbstractIntegralImageFilter {

    /**
     * image to be used as result.  size shall match to processed
     * images.  responsibility lies on caller. you are also responsible for format
     * so that values do not overflow
     *
     * @param resultImage
     */
    public IntregralImageFilter(Image resultImage) {
        super(resultImage);
    }



    protected int processPixel(int pixel) {
        return pixel;
    }


}

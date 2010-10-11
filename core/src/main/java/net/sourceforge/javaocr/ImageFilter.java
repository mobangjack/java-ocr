package net.sourceforge.javaocr;

/**
 * performs transformation of image data. can and certainly will modify image data in place
 */
public interface ImageFilter {

    /**
     * process image data in place, use entire array as boxing area.
     * this  method will be called by image itself
     * @param data data array
     * @param width image width
     * @param height image height
     */
    public void process(final int[] data, final int width, final int height);

    /**
     * process image data in place, utilise box boundaries.
     * this  method will be called by image itself
     * @param data
     * @param width
     * @param height
     * @param originX
     * @param originY
     * @param boxW
     * @param boxH
     */
    public void process(final int[] data, final int width, final int height, final int originX, final int originY,final int boxW, final int boxH);

    /**
     * process single pixel
     * @param pixel
     * @return
     */
    int processPixel(int pixel);
}

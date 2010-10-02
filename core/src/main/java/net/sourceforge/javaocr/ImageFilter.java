package net.sourceforge.javaocr;

/**
 * performs transformation of image data
 */
public interface ImageFilter {

    /**
     * process image data in place, use entire array as boxing area
     * @param data data array
     * @param width image width
     * @param height image height
     */
    public void process(int[] data, int width, int height);

    /**
     * process image data in place, utilise box boundaries
     * @param data
     * @param width
     * @param height
     * @param originX
     * @param originY
     * @param boxW
     * @param boxH
     */
    public void process(int[] data, int width, int height, int originX, int originY, int boxW, int boxH);
}

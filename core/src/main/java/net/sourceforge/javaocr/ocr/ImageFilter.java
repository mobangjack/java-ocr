package net.sourceforge.javaocr.ocr;

/**
 * performs transformation of image data
 */
public interface ImageFilter {
    
    public void process(int[] data, int width, int height);

    public void process(int[] data, int width, int height, int originX, int originY, int boxW, int boxH);
}

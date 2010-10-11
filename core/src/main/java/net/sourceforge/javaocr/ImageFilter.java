package net.sourceforge.javaocr;

/**
 * performs transformation of image data. can and certainly will modify image data in place
 * TODO: ideas how to do FIR needed
 */
public interface ImageFilter {

    /**
     * process single pixel
     * @param pixel
     * @return
     */
    int processPixel(int pixel);
}

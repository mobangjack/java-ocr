package net.sourceforge.javaocr;

/**
 * performs transformation of image data. can and certainly will modify image data in place
 */
public interface ImageFilter {

    /**
     * perform image processing possibly overwriting the image with the result
     *
     * @param image
     */
    void process(Image image);

}

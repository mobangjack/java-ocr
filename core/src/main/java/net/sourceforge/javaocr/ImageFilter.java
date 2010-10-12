package net.sourceforge.javaocr;

/**
 * performs transformation of image data. can and certainly will modify image data in place
 */
public interface ImageFilter {

    /**
     * perform image processing
     *
     * @param image
     */
    void process(Image image);

}

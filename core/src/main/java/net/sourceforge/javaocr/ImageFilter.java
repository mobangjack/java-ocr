package net.sourceforge.javaocr;

/**
 * performs transformation of image data. can and certainly will modify image data in place
 */
public interface ImageFilter {

    /**
     * perform image processing overwriting the image with the result
     *
     * @param image
     */
    void process(Image image);
    
//    /**
//     * perform image processing from input to output
//     * 
//     * @param input source image
//     * @param output destination image
//     */
//    void process(Image input, Image output);

}

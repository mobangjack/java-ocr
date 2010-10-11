package net.sourceforge.javaocr.filter;

/**
 * transform pixel from grayscale to RGBA
 */
public class GrayscaleToRGBA  extends AbstractSinglePixelFilter {

    public int processPixel(int pixel) {
        return  pixel | (pixel << 8) | (pixel << 16) | 0xff000000;
    }
}

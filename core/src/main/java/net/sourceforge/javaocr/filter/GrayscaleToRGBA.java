package net.sourceforge.javaocr.filter;

/**
 * transform pixel from grayscale to RGBA
 */
public class GrayscaleToRGBA  extends AbstractSinglePixelFilter {


    @Override
    protected void processPixel(PixelIterator iterator) {
        final int pixel = iterator.next();
        iterator.put(pixel | (pixel << 8) | (pixel << 16) | 0xff000000);
    }
}

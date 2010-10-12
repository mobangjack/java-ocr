package net.sourceforge.javaocr;

/**
 * iterates over image in image specific way
 * 
 * @author Konstantin Pribluda
 */
public interface PixelIterator {
    /**
     * retrieve next available pixel
     * @return
     */
    int next();

    /**
     * whether next pixel is available
     * @return
     */
    boolean hasNext();

    /**
     * store value at current position in the image
     * @param pixel
     */
    void put(int pixel);
}

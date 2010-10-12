package net.sourceforge.javaocr;

/**
 * Interface encapsulating image functionality. Concrete implementations shall be provided by plugins
 *
 * @author Konstantin Pribluda
 */
public interface Image {


    /**
     * retrieve value of pixel in image specific fashion
     *
     * @param x
     * @param y
     * @return
     */
    int getPixel(int x, int y);

    void putPixel(int x, int y, int value);

    /**
     * whether horisontal line is empty between specified coordinates
     * in image specific fashion
     *
     * @param y     Y value
     * @param from  inclusive from
     * @param to    inclusive to
     * @param value
     * @return whether line is empty in image specific fashion
     * @deprecated use iterator instead
     */
    boolean horizontalSpanEquals(final int y, final int from, final int to, final int value);

    /**
     * whether vertical line is empty between specified points
     *
     * @param x     X Value
     * @param from  inclusive from
     * @param to    inclusive to
     * @param value
     * @return whether line is empty in image specific fashion
     * @deprecated use iterators instead
     */
    boolean verticalSpanEquals(final int x, final int from, final int to, final int value);


    int getWidth();

    int getHeight();

    /**
     * accept image filter. traversal is handled by image
     * filtering is always done over whole image.
     *
     * @param filter
     */
    void filter(ImageFilter filter);

    /**
     * convenience method to retrieve iterator over whole image row
     *
     * @return iterator
     */
    PixelIterator hIterator(int y);

    /**
     * retrieve iterator over part of row
     *
     * @param from from position
     * @param to   to position
     * @return iterator
     */
    PixelIterator hIterator(int y,int from, int to);

    /**
     * convenience method retrieve iterator over whole image column
     *
     * @return iterator
     */
    PixelIterator vIterator(int x);

    /**
     * retrieve iterator over part of image column
     *
     * @param from
     * @param to
     * @return
     */
    PixelIterator vIterator(int x,int from, int to);
}

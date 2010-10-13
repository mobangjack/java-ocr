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
     * convenience method to initialize iterator over whole image row
     */
    void iterateH(int y);

    /**
     * initialize iterator over part of row
     *
     * @param from from position
     * @param to   to position
     */
    void iterateH(int y, int from, int to);

    /**
     * convenience method to initialize iterator over whole image column
     */
    void iterateV(int x);

    /**
     * rinitialize iterator over part of column
     *
     * @param from
     * @param to
     */
    void iterateV(int x, int from, int to);

    /**
     * advance to next available pixel
     *
     * @return
     */
    void next();

    /**
     * whether next pixel is available
     *
     * @return
     */
    boolean hasNext();

}

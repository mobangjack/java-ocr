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
    int get(int x, int y);

    /**
     * store pixel at specified position
     *
     * @param x
     * @param y
     * @param value
     */
    void put(int x, int y, int value);

    /**
     * retrieve pixekl at current position
     *
     * @return
     */
    int get();

    /**
     * store pixel at current position
     *
     * @param value
     */
    void put(int value);

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
     * initialize iterator over part of column
     *
     * @param from
     * @param to
     */
    void iterateV(int x, int from, int to);

    /**
     * advance and retrieve next available pixel
     *
     * @return
     */
    int next();

    /**
     * whether next pixel is available
     *
     * @return
     */
    boolean hasNext();

    /**
     * copy image content to another image
     * TODO: do we have to check for sizes???
     *
     * @param dst
     */
    void copy(Image dst);

    /**
     * copy image swithcing axes in process
     *
     * @param dst
     */
    void flip(Image dst);
}

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

    /**
     * whether horisontal line is empty between specified coordinates
     * in image specific faship
     *
     * @param y    Y value
     * @param from inclusive from
     * @param to   inclusive to
     * @return whether line is empty in image specific fashion
     */
    boolean emptyHorizontal(int y, int from, int to);

    /**
     * whether vertical line is empty between specified points
     *
     * @param x    X Value
     * @param from inclusive from
     * @param to   inclusive to
     * @return whether line is empty in image specific fashion
     */
    boolean emptyVertical(int x, int from, int to);


    int getWidth();

    int getHeight();

    /**
     * accept image filter. traversal is handled by image
     * filtering is always done over whole image. 
     *
     * @param filter
     */
    void filter(ImageFilter filter); 
}

package net.sourceforge.javaocr.ocr;

/**
 * Inteface encapsulating image finctionality. Concrete implementations shall be provided by plugins
 *
 * @author Konstantin Pribluda
 */
public interface Image {
    /**
     * TODO: not sure what this means,  keep it for now
     *
     * @param x
     * @param y
     * @return
     */
    int getPixelIndex(int x, int y);

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
}

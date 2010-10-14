package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.Image;

/**
 * abstract base class encapsulating image functionality  for images repesented by linear arrays.
 * This class is stateful in regard to filtering, and thus not thread safe. Those optimisations aim
 * to reduce method invocations to be performant on davlik (android) where direct field access if more
 * performant than going through getter
 *
 * @author Konstantin Pribluda
 *         TODO: move scanning code for segmentation here!!!!
 */
public abstract class AbstractLinearImage implements Image {
    /**
     * Width of the image, in pixels.
     */
    public final int width;
    /**
     * Height of the image, in pixels.
     */
    protected final int height;
    protected final int originX;
    protected final int originY;
    protected final int boxW;
    protected final int boxH;
    /**
     * actual position being processed
     */
    protected int currentIndex;

    /**
     * Aspect ratio of the image (<code>width/height</code>).
     */
    public final float aspectRatio;


    // iteration  step
    int step;
    // iteration border
    int border;

    /**
     * construct image over while linear array with specified width and height
     *
     * @param width  image width
     * @param height image height
     */
    protected AbstractLinearImage(int width, int height) {
        this(width, height, 0, 0, width, height);

    }

    /**
     * construct image over subset of image in linear array
     *
     * @param width   full image width
     * @param height  full image height
     * @param originX X-origin of subimage
     * @param originY Y-origin of subimage
     * @param boxW    subimage width
     * @param boxH    subimage height
     */
    protected AbstractLinearImage(int width, int height, int originX, int originY, int boxW, int boxH) {
        this.boxH = boxH;
        this.boxW = boxW;
        this.height = height;
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        aspectRatio = ((float) width) / ((float) height);
    }


    /**
     * retrieve current  pixel value
     *
     * @return current pixel value
     */
    abstract public int get();

    /**
     * save pixel to current position
     *
     * @param value
     */
    abstract public void put(int value);


    /**
     * Get the value of a pixel at a specific <code>x,y</code> position.
     *
     * @param x The pixel's x position.
     * @param y The pixel's y position.
     * @return The value of the pixel.
     */
    public int get(int x, int y) {
        setCurrentIndex(x, y);
        return get();
    }


    protected void setCurrentIndex(int x, int y) {
        currentIndex = ((y + originY) * width) + x + originX;
    }

    /**
     * store pixel value
     *
     * @param x
     * @param y
     * @param value
     */
    public void put(int x, int y, int value) {
        setCurrentIndex(x, y);
        put(value);
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }


    /**
     * whether given span equals to specific value
     *
     * @param y     Y value
     * @param from  inclusive from
     * @param to    inclusive to
     * @param value value to be mathced
     * @return
     */
    public boolean horizontalSpanEquals(final int y, final int from, final int to, final int value) {
        for (currentIndex = y * width + from; currentIndex <= y * width + to; currentIndex++) {
            if (get() != value) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param x     X Value
     * @param from  inclusive from
     * @param to    inclusive to
     * @param value valye to be matched
     * @return
     */
    public boolean verticalSpanEquals(final int x, final int from, final int to, final int value) {
        for (currentIndex = from * width + x; currentIndex <= to * width + x; currentIndex += width) {
            if (get() != value) {
                return false;
            }
        }
        return true;
    }


    public void iterateV(int x, int from, int to) {
        currentIndex = (from + originY - 1) * width + x + originX;
        border = (to + originY) * width + x + originX;
        step = width;
    }

    public void iterateH(int y, int from, int to) {
        final int base = (y + originY) * width + originX - 1;
        currentIndex = base + from;
        border = base + to + 1;
        step = 1;
    }

    public void iterateH(int y) {
        iterateH(y, 0, width-1);
    }

    public void iterateV(int x) {
        iterateV(x, 0, height-1);
    }


    public boolean hasNext() {
        System.err.println("current: " + currentIndex + " border:" + border);
        return currentIndex < border;
    }

    public int next() {
        currentIndex += step;
        return get();
    }
}

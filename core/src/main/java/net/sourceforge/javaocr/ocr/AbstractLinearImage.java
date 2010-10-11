package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;

/**
 * abstract base class encapsulating image functionality  for images repesented by linear arrays.
 * This class is stateful in regard to filtering, and thus not thread safe. Those optimisations aim
 * to reduce method invocations to be performant on davlik (android) wheredirect field access if more
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
     * actual filter being processed
     */
    protected ImageFilter currentFilter;
    /**
     * Aspect ratio of the image (<code>width/height</code>).
     */
    public final float aspectRatio;

    protected AbstractLinearImage(int width, int height) {
        this(width, height, 0, 0, width, height);

    }

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
     * perform image filtering
     *
     * @param filter
     */
    public void filter(ImageFilter filter) {
        final int maxRow = originY + boxH;
        for (int j = originY; j < maxRow; j++) {
            final int scanStart = originX + j * width;
            final int scanEnd = scanStart + boxW;
            for (currentIndex = scanStart; currentIndex < scanEnd; currentIndex++) {
                // we aim to do this without explicit parameters
                processCurrent();
            }
        }
    }

    /**
     * process current pixel while filtering
     */
    abstract protected void processCurrent();

    /**
     * retrieve current  pixel value
     *
     * @return current pixel value
     */
    abstract protected int getCurrent();


    /**
     * Get the value of a pixel at a specific <code>x,y</code> position.
     *
     * @param x The pixel's x position.
     * @param y The pixel's y position.
     * @return The value of the pixel.
     */
    public int getPixel(int x, int y) {
        currentIndex = ((y + originY) * width) + x + originX;
        return getCurrent();
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
     * Get the index of a pixel at a specific <code>x,y</code> position.
     * TODO: remove me
     *
     * @param x The pixel's x position.
     * @param y The pixel's y position.
     * @return The pixel index (the index into the <code>pixels</code> array)
     *         of the pixel.
     * @deprecated this is unnecessary for outside  entities, as we are opaque
     */
    public final int getPixelIndex(int x, int y) {
        return (y * width) + x;
    }

    /**
     * whether given span is empty.  we thread 0 as black and filed
     *
     * @param y     Y value
     * @param from  inclusive from
     * @param to    inclusive to
     * @param value value to be mathced
     * @return
     */
    public boolean horizontalSpanEquals(final int y, final int from, final int to, final int value) {
        for (currentIndex = y * width + from; currentIndex <= y * width + to; currentIndex++) {
            if (getCurrent() != value) {
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
            if (getCurrent() != value) {
                return false;
            }
        }
        return true;
    }
}

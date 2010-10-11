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
    protected  final int originX;
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
    }


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
     * perform actual pixel processing
     *
     * @param filter
     */
    abstract protected void processCurrent();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

// PixelImage.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocr;


/**
 * contains pixel representation of an image
 *
 * @author Ronald B. Cemer
 * @author Konstantin Pribluda
 */
public class PixelImage implements net.sourceforge.javaocr.Image {
    // 10-tap, lowpass Finite Impulse Response (FIR) filter.

    protected static final float[] FILTER_FIR_COEFFS =
            {
                    0.05001757311983922f,
                    -0.06430830829693616f,
                    -0.0900316316157106f,
                    0.1500527193595177f,
                    0.45015815807855303f,
                    0.45015815807855303f,
                    0.1500527193595177f,
                    -0.0900316316157106f,
                    -0.06430830829693616f,
                    0.05001757311983922f,
            };
    /**
     * An array of pixels.  This can be in RGBA or grayscale.
     * By default, it is RGBA, but if the <code>toGrayScale()</code> method
     * has been called, each pixel will be in the range of 0-255 grayscale.
     */
    public final int[] pixels;
    /**
     * Width of the image, in pixels.
     */
    public final int width;
    /**
     * Height of the image, in pixels.
     */
    public final int height;

    public final int originX;
    public final int originY;
    public final int boxW;
    public final int boxH;
    /**
     * Aspect ratio of the image (<code>width/height</code>).
     */
    public final float aspectRatio;


    /**
     * create empty pixel image
     *
     * @param height
     * @param width
     */
    public PixelImage(int width, int height) {
        this(new int[width * height], width, height, 0, 0, width, height);
    }

    /**
     * Construct a new <code>PixelImage</code> object from an array of
     * pixels.
     *
     * @param pixels An array of pixels.
     * @param width  Width of the image, in pixels.
     * @param height Height of the image, in pixels.
     */
    public PixelImage(int[] pixels, int width, int height) {
        this(pixels, width, height, 0, 0, width, height);
    }

    /**
     * contruct image over the region of array
     *
     * @param data      array of pixel data
     * @param width     image width
     * @param height    image height
     * @param originX   image region origin x
     * @param originY   image region origin y
     * @param boxWidth  image region width
     * @param boxHeight image region height
     */
    public PixelImage(int[] data, int width, int height, int originX, int originY, int boxWidth, int boxHeight) {
        this.pixels = data;
        this.height = height;
        this.width = width;
        this.originX = originX;
        this.originY = originY;
        this.boxW = boxWidth;
        this.boxH = boxHeight;
        aspectRatio = ((float) width) / ((float) height);
    }


    public float getAspectRatio() {
        return aspectRatio;
    }

    public int getHeight() {
        return height;
    }

    public void filter(net.sourceforge.javaocr.ImageFilter filter) {
        filter.process(pixels, width, height);
    }

    public int getWidth() {
        return width;
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
     * Get the value of a pixel at a specific <code>x,y</code> position.
     *
     * @param x The pixel's x position.
     * @param y The pixel's y position.
     * @return The value of the pixel.
     */
    public final int getPixel(int x, int y) {
        return pixels[(y * width) + x];
    }

    /**
     * whether given span is empty.  we thread 0 as black and filed
     *
     * @param y    Y value
     * @param from inclusive from
     * @param to   exclusive to
     * @return
     */
    public boolean emptyHorizontal(int y, int from, int to) {
        for (int idx = y * width + from; idx <= y * width + to; idx++) {
            if (pixels[idx] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param x    X Value
     * @param from inclusive from
     * @param to   exclusive to
     * @return
     */
    public boolean emptyVertical(final int x, final int from, final int to) {
        for (int idx = from * width + x; idx <= to * from + x; idx += width) {
            if (pixels[idx] == 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * TODO:  factor out in filter class
     * @deprecated
     * @param pixels
     * @param width
     * @param height
     */
    public final void filter(int[] pixels, int width, int height) {
        float[] firSamples = new float[FILTER_FIR_COEFFS.length];
        float c;
        int lastPos = firSamples.length - 1;
        // Filter horizontally.
        for (int y = 0; y < height; y++) {
            for (int i = 0; i < firSamples.length; i++) {
                firSamples[i] = 255.0f;
            }
            int outX = -(firSamples.length / 2);
            for (int x = 0; x < width; x++, outX++) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = getPixel(x, y);
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                if (outX >= 0) {
                    pixels[getPixelIndex(outX, y)] = (int) c;
                }
            }
            while (outX < width) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = 255.0f;
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                pixels[getPixelIndex(outX, y)] = (int) c;
                outX++;
            }
        }
        // Filter vertically.
        for (int x = 0; x < width; x++) {
            for (int i = 0; i < firSamples.length; i++) {
                firSamples[i] = 255.0f;
            }
            int outY = -(firSamples.length / 2);
            for (int y = 0; y < height; y++, outY++) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = getPixel(x, y);
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                if (outY >= 0) {
                    pixels[getPixelIndex(x, outY)] = (int) c;
                }
            }
            while (outY < height) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = 255.0f;
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                pixels[getPixelIndex(x, outY)] = (int) c;
                outY++;
            }
        }
    }
}

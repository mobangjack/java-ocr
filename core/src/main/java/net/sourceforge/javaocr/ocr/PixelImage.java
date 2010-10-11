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
public class PixelImage extends AbstractLinearImage {
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

    public PixelImage(int[] pixels, int width, int height, int originX, int originY, int boxW, int boxH) {
        super(width, height, originX, originY, boxW, boxH);
        this.pixels = pixels;
    }

    /**
     * process pixel in place
     */
    @Override
    protected void processCurrent() {
        pixels[currentIndex] = currentFilter.processPixel(pixels[currentIndex]);
    }

    @Override
    protected int getCurrent() {
        return pixels[currentIndex];
    }

    /**
     * TODO:  factor out in filter class
     *
     * @param pixels
     * @param width
     * @param height
     * @deprecated
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

// PixelImage.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.Image;


/**
 * contains pixel representation of an image
 *
 * @author Ronald B. Cemer
 * @author Konstantin Pribluda
 */
public class PixelImage extends AbstractLinearImage {


    /**
     * An array of pixels.  we make no assumption about components here
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

    @Override

    public int get() {
        return pixels[currentIndex];
    }

    @Override
    public void put(int value) {
        pixels[currentIndex] = value;

    }

    public Image chisel(int fromX, int fromY, int width, int height) {
        return new PixelImage(pixels, arrayWidth, arrayHeight, originX + fromX, originY + fromY, width, height);
    }

    @Override
    public String toString() {
        return "PixelImage{} " + super.toString();
    }
}

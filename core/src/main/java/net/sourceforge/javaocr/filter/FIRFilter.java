package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.ImageFilter;

/**
 * apply FIR filter to image
 */
public class FIRFilter implements ImageFilter {
    float[] coeffs;

    /**
     * create FIR filter with coeffs
     * @param coeffs
     */
    public FIRFilter(float[] coeffs) {
        this.coeffs = coeffs;
    }

    public void process(int[] data, int width, int height) {
        process(data, width, height, 0, 0, width, height);
    }

    public void process(int[] data, int width, int height, int originX, int originY, int boxW, int boxH) {

    }
}

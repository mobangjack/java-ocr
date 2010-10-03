package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.ImageFilter;

/**
 * apply FIR filter to image
 */
public class FIRFilter extends AbstractBaseFilter{
    float[] coeffs;

    /**
     * create FIR filter with coeffs
     * @param coeffs
     */
    public FIRFilter(float[] coeffs) {
        this.coeffs = coeffs;
    }

    public void process(int[] data, int width, int height, int originX, int originY, int boxW, int boxH) {

    }
}

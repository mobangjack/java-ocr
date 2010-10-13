package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * apply FIR filter to image
 * TODO: implement me properly
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


    public void process(Image image) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

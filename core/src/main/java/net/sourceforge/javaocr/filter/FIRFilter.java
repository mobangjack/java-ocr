package net.sourceforge.javaocr.filter;

/**
 * apply FIR filter to image
 * TODO: implement me
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


    public int processPixel(int pixel) {
        return 0; 
    }
}

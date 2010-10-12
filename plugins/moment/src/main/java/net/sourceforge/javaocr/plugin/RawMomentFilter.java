package net.sourceforge.javaocr.plugin;

import net.sourceforge.javaocr.filter.AbstractBaseFilter;

/**
 * process image and compute raw image moment. does not modify the image.  this filter is stateful and not thread safe
 */
public class RawMomentFilter extends AbstractBaseFilter {
    int currentX;
    int currentY;

    int a;
    int b;

    float moment;


    /**
     * filter computing moment with given cardinality
     * @param a
     * @param b
     */
    public RawMomentFilter(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * compute moment from single pixel
     * @param pixel  value of pixel in question
     * @param currentX current x cooordinat inside scan
     * @param currentY  current y coordinate inside scan
     * @return
     */
    private void computeIndivudualMoment(int pixel, int currentX, int currentY) {
       moment +=  Math.pow(currentX, a) * Math.pow(currentY ,b) * pixel;
    }


}

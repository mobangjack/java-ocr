package net.sourceforge.javaocr.plugin.moment;

/**
 * process image and compute raw image moment. does not modify the image.  this filter is stateful and not thread safe
 */
public class RawMomentFilter extends AbstractMomentFilter {


    /**
     * filter computing moment with given cardinality
     * @param p
     * @param q
     */
    public RawMomentFilter(int p, int q) {
        super(p, q);
    }

    /**
     * compute moment from single pixel
     * @param pixel  value of pixel in question
     * @param currentX current x cooordinat inside scan
     * @param currentY  current y coordinate inside scan
     * @return
     */
    @Override
    protected void computeIndividualMoment(int pixel, int currentX, int currentY) {
       moment +=  Math.pow(currentX, p) * Math.pow(currentY , q) * pixel;
    }


}

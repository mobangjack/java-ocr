package net.sourceforge.javaocr.plugin.moment;

/**
 * computes central moments of image. see wikipedia or book of your choice about
 * details. needs M10 & M01 to be computed before using it
 *
 * TODO: add m00, and normalisation computation !!!!! (page45) 
 */
public class CentralMomentFilter extends AbstractMomentFilter {
    float xMean;
    float yMean;


    /**
     * @param p     order of x
     * @param q     order of y
     * @param xMean M10
     * @param yMean M01
     */
    public CentralMomentFilter(int p, int q, float xMean, float yMean) {
        super(p, q);
        this.xMean = xMean;
        this.yMean = yMean;
    }


    @Override
    protected void computeIndividualMoment(int pixel, int currentX, int currentY) {
        moment += Math.pow(currentX - xMean, p) * Math.pow(currentY - yMean, q) * pixel;
    }
}

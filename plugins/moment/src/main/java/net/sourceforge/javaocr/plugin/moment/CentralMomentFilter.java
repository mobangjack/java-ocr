package net.sourceforge.javaocr.plugin.moment;

/**
 * computes central moments of image. see wikipedia or book of your choice about
 * details. needs M10 & M01 to be computed before using it
 */
public class CentralMomentFilter extends AbstractMomentFilter {
    double xMean;
    double yMean;


    /**
     * @param p     order of x
     * @param q     order of y
     * @param xMean M10
     * @param yMean M01
     */
    public CentralMomentFilter(int p, int q, double xMean, double yMean) {
        super(p, q);
        this.xMean = xMean;
        this.yMean = yMean;
    }


    @Override
    protected void computeIndividualMoment(int pixel, int currentX, int currentY) {
        moment += Math.pow(currentX - xMean, p) * Math.pow(currentY - yMean, q) * pixel;
    }

    /**
     * normalise, not sure whether this has be done here...
     * @param m00
     * @return  normalised moment value
     */
    public double normalise(double m00) {
       return getMoment() / Math.pow(m00,(p + q)/2 + 1); 
    }
}

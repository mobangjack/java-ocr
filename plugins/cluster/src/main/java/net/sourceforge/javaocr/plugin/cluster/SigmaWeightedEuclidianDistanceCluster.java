package net.sourceforge.javaocr.plugin.cluster;

/**
 * normalise  dimenstion by sigma
 */
public class SigmaWeightedEuclidianDistanceCluster extends NormalDistributionCluster {
    /**
     * create cluster calculating euclidian distance between center and feature
     * vector
     *
     * @param c    assotiated character
     * @param size size of feature cluster
     */
    public SigmaWeightedEuclidianDistanceCluster(int dimensions) {
        super(dimensions);
    }

    /**
     * compute dimension normalised by sigma
     * @param dimension
     * @param i
     * @return
     */
    @Override
    double computeDimension(double dimension, int i) {
        return super.computeDimension(dimension, i) / Math.sqrt(getQuads()[i]);
    }
}

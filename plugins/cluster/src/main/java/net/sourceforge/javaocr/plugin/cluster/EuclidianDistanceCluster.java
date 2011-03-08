package net.sourceforge.javaocr.plugin.cluster;

/**
 * cluster calculating euclidian distance
 *
 * @author Konstantin Pribluda
 */
public class EuclidianDistanceCluster extends AbstractBaseCluster {
    public EuclidianDistanceCluster() {
    }

    /**
     * create cluster calculating euclidian distance between center and feature
     * vector
     *
     * @param c    assotiated character
     * @param size size of feature cluster
     */
    public EuclidianDistanceCluster(int dimensions) {
        super(dimensions);
    }

    public EuclidianDistanceCluster(double[] mx) {
        super(mx);
    }

    public double distance(double[] features) {
        double cumulated = 0;
        for (int i = 0; i < getDimensions(); i++) {
            cumulated += computeDimension(features[i], i);
        }
        return Math.sqrt(cumulated);
    }

    double computeDimension(double dimension, int i) {
        return Math.pow(center()[i] - dimension, 2);
    }
}

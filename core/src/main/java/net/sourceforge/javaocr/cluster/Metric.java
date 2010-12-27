package net.sourceforge.javaocr.cluster;

/**
 * metric computes "distance" between feature cluster extracted from candidate image
 * and data stored inside match.
 */
public interface Metric {

    /**
     * compute distance between metric and feature vectors. INdividual plugins will define concrete
     * metric implementation ( euclidian, 
     * @param features
     * @return
     */
    double distance(double[] features);
}

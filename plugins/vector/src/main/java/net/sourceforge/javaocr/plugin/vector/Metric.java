package net.sourceforge.javaocr.plugin.vector;

/**
 * metric computes "distance" between feature vector extracted from candidate image
 * and data stored inside match.
 */
public interface Metric {

    /**
     * compute distance between metric and feature vectors
     * @param match
     * @param features
     * @return
     */
    double distance(Match match, double[] features);
}

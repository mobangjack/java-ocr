package net.sourceforge.javaocr.plugin.cluster;

import net.sourceforge.javaocr.cluster.Metric;

import java.util.List;

/**
 * cluster represents some  feature vectors belonging together.
 * cluster can be trained with samples
 * <p/>
 *
 * @author Konstantin Pribluda
 */
public interface Cluster extends Metric {

    /**
     * centroid of cluster
     *
     * @return centroid of cluster
     */
    public double[] center();

    /**
     * train cluster with feature vector
     *
     * @param features
     */
    public void train(double[] features);


    /**
     * computes maximal distance of sample from center of cluster
     *
     * @param samples sample group, sample size shall correspond to cluster dimensions
     */
    public double radius(List<double[]> samples);

}

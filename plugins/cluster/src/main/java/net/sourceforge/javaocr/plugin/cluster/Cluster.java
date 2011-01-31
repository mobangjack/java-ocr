package net.sourceforge.javaocr.plugin.cluster;

import net.sourceforge.javaocr.cluster.Metric;

/**
 * cluster represents some  feature vectors belonging together to
 * image class. cluster can be trained with samples beloged to this class
 * <p/>
 * mx and var of cluster membvers can be used to measure quality of cluster match
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
     * mean distance of cluster members from
     * middle of the cluster
     *
     * @return
     */
    public double mx();

    /**
     * variance of cluster members distance
     *
     * @return
     */
    public double var();
}

package net.sourceforge.javaocr.plugin.cluster;

import net.sourceforge.javaocr.cluster.Metric;

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

  
}

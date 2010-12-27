package net.sourceforge.javaocr.plugin.cluster;

import net.sourceforge.javaocr.cluster.Metric;

/**
 * cluster represents some  feature vectors belonging together to
 * image class
 * @author Konstantin Pribluda
 */
public interface Cluster extends Metric {

    public void train(double[] features);
}

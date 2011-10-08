package net.sourceforge.javaocr.plugin.cluster;

import net.sourceforge.javaocr.matcher.MetricContainer;

/**
 * container class to serialize and deserialize cluster data to external storage.
 * Purpose of this class is to provide type information for deserialisation of JSON
 * @author Konsyantin Pribluda
 */
public class MahalanobisClusterContainer extends MetricContainer{

    public void setMetric(MahalanobisDistanceCluster metric) {
       super.setMetric(metric);
    }

}

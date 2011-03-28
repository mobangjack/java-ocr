package net.sourceforge.javaocr.plugin.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * base metric matcher.  returns ordered list of matches
 *
 * @author Konstantin Pribluda
 */
public class MetricMatcher implements Matcher {
    List<Cluster> clusters = new ArrayList();

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    /**
     * compute ordered list of clusters matching those features
     *
     * @param features
     * @return list of clusters ordered by distance
     */
    public List<Match> match(double[] features) {
        List<Match> sorted = new ArrayList();
        for (Cluster cluster : clusters) {
            sorted.add(new Match(cluster, cluster.distance(features)));
        }
        Collections.sort(sorted);
        return sorted;
    }
}

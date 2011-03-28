package net.sourceforge.javaocr.plugin.cluster;

import java.util.List;

/**
 * matches feature vector to clusters
 */
public interface Matcher {
    /**
     * return list of clusters sorted by distance. better matches come first
     *
     * @param features
     * @return
     */
    List<Match> match(double[] features);
}

package net.sourceforge.javaocr.plugin.cluster;

/**
 * matches feature vector to clusters
 */
public interface Matcher {    
     Cluster match(double[] features);
}

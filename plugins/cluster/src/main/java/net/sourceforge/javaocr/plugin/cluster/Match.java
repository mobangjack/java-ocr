package net.sourceforge.javaocr.plugin.cluster;

/**
 * container for cluster and distance - usefull to keep together cluster and distance,
 * and makes sorting easier
 *
 * @author Konstantin Pribluda
 */
public class Match implements Comparable<Match> {
    Cluster cluster;
    double distance;

    public Match() {
    }

    public Match(Cluster cluster, double distance) {
        this.cluster = cluster;
        this.distance = distance;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int compareTo(Match o) {
        return new Double(distance).compareTo(new Double(o.getDistance()));
    }
}

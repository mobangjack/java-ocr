package net.sourceforge.javaocr.plugin.cluster;

/**
 * container class to serialize and deserialize cluster data to external storage
 */
public class MahalanobisClusterContainer {

    MahalanobisDistanceCluster metric;
    Character character;
    double red;
    double yellow;

    public MahalanobisDistanceCluster getMetric() {
        return metric;
    }

    public void setMetric(MahalanobisDistanceCluster metric) {
        this.metric = metric;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getYellow() {
        return yellow;
    }

    public void setYellow(double yellow) {
        this.yellow = yellow;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}

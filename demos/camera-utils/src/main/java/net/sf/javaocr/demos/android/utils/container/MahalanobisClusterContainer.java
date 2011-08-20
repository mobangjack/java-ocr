package net.sf.javaocr.demos.android.utils.container;

import net.sourceforge.javaocr.plugin.cluster.MahalanobisDistanceCluster;

/**
 * container class to serialize and deserialize cluster data to external storage
 */
public class MahalanobisClusterContainer {

    MahalanobisDistanceCluster cluster;
    Character character;
    double red;
    double yellow;

    public MahalanobisDistanceCluster getCluster() {
        return cluster;
    }

    public void setCluster(MahalanobisDistanceCluster cluster) {
        this.cluster = cluster;
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

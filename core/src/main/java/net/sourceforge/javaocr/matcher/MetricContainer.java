package net.sourceforge.javaocr.matcher;

import net.sourceforge.javaocr.cluster.Metric;

/**
 * Created by IntelliJ IDEA.
 * User: ko5tik
 * Date: Sep 3, 2011
 * Time: 4:58:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetricContainer {

    Metric metric;
    Character character;
    double red;
    double yellow;


    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
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
}

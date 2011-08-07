package net.sourceforge.javaocr.matcher;

/**
 * conrainer to encapsulate mathichg result, distance and quality thresholds.
 *
 * @author Konstantin Pribluda
 */
public class Match implements Comparable<Match> {
    double distance;
    Character chr;
    double yellow;
    double red;


    public Match() {
    }

    public Match(Character chr, double distance, double yellow, double red) {
        this.chr = chr;
        this.distance = distance;
        this.red = red;
        this.yellow = yellow;
    }

    /**
     * mnatched character
     */
    public Character getChr() {
        return chr;
    }

    public void setChr(Character chr) {
        this.chr = chr;
    }

    /**
     * cgaracter distance provided by metric
     *
     * @return
     */
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * threshold marking invalid match
     *
     * @return
     */
    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    /**
     * threshold marking problematic (low quality) match
     *
     * @return
     */
    public double getYellow() {
        return yellow;
    }

    public void setYellow(double yellow) {
        this.yellow = yellow;
    }

    public int compareTo(Match o) {
        return new Double(distance).compareTo(new Double(o.getDistance()));
    }
}

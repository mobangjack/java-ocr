package net.sf.javaocr.demos.android;

import java.util.ArrayList;
import java.util.List;

/**
 * match for single character
 */
public class Match {
    char c;
    List<Double>[] momentSamples = new List[7];

    // mean value
    double[] mx = new double[7];
    // variance
    double[] var = new double[7];

    public Match(char c) {
        this.c = c;
        for (int i = 0; i < momentSamples.length; i++) {
            momentSamples[i] = new ArrayList<Double>();
        }
    }

    public char getC() {
        return c;
    }

    /**
     * perform sample image training - cumulate values and compute
     * moments
     *
     * @param samples
     */
    void train(double samples[]) {

        System.err.println("********** training matcher for:" + c);

        for (int i = 0; i < 7; i++) {
            momentSamples[i].add(samples[i]);

            mx[i] = rawMoment(1, momentSamples[i]);
            var[i] = centralMoment(2, mx[i], momentSamples[i]);
            System.err.println("*********** \t" + i + "\tmx:\t" + mx[i] + "\t var:\t" + var[i]);
        }

    }

    /**
     * compute raw moment out of  series
     *
     * @param order
     * @return
     */
    double rawMoment(int order, List<Double> series) {
        double cumulated = 0;
        for (double samlpe : series) {
            cumulated += Math.pow(samlpe, order);
        }

        return cumulated / series.size();
    }

    double centralMoment(int order, double m1, List<Double> series) {
        double cumulated = 0;
        for (double sample : series) {
            cumulated += Math.pow(sample - m1, order);
        }

        return cumulated / series.size();
    }

    /**
     * compute combined difference normalised by variance
     *
     * @param moments hu moments
     */
    public double lsd(double[] moments) {
        double cumulated = 0;
        for (double normalised : diffs(moments)) {
            cumulated += normalised;
        }
        return cumulated;
    }

    /**
     * calculate swquared diffs normalised by variance
     *
     * @return array of differences
     */
    public double[] diffs(double[] moments) {
        double[] retval = new double[momentSamples.length];
        StringBuilder sb = new StringBuilder("************* diffs for " + c + "\t");
        for (int i = 0; i < momentSamples.length; i++) {
            retval[i] = Math.pow(moments[i] - mx[i], 2) / var[i];
            sb.append(retval[i]).append("\t");
        }
        System.err.println(sb.toString());
        return retval;
    }
}

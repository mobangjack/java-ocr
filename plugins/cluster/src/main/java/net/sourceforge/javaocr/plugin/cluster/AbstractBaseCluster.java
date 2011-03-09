package net.sourceforge.javaocr.plugin.cluster;

import java.util.List;

/**
 * base cluster functionality. provides mx
 *
 * @author Konstantin Pribluda
 */
public abstract class AbstractBaseCluster implements Cluster {
    private double[] sum;
    private double[] mx;
    private int amountSamples;
    private int dimensions;

    public AbstractBaseCluster() {
    }

    protected AbstractBaseCluster(int dimensions) {
        this.dimensions = dimensions;
        sum = new double[dimensions];
    }

    protected AbstractBaseCluster(double[] mx) {
        this.mx = mx;
        this.dimensions = mx.length;
    }

    /**
     * lazily calculate and return expectation cluster
     *
     * @return expectation vector
     */
    public double[] center() {
        if (mx == null) {
            mx = new double[getDimensions()];
            for (int i = 0; i < getDimensions(); i++) {
                mx[i] = getAmountSamples() == 0 ? 0 : sum[i] / getAmountSamples();
            }
        }
        return mx;
    }

    public int getAmountSamples() {
        return amountSamples;
    }

    public double[] getMx() {
        return mx;
    }

    public void setMx(double[] mx) {
        this.mx = mx;
        if (mx != null) {
            setDimensions(mx.length);
        }
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }


    public int getDimensions() {
        return dimensions;
    }

    public void setAmountSamples(int amountSamples) {
        this.amountSamples = amountSamples;
    }

    /**
     * training means coputing sum of values. mx shall be reset and samples countet up
     *
     * @param features
     */
    public void train(double[] features) {

        amountSamples++;
        // reset mx and variance
        mx = null;
        for (int i = 0; i < getDimensions(); i++) {
            sum[i] += features[i];
        }
    }

    public double[] getSum() {
        return sum;
    }

    public void setSum(double[] sum) {
        this.sum = sum;
    }

    /**
     * calculate maximal distance for group from center
     * @param samples sample group, sample size shall correspond to cluster dimensions
     * @return
     */
    public double radius(List<double[]> samples) {
        double max = 0;
        for(double[] sample: samples) {
            final double dist = distance(sample);
            if(dist > max)
                max = dist;
        }
        return max;
    }
}

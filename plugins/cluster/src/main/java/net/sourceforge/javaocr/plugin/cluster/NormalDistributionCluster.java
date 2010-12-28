package net.sourceforge.javaocr.plugin.cluster;

/**
 * cluster woth normally distributed features. this abstract provides computation of
 * expectation and standart deviation without storing sample values 
 *
 * @author Konstantin Pribluda
 */
public abstract class NormalDistributionCluster implements Cluster {
    double[] sum;
    double[] quads;
    double[] mx;
    double[] var;
    int amountSamples;

    int dimensions;

    /**
     * constructs
     *
     * @param c    assotiated character
     * @param size size of feature cluster
     */
    public NormalDistributionCluster(int dimensions) {
        this.dimensions = dimensions;
        sum = new double[dimensions];
        quads = new double[dimensions];
    }


    /**
     * lazily calculate and return expectation cluster
     *
     * @return expectation cluster
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

    /**
     * lazily calculate and return variance cluster
     *
     * @return variance cluster
     */
    public double[] getVar() {
        if (var == null) {
            var = new double[getDimensions()];
            for (int i = 0; i < getDimensions(); i++) {
                var[i] = getAmountSamples() == 0 ? 0 : quads[i] - sum[i] * sum[i] / getAmountSamples();
            }
        }
        return var;
    }

    /**
     * perform sample image training - cumulate values and compute
     * moments
     *
     * @param samples
     */
    public void train(double samples[]) {

        amountSamples++;
        // reset mx and variance
        mx = null;
        var = null;

        for (int i = 0; i < getDimensions(); i++) {
            sum[i] += samples[i];
            quads[i] += samples[i] * samples[i];
        }
    }


    public double[] getQuads() {
        return quads;
    }

    public void setQuads(double[] quads) {
        this.quads = quads;
    }

    public double[] getSum() {
        return sum;
    }

    public void setSum(double[] sum) {
        this.sum = sum;
    }


    public int getAmountSamples() {
        return amountSamples;
    }

    public void setAmountSamples(int amountSamples) {
        this.amountSamples = amountSamples;
    }

    public int getDimensions() {
        return dimensions;
    }
}

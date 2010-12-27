package net.sourceforge.javaocr.plugin.cluster;

/**
 * defines cluster of features 
 * @author Konstantin Pribluda
 */
public class NormalDistributionCluster {
    char c;

    double[] sum;
    double[] quads;
    double[] mx;
    double[] var;
    int amountSamples;

    int size;

    /**
     * construct match object
     *
     * @param c    assotiated character
     * @param size size of feature cluster
     */
    public NormalDistributionCluster(char c, int size) {
        this.c = c;
        this.size = size;
        sum = new double[size];
        quads = new double[size];
    }


    /**
     * lazily calculate and return expectation cluster
     *
     * @return expectation cluster
     */
    public double[] getMx() {
        if (mx == null) {
            mx = new double[getSize()];
            for (int i = 0; i < size; i++) {
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
            var = new double[getSize()];
            for (int i = 0; i < getSize(); i++) {
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

        for (int i = 0; i < getSize(); i++) {
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

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}

package net.sourceforge.javaocr.plugin.vector;

/**
 * contains vector of image features expressed as double values for some character.
 * instance of match are trained by means of feature vectors.  feature extraction
 * and classification is out of scope of this class
 * training involves collection of statistic data. calculation of distribution
 * parameters is done in sliding way - samples are not kept
 *
 * @author Konstantin Pribluda
 */
public class Match {
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
     * @param size size of feature vector
     */
    public Match(char c, int size) {
        this.c = c;
        this.size = size;
        sum = new double[size];
        quads = new double[size];
    }


    /**
     * lazily calculate and return expectation vector
     *
     * @return expectation vector
     */
    public double[] getMx() {
        if (mx == null) {
            mx = new double[getSize()];
            for (int i = 0; i < size; i++) {
                mx[i] = sum[i] / getAmountSamples();
            }
        }
        return mx;
    }

    /**
     * lazily calculate and return variance vector
     *
     * @return variance vector
     */
    public double[] getVar() {
        if (var == null) {
            var = new double[getSize()];
            for (int i = 0; i < getSize(); i++) {
                var[i] = quads[i] - sum[i] * sum[i] / getAmountSamples();
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

package net.sourceforge.javaocr.plugin.cluster;

/**
 * cluster with normally distributed features. this abstract provides computation of
 * expectation and standart deviation without storing sample values
 *
 * @author Konstantin Pribluda
 */
public abstract class NormalDistributionCluster extends EuclidianDistanceCluster {
    double[] quads;
    double[] var;


    /**
     * default constructor for sake of serialisation frameworks
     */
    protected NormalDistributionCluster() {
    }

    /**
     * constructs
     *
     * @param dimensions amount of dimenstions
     */
    public NormalDistributionCluster(int dimensions) {
        super(dimensions);
        quads = new double[dimensions];
    }

    /**
     * convenience constructor to create already trained distribution cluster
     *
     * @param mx  precooked expectation values
     * @param var precooked variance
     */
    public NormalDistributionCluster(double[] mx, double[] var) {
        super(mx);
        this.var = var;
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
                var[i] = getAmountSamples() == 0 ? 0 : (quads[i] - getSum()[i] * getSum()[i] / getAmountSamples()) / getAmountSamples();
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
        super.train(samples);
        var = null;
        for (int i = 0; i < getDimensions(); i++) {
            quads[i] += samples[i] * samples[i];
        }
    }


    public double[] getQuads() {
        return quads;
    }

    public void setQuads(double[] quads) {
        this.quads = quads;
    }


    public void setVar(double[] var) {
        this.var = var;
    }

}

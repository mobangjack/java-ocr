package net.sourceforge.javaocr.plugin.cluster;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.DecompositionSolver;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.RealMatrix;

/**
 * cluster providing Mahalanobis distance meassure
 * ( do not ask me to pronouce this ) 
 * @author Konstantin Pribluda
 */
public class MahalanobisDistanceCluster extends NormalDistributionCluster {

    double[][] sumxy;
    double invcov[][];

    /**
     * constructs   mahalanobis distance cluster
     *
     * @param c    assotiated character
     * @param size size of feature cluster
     */
    public MahalanobisDistanceCluster(int dimensions) {
        super(dimensions);
        sumxy = new double[dimensions][dimensions];
    }

    /**
     * calculate mahalanubis distance
     *
     * @param features amoutn of fweatures shall correspond to amount dimenstions
     * @return
     */
    public double distance(double[] features) {
        // if we were invalidated,  recalculate matrix
        if(invcov == null) {
            invcov = matrix();
        }
        // calculate mahalanubis distance
        double cumulated = 0;
        for(int i = 0; i < getDimensions(); i++) {
            double xmxc = 0;
            for(int j = 0; j < getDimensions(); j++) {
                xmxc += invcov[j][i] * (features[j] - center()[j]);
            }
            cumulated += xmxc * (features[i] - center()[i]);
        }


        return Math.sqrt(cumulated);
    }

    /**
     * gather samples - sum of x*y into matrix
     *
     * @param samples samples belonging to cluster
     */
    @Override
    public void train(double[] samples) {
        super.train(samples);
        // invalidate cumulated covariance
        invcov = null;
        for (int i = 0; i < getDimensions(); i++)
            for (int j = 0; j < getDimensions(); j++) {
                sumxy[i][j] += samples[i] * samples[j];
            }
    }

    /**
     * calculate covariance matrix  and invert it
     * @return
     */
    double[][] matrix() {
        double cov[][] = new double[getDimensions()][getDimensions()];
       // System.out.println("covariance:");
       // StringBuilder var = new StringBuilder();
        for (int i = 0; i < getDimensions(); i++) {
           // var.append(getVar()[i]).append("\t");
          //  StringBuilder sb = new StringBuilder();
            for (int j = 0; j < getDimensions(); j++) {

                cov[i][j] += sumxy[i][j] / getAmountSamples() - center()[i] * center()[j];
             //   sb.append(cov[i][j]).append("\t");
            }
           // System.out.println(sb.toString());
        }
       // System.out.println("variance:");
       // System.out.println(var.toString());
        RealMatrix a = new Array2DRowRealMatrix(cov);
        DecompositionSolver solver = new LUDecompositionImpl(a,Double.MIN_VALUE).getSolver();

        final RealMatrix inverse = solver.getInverse();
       // System.out.println("inverse:" + inverse);
        return inverse.getData();
    }
}

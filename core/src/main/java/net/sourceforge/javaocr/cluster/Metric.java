package net.sourceforge.javaocr.cluster;

/**
 * metric computes "distance" between feature cluster extracted from candidate image
 * and data stored inside match.
 * @author Konstantin Pribluda
 */
public interface Metric {

    /**
     * compute distance between metric and feature vectors. Individual plugins will define concrete
     * metric implementation ( euclidian,  mahalonoubis, battacharya etc. )
     * @param features   amoutn of fweatures shall correspond to amount dimenstions
     * @return distance between features and vector
     */
    double distance(double[] features);

    /**
     * amount of dimensions of feature vectors
     * @return amount of dimensions
     */
    int getDimensions();

   
}

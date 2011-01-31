package net.sourceforge.javaocr.cluster;

/**
 * metric computes "distance" between feature cluster extracted from candidate image
 * and data stored inside match.
 */
public interface Metric {

    /**
     * compute distance between metric and feature vectors. Individual plugins will define concrete
     * metric implementation ( euclidian,  mahalonoubis, battacharyya etc. )
     * @param features   amoutn of fweatures shall correspond to amount dimenstions
     * @return
     */
    double distance(double[] features);

    /**
     * amount of dimensions of feature vectors
     * @return amount of dimensions
     */
    int getDimensions();

   
}

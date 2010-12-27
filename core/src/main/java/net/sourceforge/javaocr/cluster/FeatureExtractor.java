package net.sourceforge.javaocr.cluster;

import net.sourceforge.javaocr.Image;

/**
 * extract image features to be used in cluster analysis. concrete implementations will be
 * provided by plugins.
 */
public interface FeatureExtractor {
    /**
     * not sure whether we really need this,  as some extractors may provide variable
     * amount of features
     * TODO: do we really need this ?
     *
     * @return size of produced feature cluster
     */
    public int getSize();

    /**
     * extract image features
     *
     * @param image
     * @return
     */
    public double[] extract(Image image);

}

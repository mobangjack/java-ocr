package net.sourceforge.javaocr.plugin.cluster;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.cluster.FeatureExtractor;

/**
 * composite feature extractor - combines several extractors into one
 *
 * @author Konstantin Pribluda
 */
public class CompositeExtractor implements FeatureExtractor {

    int size;
    FeatureExtractor[] extractors;

    public CompositeExtractor(FeatureExtractor... extractors) {
        this.extractors = extractors;
        for (FeatureExtractor extractor : extractors)
            size += extractor.getSize();
    }

    public int getSize() {
        return size;
    }

    /**
     * delegate feature extraction from multiple backend extractors
     *
     * @param image image to process
     * @return combined feature vector
     */
    public double[] extract(Image image) {
        double[] features = new double[getSize()];
        int idx = 0;
        for (FeatureExtractor extractor : extractors)
            for (double feature : extractor.extract(image))
                features[idx++] = feature;
        return features;
    }
}

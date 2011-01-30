package net.sourceforge.javaocr.plugin.cluster.extractor;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.cluster.FeatureExtractor;

/**
 * extract aspect ratio out of image
 */
public class AspectRatioExtractor implements FeatureExtractor {
    public int getSize() {
        return 1;
    }

     public double[] extract(Image image) {
        double[] features = new double[1];
        features[0] = image.getAspectRatio();
        return features;
    }
}

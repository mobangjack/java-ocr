package net.sourceforge.javaocr.matcher;

import java.util.List;

/**
 * perform matching of features to concrete characters. returned list is sorted by distance.
 * result may contain several matches for same character (for example for different fonts). Resulted lists may need come more
 * processing to improve matching results
 * 
 * @author Konstantin Pribluda
 */
public interface Matcher {
    /**
     * provide ordered list of matches for provided features
     *
     * @param features  features extracted for character image
     * @return ordered list of matches
     */
    List<Match> classify(double[] features);
}

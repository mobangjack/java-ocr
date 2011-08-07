package net.sourceforge.javaocr.matcher;

import java.util.List;

/**
 * classify by amount of free spaces.  accepts only one feature
 * @author Konstantin Pribluda
 */
public class FreeSpacesMatcher implements Matcher {
    /**
     * list of suitable matches.  distance is kind of weight, but less relevant.
     * important is presence in the list
     * @param features  first value is treated as amount of consecutive free spaces. other areignored
     * @return
     */
    public List<Match> classify(double[] features) {
        return null;
    }
}

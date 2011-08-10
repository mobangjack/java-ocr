package net.sourceforge.javaocr.matcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * useful utility methods
 *
 * @author Konstantin Pribluda
 */
public class MatcherUtil {
    /**
     * extract candidates with matches in required list. Useful to increase match
     * quality (use free space matches as required)
     *
     * @param candidates candidates to check
     * @param required   only those matches are passed through
     * @return combined list of matches
     */
    public static List<Match> merge(List<Match> candidates, List<Match> required) {
        ArrayList<Match> retval = new ArrayList();
        Set<Character> contained = new HashSet();
        for (Match match : required) {
            contained.add(match.getChr());
        }
        for (Match match : candidates) {
            if (contained.contains(match.getChr()))
                retval.add(match);
        }
        return retval;
    }
}

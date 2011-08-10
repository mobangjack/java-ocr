package net.sourceforge.javaocr.matcher;

import java.util.*;

/**
 * classify by amount of free spaces.  accepts only one feature
 *
 * @author Konstantin Pribluda
 */
public class FreeSpacesMatcher implements Matcher {
    private Map<Integer, Integer> totals = new HashMap();
    private Map<Integer, Map<Character, Integer>> counts = new HashMap();


    /**
     * create list of matches (sorted)
     *
     * @param features features to be matched. we accept only 1 value (must be integer, will be cast there)
     * @return ordered list of matches.  weight == probability
     */
    public List<Match> classify(double[] features) {
        List<Match> ret = new ArrayList();

        // retrieve total count
        final int key = (int) features[0];
        final Integer total = totals.get(key);
        if (total == null) {
            // nothing there  - return empty list
            return Collections.EMPTY_LIST;
        }


        // now, we have totals,  create matches and probabilities
        final Map<Character, Integer> countsMap = counts.get(key);

        List<Match> matches = new ArrayList();

        for (Character c : countsMap.keySet()) {
            double distance = (double) countsMap.get(c) / (double) total;
            // distance or red/yelow concept are not really applicable for this kind
            // of matches 
            Match match = new Match(c, distance,0,0);
            matches.add(match);
        }

        // map counted,  set up list
        Collections.sort(matches, Collections.reverseOrder());

        return matches;
    }


    /**
     * train certain character occurence
     *
     * @param c   character
     * @param i   amount of free spaces
     */
    public void train(char c, int i) {
        // update amount of totals
        Integer integer = totals.get(i);
        int accumulatedTotal = integer != null ? integer + 1 : 1;
        totals.put(i, accumulatedTotal);

        // update amount of individual counts
        Map<Character, Integer> characterCounts = counts.get(i);
        if (characterCounts == null) {
            characterCounts = new HashMap();
            counts.put(i, characterCounts);
        }

        //  accumulate  character count
        integer = characterCounts.get(c);
        int accumulatedCount = integer != null ? integer + 1 : 1;
        characterCounts.put(c, accumulatedCount);
    }

}

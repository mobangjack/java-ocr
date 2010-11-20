package net.sf.javaocr.demos.android;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * matcher interface.  implementors are able to match  moments to characters
 */
public class Matcher {
    // arbitrary threshold for combined value separation
    double THRESHOLD = 3.0;
    Map<Character, Match> matches;
    private static final int VARIANCE_DIFF = 1;


    public Matcher() {
        matches = new HashMap<Character, Match>();
    }


    public void train(char c, double[] moments) {
        System.err.println("************* teaching char:" + c);
        Match match = matches.get(c);
        if (match == null) {
            System.err.println("************ new characted");
            // no match, create and init new
            match = new Match(c);
            // store it
            matches.put(c, match);
        }
        // train this matcher with coeffs
        match.train(moments);
    }

    /**
     * match array of invariants to character
     *
     * @param invariants computed invariants
     * @return character match if possible or null
     */
    public Character match(double[] invariants) {

        // sort matches by weight
        Map<Double, Match> weightMap = new HashMap<Double, Match>();
        for (Match match : matches.values()) {
            weightMap.put(match.lsd(invariants), match);
        }
        ArrayList<Double> weights = new ArrayList(weightMap.keySet());
        Collections.sort(weights);
        // ok, now we have array of match weights sorted
        System.err.println("*************** weights:" + weights);

        ArrayList<Double> candidates = new ArrayList();
        // gather list of candidates, with combined value up to threshold
        for (double weight : weights) {
            if (weight / weights.get(0) < THRESHOLD) {
                candidates.add(weight);
            }
        }
        // nothing there
        if(candidates.size() == 0)
                return 0;
        // now we have array of candidates
        if (candidates.size() == 1) {
            // only one candidate - return immediately
            final Match match = weightMap.get(weights.get(0));
            System.err.println("******** single candidate match:" + match.getC());
            return match.getC();
        }
        // several candidates,  decide which is better
        HashMap<Integer, Match> candidateMap = new HashMap();
        for (double candidateWeight : candidates) {
            int count = 0;
            Match match = weightMap.get(candidateWeight);
            final double[] diffs = match.diffs(invariants);
            // debug output
            StringBuilder sb = new StringBuilder("************ candidate " + match.getC() + "\t" + candidateWeight + "\tdiffs:\t");
            for (double diff : diffs) {
                if (diff < VARIANCE_DIFF) {
                    count++;
                }
                sb.append(diff).append("\t");
            }
            System.err.println(sb.toString());
            System.err.println("*********** iniside: \t" + count);
            candidateMap.put(count, match);
        }

        ArrayList<Integer> candidateCounts = new ArrayList(candidateMap.keySet());
        Collections.sort(candidateCounts);


        final Match result = candidateMap.get(candidateCounts.get(candidateCounts.size() -1));
        System.err.println("********* resolved to:" + result.getC());
        return result.getC();
    }
}

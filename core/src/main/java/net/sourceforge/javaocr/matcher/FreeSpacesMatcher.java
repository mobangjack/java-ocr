package net.sourceforge.javaocr.matcher;

import java.util.*;

/**
 * classify by amount of free spaces.  accepts only one feature
 *
 * @author Konstantin Pribluda
 */
public class FreeSpacesMatcher implements Matcher {
    private Map<Integer, Set<Character>> counts = new HashMap();


    /**
     * create list of matches (sorted)
     *
     * @param features features to be matched. we accept only 1 value (must be integer, will be cast there)
     * @return list of matches  without ordering
     */
    public List<Match> classify(double[] features) {

        // retrieve total count
        final int key = (int) features[0];
        // now, we have totals,  create matches and probabilities
        final Set<Character> characterSet = counts.get(key);

        if (characterSet == null) {
            return Collections.EMPTY_LIST;
        }
        
        List<Match> matches = new ArrayList();

        for (Character c : characterSet) {
            // distance or red/yelow concept are not really applicable for this kind
            // of matches 
            Match match = new Match(c, 1, 0, 0);
            matches.add(match);
        }
        return matches;
    }


    /**
     * train certain character occurence
     *
     * @param c character
     * @param i amount of free spaces
     */
    public void train(char c, int i) {


        // update amount of individual counts
        Set<Character> characters = counts.get(i);
        if (characters == null) {
            characters = new HashSet();
            counts.put(i, characters);
        }


        characters.add(c);
    }


    public Map<Integer, Set<Character>> getCounts() {
        return counts;
    }


    /**
     * extract list of configuration beans for serialisation
     *
     * @return
     */
    public List<FreeSpacesContainer> getContainers() {
        List<FreeSpacesContainer> freeSpaceContainers = new ArrayList<FreeSpacesContainer>();
        for (Integer count : getCounts().keySet()) {
            final FreeSpacesContainer spacesContainer = new FreeSpacesContainer();
            spacesContainer.setCount(count);

            final Set<Character> characters = getCounts().get(count);
            spacesContainer.setCharacters(characters.toArray(new Character[]{}));
            freeSpaceContainers.add(spacesContainer);
        }

        return freeSpaceContainers;
    }

    /**
     * configure matcher  with external configuration data - just train it
     *
     * @param containers
     */
    public void setContainers(List<FreeSpacesContainer> containers) {
        for (FreeSpacesContainer container : containers) {
            for (Character chr : container.getCharacters()) {
                train(chr, container.getCount());
            }
        }
    }

}

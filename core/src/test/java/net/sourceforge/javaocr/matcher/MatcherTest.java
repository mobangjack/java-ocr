package net.sourceforge.javaocr.matcher;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test capabilities of matcher
 */
public class MatcherTest {

    /**
     * test proper comparison
     */
    @Test
    public void testComparison() {
        Match match = new Match();
        match.setDistance(0.1);
        Match anotherMatch = new Match();
        anotherMatch.setDistance(0.3);
        assertEquals(-1, match.compareTo(anotherMatch));
        assertEquals(1, anotherMatch.compareTo(match));
        assertEquals(0, match.compareTo(match));

    }

}

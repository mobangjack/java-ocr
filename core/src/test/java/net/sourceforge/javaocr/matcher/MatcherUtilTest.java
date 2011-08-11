package net.sourceforge.javaocr.matcher;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * test proper function of matcher util
 */
public class MatcherUtilTest {

    @Test
    public void testMerge() {
        Match included = new Match('a', 0, 0, 0);
        Match excluded = new Match('b', 0, 0, 0);
        Match mergeFactor = new Match('a', 0, 0, 0);

        final List<Match> merged = MatcherUtil.merge(Arrays.asList(excluded, included), Arrays.asList(mergeFactor));
        assertTrue(merged.contains(included));
        assertFalse(merged.contains(excluded));
    }
}

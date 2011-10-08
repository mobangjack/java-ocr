package net.sourceforge.javaocr.matcher;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * test proper function of free space matching
 */
public class FreeSpacesMatcherTest {

    /**
     * matcher shall be configured properly when container list is set
     */
    @Test
    public void testContainerSetting() {
        FreeSpacesMatcher matcher = new FreeSpacesMatcher();

        FreeSpacesContainer container = new FreeSpacesContainer();
        container.setCharacters(new Character[]{'a'});
        container.setCount(10);

        FreeSpacesContainer anotherContainer = new FreeSpacesContainer();
        anotherContainer.setCharacters(new Character[]{'b'});
        anotherContainer.setCount(23);


        matcher.setContainers(Arrays.asList(container,anotherContainer));

        List<Match> matchList = matcher.classify(new double[]{5});
        assertEquals(0, matchList.size());

        matchList = matcher.classify(new double[]{23});
        assertEquals(1, matchList.size());
        assertEquals((Object) 'b', matchList.get(0).getChr());

        matchList = matcher.classify(new double[]{10});
        assertEquals(1, matchList.size());
        assertEquals((Object) 'a', matchList.get(0).getChr());
    }

}

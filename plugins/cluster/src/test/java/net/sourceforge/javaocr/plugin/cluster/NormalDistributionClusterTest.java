package net.sourceforge.javaocr.plugin.cluster;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * test capabilities of match class
 */
public class NormalDistributionClusterTest extends TestCase {

    /**
     * test that constructor paramaters  are stored
     */
    public void testParameterStorage() {
        NormalDistributionCluster normalDistributionCluster = new NormalDistributionCluster('a', 1);
        assertEquals('a', normalDistributionCluster.getC());
        assertEquals(1, normalDistributionCluster.getSize());
    }

    /**
     * if there are no samples, expectation and variance shall be null
     */
    @Test
    public void testThatNoSamplesMeansNullExpectationEndVariation() {
        NormalDistributionCluster normalDistributionCluster = new NormalDistributionCluster('a', 1);

        for (double mx : normalDistributionCluster.getMx())
            assertEquals(0d, mx);

        for (double var : normalDistributionCluster.getVar())
            assertEquals(0d, var);
    }

    /**
     * all the arrays shall have same size
     */
    @Test
    public void testSizeIsPropagatedToArrays() {
        NormalDistributionCluster normalDistributionCluster = new NormalDistributionCluster('a', 5);

        assertEquals(5, normalDistributionCluster.getMx().length);
        assertEquals(5, normalDistributionCluster.getVar().length);
        assertEquals(5, normalDistributionCluster.getSum().length);
        assertEquals(5, normalDistributionCluster.getQuads().length);
    }

}

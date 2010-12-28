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
        NormalDistributionCluster normalDistributionCluster = new NormalDistributionCluster(1){
            public double distance(double[] features) {
                return 0;
            }
        };

        assertEquals(1, normalDistributionCluster.getDimensions());
    }

    /**
     * if there are no samples, expectation and variance shall be null
     */
    @Test
    public void testThatNoSamplesMeansNullExpectationEndVariation() {
        NormalDistributionCluster normalDistributionCluster = new NormalDistributionCluster(1){
            public double distance(double[] features) {
                return 0;
            }
        };

        for (double mx : normalDistributionCluster.center())
            assertEquals(0d, mx);

        for (double var : normalDistributionCluster.getVar())
            assertEquals(0d, var);
    }

    /**
     * all the arrays shall have same size
     */
    @Test
    public void testSizeIsPropagatedToArrays() {
        NormalDistributionCluster normalDistributionCluster = new NormalDistributionCluster(5){
            public double distance(double[] features) {
                return 0;
            }
        };;

        assertEquals(5, normalDistributionCluster.center().length);
        assertEquals(5, normalDistributionCluster.getVar().length);
        assertEquals(5, normalDistributionCluster.getSum().length);
        assertEquals(5, normalDistributionCluster.getQuads().length);
    }

}

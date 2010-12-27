package net.sourceforge.javaocr.plugin.cluster;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * cover capabilities of euclidian cdistance cluster
 */
public class EuclidianClusterTest {


    @Test
    public void testProperComputation() {
        EuclidianDistanceCluster cluster = new EuclidianDistanceCluster(2);
        cluster.train(new double[]{0, 0});
        assertEquals(5d,cluster.distance(new double[]{3,4})) ;
    }
}

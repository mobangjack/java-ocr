package net.sourceforge.javaocr.plugin.moment;

import junit.framework.TestCase;

/**
 * test capability of raw moment filter (computation)
 */
public class RawMomentFilterTest extends TestCase {

    /**
     * raw moment of order (p,q) is defined as SUM_OVER_ALL_POINTS( x**p * y**q * sample(x,y))
     */
    public void testMomentComputation() {
        RawMomentFilter filter = new RawMomentFilter(2, 3);
        filter.computeIndividualMoment(239, 5, 7);
        assertEquals(Math.pow(5, 2) * Math.pow(7, 3) * 239, filter.getMoment(), 0);
    }

    /**
     * shall be additive.
     */
    public void testAdditivity() {
        RawMomentFilter filter = new RawMomentFilter(0, 0);
        filter.computeIndividualMoment(100, 1, 2);
        filter.computeIndividualMoment(120, 1, 5);
        assertEquals(220d, filter.getMoment());
    }
}
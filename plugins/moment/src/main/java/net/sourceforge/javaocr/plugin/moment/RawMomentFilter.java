package net.sourceforge.javaocr.plugin.moment;

import net.sourceforge.javaocr.Image;

/**
 * process image and compute raw image moment. does not modify the image.  this filter is stateful and not thread safe
 */
public class RawMomentFilter extends AbstractMomentFilter {


    /**
     * filter computing moment with given cardinality
     *
     * @param p
     * @param q
     */
    public RawMomentFilter(int p, int q) {
        super(p, q);
    }

   

    @Override
    protected double[] precomputeX(Image image) {
        final double[] doubles = new double[image.getWidth()];
        for (int i = 0; i < doubles.length; i++)
            doubles[i] = Math.pow(i, p);
        return doubles;
    }

    @Override
    protected double[] precomputeY(Image image) {
        final double[] doubles = new double[image.getHeight()];
        for (int i = 0; i < doubles.length; i++)
            doubles[i] = Math.pow(i, q);
        return doubles;
    }

}

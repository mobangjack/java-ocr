package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;

/**
 * apply lookup table to image
 */
public class LookupTableFilter extends AbstractSinglePixelFilter {

    int[] lut;

    /**
     * construct with supplied lookup table
     * @param lut
     */
    public LookupTableFilter(int[] lut) {
        this.lut = lut;
    }

    @Override
    protected void processPixel(Image image) {
         image.put(lut[image.next()]);
    }
}

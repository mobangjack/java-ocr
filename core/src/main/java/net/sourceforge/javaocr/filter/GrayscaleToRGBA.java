package net.sourceforge.javaocr.filter;

/**
 * transform pixel from grayscale to RGBA  (well,  actually ARGB)
 */
public class GrayscaleToRGBA extends LookupTableFilter {
  
    /**
     * construct flat ramp
     */
    public GrayscaleToRGBA() {
       super(new int[256]);
        for(int i = 0; i < 256; i++) {
            lut[i] = 0xff000000 | i << 16 | i <<8 | i;
        }
    }
}

package net.sourceforge.javaocr.filter;

/**
 * normalise grayscale pixels linear basing on min/max values
 * @author Konstantin Pribluda
 */
public class NormaliseGrayscaleFilter extends LookupTableFilter {
   

    /**
     * create and initialise lookup table
     * @param max max pixel value in image
     * @param min min pixel value in image
     */
    public NormaliseGrayscaleFilter(int min, int max) {
        super(new int[256]);
        int range = max - min;
        for(int i = 0; i < 256; i++){
            lut[i] = Math.min(255, Math.max(0, ((i - min) * 255) / range));           
        }
    }

}

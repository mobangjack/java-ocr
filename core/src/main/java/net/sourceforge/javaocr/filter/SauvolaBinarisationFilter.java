package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;

/**
 * Performs local image threshloding with Sauvola algorythm.
 * allocates integral images to achieve O(N) performance instead  ow O(N2W2).
 * This filter can be reused.
 * <p/>
 * Based on "Efficient Implementation of Local Adaptive Thresholding Techniques Using Integral Images" by
 * Faisal Shafaita , Daniel Keysersa , Thomas M. Breuel from Image Understanding and Pattern Recognition (IUPR) Research Group
 * German Research Center for Artificial Intelligence (DFKI) GmbH
 * D-67663 Kaiserslautern, Germany
 * and  Thomas M. Breuel from Department of Computer Science, Technical University of Kaiserslautern
 * D-67663 Kaiserslautern, Germany
 *
 * @author Konstantin Pribluda
 */
public class SauvolaBinarisationFilter  implements ImageFilter {



    public void process(Image image) {
        
    }
}

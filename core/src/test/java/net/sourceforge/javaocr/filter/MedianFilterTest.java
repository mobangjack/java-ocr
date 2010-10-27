package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.ocr.PixelImage;
import junit.framework.TestCase;

/**
 * Test for <code>MedianFilter</code> class
 */
public class MedianFilterTest extends TestCase {
	
    public void testMedianFilter() {
    	int imageW = 4;
    	int imageH = 3;
    	
        int[] inData = new int[] {
        	255, 255, 255, 255,
       		255,   0,   0, 255,
       		255, 255, 255, 255
        };
        
        PixelImage imageIn = new PixelImage(inData, imageW, imageH);
        PixelImage imageOut = new PixelImage(imageW, imageH);
        MedianFilter filter = new MedianFilter(3, 3);

        filter.process(imageIn, imageOut);

        for (int y = 0; y < imageH; ++y) {
        	for (int x = 0; x < imageW; ++x) {
        		assertEquals(255, imageOut.get(x, y));
        	}
        }
    }
    
}

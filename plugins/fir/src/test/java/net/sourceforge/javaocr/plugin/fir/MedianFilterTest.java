package net.sourceforge.javaocr.plugin.fir;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;
import net.sourceforge.javaocr.plugin.fir.MedianFilter;

/**
 * Test for <code>MedianFilter</code> class
 */
public class MedianFilterTest extends TestCase {
	
    public void testLeaveBordersIntact() {
    	int imageW = 5;
    	int imageH = 5;
    	int filterW = 3;
    	int filterH = 3;
    	
        int[] data = new int[] {
        	255,   0, 255,   0, 255,
        	  0, 255, 255, 255,   0,
        	255, 255, 255, 255, 255,
        	  0, 255, 255, 255,   0,
        	255,   0, 255,   0, 255,
        };
        
        PixelImage imageIn = new PixelImage(data, imageW, imageH);
        PixelImage imageOut = new PixelImage(imageW, imageH);
        MedianFilter filter = new MedianFilter(filterW, filterH, imageOut);
        
        filter.process(imageIn);

        for (int y = 0; y < imageH; ++y) {
        	for (int x = 0; x < imageW; ++x) {
        		assertEquals(data[y*imageW+x], imageOut.get(x, y));
        	}
        }
    }
    
    public void testMedianFilterDenoise() {
    	int imageW = 7;
    	int imageH = 12;
    	int filterW = 3;
    	int filterH = 3;
    	
        int[] inData = new int[] {
        	255, 255, 255, 255, 255, 255, 255,
        	255, 255,   0,   0,   0,   0, 255,
        	255,   0,   0,   0,   0,   0, 255,
        	255,   0,   0, 255, 255, 255, 255,
        	255,   0,   0, 255,   0, 255, 255,
        	255,   0,   0, 255, 255,   0, 255,
        	255,   0,   0, 255, 255, 255, 255,
        	255,   0,   0, 255, 255,   0, 255,
        	255,   0,   0, 255, 255, 255, 255,
        	255,   0,   0,   0,   0,   0, 255,
        	255, 255,   0,   0,   0,   0, 255,
        	255, 255, 255, 255, 255, 255, 255,
        };
        
        int[] truthData = new int[] {
        	255, 255, 255, 255, 255, 255, 255,
        	255, 255,   0,   0,   0, 255, 255,
        	255,   0,   0,   0,   0, 255, 255,
        	255,   0,   0,   0, 255, 255, 255,
        	255,   0,   0, 255, 255, 255, 255,
        	255,   0,   0, 255, 255, 255, 255,
        	255,   0,   0, 255, 255, 255, 255,
        	255,   0,   0, 255, 255, 255, 255,
        	255,   0,   0,   0, 255, 255, 255,
        	255,   0,   0,   0,   0, 255, 255,
        	255, 255,   0,   0,   0, 255, 255,
        	255, 255, 255, 255, 255, 255, 255,
        };
        
        PixelImage imageIn = new PixelImage(inData, imageW, imageH);
        PixelImage imageOut = new PixelImage(imageW, imageH);
        
        MedianFilter filter = new MedianFilter(filterW, filterH, imageOut);
        filter.process(imageIn);
        
        PixelImage imageTruth = new PixelImage(truthData, imageW, imageH);
        
        for (int y = 0; y < imageH; ++y) {
        	for (int x = 0; x < imageW; ++x) {
        		assertEquals(imageTruth.get(x, y), imageOut.get(x, y));
        	}
        }
    }
    
    public void testMeanWhenCounterIsEven() {
    	int imageW = 3;
    	int imageH = 2;
    	int filterW = 2;
    	int filterH = 2;
    	
        int[] data = new int[] {
        	  0,   0, 255,
        	255, 255, 255,
        };
        
        PixelImage imageIn = new PixelImage(data, imageW, imageH);
        PixelImage imageOut = new PixelImage(imageW, imageH);
        MedianFilter filter = new MedianFilter(filterW, filterH, imageOut);
        
        filter.process(imageIn);

   		assertEquals(127, imageOut.get(0, 0));
   		assertEquals(255, imageOut.get(0, 1));
    }
}

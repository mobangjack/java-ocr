package net.sourceforge.javaocr.filter;

import net.sourceforge.javaocr.ocr.PixelImage;
import junit.framework.TestCase;

/**
 * Test for <code>MedianFilter</code> class
 */
public class MedianFilterTest extends TestCase {
	
    public void testMedianFilterDenoise() {
    	int imageW = 5;
    	int imageH = 4;
    	int filterW = 3;
    	int filterH = 3;
    	
        int[] inData = new int[] {
        	255, 255, 255, 255, 255,
       		255,   0, 255, 255, 255,
       		255, 255, 255,   0, 255,
       		255, 255, 255, 255, 255,
        };
        
        PixelImage imageIn = new PixelImage(inData, imageW, imageH);
        PixelImage imageOut = new PixelImage(imageW, imageH);
        MedianFilter filter = new MedianFilter(imageOut, filterW, filterH);

        filter.process(imageIn);

        for (int y = 0; y < imageH; ++y) {
        	for (int x = 0; x < imageW; ++x) {
        		assertEquals(255, imageOut.get(x, y));
        	}
        }
    }

    public void testMedianFilterBorders() {
    	int imageW = 5;
    	int imageH = 5;
    	int filterW = 3;
    	int filterH = 3;
    	
        int[] inData = new int[] {
        	255,   0,   0,   0, 255,
        	  0, 255, 255, 255,   0,
        	  0, 255, 255, 255,   0,
        	  0, 255, 255, 255,   0,
        	255,   0,   0,   0, 255,
        };
        
        PixelImage imageIn = new PixelImage(inData, imageW, imageH);
        PixelImage imageOut = new PixelImage(imageW, imageH);
        MedianFilter filter = new MedianFilter(imageOut, filterW, filterH);
        
        filter.process(imageIn);

        for (int y = 0; y < imageH; ++y) {
        	for (int x = 0; x < imageW; ++x) {
        		assertEquals(255, imageOut.get(x, y));
        	}
        }
    }
    
    public void testMedianFilterCorners() {
    	int imageW = 3;
    	int imageH = 3;
    	int filterW = 3;
    	int filterH = 3;
    	
        int[] inData = new int[] {
        	  0, 255,   0,
        	255, 255, 255,
        	  0, 255,   0,
        };
        
        PixelImage imageIn = new PixelImage(inData, imageW, imageH);
        PixelImage imageOut = new PixelImage(imageW, imageH);
        MedianFilter filter = new MedianFilter(imageOut, filterW, filterH);
        
        filter.process(imageIn);

        for (int y = 0; y < imageH; ++y) {
        	for (int x = 0; x < imageW; ++x) {
        		assertEquals(255, imageOut.get(x, y));
        	}
        }
    }
}

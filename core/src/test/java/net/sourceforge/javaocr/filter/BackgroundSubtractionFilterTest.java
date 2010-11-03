package net.sourceforge.javaocr.filter;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

public class BackgroundSubtractionFilterTest extends TestCase {
	
	public void testBackgroundSubtraction() {
        int[] bgData = new int[] {
        	33, 34, 15,
 			32, 26, 18
        };
        
        int[] inData = new int[] {
        	133, 134, 115,
 			132, 126, 118
        };
        
        PixelImage bgImage = new PixelImage(bgData, 3, 2);
        PixelImage inImage = new PixelImage(inData, 3, 2);
        BackgroundSubtractionFilter filter = 
        	new BackgroundSubtractionFilter(bgImage);
        
        filter.process(inImage);
        
    	for (int p = 0; p < 6; ++p) {
    		assertEquals(inData[p], 100);
    	}
	}
	
	public void testBackgroundSubtractionWithRange() {
        int[] bgData = new int[] {
        	33, 34, 15,
        	14, 26, 17,
 			32, 21, 18
        };
        
        int[] inData = new int[] {
        	133, 200, 115,
        	200, 126, 200,
 			132, 200, 118
        };
        
        PixelImage bgImage = new PixelImage(bgData, 3, 3);
        PixelImage inImage = new PixelImage(inData, 3, 3);
        BackgroundSubtractionFilter filter = 
        	new BackgroundSubtractionFilter(bgImage, 0, 160);
        
        filter.process(inImage);
        
    	for (int p = 0; p < 9; ++p) {
    		if (p % 2 == 0) assertEquals(inData[p], 100);
    		else assertEquals(inData[p], 200);
    	}	
	}
}

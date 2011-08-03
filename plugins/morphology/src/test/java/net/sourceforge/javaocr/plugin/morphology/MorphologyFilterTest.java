package net.sourceforge.javaocr.plugin.morphology;

import junit.framework.TestCase;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * Test for <code>ErosionFilter</code>, <code>DilationFilter</code>,
 * <code>OpeningFilter</code> and <code>ClosingFilter</code> class
 */
public class MorphologyFilterTest extends TestCase {

  static int B = 0;
  static int W = 255;
  
  public void testErosionFilter() {
    int imW = 9, imH = 7;
    int seW = 3, seH = 3;

    int[] inData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, W, W, W, B, W, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    int[] seData = new int[] {
        B, W, B,
        W, W, W,
        B, W, B,
    };
    
    int[] gtData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, W, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    PixelImage imageIn = new PixelImage(inData, imW, imH);
    PixelImage imageSE = new PixelImage(seData, seW, seH);
    PixelImage imageOut = new PixelImage(imW, imH);

    ErosionFilter filter = new ErosionFilter(imageSE, imageOut, W, B);
    filter.process(imageIn);

    PixelImage imageGT = new PixelImage(gtData, imW, imH);

    for (int y = 0; y < imH; ++y) {
      for (int x = 0; x < imW; ++x) {
        assertEquals(imageGT.get(x, y), imageOut.get(x, y));
      }
    }
  }

  public void testDilationFilter() {
    int imW = 9, imH = 7;
    int seW = 3, seH = 3;

    int[] inData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, W, W, W, B, W, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    int[] seData = new int[] {
        B, W, B,
        W, W, W,
        B, W, B,
    };
    
    int[] gtData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, W, W, W, W, W, B, B,
        B, W, W, W, W, W, W, W, B,
        B, W, W, W, W, W, W, W, B,
        B, W, W, W, W, W, W, W, B,
        B, B, W, W, W, W, W, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    PixelImage imageIn = new PixelImage(inData, imW, imH);
    PixelImage imageSE = new PixelImage(seData, seW, seH);
    PixelImage imageOut = new PixelImage(imW, imH);

    DilationFilter filter = new DilationFilter(imageSE, imageOut, W, B);
    filter.process(imageIn);

    PixelImage imageGT = new PixelImage(gtData, imW, imH);

    for (int y = 0; y < imH; ++y) {
      for (int x = 0; x < imW; ++x) {
        assertEquals(imageGT.get(x, y), imageOut.get(x, y));
      }
    }
  }
  
  public void testOpeningFilter() {
    int imW = 9, imH = 7;
    int seW = 3, seH = 3;

    int[] inData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, W, W, W, B, W, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    int[] seData = new int[] {
        B, W, B,
        W, W, W,
        B, W, B,
    };
    
    int[] gtData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, W, B, B, B, B, B,
        B, B, W, W, W, B, B, B, B,
        B, B, B, W, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    PixelImage imageIn = new PixelImage(inData, imW, imH);
    PixelImage imageSE = new PixelImage(seData, seW, seH);
    PixelImage imageOut = new PixelImage(imW, imH);

    OpeningFilter filter = new OpeningFilter(imageSE, imageOut, W, B);
    filter.process(imageIn);

    PixelImage imageGT = new PixelImage(gtData, imW, imH);

    for (int y = 0; y < imH; ++y) {
      for (int x = 0; x < imW; ++x) {
        assertEquals(imageGT.get(x, y), imageOut.get(x, y));
      }
    }
  }
  
  public void testClosingFilter() {
    int imW = 9, imH = 7;
    int seW = 3, seH = 3;

    int[] inData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, W, W, W, B, W, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    int[] seData = new int[] {
        B, W, B,
        W, W, W,
        B, W, B,
    };
    
    int[] gtData = new int[] {
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, W, W, W, W, W, B, B,
        B, B, B, B, B, B, B, B, B,
        B, B, B, B, B, B, B, B, B,
    };

    PixelImage imageIn = new PixelImage(inData, imW, imH);
    PixelImage imageSE = new PixelImage(seData, seW, seH);
    PixelImage imageOut = new PixelImage(imW, imH);

    ClosingFilter filter = new ClosingFilter(imageSE, imageOut, W, B);
    filter.process(imageIn);

    PixelImage imageGT = new PixelImage(gtData, imW, imH);

    for (int y = 0; y < imH; ++y) {
      for (int x = 0; x < imW; ++x) {
        assertEquals(imageGT.get(x, y), imageOut.get(x, y));
      }
    }
  }
  
}

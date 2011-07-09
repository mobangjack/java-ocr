package net.sourceforge.javaocr.plugin.fir;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * Apply closing by structuring element to binarized source image.
 * TODO FIXME Works for binarized images only!
 * @author Andrea De Pasquale
 */
public class ClosingFilter implements ImageFilter {

  protected DilationFilter dilationFilter;
  protected Image tempImage;
  protected ErosionFilter erosionFilter;
  protected Image destImage;
  
  /**
   * Create a <code>ClosingFilter</code> with default values
   * of 255 for the foreground and 0 for the background.
   * @param strElem Structuring element
   * @param dest Output image
   */
  public ClosingFilter(Image strElem, Image dest) {
    this(strElem, dest, 255, 0);
  }
  
  /**
   * Create a <code>ClosingFilter</code>.
   * @param strElem Structuring element
   * @param dest Output image
   * @param full Foreground value 
   * @param empty Background value
   */
  public ClosingFilter(Image strElem, Image dest, int full, int empty) {
    tempImage = new PixelImage(dest.getWidth(), dest.getHeight());
    dilationFilter = new DilationFilter(strElem, tempImage, full, empty);
    destImage = dest;
    erosionFilter = new ErosionFilter(strElem, destImage, full, empty);
  }
  
  @Override
  public void process(Image image) {
    dilationFilter.process(image);
    erosionFilter.process(tempImage);
  }

}

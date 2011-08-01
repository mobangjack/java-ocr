package net.sourceforge.javaocr.plugin.morphology;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * Apply opening by structuring element to binarized source image.
 * TODO Works for binarized images only, could be extended to grayscale.
 * 
 * The opening of an image A by a structuring element B is obtained by the 
 * erosion of A by B, followed by dilation of the resulting image by B.
 * 
 * http://en.wikipedia.org/wiki/Opening_%28morphology%29
 * 
 * @author Andrea De Pasquale
 */
public class OpeningFilter implements ImageFilter {

  protected ErosionFilter erosionFilter;
  protected Image tempImage;
  protected DilationFilter dilationFilter;
  protected Image destImage;
  
  /**
   * Create an <code>OpeningFilter</code> with default values
   * of 255 for the foreground and 0 for the background.
   * @param strElem Structuring element
   * @param dest Output image
   */
  public OpeningFilter(Image strElem, Image dest) {
    this(strElem, dest, 255, 0);
  }
  
  /**
   * Create an <code>OpeningFilter</code>.
   * @param strElem Structuring element
   * @param dest Output image
   * @param full Foreground value 
   * @param empty Background value
   */
  public OpeningFilter(Image strElem, Image dest, int full, int empty) {
    tempImage = new PixelImage(dest.getWidth(), dest.getHeight());
    erosionFilter = new ErosionFilter(strElem, tempImage, full, empty);
    destImage = dest;
    dilationFilter = new DilationFilter(strElem, destImage, full, empty);
  }
  
  @Override
  public void process(Image image) {
    erosionFilter.process(image);
    dilationFilter.process(tempImage);
  }

}

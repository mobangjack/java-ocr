package net.sourceforge.javaocr.plugin.fir;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;

/**
 * Abstract class for mathematical morphology filters
 * @author Andrea De Pasquale
 */
public abstract class AbstractMorphologyFilter implements ImageFilter {
  
  protected Image seImage;
  protected Image destImage;
  protected int seImageW, seImageH;
  protected int sizeL, sizeR, sizeT, sizeB;
  protected int full, empty;

  /**
   * Create a mathematical morphology filter, e.g. erosion and dilation
   * 
   * @param strElem Structuring element
   * @param dest Output image
   * @param fg Foreground value
   * @param bg Background value
   */
  public AbstractMorphologyFilter(Image strElem, Image dest, int fg, int bg) {
    seImage = strElem;
    seImageW = (seImage.getWidth()  > 0 ? seImage.getWidth()  : 1);
    seImageH = (seImage.getHeight() > 0 ? seImage.getHeight() : 1);
    sizeL = (seImageW - 1) / 2;
    sizeR = (seImageW - 1) - sizeL;
    sizeT = (seImageH - 1) / 2;
    sizeB = (seImageH - 1) - sizeT;
    destImage = dest;
    full = fg;
    empty = bg;
  }

}

package net.sourceforge.javaocr.plugin.morphology;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;

/**
 * Abstract class for mathematical morphology filters.
 * 
 * The basic idea in binary morphology is to probe an image with a simple,
 * pre-defined shape, drawing conclusions on how this shape fits or misses the
 * shapes in the image. This simple "probe" is called structuring element, and
 * is itself a binary image. Typical structuring elements are 3x3 square and
 * 3x3 cross (i.e. a 3x3 "plus sign" image).
 * 
 * http://en.wikipedia.org/wiki/Mathematical_morphology#Binary_morphology
 * http://en.wikipedia.org/wiki/Structuring_element
 * 
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

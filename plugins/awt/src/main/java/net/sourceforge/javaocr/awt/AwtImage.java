package net.sourceforge.javaocr.awt;

import net.sourceforge.javaocr.ocr.PixelImage;

import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

/**
 * encapsulates AWT related functionality (image creation out of awt)
 */
public class AwtImage extends PixelImage {

    /**
     *create pixel image out of awt image
     * @param image
     */
    public AwtImage(Image image) {
        super(image.getWidth(null), image.getHeight(null));
        
        PixelGrabber grabber = new PixelGrabber(image, 0, 0, arrayWidth, arrayHeight, pixels, 0, arrayWidth);
        try {
            grabber.grabPixels();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * retrieve awt image out of pixels
     * @param rgbPixels
     * @param width
     * @param height
     * @param comp
     * @return
     */
    public java.awt.Image rgbToImage(int[] rgbPixels, int width, int height, Component comp) {
        return comp.createImage(new MemoryImageSource(width, height, rgbPixels, 0, width));
    }


}

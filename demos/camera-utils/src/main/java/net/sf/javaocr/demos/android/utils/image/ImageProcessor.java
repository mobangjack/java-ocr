package net.sf.javaocr.demos.android.utils.image;
import net.sourceforge.javaocr.ocr.PixelImage;

/**
 * image preprocessing
 * @author Konstantin Pribluda
 */
public interface ImageProcessor {
    /**
     * process image in some obscure way
     *
     * @param image incoming image.  may be modified
     * @return processed image, may be subimage over original one, and is definitely pixel image as we need integer pixel array for further processing
     * TODO this is not really nice,  but we need pixel array for further processing
     */
    PixelImage prepareImage(byte[] image, int offsetX, int offsetY);


}

package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.Image;

/**
 * class performing image slicing.  it's a good idea to pre process image and made it binary.
 * image slicing does not move data around and does not modify it.  slicer is not thread safe.
 *
 * @author Konstantin Pribluda
 */
public class SlicerH extends AbstractBaseSlicer  {

    /**
     * @param image image to be sliced
     * @param empty empty empty
     */
    public SlicerH(Image image, int empty) {
        super(empty, image, image.getHeight());
    }

    @Override
    protected void iterateSpan() {
        image.iterateH(currentPosition);
    }


    @Override
    protected Image chisel(int imageStart) {
        return image.chisel(0, imageStart, image.getWidth(), currentPosition - imageStart);
    }
}

package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.Image;

/**
 * slice image in vertical chunks
 */
public class SlicerV extends AbstractBaseSlicer {

    public SlicerV(Image image, int empty) {
        super(empty, image, image.getWidth());
    }


    @Override
    protected Image chisel(int imageStart) {
        return image.chisel(imageStart, 0, currentPosition - imageStart, image.getHeight());
    }

    @Override
    protected void iterateSpan() {
        image.iterateV(currentPosition);
    }
}

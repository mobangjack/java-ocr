package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageSlicer;

/**
 * class performing image sliciing.  it's a goog idea to preprocess image and made it binary.
 * image slicing does not move data around and does not modify it.  slicer is not thread safe.
 *
 * @author Konstantin Pribluda
 */
public class SlicerH implements ImageSlicer {
    Image image;
    int empty;
    private int currentPosition;
    int tolerance;

    /**
     * @param image image to be sliced
     * @param empty empty empty
     */
    public SlicerH(Image image, int empty) {
        this.image = image;
        this.empty = empty;
    }

    /**
     * start horisontal slicing from designated position
     *
     * @param fromY starting Y
     */
    public void slice(int fromY) {
        slice(fromY, 0);
    }

    /**
     * start horisontal slicing from designated position tolerating some empty rows
     *
     * @param from      starting position
     * @param tolerance amount of empty rows allowed inside consecutive image
     */
    public void slice(int from, int tolerance) {

        boolean rowEmpty;
        this.tolerance = tolerance;
        for (currentPosition = from; currentPosition < image.getHeight(); currentPosition++) {
            rowEmpty = rowEmpty();
            System.err.println("row " + currentPosition + " empty:" + rowEmpty);
            if (!rowEmpty) {
                break;
            }
        }
    }

    /**
     * whether current image row is empty
     *
     * @return
     */
    private boolean rowEmpty() {
        boolean rowEmpty;
        rowEmpty = true;
        // walk through row
        image.iterateH(currentPosition);
        while (image.hasNext())
            if (image.next() != empty) {
                rowEmpty = false;
                break;
            }
        return rowEmpty;
    }


    public boolean hasNext() {
        return currentPosition < image.getHeight();
    }

    /**
     * chisel out subimage  with preconfigured tolerance
     *
     * @return subimage
     */
    public Image next() {
        // we stay on first row of image,  which is per definition non
        // empty (that's why  we terminated previous loop)

        // init tolerance reserve counter
        int toleranceReserve = tolerance + 1;
        // save current position
        int imageStart = currentPosition;
        // iterate over image as long as there rows left and tolerance reserve met
        for (; currentPosition < image.getHeight() && toleranceReserve > 0; currentPosition++) {
            if (rowEmpty()) {
                // decrease reserve
                toleranceReserve--;
            } else {
                // increase reserve
                toleranceReserve = tolerance + 1;
            }
        }
        // ok,  now we are ready with scan did we collected something?
        if( imageStart != currentPosition) {
            System.err.println("image start:" + imageStart);
            System.err.println("currentPosition:" + currentPosition);
            // apparently. chisel image
            final Image returnImage = image.chisel(0, imageStart, image.getWidth(), currentPosition - imageStart);

            // skip to the next image
            slice(currentPosition,tolerance);
            return  returnImage;
        }
        // that's patologic and shall not happen
        return null;
    }
}

package net.sourceforge.javaocr;

/**
 * slices image into pieces. kind of iterator
 */
public interface ImageSlicer {
    /**
     * start horisontal slicing iteration from certain position
     * @param fromY  starting Y
     */
    public void sliceH(int fromY);

    /**
     * start vertical imageslicing from certain position
     * @param fromX     starting X
     */
    public void sliceV(int fromX);


    boolean hasNext();

    /**
     * retrieve next image
     * @return
     */
    Image next();
}

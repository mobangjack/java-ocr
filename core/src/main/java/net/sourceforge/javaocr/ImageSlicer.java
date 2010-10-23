package net.sourceforge.javaocr;

/**
 * slices image into pieces. utilises iterator pattern, concrete implementations are provided for
 * horizontal and vertical slicing
 */
public interface ImageSlicer {
    /**
     * start horizontal slicing iteration from certain position  empty row means new image
     *
     * @param fromY starting Y
     */
    public void slice(int fromY);

    /**
     * start horizontal slicing with defined tolerance
     *
     * @param fromY     starting position
     * @param tolerance amount of empty rows allowed inside consecutive image
     */
    public void slice(int fromY, int tolerance);


    /**
     * whether next slice is available
     *
     * @return availability of next slice
     */
    boolean hasNext();


    /**
     * retrieve next slice and advance
     *
     * @return next image slice
     */
    Image next();
}

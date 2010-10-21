package net.sourceforge.javaocr;

/**
 * slices image into pieces. kind of iterator
 */
public interface ImageSlicer {
    /**
     * start horizontal slicing iteration from certain position  empty row means new image
     * @param fromY  starting Y
     */
    public void sliceH(int fromY);

    /**
     * start horizontal slicing with some tolerance
     * @param fromY   starting position
     * @param tolerance  amount of empty rows allowed inside consecutive image
     */
    public void sliceH(int fromY, int tolerance);

    /**
     * start vertical image slicing from certain position , empty column means new image
     * @param fromX     starting X
     */
    public void sliceV(int fromX);

    /**
     * tart vertical image slicing from certain position with some tolerance
     * @param fromX    starting x
     * @param tolerance  amount of empty columns inside one image
     */
    public void sliceV(int fromX, int tolerance);

    /**
     * whether next slice is available
     * @return  availability of next slice
     */
    boolean hasNext();

    /**
     * retrieve current position in image
     * @return  current scanning position
     */
    int position();
    /**
     * retrieve next slice and advance 
     * @return next slice
     */
    Image next();
}

package net.sourceforge.javaocr;

/**
 * Interface contract to perform document scanning
 *
 * @author Konstantin Pribluda
 * @deprecated - do we need this interface at all?
 */
public interface ImageScanner {
    /**
     * scan document and generate events for interested parties
     *
     * @param image    Image to be scanned
     * @param listener Listener receiving events
     * @param left     boundary
     * @param top      boundary
     * @param right    boundary
     * @param bottom   boundary
     */
    void scan(
            Image image,
            DocumentScannerListener listener,
            int left,
            int top,
            int right,
            int bottom);
}

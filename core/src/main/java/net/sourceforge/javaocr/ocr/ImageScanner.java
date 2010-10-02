package net.sourceforge.javaocr.ocr;

/**
 * Interface contract to perform document scannung
 *
 * @author Konstantin Pribluda
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
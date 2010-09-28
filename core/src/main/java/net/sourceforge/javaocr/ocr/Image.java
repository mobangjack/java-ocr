package net.sourceforge.javaocr.ocr;

/**
 * Inteface encapsulating cimage finctionality. comcrete implementations shall be provided by plugins
 * @author Konstantin Pribluda
 */
public interface Image {
    int getPixelIndex(int x, int y);

    int getPixel(int x, int y);
}

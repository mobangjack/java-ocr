package net.sourceforge.javaocr.ocr;

/**
 * image backed by byte array
 */
public class ByteImage extends AbstractLinearImage {
    byte[] image;


    public ByteImage(int width, int height) {
        this(new byte[width * height], width, height);
    }

    public ByteImage(byte[] image, int width, int height) {
        this(image, width, height, 0, 0, width, height);
    }

    public ByteImage(byte[] image, int arrayWidth, int arrayHeight, int originX, int originY, int width, int height) {
        super(arrayWidth, arrayHeight, originX, originY, width, height);
        this.image = image;
    }

    @Override
    public int get() {
       return image[currentIndex];
    }

    @Override
    public void put(int value) {
        image[currentIndex] = (byte) value;
    }

    @Override
    public String toString() {
        return "ByteImage{} " + super.toString();
    }
}

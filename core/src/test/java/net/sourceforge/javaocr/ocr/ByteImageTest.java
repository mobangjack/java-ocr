package net.sourceforge.javaocr.ocr;

import junit.framework.TestCase;

/**
 * test capabilities of byte images
 * TODO: imaplement testing of byte to int conversion,  shall be able to set and retrieve values from 0 to 255 properly, maybe bigger
 */
public class ByteImageTest extends TestCase {

    public void testValuesAreSetAndRetrievedProperlyAsUnsigned() {
        ByteImage image = new ByteImage(1, 1);

        image.setCurrentIndex(0, 0);
        image.put(255);
        assertEquals(255, image.get());

        image.put(129);
        assertEquals(129, image.get());
    }
}

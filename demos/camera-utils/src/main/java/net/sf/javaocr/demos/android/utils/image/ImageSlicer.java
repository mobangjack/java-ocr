package net.sf.javaocr.demos.android.utils.image;

import net.sourceforge.javaocr.Image;

import java.util.List;

/**
 * slices image into distinct glyphs
 */
public interface ImageSlicer {
    /**
     * slice image into disticnt glyphs
     * @param image
     * @return
     */
    List<List<Image>> sliceUp(Image image);
}

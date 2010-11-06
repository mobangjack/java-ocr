package net.sourceforge.javaocr;

/**
 * shrink image in controlled way
 * TODO: do we need separate interface for it??????
 */
public interface ImageShrinker {
    Image shrink(Image source);
}

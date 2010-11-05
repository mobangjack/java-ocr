// DocumentScannerListenerAdapter.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocr;

import net.sourceforge.javaocr.DocumentScannerListener;
import net.sourceforge.javaocr.Image;

/**
 * Empty implementation of DocumentScannerListener interface which can be
 * subclassed and only have the needed methods overridden.  This prevents
 * implementing classes from being forced to implement all methods in the
 * interface.
 * @author Ronald B. Cemer
 * @deprecated this functionality is provided by slicers
 */
public class DocumentScannerListenerAdaptor
        implements DocumentScannerListener
{

    public void beginDocument(Image pixelImage)
    {
    }

    public void beginRow(Image pixelImage, int y1, int y2)
    {
    }

    public void processChar(Image pixelImage, int x1, int y1, int x2, int y2, int rowY1, int rowY2)
    {
    }

    public void processSpace(Image pixelImage, int x1, int y1, int x2, int y2)
    {
    }

    public void endRow(Image pixelImage, int y1, int y2)
    {
    }

    public void endDocument(Image pixelImage)
    {
    }
}

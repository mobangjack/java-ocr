// CharacterTracer.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.ocrPlugins.charTracer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sourceforge.javaocr.scanner.DocumentScanner;
import net.sourceforge.javaocr.scanner.DocumentScannerListenerAdaptor;
import net.sourceforge.javaocr.scanner.PixelImage;

/**
 * Saves all the characters in an image to an output directory individually.
 * @author William Whitney
 */
public class CharacterTracer extends DocumentScannerListenerAdaptor
{

    private DocumentScanner documentScanner = new DocumentScanner();
    private BufferedImage bfImage;
    private Graphics2D bfImageGraphics;

    public BufferedImage getTracedImage(File inputImage)
    {
        try
        {
            bfImage = ImageIO.read(inputImage);
            bfImageGraphics = bfImage.createGraphics();

            Image img = ImageIO.read(inputImage);
            PixelImage pixelImage = new PixelImage(img);
            pixelImage.toGrayScale(true);
            pixelImage.filter();
            documentScanner.scan(pixelImage, this, 0, 0, pixelImage.width, pixelImage.height);
        }
        catch (IOException ex)
        {
            LOG.log(Level.SEVERE, null, ex);
        }
        bfImageGraphics.dispose();

        return bfImage;
    }

    @Override
    public void processChar(PixelImage pixelImage, int x1, int y1, int x2, int y2, int rowY1, int rowY2)
    {
        try
        {
            bfImageGraphics.setStroke(new BasicStroke(4));
            bfImageGraphics.setColor(Color.red);
            bfImageGraphics.drawRect(x1, y1, x2 - x1, y2 - y1);
        }
        catch (Exception ex)
        {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processSpace(PixelImage pixelImage, int x1, int y1, int x2, int y2)
    {
        try
        {
            bfImageGraphics.setStroke(new BasicStroke(4));
            bfImageGraphics.setColor(Color.yellow);
            bfImageGraphics.drawRect(x1, y1, x2 - x1, y2 - y1);
        }
        catch (Exception ex)
        {
           LOG.log(Level.SEVERE, null, ex);
        }
    }
    private static final Logger LOG = Logger.getLogger(CharacterTracer.class.getName());
}

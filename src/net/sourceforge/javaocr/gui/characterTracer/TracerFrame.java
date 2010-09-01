// TracerFrame.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.characterTracer;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Frame to show BufferedImage.
 * @author William Whitney
 */
public class TracerFrame extends JFrame
{
    public static final long serialVersionUID = 0;

    public TracerFrame(BufferedImage img)
    {
        //Set Title
        this.setTitle("Image Tracer");

        //Add image panel
        ImagePanel imgPanel = new ImagePanel(img);

        //Add to view
        this.add(imgPanel);

        //Set frame width
        this.setSize(img.getWidth(), img.getHeight());

    }
    
    public void showFrame()
    {
        this.setVisible(true);
    }


    private static final Logger LOG = Logger.getLogger(TracerFrame.class.getName());
}

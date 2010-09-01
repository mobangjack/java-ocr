// ImagePanel.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.characterTracer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * JPanel for showing images.
 * @author William Whitney
 */
public class ImagePanel extends JPanel
{
    private BufferedImage img;
    public static final long serialVersionUID = 0;

    public ImagePanel(BufferedImage img)
    {
        this.img = img;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(img, 0, 0, null);
    }
    private static final Logger LOG = Logger.getLogger(ImagePanel.class.getName());
}

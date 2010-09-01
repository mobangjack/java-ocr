// OCRDisplay.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui;

import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Provides a display for text.
 * @author William Whitney
 */
public class OCRDisplay extends JFrame
{

    public static final long serialVersionUID = 1;

    public OCRDisplay(String displayText)
    {
        this.setTitle("JavaOCR Text Viewer");
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(displayText);

        JScrollPane sPane = new JScrollPane(textArea);
        this.add(sPane);
        this.setSize(400, 600);
    }

    public void showWindow()
    {
        this.setVisible(true);
    }
    private static final Logger LOG = Logger.getLogger(OCRDisplay.class.getName());
}

// HandwritingRecognizer.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.handwritingRecognizer;

import java.awt.BorderLayout;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import net.sourceforge.javaocr.gui.GUIController;

/**
 * Provides a tab for the hand writing recognition module.
 * @author William Whitney
 */
public class HandwritingRecognizer extends JPanel
{
    public static final long serialVersionUID = 0;
    private final GUIController guiController;

    public HandwritingRecognizer(GUIController guiController)
    {
        this.guiController = guiController;

        //Set Layout
        this.setLayout(new BorderLayout());

        //Add Description
        this.add(getDescription(), BorderLayout.NORTH);

        //Add Inner Tab Pane
        this.add(getInnerTabPane(), BorderLayout.CENTER);
    }

    private JTextArea getDescription()
    {
        TitledBorder border = new TitledBorder("Description");
        JTextArea textArea = new JTextArea();
        textArea.setBorder(border);
        textArea.setEditable(false);
        textArea.setBackground(this.getBackground());
        String desc = "This feature learns to recognize handwriting for a specific individual.";

        textArea.setText(desc);
        return textArea;
    }

    private JTabbedPane getInnerTabPane()
    {
        JTabbedPane pane = new JTabbedPane();

        pane.add("1. Train", guiController.getHandWriteTrainingPanel());

        pane.add("2. Configure", guiController.getHandWriteConfigPanel());

        pane.add("3. Process", guiController.getHandWriteProcess());

        return pane;
    }
    private static final Logger LOG = Logger.getLogger(HandwritingRecognizer.class.getName());
}

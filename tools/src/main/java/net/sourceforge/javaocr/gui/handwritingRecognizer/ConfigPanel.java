// ConfigPanel.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.handwritingRecognizer;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import net.sourceforge.javaocr.gui.GUIController;

/**
 * Provides a configuration panel for the handwriting feature.
 * @author William Whitney
 */
public class ConfigPanel extends JPanel
{

    public static final long serialVersionUID = 0;
    private final GUIController guiController;
    private JRadioButton mseRec;
    private JRadioButton ratioAnz;

    public ConfigPanel(GUIController guiController)
    {
        this.guiController = guiController;

        //Set Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Add Processing Options Panel
        this.add(getProcessingOptionsPanel());

    }

    public boolean isMSEEnabled()
    {
        return this.mseRec.isSelected();
    }

    public boolean isRatioAnzEnabled()
    {
        return this.ratioAnz.isSelected();
    }

    private JPanel getProcessingOptionsPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new TitledBorder("Recognition Options"));
        panel.add(getPanelDescription());

        panel.add(getRecognizerCheckboxes());

        return panel;
    }

    private JTextArea getPanelDescription()
    {
        JTextArea area = new JTextArea();
        area.setBackground(this.getBackground());
        area.setEditable(false);

        String desc = "Select the desired Optical Character Recognition recognition option below:";

        area.setText(desc);
        return area;
    }

    private JPanel getRecognizerCheckboxes()
    {
        int rows = 3;
        int cols = 1;

        JPanel panel = new JPanel(new GridLayout(rows, cols));

        mseRec = new JRadioButton("Mean Square Recognizer");
        panel.add(mseRec);

        ratioAnz = new JRadioButton("Character Aspect Ratio Analyzer");
        panel.add(ratioAnz);

        ButtonGroup group = new ButtonGroup();
        group.add(mseRec);
        group.add(ratioAnz);

        return panel;
    }
    private static final Logger LOG = Logger.getLogger(ConfigPanel.class.getName());
}

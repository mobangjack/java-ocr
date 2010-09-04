// TrainingPanel.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.handwritingRecognizer;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.sourceforge.javaocr.gui.GUIController;

/**
 * Provides a training panel for the handwriting feature.
 * @author William Whitney
 */
public class TrainingPanel extends JPanel
{

    public static final long serialVersionUID = 0;
    private final GUIController guiController;
    private JTextField trainingImgLoc;
    private JCheckBox zeroToNineCheckbox;
    private JCheckBox lowCaseAtoZCheckbox;
    private JCheckBox upCaseAtoZCheckbox;

    /**
     * Default constructor.
     * @param guiController
     */
    public TrainingPanel(GUIController guiController)
    {
        this.guiController = guiController;
        trainingImgLoc = new JTextField(40);

        //Set Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Add Initial File Selector
        this.add(getTrainingImageLoc(trainingImgLoc));

        //Add Training Options Panel
        this.add(getTrainingImageOptions());

    }

    public File getTrainingImgFile()
    {
        return new File(trainingImgLoc.getText());
    }

    public boolean isLearnLowerCaseAtoZ()
    {
        return lowCaseAtoZCheckbox.isSelected();
    }

    public boolean isLearnUpCaseAtoZ()
    {
        return upCaseAtoZCheckbox.isSelected();
    }

    public boolean isLearnZeroToNine()
    {
        return zeroToNineCheckbox.isSelected();
    }

    private JPanel getTrainingImageOptions()
    {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new TitledBorder("Training Options"));

        JTextArea helpLabel = new JTextArea("Each line of the training image should be ordered"
                + " as the selected checkboxes.\nEx. Selecting 0-9 would mean the training "
                + "image first line contains characters\n0 to 9.");
        helpLabel.setBackground(this.getBackground());
        helpLabel.setEditable(false);
        panel.add(helpLabel);

        panel.add(getCheckboxPanel());

        return panel;
    }

    private JPanel getCheckboxPanel()
    {
        int rows = 4;
        int cols = 2;

        JPanel panel = new JPanel(new GridLayout(rows, cols));
        zeroToNineCheckbox = new JCheckBox("Learn Line of Characters 0-9");
        panel.add(zeroToNineCheckbox);

        lowCaseAtoZCheckbox = new JCheckBox("Learn Line of Characters a-z");
        panel.add(lowCaseAtoZCheckbox);

        upCaseAtoZCheckbox = new JCheckBox("Learn Line of Characters A-Z");
        panel.add(upCaseAtoZCheckbox);

        return panel;
    }

    private JPanel getTrainingImageLoc(JTextField trainingImgLoc)
    {
        JPanel targPanel = new JPanel();
        targPanel.setBorder(new TitledBorder("Select Training Image"));
        targPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Location: ");
        targPanel.add(label);

        targPanel.add(trainingImgLoc);

        JButton button = new JButton("Select");
        button.addActionListener(getSelectTrainingImgBtnAction());
        targPanel.add(button);

        return targPanel;

    }

    private ActionListener getSelectTrainingImgBtnAction()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    trainingImgLoc.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }
    private static final Logger LOG = Logger.getLogger(TrainingPanel.class.getName());
}

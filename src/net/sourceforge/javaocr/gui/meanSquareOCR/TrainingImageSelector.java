// TrainingImageSelector.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.meanSquareOCR;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;

/**
 * Provides a panel to input training images.
 * @author William Whitney
 */
public class TrainingImageSelector extends JPanel
{

    public static final long serialVersionUID = 0;
    private JPanel trainingPanel;
    private JPanel targetPanel;
    private JTextField targetImage;
    private ArrayList<JTextField> trainingImageLocs;
    private final ArrayList<JTextField> startCharVals;
    private final ArrayList<JTextField> endCharVals;
   
    public TrainingImageSelector()
    {
        //Setup view
        trainingImageLocs = new ArrayList<JTextField>();
        startCharVals = new ArrayList<JTextField>();
        endCharVals = new ArrayList<JTextField>();
        setupPanel();
    }

    public void addTrainingImage()
    {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Training Image"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Select Image: ");
        firstPanel.add(label);

        JTextField trainImageLoc = new JTextField(40);
        firstPanel.add(trainImageLoc);
        trainingImageLocs.add(trainImageLoc);

        JButton button = new JButton("Select");
        button.addActionListener(getSelectTrainingImageListener(trainImageLoc));
        firstPanel.add(button);
        panel.add(firstPanel);


        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new FlowLayout());

        JLabel startChar = new JLabel("Start Char: ");
        secondPanel.add(startChar);


        JTextField startCharField = new JTextField(2);
        secondPanel.add(startCharField);
        startCharVals.add(startCharField);

        JLabel endChar = new JLabel("End Char: ");
        secondPanel.add(endChar);

        JTextField endCharField = new JTextField(2);
        secondPanel.add(endCharField);
        endCharVals.add(endCharField);

        panel.add(secondPanel);

        this.trainingPanel.add(panel);

    }

    public void removeTraningImage()
    {
        int compCount = trainingPanel.getComponentCount();
        if (compCount > 1)
        {
            this.trainingPanel.remove(trainingPanel.getComponentCount() - 1);
            trainingImageLocs.remove(trainingImageLocs.size() - 1);
            startCharVals.remove(startCharVals.size() - 1);
            endCharVals.remove(endCharVals.size() - 1);
        }
    }

    public boolean isTraningImagesValid()
    {
        for (JTextField imageLoc : trainingImageLocs)
        {
            File currFile = new File(imageLoc.getText());
            if (!currFile.exists())
            {
                return false;
            }
        }

        for (JTextField startChar : startCharVals)
        {
            String currStr = startChar.getText();

            if (currStr.length() != 1)
            {
                return false;
            }
        }

        for (JTextField startChar : endCharVals)
        {
            String currStr = startChar.getText();

            if (currStr.length() != 1)
            {
                return false;
            }
        }

        return true;
    }

    public boolean isTargetImageValid()
    {
        File currFile = new File(getTargetImageLoc());
        return currFile.exists();
    }

    public ArrayList<TrainingImageSpec> getTrainingImages()
    {
        ArrayList<TrainingImageSpec> images = new ArrayList<TrainingImageSpec>();

        for (int i = 0; i < trainingImageLocs.size(); i++)
        {
            String trainingImageLoc = trainingImageLocs.get(i).getText();
            int startChar = startCharVals.get(i).getText().charAt(0);
            int endChar = endCharVals.get(i).getText().charAt(0);

            TrainingImageSpec newImage = new TrainingImageSpec();
            newImage.setFileLocation(trainingImageLoc);
            newImage.setCharRange(new CharacterRange(startChar, endChar));

            images.add(newImage);

        }
        return images;
    }

    public String getTargetImageLoc()
    {
        return targetImage.getText();
    }

    private void setupPanel()
    {
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.trainingPanel = getTrainingPanel();
        this.targetPanel = getTargetPanel();

        this.add(trainingPanel);
        this.add(targetPanel);

        addTrainingImage();
    }

    private JPanel getTrainingPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    private JPanel getTargetPanel()
    {
        JPanel targPanel = new JPanel();
        targPanel.setBorder(new TitledBorder("Target Image"));
        targPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Select Image: ");
        targPanel.add(label);

        targetImage = new JTextField(40);
        targPanel.add(targetImage);

        JButton button = new JButton("Select");
        button.addActionListener(getImageTargetAction());
        targPanel.add(button);

        return targPanel;

    }

    private ActionListener getImageTargetAction()
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
                    targetImage.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }

    private ActionListener getSelectTrainingImageListener(final JTextField trainImageLoc)
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
                    trainImageLoc.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }
    private static final Logger LOG = Logger.getLogger(TrainingImageSelector.class.getName());
}

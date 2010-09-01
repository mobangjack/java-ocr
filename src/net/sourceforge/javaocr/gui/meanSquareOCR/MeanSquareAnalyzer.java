// MeanSquareAnalyzer.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.meanSquareOCR;

import net.sourceforge.javaocr.gui.OCRDisplay;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import net.sourceforge.javaocr.gui.GUIController;

/**
 * Performs mean square character analysis.
 * @author William Whitney
 */
public class MeanSquareAnalyzer extends JPanel
{

    public static final long serialVersionUID = 0;
    private final GUIController guiController;
    private final TrainingImageSelector trainingImageSelector;
    private static Dimension btnSize = new Dimension(150, 25);

    public MeanSquareAnalyzer(GUIController guiController)
    {
        this.guiController = guiController;

        //Set Layout
        this.setLayout(new BorderLayout());

        //Add Description
        this.add(getDescription(), BorderLayout.NORTH);

        //Get Training Image Selector
        this.trainingImageSelector = new TrainingImageSelector();
        this.add(trainingImageSelector, BorderLayout.CENTER);

        //Add Training Image Buttons
        this.add(getButtons(), BorderLayout.EAST);

    }

    private JPanel getButtons()
    {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));

        //Create buttons
        JButton addBtn = new JButton("Add Training Image");
        addBtn.addActionListener(getAddBtnListener());

        JButton delBtn = new JButton("Delete Traning Image");
        delBtn.addActionListener(getDelBtnListener());

        JButton processBtn = new JButton("Process Image");
        processBtn.addActionListener(getProcessBtnListener());

        //Set button size
        delBtn.setMinimumSize(btnSize);
        delBtn.setPreferredSize(btnSize);
        delBtn.setMaximumSize(btnSize);

        addBtn.setMinimumSize(btnSize);
        addBtn.setPreferredSize(btnSize);
        addBtn.setMaximumSize(btnSize);

        processBtn.setMinimumSize(btnSize);
        processBtn.setPreferredSize(btnSize);
        processBtn.setMaximumSize(btnSize);

        //Add to panel
        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        btnPanel.add(processBtn);


        return btnPanel;
    }

    private JTextArea getDescription()
    {
        TitledBorder border = new TitledBorder("Description");
        JTextArea textArea = new JTextArea();
        textArea.setBorder(border);
        textArea.setEditable(false);
        textArea.setBackground(this.getBackground());
        String desc = "This feature utilizes a set of training images each with "
                + "a specific character\nrange to perform mean square character"
                + " recognition on the target image.";

        textArea.setText(desc);
        return textArea;
    }

    private ActionListener getAddBtnListener()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                trainingImageSelector.addTrainingImage();
                guiController.repaint();
            }
        };
    }

    private ActionListener getDelBtnListener()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                trainingImageSelector.removeTraningImage();
                guiController.repaint();
            }
        };
    }

    private ActionListener getProcessBtnListener()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {

                if (!trainingImageSelector.isTraningImagesValid())
                {
                    JOptionPane.showMessageDialog(null, "Check training images!");
                }
                else if (!trainingImageSelector.isTargetImageValid())
                {
                    JOptionPane.showMessageDialog(null, "Check target image!");
                }
                else
                {
                    try
                    {
                        ArrayList<TrainingImageSpec> images = trainingImageSelector.getTrainingImages();
                        String targImageLoc = trainingImageSelector.getTargetImageLoc();
                        OCRDisplay ocrDisplay = new OCRDisplay(guiController.performMSEOCR(images, targImageLoc));
                        ocrDisplay.showWindow();
                        JOptionPane.showMessageDialog(null, "Done!");
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error!");
                        Logger.getLogger(MeanSquareAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }
    private static final Logger LOG = Logger.getLogger(MeanSquareAnalyzer.class.getName());
}

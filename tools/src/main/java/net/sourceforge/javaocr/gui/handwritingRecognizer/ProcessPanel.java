// ProcessPanel.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.handwritingRecognizer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.sourceforge.javaocr.gui.GUIController;
import net.sourceforge.javaocr.gui.OCRDisplay;

/**
 * Provides a process panel for the handwriting feature.
 * @author William Whitney
 */
public class ProcessPanel extends JPanel
{

    public static final long serialVersionUID = 0;
    private final GUIController guiController;
    private JTextField targetImgLoc;

    public ProcessPanel(GUIController guiController)
    {
        this.guiController = guiController;
        targetImgLoc = new JTextField(40);

        //Set Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Add Initial File Selector
        this.add(getTargetImageLoc(targetImgLoc));

    }

    public File getTargetFile()
    {
        return new File(targetImgLoc.getText());
    }

    private ActionListener getProcessBtnListener()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                HandWritingFormProcessor processor = new HandWritingFormProcessor(guiController);
                if (processor.isValid())
                {
                    try
                    {
                        String text = guiController.processHandwriting(processor.getSourceImageFile(),
                                processor.getTargetImageFile(), processor);
                        OCRDisplay display = new OCRDisplay(text);
                        display.showWindow();
                    }
                    catch (Exception ex)
                    {
                        Logger.getLogger(ProcessPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }

    private JPanel getTargetImageLoc(JTextField targetImgLoc)
    {
        JPanel targPanel = new JPanel();
        targPanel.setBorder(new TitledBorder("Select Target Image"));
        targPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Location: ");
        targPanel.add(label);

        targPanel.add(targetImgLoc);

        JButton button = new JButton("Select");
        button.addActionListener(getSelectTargetImgBtnAction());
        targPanel.add(button);

        JButton processBtn = new JButton("Process");

        processBtn.addActionListener(getProcessBtnListener());
        targPanel.add(processBtn);

        return targPanel;

    }

    private ActionListener getSelectTargetImgBtnAction()
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
                    targetImgLoc.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }
    private static final Logger LOG = Logger.getLogger(ProcessPanel.class.getName());
}

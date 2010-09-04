// LineExtractionPanel.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.lineExtractor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.sourceforge.javaocr.gui.GUIController;

/**
 * Provides a panel to save out individual characters as images.
 * @author William Whitney
 */
public class LineExtractionPanel extends JPanel
{

    public static final long serialVersionUID = 0;
    private JTextField imageLoc;
    private JTextField outputDir;
    private final GUIController guiController;

    public LineExtractionPanel(GUIController guiController)
    {
        this.guiController = guiController;

        //Set Layout
        this.setLayout(new BorderLayout());

        //Add Description
        this.add(getDescription(), BorderLayout.NORTH);

        //Get Control Panel
        this.add(getControlPanel(), BorderLayout.CENTER);

        //Add Extract button
        this.add(getExtractButton(), BorderLayout.EAST);
    }

    private JTextArea getDescription()
    {
        TitledBorder border = new TitledBorder("Description");
        JTextArea textArea = new JTextArea();
        textArea.setBorder(border);
        textArea.setEditable(false);
        textArea.setBackground(this.getBackground());
        String desc = "This feature will isolate lines within an image and save them individually\nto the selected output directory.";

        textArea.setText(desc);
        return textArea;
    }

    private JPanel getControlPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //Create Image Selector
        panel.add(getImageSelectPanel());

        //Create Ouput Dir Selector
        panel.add(getOutputDirSelector());



        return panel;
    }

    private JPanel getExtractButton()
    {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());

        JButton btn = new JButton("Extract Lines");
        btn.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                File imageFile = new File(imageLoc.getText());
                File outDir = new File(outputDir.getText());

                if (!imageFile.exists() || !outDir.exists())
                {
                    JOptionPane.showMessageDialog(null, "Check image or output directory!");
                }
                else
                {
                    guiController.extractLines(imageFile, outDir);
                    JOptionPane.showMessageDialog(null, "Done!");
                }
            }
        });

        btnPanel.add(btn);

        return btnPanel;
    }

    private JPanel getImageSelectPanel()
    {
        JPanel imageSel = new JPanel();
        imageSel.setLayout(new BoxLayout(imageSel, BoxLayout.X_AXIS));
        JLabel imgLable = new JLabel("Select Image: ");
        imageSel.add(imgLable);

        imageLoc = new JTextField(40);
        imageSel.add(imageLoc);

        JButton button = new JButton("Select");
        button.addActionListener(getImageSelectAction());
        imageSel.add(button);

        return imageSel;
    }

    private JPanel getOutputDirSelector()
    {
        JPanel dirSelectPanel = new JPanel();
        dirSelectPanel.setLayout(new BoxLayout(dirSelectPanel, BoxLayout.X_AXIS));
        JLabel dirLabel = new JLabel("Select Output Dir: ");
        dirSelectPanel.add(dirLabel);

        outputDir = new JTextField(40);
        dirSelectPanel.add(outputDir);

        JButton button = new JButton("Select");
        button.addActionListener(getOutDirSelectAction());
        dirSelectPanel.add(button);

        return dirSelectPanel;
    }

    private ActionListener getImageSelectAction()
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
                    imageLoc.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }

    private ActionListener getOutDirSelectAction()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    outputDir.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        };
    }
    private static final Logger LOG = Logger.getLogger(LineExtractionPanel.class.getName());
}

// CharacterTracerPanel.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.characterTracer;

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
public class CharacterTracerPanel extends JPanel
{

    public static final long serialVersionUID = 0;
    private JTextField imageLoc;
    private final GUIController guiController;

    public CharacterTracerPanel(GUIController guiController)
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
        String desc = "This feature will isolate characters within an image and trace them in the GUI.";

        textArea.setText(desc);
        return textArea;
    }

    private JPanel getControlPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //Create Image Selector
        panel.add(getImageSelectPanel());

        return panel;
    }

    private JPanel getExtractButton()
    {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());

        JButton btn = new JButton("Trace Characters");
        btn.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                File imageFile = new File(imageLoc.getText());


                if (!imageFile.exists())
                {
                    JOptionPane.showMessageDialog(null, "Source Image Does Not Exist!");
                }
                else
                {
                    guiController.traceChars(imageFile);
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
    private static final Logger LOG = Logger.getLogger(CharacterTracerPanel.class.getName());
}

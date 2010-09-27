// OCRMenuBar.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;


/**
 * Provides the menu bar for the GUI.
 * @author William Whitney
 */
public class OCRMenuBar extends JMenuBar
{

    public static final long serialVersionUID = 0;
    private final GUIController guiController;

    public OCRMenuBar(GUIController guiController)
    {
        this.guiController = guiController;

        //Add File Menu
        addFileMenu();

        //Add About Menu
        addHelpMenu();
    }

    private void addFileMenu()
    {
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitBtn = new JMenuItem("Exit");
        exitBtn.addActionListener(getExitBtnListener());
        fileMenu.add(exitBtn);
        this.add(fileMenu);
    }

    private void addHelpMenu()
    {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(getAboutBtnListener());
        helpMenu.add(about);
        this.add(helpMenu);
    }

    private ActionListener getExitBtnListener()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        };
    }

    private ActionListener getAboutBtnListener()
    {
        return new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Java OCR\n Version: " + "1.102"
                        + "\n" + "https://sourceforge.net/projects/javaocr/" 
                        , "About", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }
    private static final Logger LOG = Logger.getLogger(OCRMenuBar.class.getName());
}

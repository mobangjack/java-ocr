// MainFrame.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Provides the main frame that the GUI utilizes.
 * @author William Whitney
 */
public class MainFrame extends JFrame
{

    public static final long serialVersionUID = 1;
    public static final int k_width = 700;
    public static final int k_height = 500;
    private final GUIController guiController;

    public MainFrame(GUIController guiController)
    {
        this.guiController = guiController;
        configureFrame();
        addComponents();
    }

    private void configureFrame()
    {
        this.setSize(k_width, k_height);
        this.setMinimumSize(new Dimension(k_width, k_height));
        this.setTitle("Java OCR");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponents()
    {
        //Create Internal frame panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //Get Menu
        JMenuBar menuBar = new OCRMenuBar(guiController);
        panel.add(menuBar, BorderLayout.NORTH);

        //Get internal tabbed pane
        JTabbedPane tabPane = new OCRTabPane(guiController);
        panel.add(tabPane, BorderLayout.CENTER);

        this.add(panel);
    }
    private static final Logger LOG = Logger.getLogger(MainFrame.class.getName());
}

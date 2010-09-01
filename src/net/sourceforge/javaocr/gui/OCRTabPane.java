// OCRTabPane.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui;

import java.util.logging.Logger;
import net.sourceforge.javaocr.gui.meanSquareOCR.MeanSquareAnalyzer;
import net.sourceforge.javaocr.gui.characterExtractor.CharExtractionPanel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import net.sourceforge.javaocr.gui.characterTracer.CharacterTracerPanel;
import net.sourceforge.javaocr.gui.handwritingRecognizer.HandwritingRecognizer;
import net.sourceforge.javaocr.gui.lineExtractor.LineExtractionPanel;

/**
 * Provides a tabbed pane to hold the separate capabilities of the system.
 * @author William Whitney
 */
public class OCRTabPane extends JTabbedPane
{

    public static final long serialVersionUID = 0;
    private final GUIController guiController;

    public OCRTabPane(GUIController guiController)
    {
        this.guiController = guiController;

        JPanel characterTracer = new CharacterTracerPanel(guiController);
        this.add("Character Tracer", characterTracer);

        JPanel characterExtractor = new CharExtractionPanel(guiController);
        this.add("Character Extractor", characterExtractor);

        JPanel lineExtractor = new LineExtractionPanel(guiController);
        this.add("Line Extractor", lineExtractor);

        JPanel meanSquareAnalyzer = new MeanSquareAnalyzer(guiController);
        this.add("Mean Square OCR Recognizer", meanSquareAnalyzer);

        JPanel handwritingRecognizer = new HandwritingRecognizer(guiController);
        this.add("[Pre-Alpha] Handwriting Recognizer", handwritingRecognizer);

    }
    private static final Logger LOG = Logger.getLogger(OCRTabPane.class.getName());
}

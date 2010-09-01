// GUIController.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.UIManager;
import net.sourceforge.javaocr.gui.characterTracer.TracerFrame;
import net.sourceforge.javaocr.gui.handwritingRecognizer.ConfigPanel;
import net.sourceforge.javaocr.gui.handwritingRecognizer.HandWritingFormProcessor;
import net.sourceforge.javaocr.gui.handwritingRecognizer.ProcessPanel;
import net.sourceforge.javaocr.gui.handwritingRecognizer.TrainingPanel;
import net.sourceforge.javaocr.gui.meanSquareOCR.TrainingImageSpec;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.OCRScanner;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImageLoader;
import net.sourceforge.javaocr.ocrPlugins.charExtractor.CharacterExtractor;
import net.sourceforge.javaocr.ocrPlugins.charTracer.CharacterTracer;
import net.sourceforge.javaocr.ocrPlugins.lineExtractor.LineExtractor;
import net.sourceforge.javaocr.ocrPlugins.handWriting.HandwritingOCR;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.CharacterRange;
import net.sourceforge.javaocr.ocrPlugins.mseOCR.TrainingImage;

/**
 * Allows all GUI elements to be controlled.
 * @author William Whitney
 */
public class GUIController
{

    private MainFrame mainFrame;
    private TrainingPanel handWriteTrainingPanel;
    private ConfigPanel handWriteConfigPanel;
    private ProcessPanel handWriteProcess;

    public GUIController()
    {
        setLookandFeel();
        handWriteTrainingPanel = new TrainingPanel(this);
        handWriteConfigPanel = new ConfigPanel(this);
        handWriteProcess = new ProcessPanel(this);
        mainFrame = new MainFrame(this);
    }

    public String performMSEOCR(ArrayList<TrainingImageSpec> imgs, String targImageLoc) throws Exception
    {
        OCRScanner ocrScanner = new OCRScanner();
        HashMap<Character, ArrayList<TrainingImage>> trainingImages = getTrainingImageMap(imgs);
        ocrScanner.addTrainingImages(trainingImages);
        BufferedImage targetImage = ImageIO.read(new File(targImageLoc));
        String text = ocrScanner.scan(targetImage, 0, 0, 0, 0, null);
        return text;
    }

    public void traceChars(File imageFile)
    {
        CharacterTracer tracer = new CharacterTracer();
        BufferedImage img = tracer.getTracedImage(imageFile);


        int width = img.getWidth();


        if (width > 1000)
        {
            //Make image always 1000px wide
            double scaleAmount = 1000.0 / width;

            AffineTransform tx = new AffineTransform();
            tx.scale(scaleAmount, scaleAmount);

            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

            img = op.filter(img, null);
        }

        TracerFrame tFrame = new TracerFrame(img);
        tFrame.showFrame();
    }

    public void extractLines(File imageFile, File outDir)
    {
        LineExtractor slicer = new LineExtractor();
        slicer.slice(imageFile, outDir);
    }

    public void extractChars(File inputImage, File outputDir, int std_width, int std_height)
    {
        CharacterExtractor slicer = new CharacterExtractor();
        slicer.slice(inputImage, outputDir, std_width, std_height);
    }

    private HashMap<Character, ArrayList<TrainingImage>> getTrainingImageMap(ArrayList<TrainingImageSpec> imgs) throws Exception
    {
        TrainingImageLoader loader = new TrainingImageLoader();
        HashMap<Character, ArrayList<TrainingImage>> trainingImageMap = new HashMap<Character, ArrayList<TrainingImage>>();

        for (int i = 0; i < imgs.size(); i++)
        {
            loader.load(
                    imgs.get(i).getFileLocation(),
                    imgs.get(i).getCharRange(),
                    trainingImageMap);
        }

        return trainingImageMap;
    }

    public String processHandwriting(File sourceImage, File targetImage, HandWritingFormProcessor form) throws Exception
    {
        //Make temp directory
        File tempDir = new File("./temp");
        if (!tempDir.mkdir())
        {
            removeAllFiles(tempDir.listFiles());
        }

        //Extract all the lines to it
        LineExtractor lineExtractor = new LineExtractor();
        lineExtractor.slice(sourceImage, tempDir);

        ArrayList<TrainingImageSpec> imgs = new ArrayList<TrainingImageSpec>();
        File[] files = sortFiles(tempDir.listFiles());

        int linesUsed = 0;

        if (form.isLearnZeroToNine())
        {
            TrainingImageSpec trainImage = new TrainingImageSpec();
            trainImage.setFileLocation(files[linesUsed].getAbsolutePath());
            trainImage.setCharRange(new CharacterRange((int) '0', (int) '9'));
            imgs.add(trainImage);
            linesUsed++;
        }

        if (form.isLearnLowerAtoZ())
        {
            TrainingImageSpec trainImage = new TrainingImageSpec();
            trainImage.setFileLocation(files[linesUsed].getAbsolutePath());
            trainImage.setCharRange(new CharacterRange((int) 'a', (int) 'z'));
            imgs.add(trainImage);
            linesUsed++;
        }

        if (form.isLearnUpperAtoZ())
        {
            TrainingImageSpec trainImage = new TrainingImageSpec();
            trainImage.setFileLocation(files[linesUsed].getAbsolutePath());
            trainImage.setCharRange(new CharacterRange((int) 'A', (int) 'Z'));
            imgs.add(trainImage);
            linesUsed++;
        }

        HashMap<Character, ArrayList<TrainingImage>> trainingImages = getTrainingImageMap(imgs);
        HandwritingOCR handwritingOCR = new HandwritingOCR(trainingImages);

        handwritingOCR.setEnableMSEOCR(form.isMSEOCR());
        handwritingOCR.setEnableAspectOCR(form.isAspectOCR());

        BufferedImage targetBfImage = ImageIO.read(targetImage);
        String text = handwritingOCR.scan(targetBfImage);

        removeAllFiles(files);
        tempDir.delete();

        return text;
    }

    public void showGUI()
    {
        mainFrame.setVisible(true);
    }

    public TrainingPanel getHandWriteTrainingPanel()
    {
        return handWriteTrainingPanel;
    }

    public ConfigPanel getHandWriteConfigPanel()
    {
        return handWriteConfigPanel;
    }

    public ProcessPanel getHandWriteProcess()
    {
        return handWriteProcess;
    }

    public void repaint()
    {
        mainFrame.repaint();
    }

    private void setLookandFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private File[] sortFiles(File[] files)
    {
        ArrayList<File> fileList = new ArrayList<File>();
        fileList.addAll(Arrays.asList(files));

        Collections.sort(fileList, new Comparator<File>()
        {

            public int compare(File f1, File f2)
            {
                String fileName1 = f1.getName();
                String fileName2 = f2.getName();
                return fileName1.compareTo(fileName2);
            }
        });

        for (int i = 0; i < files.length; i++)
        {
            files[i] = fileList.get(i);
        }
        return files;
    }

    private void removeAllFiles(File[] files)
    {
        for (int i = 0; i < files.length; i++)
        {
            files[i].delete();
        }
    }
    private static final Logger LOG = Logger.getLogger(GUIController.class.getName());
}

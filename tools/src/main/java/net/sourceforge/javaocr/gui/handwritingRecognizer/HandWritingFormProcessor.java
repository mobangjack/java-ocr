// HandWritingFormProcessor.java
// Copyright (c) 2010 William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package net.sourceforge.javaocr.gui.handwritingRecognizer;

import java.io.File;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sourceforge.javaocr.gui.GUIController;

/**
 * Ensures the form is valid.
 * @author William Whitney
 */
public class HandWritingFormProcessor
{

    public static final long serialVersionUID = 0;
    private final GUIController guiController;

    public HandWritingFormProcessor(GUIController guiController)
    {
        this.guiController = guiController;
    }

    public boolean isValid()
    {
        if (!getSourceImageFile().exists())
        {
            JOptionPane.showMessageDialog(null, "Source Image File Does Not Exist!");
            return false;
        }

        TrainingPanel trainPanel = guiController.getHandWriteTrainingPanel();

        if (!trainPanel.isLearnLowerCaseAtoZ()
                && !trainPanel.isLearnUpCaseAtoZ()
                && !trainPanel.isLearnZeroToNine())
        {
            JOptionPane.showMessageDialog(null, "Please Select a Training Option!");
            return false;
        }
        ConfigPanel cfgPanel = guiController.getHandWriteConfigPanel();
        if (!cfgPanel.isMSEEnabled()
                && !cfgPanel.isRatioAnzEnabled())
        {
            JOptionPane.showMessageDialog(null, "Please Select a Recognition Option!");
            return false;
        }

        if (!getTargetImageFile().exists())
        {
            JOptionPane.showMessageDialog(null, "Target Image File Does Not Exist!");
            return false;
        }

        return true;
    }

    public File getSourceImageFile()
    {
        return guiController.getHandWriteTrainingPanel().getTrainingImgFile();
    }

    public File getTargetImageFile()
    {
        return guiController.getHandWriteProcess().getTargetFile();
    }

    public boolean isLearnZeroToNine()
    {
        TrainingPanel trainPanel = guiController.getHandWriteTrainingPanel();
        return trainPanel.isLearnZeroToNine();
    }

    public boolean isLearnLowerAtoZ()
    {
        TrainingPanel trainPanel = guiController.getHandWriteTrainingPanel();
        return trainPanel.isLearnLowerCaseAtoZ();
    }

    public boolean isLearnUpperAtoZ()
    {
        TrainingPanel trainPanel = guiController.getHandWriteTrainingPanel();
        return trainPanel.isLearnUpCaseAtoZ();
    }

    public boolean isMSEOCR()
    {
        ConfigPanel cfgPanel = guiController.getHandWriteConfigPanel();
        return cfgPanel.isMSEEnabled();
    }

    public boolean isAspectOCR()
    {
        ConfigPanel cfgPanel = guiController.getHandWriteConfigPanel();
        return cfgPanel.isRatioAnzEnabled();
    }

    private static final Logger LOG = Logger.getLogger(HandWritingFormProcessor.class.getName());
}

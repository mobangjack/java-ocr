package net.sourceforge.javaocr.plugin;

import net.sourceforge.javaocr.filter.AbstractBaseFilter;

/**
 * process image and compute raw image moment. does not modify the image.  this filter is stateful and not thread safe
 */
public class RawMomentFilter extends AbstractBaseFilter {
    int currentX;
    int currentY;

    int a;
    int b;

    float moment;


    /**
     * filter computing moment with giveb cardinality
     * @param a
     * @param b
     */
    public RawMomentFilter(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public void process(int[] data, int width, int height, int originX, int originY, int boxW, int boxH) {
        int scanStart;
        int scanEnd;
        final int maxRow = originY + boxH;
        for (int j = originY, currentY = 0; j < maxRow; j++, currentY++) {
            scanStart = originX + j * width;
            scanEnd = scanStart + boxW;
            for (int i = scanStart, currentX = 0; i < scanEnd; i++, currentX++) {
                computeIndivudualMoment(data[i], currentX, currentY);
            }
        }
    }

    /**
     * compute moment from single pixel
     * @param pixel  value of pixel in question
     * @param currentX current x cooordinat inside scan
     * @param currentY  current y coordinate inside scan
     * @return
     */
    private void computeIndivudualMoment(int pixel, int currentX, int currentY) {
       moment +=  Math.pow(currentX, a) * Math.pow(currentY ,b) * pixel;
    }


}

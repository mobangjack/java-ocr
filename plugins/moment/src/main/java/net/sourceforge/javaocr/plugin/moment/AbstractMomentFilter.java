package net.sourceforge.javaocr.plugin.moment;

import net.sourceforge.javaocr.Image;
import net.sourceforge.javaocr.ImageFilter;

/**
 * base functionality for moment filters (traversal
 */
public abstract class AbstractMomentFilter implements ImageFilter {
    int currentX;
    int currentY;
    int p;
    int q;
    double moment;

    public AbstractMomentFilter(int p, int q) {
        this.p = p;
        this.q = q;
    }


    protected abstract void computeIndividualMoment(int pixel, int currentX, int currentY);

    /**
     * navigate through while image
     *
     * @param image
     */
    public void process(Image image) {
        int x;
        for (int y = 0; y < image.getHeight(); y++) {

            image.iterateH(y);
            x = 0;
            while (image.hasNext())
                computeIndividualMoment(image.next(), x++, y);
        }
    }

    public double getMoment() {
        return moment;
    }
}

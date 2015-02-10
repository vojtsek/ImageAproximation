package image_aproximation;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author Vojtech Hudecek
 * Serves to provide essential methods to manipulate the individual in the population
 */
public interface IInd extends Comparable<IInd> {
    public void computeFitness(int jump);
    public int getFitness();
    public int getWidth();
    public int getHeight();
    public int getRGB(int x, int y);
    public void repaint();
    public void refresh();
    public void setMutProb(double prob);
    public void setContent(LinkedList<?> list);
    public LinkedList<? extends AReproductable> getList();
    public void setContent(IInd i1, IInd i2);
    public void setScaleF(double f);
    public BufferedImage getImage();
    public void changeCount(boolean incr);
}

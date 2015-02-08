/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package image_aproximation;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author vojcek
 */
public interface IInd extends Comparable<IInd> {
    public void computeFitness(int jump);
    public int getFitness();
    public int getWidth();
    public int getHeight();
    public int getRGB(int x, int y);
    public void repaint();
    public void refresh(boolean add);
    public void setMutProb(double prob);
    public void setContent(LinkedList<?> list);
    public LinkedList<? extends ACross> getList();
    public void setContent(IInd i1, IInd i2);
    public void setScaleF(double f);
    public BufferedImage getImage();
}

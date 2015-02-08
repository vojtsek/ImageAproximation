/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package image_aproximation;

import java.awt.AlphaComposite;
import java.awt.Color;

/**
 *
 * @author vojcek
 */
public abstract class ACross {
    public abstract int getX();
    public abstract int getY();
    public abstract Color getColor();
    public abstract double getScale();
    public abstract double getRotation();
    public abstract void setX(int x);
    public abstract void setY(int y);
    public abstract void setColor(Color c);
    public abstract void setScale(double s);
    public abstract void setRotation(double fi);
}

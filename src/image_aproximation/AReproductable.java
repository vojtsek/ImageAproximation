package image_aproximation;

import java.awt.Color;

/**
 *
 * @author Vojtech Hudecek
 * 
 * abstract class which defines generic type which is
 * able to reproduce.
 */
public abstract class AReproductable {
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

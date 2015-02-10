package image_aproximation;

import java.awt.Color;

/**
 *
 * @author Vojtech Hudecek
 * class which represents single concrete object(polygon) that can reproduce.
 * It only holds and offers some information about the object.
 */
public class PolyInst extends AReproductable {
    public Color color;
    public double scale;
    public double rotation;
    public int x,y;

    public PolyInst(Color color, double scale, double fi, int x, int y) {
        this.color = color;
        this.scale = scale;
        this.rotation = fi;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public void setScale(double s) {
        this.scale = s;
    }

    @Override
    public void setRotation(double fi) {
        this.rotation = fi;
    }   
}

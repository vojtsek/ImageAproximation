package image_aproximation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Vojtech Hudecek
 * represents the 
 */

public class PolyShape {
    
    
    public Point basePoint;
    private double scale;
    private LinkedList<Point> vertices;
    private Color bg_col;

    public PolyShape(int x, int y) {
        bg_col = Color.WHITE;
        basePoint = new Point(x, y);
        scale = 1.0;
        vertices = new LinkedList<>();
    }
    
    public double getScale() {
        return scale;
    }
    
    public void setVertices(LinkedList<Point> v) {
        vertices = new LinkedList<>();
        for(Point p : v)
            vertices.add(new Point(p.x, p.y));
    }
    public LinkedList<Point> getPoints() {
        return vertices;
    }
    
    public void setScale(double s) {
        scale = s;
    }
    
    public int getCount() {
        return vertices.size() + 1;
    }
    
    public void setCol(Color c) {
        bg_col = c;
    }
    
    public void addVertex(int x, int y) {
        vertices.add(new Point(basePoint.x - x, basePoint.y - y));
    }
    
    public void remove() {
        if(vertices.size() > 0)
            vertices.remove(vertices.getLast());
    }
    
    public int getPosX(int x) {
        return (basePoint.x - ((int) (x * scale)));
    }
    
    public int getPosY(int y) {
        return (basePoint.y - ((int) (y * scale)));
    }
    
    private void draw_point(Point p, Graphics g) {
        g.fillOval(getPosX(p.x), getPosY(p.y), 5, 5);
    }
    
    public void draw(Graphics g) {
        g.setColor(bg_col);
        g.fillOval(basePoint.x, basePoint.y, 5, 5);
        Point last = basePoint;
        for(Point p : vertices) {
            draw_point(p, g);
            if(last != basePoint)
                g.drawLine(getPosX(last.x), getPosY(last.y), getPosX(p.x), getPosY(p.y));
            else
                g.drawLine(last.x, last.y, getPosX(p.x), getPosY(p.y));
            last = p;
        }
        if(last != basePoint)
            g.drawLine(getPosX(last.x), getPosY(last.y), basePoint.x, basePoint.y);
    }
}

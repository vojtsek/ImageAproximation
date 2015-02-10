package image_aproximation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Vojtech Hudecek
 * represents the polygon object in terms of structure, holds the points
 * and computes the coordinates
 * used by the PolyInst to actually create instances of polygons
 */

public class PolyShape {
    
    
    public Point base_point;
    private double scale;
    private LinkedList<Point> vertices;
    private Color bg_col;

    public PolyShape(int x, int y) {
        bg_col = Color.WHITE;
        base_point = new Point(x, y);
        scale = 1.0;
        vertices = new LinkedList<>();
    }
    
    public double getScale() {
        return scale;
    }
    
    /**
     * 
     * @param v list of vertices
     * instantly add vertices
     */
    public void setVertices(LinkedList<Point> v) {
        vertices = new LinkedList<>();
        for(Point p : v)
            vertices.add(new Point(p.x, p.y));
    }
    
    public LinkedList<Point> getPoints() {
        return vertices;
    }
    
    /**
     * 
     * @param s scale with which to paint this polygon
     */
    public void setScale(double s) {
        scale = s;
    }
    
    public int getCount() {
        return vertices.size() + 1;
    }
    
    public void setCol(Color c) {
        bg_col = c;
    }
    
    /**
     * 
     * @param x coordinate
     * @param y coordinate
     * adds a new vertex vertex, relative to the base_point
     */
    public void addVertex(int x, int y) {
        vertices.add(new Point(base_point.x - x, base_point.y - y));
    }
    
    /**
     * removes last vertex
     */
    public void remove() {
        if(vertices.size() > 0)
            vertices.remove(vertices.getLast());
    }
    
    /**
     * 
     * @param x relative x coordinate
     * @return absolute x coordinate
     */
    public int getPosX(int x) {
        return (base_point.x - ((int) (x * scale)));
    }
    
    /**
     * 
     * @param y relative y coordinate
     * @return absolute y coordinate
     */
    public int getPosY(int y) {
        return (base_point.y - ((int) (y * scale)));
    }
    
    /**
     * 
     * @param p holds the point coordinates
     * @param g Graphics instance
     * draws a point on the given coordinates
     */
    private void draw_point(Point p, Graphics g) {
        g.fillOval(getPosX(p.x), getPosY(p.y), 5, 5);
    }
    
    /**
     * 
     * @param g Graphics instance
     * iterates the list of points and draws border of the polygon
     */
    public void draw(Graphics g) {
        g.setColor(bg_col);
        g.fillOval(base_point.x, base_point.y, 5, 5);
        Point last = base_point;
        for(Point p : vertices) {
            draw_point(p, g);
            if(last != base_point)
                g.drawLine(getPosX(last.x), getPosY(last.y), getPosX(p.x), getPosY(p.y));
            else
                g.drawLine(last.x, last.y, getPosX(p.x), getPosY(p.y));
            last = p;
        }
        if(last != base_point)
            g.drawLine(getPosX(last.x), getPosY(last.y), base_point.x, base_point.y);
    }
}

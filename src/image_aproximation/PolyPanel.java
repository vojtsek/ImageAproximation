package image_aproximation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;    
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author Vojtech Hudecek
 * class which serves to model the polygon using mouse
 */
public class PolyPanel extends JPanel {
    private final int HEIGHT = 200;
    private final int WIDTH = 200;
    public PolyShape polygon;
    private boolean adding;
    
    public PolyPanel() {
        super();
        polygon = new PolyShape(WIDTH / 2, HEIGHT / 2);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if(adding) {
                    polygon.addVertex(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }
    
    /**
     * 
     * @param add whether adding or not
     * switch between adding states
     */
    public void setAdding(boolean add) {
        if(add) {
            adding = true;
            polygon.setScale(1.0);
            polygon.setCol(Color.GREEN);
        } else {
            adding = false;
            polygon.setScale(1.0);
            polygon.setCol(Color.WHITE);
        }
        repaint();
    }
    
    /**
     * resets the polygon
     */
    public void reset() {
        polygon = new PolyShape(WIDTH / 2, HEIGHT / 2);
        setAdding(false);
    }
    
    /**
     * removes the last added vertex
     */
    public void remove() {
        polygon.remove();
        setAdding(false);
    }
    
    /**
     * 
     * @param g Graphics instance
     * draw a polygon, uses a polygon class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if(polygon != null) {
            polygon.draw(g);
        }
    }
    
}

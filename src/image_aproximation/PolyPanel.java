/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package image_aproximation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author vojcek
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
    
    public void setAdding() {
        adding = true;
        polygon.setScale(1.0);
        polygon.setCol(Color.GREEN);
        repaint();
    }
    
    public void reset() {
        polygon = new PolyShape(WIDTH / 2, HEIGHT / 2);
        repaint();
        adding = false;
    }
    
    public void remove() {
        adding = false;
        polygon.setCol(Color.WHITE);
        polygon.remove();
        repaint();
    }
    
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

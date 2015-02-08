/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_aproximation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author vojcek
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;
    private double q;
    private int HEIGHT = 150;
    private int WIDTH = 150;

    public ImagePanel(BufferedImage img, int w, int h) {
        super();
        WIDTH = w;
        HEIGHT = h;
        setImage(img);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setWidth(int w) {
        WIDTH = w;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setHeight(int h) {
        HEIGHT = h;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void setImage(BufferedImage img) {
        if (img != null) {
            double qw = ((double) WIDTH) / img.getWidth();
            double qh = ((double) HEIGHT) / img.getHeight();
            q = (qw > qh) ? qh : qw;
            try {
                image = new BufferedImage((int) (img.getWidth() * q) - 10, (int) (img.getHeight() * q) - 10, BufferedImage.TYPE_INT_ARGB);
            } catch (IllegalArgumentException iae) {}
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.scale(q, q);
            g.drawImage(img, 0, 0, null);
            g.dispose();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        if (image != null) {
            g.drawImage(image, 5, 5, image.getWidth(), image.getHeight(), null);
        }
    }

}

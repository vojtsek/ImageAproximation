/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_aproximation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author vojcek
 */
public class ApxPanel extends JPanel implements IInd {

    private int fitness;
    private PolyShape shape;
    private int WIDTH;
    private int HEIGHT;
    private int poly_count;
    private int count = 0;
    private double mut_prob, scale_factor;
    private int MAX_POLY_COUNT = 300;
    private Graphics2D g2;
    private LinkedList<PolyInst> polygons;
    BufferedImage img, orig;
    Color bg_col;

    public ApxPanel(PolyShape p, BufferedImage o, int pc,
            double mp, double scf, Color c) {
        super();
        shape = p;
        orig = o;
        WIDTH = o.getWidth();
        HEIGHT = o.getHeight();
        count = poly_count = pc;
        scale_factor = scf;
        polygons = new LinkedList<>();
        bg_col = c;
        Random rand = new Random();
        for (int i = 0; i < MAX_POLY_COUNT; i++) {
            polygons.add(randPolygon(rand));
        }
        mut_prob = mp;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = img.createGraphics();
        refresh();
    }

    private Color randColor() {
        return new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random());
    }
    
    public BufferedImage getImage() {
        return img;
    }

    private PolyInst randPolygon(Random rand) {
        return new PolyInst(randColor(),
                Math.random() * scale_factor,
                Math.random() * Math.PI * 2,
                rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
    }

    @Override
    public void setMutProb(double p) {
        mut_prob = p;
    }

    @Override
    public synchronized void refresh() {
        // if (img == null) {
        g2.setColor(bg_col);
        //g2.clearRect(0, 0, WIDTH, HEIGHT);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < count; i++) {

            g2.setColor(polygons.get(i).color);
            shape.setScale(polygons.get(i).scale);
            int x[] = new int[shape.getCount()];
            int y[] = new int[shape.getCount()];
            int j = 1, xx, yy;
            shape.basePoint.x = polygons.get(i).x;
            shape.basePoint.y = polygons.get(i).y;
            double rot = polygons.get(i).rotation;
            xx = shape.basePoint.x;
            yy = shape.basePoint.y;
            x[0] = xx;
            y[0] = yy;
            for (Point p : shape.getPoints()) {
                xx = p.x;
                yy = p.y;
                x[j] = shape.getPosX((int) (xx * Math.cos(rot) - yy * Math.sin(rot)));
                y[j] = shape.getPosY((int) (xx * Math.sin(rot) + yy * Math.cos(rot)));

                j++;
            }
            g2.fillPolygon(x, y, j);
        }
    }

    @Override
    protected void paintComponent(Graphics g
    ) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString(Integer.toString(fitness), 10, 10);
    }

    @Override
    public void setContent(LinkedList<?> poly
    ) {
        polygons = (LinkedList<PolyInst>) poly;
    }

    @Override
    public void setContent(IInd i1, IInd i2) {
        LinkedList<? extends ACross> l1 = i1.getList();
        LinkedList<? extends ACross> l2 = i2.getList();
        Random rand = new Random();
        int size = polygons.size();
        for (int i = 0; i < size; i++) {
            ACross it = polygons.get(i), it1 = l1.get(i), it2 = l2.get(i);
            double r = Math.random();
            if (r < mut_prob) {
                it1 = it2 = randPolygon(rand);
            }
            if (r < 0.5) {
                it.setX(it2.getX());
            } else {
                it.setX(it1.getX());
            }
            if (r < 0.5) {
                it.setY(it2.getY());
            } else {
                it.setY(it1.getY());
            }
            if (r < 0.5) {
                it.setColor(it2.getColor());
            } else {
                it.setColor(it1.getColor());
            }
            if (r < 0.5) {
                it.setScale(it2.getScale());
            } else {
                it.setScale(it1.getScale());
            }

            if (r < 0.5) {
                it.setRotation(it2.getRotation());
            } else {
                it.setRotation(it1.getRotation());
            }
        }

    }

    @Override
    public void computeFitness(int jump) {
        int argb, red, green, blue, alpha;
        int argb2, red2, green2, blue2, alpha2;
        int diff = 0;
        for (int i = 0; i < WIDTH; i += jump) {
            for (int j = 0; j < HEIGHT; j += jump) {
                argb = getRGB(i, j);
                blue = argb & 255;
                green = (argb >> 8) & 255;
                red = (argb >> 16) & 255;
                alpha = (argb >> 24) & 255;

                argb2 = orig.getRGB(i, j);
                blue2 = argb2 & 255;
                green2 = (argb2 >> 8) & 255;
                red2 = (argb2 >> 16) & 255;
                alpha2 = (argb2 >> 24) & 255;

                diff += (int) Math.sqrt((int) Math.pow(blue - blue2, 2)
                        + (int) Math.pow(green - green2, 2)
                        + (int) Math.pow(red - red2, 2)
                        + (int) Math.pow(alpha - alpha2, 2));

            }
        }
        fitness = diff;
        //  System.out.println(fitness);

    }

    @Override
    public int getFitness() {
        return fitness;
    }

    @Override
    public int getRGB(int x, int y
    ) {
        return img.getRGB(x, y);
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public int compareTo(IInd t
    ) {
        if (getFitness() - t.getFitness() < 0) {
            return -1;
        } else if (getFitness() - t.getFitness() > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public LinkedList<? extends ACross> getList() {
        return polygons;
    }

    @Override
    public void setScaleF(double f) {
        scale_factor = f;
    }

    @Override
    public synchronized void changeCount(boolean incr) {
        if(incr && count < MAX_POLY_COUNT)
            count++;
        else if(count > 0)
            count--;
    }
    

}

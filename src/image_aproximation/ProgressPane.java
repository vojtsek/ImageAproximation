/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_aproximation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author vojcek
 */
public class ProgressPane extends JFrame {

    private int pop_count, WIDTH, HEIGHT, time = 0,
            cols = 5, rows, gens, survivors, pop_size, fitness_prec;
    private GridBagConstraints constr;
    private Container pane;
    private PolyShape polygon;
    private Population population;
    private double mut_prop, scale_factor, lambda;
    private LinkedList<IInd> individuals;
    private BufferedImage orig;
    JLabel gen_label, mut_label, l_label, s_label, poly_label,
            time_label, scale_label, fit_label;
    JButton shot_btt;
    Color bg_col;

    public ProgressPane(PolyShape p, int pc, BufferedImage o, double mp, double l, int s,
            int ps, double scf, int ftp, Color c) {
        super();
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pane = getContentPane();
        rows = (int) Math.floor(pc / (double) cols);
        WIDTH = cols * o.getWidth() + 50;
        HEIGHT = rows * o.getHeight() + 120;
        pane.setLayout(new GridBagLayout());
        constr = new GridBagConstraints();
        gen_label = new JLabel("Generation no.: 0");
        mut_prop = mp;
        scale_factor = scf;
        fitness_prec = ftp;
        pop_size = ps;
        bg_col = c;
        lambda = l;
        survivors = s;
        pop_count = pc;
        mut_label = new JLabel("Mutation propability: " + Double.toString(mut_prop));
        l_label = new JLabel("Lambda parameter: " + Double.toString(lambda));
        s_label = new JLabel("Number of survivors: " + Integer.toString(survivors));
        poly_label = new JLabel("Number of polygons: " + pop_size);
        time_label = new JLabel("Elapsed time: 0:00:00");
        fit_label = new JLabel("Fitness precision: " + fitness_prec);
        scale_label = new JLabel("Scale factor: " + scale_factor);
        shot_btt = new JButton("screenshot");
        final JFrame fr = this;
        shot_btt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                File outputfile = new File("image.png");
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter(
                        "Accept JPG, PNG, GIF images", "jpg", "png", "gif"));
                if (chooser.showOpenDialog(fr) == JFileChooser.APPROVE_OPTION) {
                        outputfile = chooser.getSelectedFile();
                }
                try {
                    ImageIO.write(individuals.get(0).getImage(), "png", outputfile);
                } catch (IOException ioe) {
                    System.err.println("Failed to write an image to file.");
                    ioe.printStackTrace();
                }
            }
        });
        pane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        constr.gridwidth = cols / 2;
        constr.gridy = 0;
        pane.add(gen_label, constr);
        constr.gridy = 1;
        pane.add(mut_label, constr);
        constr.gridy = 2;
        pane.add(l_label, constr);
        constr.gridy = 3;
        pane.add(s_label, constr);
        
        constr.gridy = 4;
        pane.add(shot_btt, constr);
        
        constr.gridx = cols / 2;
        constr.gridy = 0;
        pane.add(time_label, constr);
        constr.gridy = 1;
        pane.add(poly_label, constr);
        constr.gridy = 2;
        pane.add(scale_label, constr);
        constr.gridy = 3;
        pane.add(fit_label, constr);
        polygon = new PolyShape(0, 0);
        polygon.setScale(p.getScale());
        polygon.setVertices(p.getPoints());
        orig = o;
        initPopVis();
        pack();
        setVisible(true);
        
        Timer t = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ++time;
                int hours = time / 3600;
                int rem = time - hours * 3600;
                int mins = rem / 60;
                rem -= mins * 60;
                String result = String.format("%02d:%02d:%02d", hours, mins, rem );
                time_label.setText("Elapsed time: " + result);
            }
        });
        t.start();
    }

    public void setMutProp(double mp) {
        mut_prop = mp;
        mut_label.setText("Mutation propability: " + mut_prop);
        for (IInd i : individuals) {
            i.setMutProb(mut_prop);
        }
    }

    public void setLambda(double l) {
        lambda = l;
        l_label.setText("Lambda parameter: " + Double.toString(lambda));
        population.setLambda(l);
    }

    public void setSurv(int s) {
        survivors = s;
        s_label.setText("Number of survivors: " + Integer.toString(survivors));
        population.setSurvivors(survivors);
    }
    
    public void setScaleF(double f) {
        scale_factor = f;
        scale_label.setText("Scale factor: " + scale_factor);
        for(IInd p : individuals) {
            p.setScaleF(scale_factor);
        }
    }
    
    public void changeCount(boolean incr) {
        for(IInd i : individuals) {
            i.changeCount(incr);
        }
        if(incr) {
            poly_label.setText("Number of polygons: " + (++pop_size));
        } else if(pop_size > 0) {
            poly_label.setText("Number of polygons: " + (--pop_size));
        }
    }
    
    public void setFitnessP(int p) {
        fitness_prec = p;
        fit_label.setText("Fitness precision: " + fitness_prec);
        population.setFitnessP(p);
    }

    public void initPopVis() {
        individuals = new LinkedList<>();
        constr.ipadx = 1;
        constr.ipady = 1;
        constr.gridwidth = 1;
        for (int i = 0; i < rows; i++) {
            constr.gridy = i + 6;
            for (int j = 0; j < cols; j++) {
                constr.gridx = j;
                ApxPanel pnl = new ApxPanel(polygon, orig, pop_size,
                        mut_prop, scale_factor, bg_col);
                pane.add(pnl, constr);
                individuals.add(pnl);
            }
        }
        population = new Population(individuals, pop_count, lambda, survivors, fitness_prec);
        population.reproduce(true);
    }

    public void next(boolean rep) {
        gen_label.setText("Generation no.: " + Integer.toString(gens++));
        population.reproduce(rep);
    }
}

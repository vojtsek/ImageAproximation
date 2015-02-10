/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_aproximation;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author vojcek
 */
public class GUI {

    private JFrame main_frame;
    private Container pane;
    private final PolyPanel poly_panel = new PolyPanel();
    private SwingWorker<String, String> sw;
    private GridBagConstraints constr;
    private double mutation_prop = 0.02, lambda = 10.0, scale_factor = 2.0;
    private ProgressPane pp;
    private int pop_count = 15, survivors = 5, pop_size = 50, fitness_prec = 3,
            img_width = 150, img_height = 150;
    private final ImagePanel image_panel = new ImagePanel(null, img_width, img_height);
    private final int WIDTH = 650;
    private final int HEIGHT = 550;
    private BufferedImage image;
    private Color bg_col = Color.BLACK;

    final JButton incr_button = new JButton("Add polygon");
    final JButton decr_button = new JButton("Remove polygon");
    final JButton add_btt = new JButton("Add vertices");
    final JLabel img_label = new JLabel("Image to aproximate:");
    final JButton rm_btt = new JButton("Remove vertex");
    final JButton start_btt = new JButton("Start");
    final JButton stop_btt = new JButton("Stop");
    final JTextField prop_txt = new JTextField(Double.toString(mutation_prop));
    String[] sizes = {"5", "10", "15", "20", "25"};
    final JComboBox size_list = new JComboBox(sizes);
    final JTextField l_txt = new JTextField(Double.toString(lambda));
    final JTextField height_txt = new JTextField(Integer.toString(img_height));
    final JTextField s_txt = new JTextField(Integer.toString(survivors));
    final JTextField sf_txt = new JTextField(Double.toString(scale_factor));
    final JTextField polys_txt = new JTextField(Integer.toString(pop_size));
    final JTextField fit_txt = new JTextField(Integer.toString(fitness_prec));
    final JTextField width_txt = new JTextField(Integer.toString(img_width));
    final JColorChooser col_chooser = new JColorChooser(Color.BLACK);
    final JFrame col_frame = new JFrame();
    final JButton col_picker = new JButton("Choose...");

    public GUI() {
        main_frame = new JFrame();
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setJMenuBar(createMenu());
        pane = main_frame.getContentPane();
        pane.setLayout(new GridBagLayout());
        // pane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        constr = new GridBagConstraints();
        createLayout();
        main_frame.pack();
        main_frame.setVisible(true);

    }

    private void createLayout() {
        JButton load_btt = new JButton("Load new...");
        add_btt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                poly_panel.setAdding();
            }
        });

        rm_btt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                poly_panel.remove();
            }
        });

        JLabel poly_label = new JLabel("Polygon shape:");

        load_btt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                loadImage();
            }
        });
        JLabel prop_label = new JLabel("Mutation propability: ");
        prop_txt.addKeyListener(new NumFieldAdapter(prop_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                double prop = Double.parseDouble(prop_txt.getText());
                mutation_prop = prop;
                if (pp != null) {
                    pp.setMutProp(prop);
                }
            }
        });

        size_list.setSelectedIndex(2);
        JLabel size_label = new JLabel("Population size: ");
        size_list.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int count = (size_list.getSelectedIndex() + 1) * 5;
                pop_count = count;
            }
        });

        JLabel l_label = new JLabel("Lambda parameter: ");
        l_txt.addKeyListener(new NumFieldAdapter(l_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                double l = Double.parseDouble(l_txt.getText());
                lambda = l;
                if (pp != null) {
                    pp.setLambda(lambda);
                }
            }
        });

        JLabel s_label = new JLabel("Number of survivors: ");
        s_txt.addKeyListener(new NumFieldAdapter(s_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                int s = Integer.parseInt(s_txt.getText());
                survivors = s;
                if (pp != null) {
                    pp.setSurv(s);
                }
            }
        });

        JLabel sf_label = new JLabel("Scale factor: ");
        sf_txt.addKeyListener(new NumFieldAdapter(sf_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                double s = Double.parseDouble(sf_txt.getText());
                scale_factor = s;
                if (pp != null) {
                    pp.setScaleF(s);
                }
            }
        });

        JLabel polys_label = new JLabel("Number of polygons: ");
        polys_txt.addKeyListener(new NumFieldAdapter(polys_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                int s = Integer.parseInt(polys_txt.getText());
                pop_size = s;
            }
        });

        JLabel fit_label = new JLabel("Fitness precission: ");
        fit_txt.addKeyListener(new NumFieldAdapter(fit_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                int s = Integer.parseInt(fit_txt.getText());
                fitness_prec = s;
                if (pp != null) {
                    pp.setFitnessP(fitness_prec);
                }
            }
        });

        JLabel width_label = new JLabel("Width: ");
        width_txt.addKeyListener(new NumFieldAdapter(width_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                int s = Integer.parseInt(width_txt.getText());
                img_width = s;
                image_panel.setWidth(s);
                image_panel.setImage(image);
                image_panel.repaint();
                main_frame.pack();
            }
        });

        JLabel height_label = new JLabel("Height: ");
        height_txt.addKeyListener(new NumFieldAdapter(height_txt, main_frame) {

            @Override
            public void doWork() throws NumberFormatException {
                int s = Integer.parseInt(height_txt.getText());
                img_height = s;
                image_panel.setHeight(s);
                image_panel.setImage(image);
                image_panel.repaint();
                main_frame.pack();
            }
        });

        JLabel col_label = new JLabel("Default background color: ");
        col_frame.add(col_chooser);
        col_frame.pack();
        col_picker.setBackground(bg_col);
        col_chooser.getSelectionModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                col_frame.setVisible(false);
                bg_col = col_chooser.getColor();
                col_picker.setBackground(bg_col);
            }
        });
        col_picker.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                col_frame.setVisible(true);
            }
        });

        incr_button.setEnabled(false);
        incr_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                pp.changeCount(true);
            }
        });

        decr_button.setEnabled(false);
        decr_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                pp.changeCount(false);
            }
        });

        stop_btt.setEnabled(false);
        stop_btt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                stopExecution();
            }
        });

        start_btt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                enable(false);
                stop = false;
                sw = new SwingWorker<String, String>() {

                    @Override
                    protected String doInBackground() throws Exception {
                        int i = 0;
                        if (image_panel.getImage() == null) {
                            return "No image chosen.";
                        }
                        if (poly_panel.polygon.getCount() < 3) {
                            JOptionPane.showMessageDialog(main_frame, "Not a valid polygon");
                            enable(true);
                            return "invalid polygon";
                        }
                        pp = new ProgressPane(poly_panel.polygon, pop_count,
                                image_panel.getImage(), mutation_prop, lambda, survivors,
                                pop_size, scale_factor, fitness_prec, bg_col);
                        pp.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent we) {
                                stopExecution();
                            }
                        });
                        pp.setLocation(WIDTH, 0);
                        while (!stop) {
                            if (i++ % 1 == 0) {
                                pp.next(true);
                            } else {
                                pp.next(false);
                            }
                        }
                        return "OK";
                    }

                };

                sw.execute();
            }
        });

        try {
            image = ImageIO.read(new File("/home/vojcek/Pictures/I_Myself.jpg"));
            image_panel.setImage(image);
        } catch (IOException ex) {
        }

        constr.ipady = 4;
        constr.gridx = 0;
        constr.gridy = 1;
        pane.add(img_label, constr);
        constr.gridy = 2;
        pane.add(image_panel, constr);
        constr.gridy = 3;
        pane.add(load_btt, constr);

        constr.ipadx = 10;
        constr.gridx = 1;
        constr.gridy = 1;
        constr.gridwidth = 2;
        pane.add(poly_label, constr);
        constr.gridy = 2;
        pane.add(poly_panel, constr);
        constr.gridy = 3;
        constr.gridwidth = 1;
        constr.gridx = 1;
        pane.add(add_btt, constr);
        constr.gridx = 2;
        pane.add(rm_btt, constr);
        constr.ipady = 10;

        constr.gridx = 0;
        constr.gridy = 4;
        pane.add(width_label, constr);
        constr.gridx = 1;
        pane.add(width_txt, constr);

        constr.gridx = 0;
        constr.gridy = 5;
        pane.add(height_label, constr);
        constr.gridx = 1;
        pane.add(height_txt, constr);

        constr.gridx = 0;
        constr.gridy = 6;
        pane.add(l_label, constr);
        constr.gridx = 1;
        pane.add(l_txt, constr);

        constr.gridx = 0;
        constr.gridy = 8;
        pane.add(size_label, constr);
        constr.gridx = 1;
        pane.add(size_list, constr);

        constr.gridx = 2;
        constr.gridy = 4;
        pane.add(polys_label, constr);
        constr.gridx = 3;
        pane.add(polys_txt, constr);

        constr.gridx = 2;
        constr.gridy = 5;
        pane.add(sf_label, constr);
        constr.gridx = 3;
        pane.add(sf_txt, constr);

        constr.gridx = 2;
        constr.gridy = 7;
        pane.add(s_label, constr);
        constr.gridx = 3;
        pane.add(s_txt, constr);

        constr.gridx = 0;
        constr.gridy = 7;
        pane.add(prop_label, constr);
        constr.gridx = 1;
        pane.add(prop_txt, constr);

        constr.gridx = 2;
        constr.gridy = 6;
        pane.add(fit_label, constr);
        constr.gridx = 3;
        pane.add(fit_txt, constr);

        constr.gridx = 2;
        constr.gridy = 8;
        pane.add(col_label, constr);
        constr.gridx = 3;
        pane.add(col_picker, constr);

        constr.gridy = 9;
        constr.gridx = 0;
        pane.add(incr_button, constr);
        constr.gridx = 1;
        pane.add(decr_button, constr);

        constr.gridy = 10;
        constr.gridx = 3;
        pane.add(start_btt, constr);
        constr.gridx = 2;
        pane.add(stop_btt, constr);

    }

    public boolean stop = false;

    public void stopExecution() {
        poly_panel.reset();
        stop = true;
        enable(true);
        pp.takeShot();
        pp.dispose();
    }

    private void enable(boolean en) {
        size_list.setEnabled(en);
        rm_btt.setEnabled(en);
        add_btt.setEnabled(en);
        start_btt.setEnabled(en);
        col_picker.setEnabled(en);
        stop_btt.setEnabled(!en);
        incr_button.setEnabled(!en);
        decr_button.setEnabled(!en);
        height_txt.setEditable(en);
        width_txt.setEditable(en);
        polys_txt.setEditable(en);
    }

    private void loadImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Accept JPG, PNG, GIF images", "jpg", "png", "gif"));
        if (chooser.showOpenDialog(main_frame) == JFileChooser.APPROVE_OPTION) {
            try {
                image = ImageIO.read(chooser.getSelectedFile());
                image_panel.setImage(image);
                image_panel.repaint();
            } catch (IOException ioe) {
                System.out.println("Failed to load " + chooser.getSelectedFile().getName());
                System.err.println(ioe.getMessage());
            }
        }
    }

    private JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu file_menu = new JMenu("File");

        JMenuItem load = new JMenuItem("Load Image");
        JMenuItem exit = new JMenuItem("Exit");
        load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                loadImage();
            }
        });
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                main_frame.dispatchEvent(new WindowEvent(main_frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        file_menu.add(load);
        file_menu.add(exit);

        bar.add(file_menu);
        return bar;
    }
}

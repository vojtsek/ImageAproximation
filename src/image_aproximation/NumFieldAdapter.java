/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_aproximation;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author vojcek
 */
public abstract class NumFieldAdapter extends KeyAdapter {

    String old_txt;
    JTextField txt;
    JFrame fr;
    
    public NumFieldAdapter(JTextField t, JFrame f) {
        txt = t;
        fr = f;
    }
    
    public abstract void doWork() throws NumberFormatException;

    public void keyPressed(KeyEvent e) {
        old_txt = txt.getText();
    }

    public void keyReleased(KeyEvent e) {
        try {
            doWork();
        } catch (NumberFormatException ne) {
            txt.setText(old_txt);
            JOptionPane.showMessageDialog(fr, "Must be a number.");
        }
    }
}

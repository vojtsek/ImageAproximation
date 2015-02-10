package image_aproximation;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Vojtech Hudecek
 * serves as generic adapter to handle the JTextFields which should accept only numbers
 */
public abstract class NumFieldAdapter extends KeyAdapter {

    String old_txt;
    JTextField txt;
    JFrame fr;
    
    public NumFieldAdapter(JTextField t, JFrame f) {
        txt = t;
        fr = f;
    }
    
    /**
     * 
     * @throws NumberFormatException 
     * method run on each key release
     */
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

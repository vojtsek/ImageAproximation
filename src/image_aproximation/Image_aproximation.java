package image_aproximation;

/**
 *
 * @author Vojtech Hudecek
 */
public class Image_aproximation {

    /**
     * @param args the command line arguments
     * 
     * invokes the GUI class
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                GUI g = new GUI();
            }
        });
    }
    
}

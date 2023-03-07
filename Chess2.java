import javax.swing.*;
import java.awt.*;

public class Chess2 {
    public static void main(String[] args) {
        Runnable app = new Runnable() {

            @Override
            public void run() {
                Board board = new Board();

                JFrame frame = new JFrame("Louis Chess AI");
                frame.add(board.getGui());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationByPlatform(true);

                // Ensures the frame is the minimum size it needs to be in order display the components within it
                frame.pack();
                // Ensures the minimum size is enforced.
                frame.setMinimumSize(frame.getSize());
                frame.setVisible(true);
            }
        };

        SwingUtilities.invokeLater(app);

        
    }
}

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;



public class Board {
    
    private final JPanel gui = new JPanel(new BorderLayout(3, 3)); // 
    private JButton[][] chessBoardSquares = new JButton[8][8]; // Stores
    private JPanel chessBoard;
    private final JLabel message = new JLabel("EEEEEEEEEEEEEEEEEE!");
    private static final String COLS = "ABCDEFGH";
    private static Color colWhite = new Color(238,238,210);
    private static Color colBlack = new Color(118,150,86);
    private static Color colBackground = new Color(232, 232, 200);

    Board() {
        initializeBoard();
    }

    public final void initializeBoard() {
        // Set up the board
        gui.setBorder(new EmptyBorder(5,5,5,5));
        gui.setBackground(colBackground);
        
        // Create a tool bar at the top
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("New"));
        tools.add(new JButton("Save"));
        tools.add(new JButton("Restore"));
        tools.addSeparator();
        tools.add(new JButton("Resign"));
        tools.addSeparator();
        tools.add(message);
        tools.setBackground(colBackground);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        // Create the main chess board panel
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(null);
        chessBoard.setBackground(colBackground);
        gui.add(chessBoard);

        // Create the squares (buttons) of the chess board
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j<8; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setBackground( (i+j)%2 != 0 ? colWhite : colBlack );
                chessBoardSquares[j][i] = b;
            }
        }
        
        chessBoard.add(new JLabel("")); // Create the abcdefgh... 12345678...
        for (int i = 0; i < 8; i++) {
            chessBoard.add(new JLabel(COLS.substring(i, i + 1), SwingConstants.CENTER));
        }
        
        for (int i = 0; i < 8; i++) { // Create the chess squares
            for (int j = 0; j < 8; j++) {
                switch (j) {
                    case 0:
                        chessBoard.add(new JLabel("" + (i+1) , SwingConstants.CENTER));
                    default: 
                        chessBoard.add(chessBoardSquares[j][i]);
                }
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public final JComponent getChessBoard() {
        return chessBoard;
    }
    
}
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.io.File;

public class ChessGUI {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel board; // Is our JPanel of the board
    private static JButton[][] chessBoardButtons = new JButton[8][8]; // Stores the jbuttons
    private ImageIcon[][] chessPieceImages = new ImageIcon[2][6]; // Stores the images

    private final JLabel message = new JLabel("EEEEEEEEEEEEEEEEEE!");
    private static final String COLS = "abcdefgh";
    
    // Set colors for GUI
    private static Color colWhite = new Color(238,238,210);
    private static Color colBlack = new Color(118,150,86);
    private static Color colBackground = new Color(232, 232, 200);   
    private static Color colHighlight = new Color(255,102,102); 
    //private static Color colHighlight2 = new Color(255,179,179);
    

    ChessGUI() {

        createImages();

        // Set up the board
        gui.setBorder(new EmptyBorder(5,5,5,5));
        gui.setBackground(colBackground);
        
        // Create a tool bar at the top
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        
        // Create new game button
        JButton toolsNew = new JButton("New");
        // toolsNew.addActionListener(actionNewGame);
        tools.add(toolsNew);
        
        tools.addSeparator();
        tools.add(new JButton("Save"));
        tools.addSeparator();
        tools.add(new JButton("Restore"));
        tools.addSeparator();
        tools.add(new JButton("Resign"));
        tools.addSeparator();
        tools.add(message);
        tools.setBackground(colBackground);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        // Create the main chess board panel
        board = new JPanel(new GridLayout(0, 9));
        board.setBorder(null);
        board.setBackground(colBackground);
        gui.add(board);

        board.add(new JLabel("")); // Create the abcdefgh... 12345678...
        for (int i = 0; i < 8; i++) {
            board.add(new JLabel(COLS.substring(i, i + 1), SwingConstants.CENTER));
        }

        // Create the squares (buttons) of the chess board
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j<8; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setBackground( (i+j)%2 != 0 ? colWhite : colBlack );
                b.addActionListener(actionSelectSquare);

                chessBoardButtons[j][i] = b;

                switch (j) {
                    case 0:
                        board.add(new JLabel("" + (i+1) , SwingConstants.CENTER));
                    default: 
                        board.add(chessBoardButtons[j][i]);
                }
            }
        }
    }
    
    private final void createImages() {
        try {     
            BufferedImage chessImageSheet = ImageIO.read(new File("chess.png"));
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceImages[i][j] = new ImageIcon(chessImageSheet.getSubimage(j * 64, i * 64, 64, 64));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public final void setPieceIcon(int j, int i, char piece) {
        int pieceColour = (Character.isUpperCase(piece)) ? 0 : 1;
        
        switch (Character.toLowerCase(piece)) {
            case 'q':
                chessBoardButtons[j][i].setIcon(chessPieceImages[pieceColour][0]);
                break;
            case 'k':
                chessBoardButtons[j][i].setIcon(chessPieceImages[pieceColour][1]);
                break;
            case 'r':
                chessBoardButtons[j][i].setIcon(chessPieceImages[pieceColour][2]);
                break;
            case 'n':
                chessBoardButtons[j][i].setIcon(chessPieceImages[pieceColour][3]);
                break;
            case 'b':
                chessBoardButtons[j][i].setIcon(chessPieceImages[pieceColour][4]);
                break;
            case 'p':
                chessBoardButtons[j][i].setIcon(chessPieceImages[pieceColour][5]);
                break;
            default:
                chessBoardButtons[j][i].setIcon(null);
                break;
        }
        
    }

    public final JComponent getGui() {
        return gui;
    }

    public final void highlightSquare(int j, int i) {
        getSquareButton(j,i).setBackground(colHighlight);
    }

    public final JButton getSquareButton(int j, int i) {
        return chessBoardButtons[j][i];
    }

    public void resetBoardBackgrounds() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoardButtons[j][i].setBackground( (i+j)%2 != 0 ? colWhite : colBlack );
            }
        }
    }

    public void makeMove(Move move, char piece) {
        setPieceIcon(move.getTargetSquare()[0],move.getTargetSquare()[1],piece);
        setPieceIcon(move.getStartSquare()[0],move.getStartSquare()[1], ' ');
    }

    public static ActionListener actionSelectSquare = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object button = e.getSource();
            
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessBoardButtons[j][i] == button) {
                        Chess.squarePressed(j,i);
                        break;
                    }
                }
            }
        }
    };
}

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;
import java.util.List;



public class Board {
    
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private final JLabel message = new JLabel("EEEEEEEEEEEEEEEEEE!");
    private static final String COLS = "abcdefgh";

    // Create and store array of info about game
    private String gameFEN = "";
    private JButton[][] chessBoardButtons = new JButton[8][8]; // Stores the jbuttons
    public char[][] chessBoardPieces = new char[8][8];
    private JPanel chessBoard;
    private Image[][] chessPieceImages = new Image[2][6]; // Stores the images

    // Used for displaying/translating a piece letter to it's image index
    public HashMap<Character, Integer> pieceValue = new HashMap<Character, Integer>() {{
        put('q', 9);
        put('k', Integer.MAX_VALUE);
        put('r',5);
        put('n',3);
        put('b',3);
        put('p',1);
    }};

    // Used for displaying/translating a piece letter to it's image index
    public HashMap<Character, Integer> pieceLetterToImageIndex = new HashMap<Character, Integer>() {{
        put('q',0);
        put('k',1);
        put('r',2);
        put('n',3);
        put('b',4);
        put('p',5);
    }};
    public static final int black = 0, white = 1;

    // Has the player selected a piece?
    private boolean hasPlayerSelectedPiece = false;
    private int[] playerSelectedPiece = new int[2];
    
    // Set colors for GUI
    private static Color colWhite = new Color(238,238,210);
    private static Color colBlack = new Color(118,150,86);
    private static Color colBackground = new Color(232, 232, 200);   
    private static Color colHighlight = new Color(58, 74, 28); 
    private static Color colHighlight2 = new Color(88, 104, 58); 
    
    // Moves
    // public int[][] legalMoves = new int[0][2];
    List<int[]> legalMoves = new ArrayList<int[]>();
    
    Board() {
        initializeBoard();
        setupNewGame();
    }

    public final void initializeBoard() {

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
        toolsNew.addActionListener(actionNewGame);
        tools.add(toolsNew);

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
                b.addActionListener(actionSelectSquare);

                chessBoardButtons[j][i] = b;

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
                        chessBoard.add(chessBoardButtons[j][i]);
                }
            }
        }
    }

    private final void createImages() {
        try {     
            BufferedImage chessImageSheet = ImageIO.read(new File("chess.png"));
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceImages[i][j] = chessImageSheet.getSubimage(j * 64, i * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public final JComponent getGui() {
        return gui;
    }

    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JButton[][] getChessBoardSquares() {
        return chessBoardButtons;
    }
    
    private void loadFromFEN(String FEN) {
        gameFEN = FEN; // Update public FEN
        String[] arrFEN = FEN.split(" "); // Split the FEN into it's components
        String[] placementField = arrFEN[0].split("/"); // Split the placement field into an array of ranks
        String[] currentRank; // Used for iterating through a current rank
        int currentFile;

        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j<8; j++) {
                chessBoardPieces[j][i] = ' ';
                chessBoardButtons[j][i].setIcon(null);
            }
        }

        for (int i = 0; i < placementField.length; i++) {
            currentRank = placementField[i].split("");
            currentFile = 0;
            for (int j = 0; j < currentRank.length; j++) {
                char currentChar = placementField[i].charAt(j);

                if (Character.isDigit(currentChar)) {
                    currentFile += Character.getNumericValue(currentChar);
                }
                else {
                    
                    int pieceColour = (Character.isUpperCase(currentChar)) ? black : white;
                    int pieceType = pieceLetterToImageIndex.get(Character.toLowerCase(currentChar));
                    chessBoardButtons[currentFile][i].setIcon(new ImageIcon(chessPieceImages[pieceColour][pieceType]));
                    chessBoardPieces[currentFile][i] = currentChar;
                    currentFile++;
                }
            }
        }

    }

    private final void setupNewGame() {
        message.setText("loaded FEN: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        // FEN BASICS
        // Placement field / whose move it is / castling / en passant / draw legalMoves
        loadFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public final void movePiece(int j1, int i1, int j2, int i2) {
        char currentChar = chessBoardPieces[j1][i1];
        int pieceColour = (Character.isUpperCase(currentChar)) ? black : white;
        int pieceType = pieceLetterToImageIndex.get(Character.toLowerCase(currentChar));

        chessBoardButtons[j2][i2].setIcon(new ImageIcon(chessPieceImages[pieceColour][pieceType]));
        chessBoardPieces[j2][i2] = currentChar;
        chessBoardButtons[j1][i1].setIcon(null);
        chessBoardPieces[j1][i1] = ' ';

        //System.out.printf("\nMoved piece %s at %d,%d to %d,%d", currentChar,j1,i1,j2,i2);
    }

    public final void clearBackgrounds() {
        for (int i = 0; i < 8; i++) { // Reset jbutton backgrounds to being "clear"
            for (int j = 0; j<8; j++) {
                chessBoardButtons[j][i].setBackground( (i+j)%2 != 0 ? colWhite : colBlack );
            }
        }
    }
    
    public final void generateMoves(int j, int i) {
        char currentChar = chessBoardPieces[j][i];
        int pieceColour = (Character.isUpperCase(currentChar)) ? black : white;
        char pieceType = Character.toLowerCase(currentChar);
        System.out.printf("\n%d,%s at %d,%d",pieceColour,pieceType,j,i);
        
        if (pieceType == 'p') { // if a pawn
            if (pieceColour == black) {
                if (i == 6 &&  chessBoardPieces[j][i-2] == ' ') {legalMoves.add(new int[] { j, i-2 });}
                if (chessBoardPieces[j][i-1] == ' ') {legalMoves.add(new int[] { j, i-1 });}
                if (chessBoardPieces[j+1][i-1] != ' ') {legalMoves.add(new int[] { j+1, i-1 });}
                if (chessBoardPieces[j-1][i-1] != ' ') {legalMoves.add(new int[] { j-1, i-1 });}
            }
            else {
                if (i == 1 &&  chessBoardPieces[j][i+2] == ' ') {legalMoves.add(new int[] { j, i+2 });}
                if (chessBoardPieces[j][i+1] == ' ') {legalMoves.add(new int[] { j, i+1 });}
                if (chessBoardPieces[j+1][i+1] != ' ') {legalMoves.add(new int[] { j+1, i+1 });}
                if (chessBoardPieces[j-1][i+1] != ' ') {legalMoves.add(new int[] { j-1, i+1 });}
            }
        }
        else if (pieceType == 'r') { // if a rook
            if (pieceColour == black) {
                for (int k = 0; k < 8; k++) {   
                    if (chessBoardPieces[j][i-k] != ' ') {legalMoves.add(new int[] { j, i-1 });}
                }
            }
        }
    }

    public ActionListener actionSelectSquare = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Object button = e.getSource();
            
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessBoardButtons[j][i] == button) {
                        if (hasPlayerSelectedPiece) { // If we have already selected a piece, move it to this square
                            hasPlayerSelectedPiece = false;
                            int[] playerMove = new int[] { j, i };
                            for (int k = 0; k < legalMoves.size(); k++) {
                                if (Arrays.compare(playerMove,legalMoves.get(k)) == 0) {
                                    movePiece(playerSelectedPiece[0],playerSelectedPiece[1],j,i);
                                }
                                
                            }
                            clearBackgrounds();
                            legalMoves.clear();
                        }
                        else if (chessBoardPieces[j][i] != ' '){ // Select this piece
                            if (button instanceof Component) {
                                hasPlayerSelectedPiece = true;
                                playerSelectedPiece[0] = j;
                                playerSelectedPiece[1] = i;
                                generateMoves(j,i);
                                ((Component)button).setBackground(colHighlight);
                            }
                        }
                    }
                }
            }
            if (hasPlayerSelectedPiece) {
                for (int i = 0; i < legalMoves.size(); i++) {
                    System.out.printf("\nMove %d: %d, %d",i,legalMoves.get(i)[0],legalMoves.get(i)[1]);
                    chessBoardButtons[legalMoves.get(i)[0]][legalMoves.get(i)[1]].setBackground(colHighlight2);
                }
            }
            
        }
    };

    public ActionListener actionNewGame = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            setupNewGame();
        }
    };


}
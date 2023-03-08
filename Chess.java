import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;
import java.util.List;



public class Chess {
    public static ChessGUI gui = new ChessGUI();
    public static ChessPieces board = new ChessPieces();
    public static ArrayList<Move> playerMoves = new ArrayList<Move>();

    Chess () {
    }
    public static void main(String[] args) {
        Runnable app = new Runnable() {

            @Override
            public void run() {

                JFrame frame = new JFrame("Louis Chess AI");

                frame.add(gui.getGui());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationByPlatform(true);

                // Ensures the frame is the minimum size it needs to be in order display the components within it
                frame.pack();
                // Ensures the minimum size is enforced.
                frame.setMinimumSize(frame.getSize());
                frame.setVisible(true);
                setupNewGame();
            }
        };

        SwingUtilities.invokeLater(app);
    }


    public static void squarePressed(int j, int i) {
        //if (board.getTurnColour() != 0) {return;} // If it is not black's move

        if (playerMoves.size() != 0) { // Check if the square pressed is in playerMoves (i.e a move we want to make)
            for (Move move : playerMoves) {
                if (j == move.getTargetSquare()[0] && i == move.getTargetSquare()[1]) {
                    gui.makeMove(move, board.getPiece(move.getStartSquare()[0], move.getStartSquare()[1]));
                    gui.resetBoardBackgrounds();
                    board.makePlayerMove(move);
                    playerMoves = new ArrayList<Move>();
                    if (board.getTurnColour() == 0) {board.setTurnColour(1);}
                    else {board.setTurnColour(0);}
                    return;
                }
            }
        }

        gui.resetBoardBackgrounds();
        ArrayList<Move> moves = new PlayerMoves().generateMovesForPiece(j, i, board);
        //board.boardPrint();
        //System.out.printf("\n%s @ %d,%d",(board.getPiece(j, i) == ' ') ? 'E' : board.getPiece(j, i),j,i);
        
        for (Move move : moves) {
            gui.highlightSquare(move.getTargetSquare()[0],move.getTargetSquare()[1]);
            //System.out.printf("\n%d,%d, %s(%d,%d)",move.getTargetSquare()[0],move.getTargetSquare()[1],board.getPiece(j, i),j,i);
        }

        playerMoves = moves;
    }

    private final static void setupNewGame() {
        // String newGameMessage = "loaded FEN: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        // newGameMessage = newGameMessage + ((turn) ? " WHITE's turn" : " BLACK's turn");
        // message.setText(newGameMessage);

        // FEN BASICS
        // Placement field / whose move it is / castling / en passant / draw legalMoves
        //loadFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        loadFromFEN("8/8/8/1kN1R3/8/8/8/8 w KQkq - 0 1");
        board.setTurnColour(0); // Set turn to black
    }

    private static void loadFromFEN(String FEN) {
        // gameFEN = FEN; // Update public FEN
        String[] arrFEN = FEN.split(" "); // Split the FEN into it's components
        String[] placementField = arrFEN[0].split("/"); // Split the placement field into an array of ranks
        String[] fenRank; // Used for iterating through a current rank
        int file;
        
        for (int rank = 0; rank < placementField.length; rank++) {
            fenRank = placementField[rank].split("");
            file = 0;
            for (int j = 0; j < fenRank.length; j++) {
                char currentChar = placementField[rank].charAt(j);
                if (Character.isDigit(currentChar)) {
                    for (int k = 0; k < Character.getNumericValue(currentChar); k++) {
                        gui.setPieceIcon(file+k, rank, ' ');
                        board.setBlank(file+k, rank);
                    }
                    file += Character.getNumericValue(currentChar)-1;
                }
                else {
                    gui.setPieceIcon(file, rank, currentChar);
                    board.setPiece(file, rank, currentChar);
                }
                file++;
            }
        }

    }
}
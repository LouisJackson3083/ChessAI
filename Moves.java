import java.util.ArrayList;

public class Moves {
    ArrayList<Move> moves;
    
    public ArrayList<Move> generateMoves(ChessPieces board) {
        moves = new ArrayList<Move>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j<8; j++) {
                if (board.getTurnColour() == board.getPieceColour(j, i)) {
                    char piece = board.getPiece(j, i);
                    System.out.println(piece);
                    if (isSlidingPiece(piece)) {
                        generateSlidingMoves(j,i,piece,board);
                    }
                }
            }
        }

        return moves;
    }

    public ArrayList<Move> generateMovesForPiece(int j, int i, ChessPieces board) {
        moves = new ArrayList<Move>();

        if (board.getTurnColour() == board.getPieceColour(j, i)) {
            char piece = board.getPiece(j, i);
            System.out.println(piece);
            if (isSlidingPiece(piece)) {
                moves.addAll(generateSlidingMoves(j,i,piece,board));
            }
            else if (Character.toLowerCase(piece) == 'p') { // Pawn
                moves.addAll(generatePawnMoves(j,i,piece,board));
            }
        }

        return moves;
    }

    private boolean isSlidingPiece(char piece) {
        return (Character.toLowerCase(piece) == 'q' || Character.toLowerCase(piece) == 'r' || Character.toLowerCase(piece) == 'b');
    }

    private ArrayList<Move> generatePawnMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> pawnMoves = new ArrayList<Move>();
        System.out.printf("Piece at %d,%d: %s",j,i-1,board.getPiece(j, i-1));
        if (board.getPiece(j, i-1) == ' ') {pawnMoves.add(new Move(j, i, j, i-1));}
        return pawnMoves;
    }

    private ArrayList<Move> generateSlidingMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> slidingMoves = new ArrayList<Move>();
        boolean n, ne, e, se, s, sw, w, nw;
        n = ne = e = se = s = sw = w = nw = true;

        for (int k = 1; k < 8; k++) {
            if (Character.toLowerCase(piece) != 'b') { // If we are a rook or queen
                if (n && i-k>=0) { // Check the north squares
                    if (board.getPiece(j, i-k) == ' '){
                        slidingMoves.add(new Move(j, i, j, i-k));
                    }
                    else if (board.isOppositePiece(j, i-k, piece)) {
                        slidingMoves.add(new Move(j, i, j, i-k));
                        n = false;
                    }
                    else {n = false;}
                }
                if (s && i+k<=0) { // Check the north squares
                    if (board.getPiece(j, i+k) == ' '){
                        slidingMoves.add(new Move(j, i, j, i+k));
                    }
                    else if (board.isOppositePiece(j, i+k, piece)) {
                        slidingMoves.add(new Move(j, i, j, i+k));
                        s = false;
                    }
                    else {s = false;}
                }
                
            }
            if (Character.toLowerCase(piece) != 'r') { // If we are a bishop or queen
            }
        }
        return slidingMoves;
    }
}

class Move
{
    public int[] startSquare;
    public int[] targetSquare;

    Move (int j1, int i1, int j2, int i2) {
        startSquare = new int[]{j1,i1};
        targetSquare = new int[]{j2,i2};
    }

    public int[] getTargetSquare() {
        return targetSquare;
    }

    public int[] getStartSquare() {
        return startSquare;
    }

};
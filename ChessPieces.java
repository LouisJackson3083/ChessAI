public class ChessPieces {

    public static final int black = 0, white = 1;
    private char[][] chessBoardPieces = new char[8][8];
    public int colourToMove;

    public ChessPieces() {
        this.chessBoardPieces = new char[8][8];
    }

    public ChessPieces(ChessPieces another) {
        this.chessBoardPieces = another.chessBoardPieces;
    }

    public void setBlank(int j, int i) {
        chessBoardPieces[j][i] = ' ';
    }

    public void setPiece(int j, int i, char piece) {
        chessBoardPieces[j][i] = piece;
    }

    public char getPiece(int j, int i) {
        return chessBoardPieces[j][i];
    }

    public int getPieceColour(int j, int i) {
        return (Character.isUpperCase(chessBoardPieces[j][i])) ? black : white;
    }

    public int getPieceColourOfChar(char piece) {
        return (Character.isUpperCase(piece)) ? black : white;
    }
    
    public boolean isSquareEmpty(int j, int i) {
        return chessBoardPieces[j][i] == ' ';
    }

    public boolean isOppositePiece(int j, int i, char piece) {
        if (getPiece(j, i) == ' ') { return true; }
        return getPieceColour(j, i) != getPieceColourOfChar(piece);
    }

    public int getTurnColour() {
        return colourToMove;
    }
    
    public void setTurnColour(int i) {
        colourToMove = i;
    }

    public void boardPrint() {
        System.out.println("  abcdefgh");
        for (int j = 0; j < 8; j++) {
            String line = j + " ";
            for (int i = 0; i < 8; i++) {
                line = line + chessBoardPieces[i][j];
            }
            System.out.println(line);
        }
    }

    public void makePlayerMove(Move move) {
        setPiece(move.getTargetSquare()[0],move.getTargetSquare()[1],getPiece(move.getStartSquare()[0],move.getStartSquare()[1]));
        setBlank(move.getStartSquare()[0],move.getStartSquare()[1]);
    }

}

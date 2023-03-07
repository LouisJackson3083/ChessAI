import java.util.ArrayList;

public class PlayerMoves {
    
    public ArrayList<Move> generateMovesForPiece(int j, int i, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        char piece = board.getPiece(j, i);
        if (piece == ' ') { return moves; }
        int pieceColour = board.getPieceColourOfChar(piece);

        if (board.getTurnColour() == pieceColour) {
            
            if (Character.toLowerCase(piece) == 'p') { // Pawn
                moves.addAll(generatePawnMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'n') { // Knight
                moves.addAll(generateKnightMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'b') { // Bishop
                moves.addAll(generateBishopMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'r') { // Rook
                moves.addAll(generateRookMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'q') { // Queen
                moves.addAll(generateQueenMoves(j, i, piece, board));
            }
            else { // King
                moves.addAll(generateKingMoves(j, i, piece, board));
            }
        }

        // for (Move move : moves) { // Now check if these are legal moves
        //     ChessPieces boardCopy = new ChessPieces(board);
        //     boardCopy.makePlayerMove(move);


        //     ArrayList<Move> enemyMoves = new ArrayList<Move>();
        //     enemyMoves = generateEnemyMoves(pieceColour, boardCopy);
        //     for (Move enemyMove : enemyMoves) {
        //         if (pieceColour == board.getPieceColour(enemyMove.getTargetSquare()[0], enemyMove.getTargetSquare()[1])) {
        //             System.out.printf("\nILLEGAL %d,%d, %s(%d,%d)",move.getTargetSquare()[0],move.getTargetSquare()[1],board.getPiece(j, i),j,i);
        //         }
        //     }
        // }
        
        return moves;
    }

    public ArrayList<Move> generateMovesForEnemyPiece(int j, int i, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        char piece = board.getPiece(j, i);

        if (board.getTurnColour() == board.getPieceColour(j, i)) {
            
            if (Character.toLowerCase(piece) == 'p') { // Pawn
                moves.addAll(generatePawnMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'n') { // Knight
                moves.addAll(generateKnightMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'b') { // Bishop
                moves.addAll(generateBishopMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'r') { // Rook
                moves.addAll(generateRookMoves(j, i, piece, board));
            }
            else if (Character.toLowerCase(piece) == 'q') { // Queen
                moves.addAll(generateQueenMoves(j, i, piece, board));
            }
            else { // King
                moves.addAll(generateKingMoves(j, i, piece, board));
            }
        }

        return moves;
    }

    private ArrayList<Move> generatePawnMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();

        if (piece=='P') { // If a black pawn
            if (i>=1 && board.getPiece(j, i-1) == ' ') {moves.add(new Move(j, i, j, i-1));}
            if (i>=1 && j<=6 && board.getPiece(j+1, i-1) != ' ' && board.isOppositePiece(j+1, i-1, piece)) {moves.add(new Move(j, i, j+1, i-1));}
            if (i>=1 && j>=1 && board.getPiece(j-1, i-1) != ' ' && board.isOppositePiece(j-1, i-1, piece)) {moves.add(new Move(j, i, j-1, i-1));}
            if (i == 6 && board.getPiece(j, i-1) == ' ' && board.getPiece(j, i-2) == ' ') {moves.add(new Move(j, i, j, i-2));} // Double space movement
        }
        else { // If a white pawn
            if (i<=6 && board.getPiece(j, i+1) == ' ') {moves.add(new Move(j, i, j, i+1));}
            if (i<=6 && j<=6 && board.getPiece(j+1, i+1) != ' ' && board.isOppositePiece(j+1, i+1, piece)) {moves.add(new Move(j, i, j+1, i+1));}
            if (i<=6 && j>=1 && board.getPiece(j-1, i+1) != ' ' && board.isOppositePiece(j-1, i+1, piece)) {moves.add(new Move(j, i, j-1, i+1));}
            if (i == 1 && board.getPiece(j, i+1) == ' ' && board.getPiece(j, i+2) == ' ') {moves.add(new Move(j, i, j, i+2));} // Double space movement
        }
        
        return moves;
    }
    
    private ArrayList<Move> generateKnightMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        if (i>=1 && j>=2 && board.isOppositePiece(j-2, i-1, piece)) {moves.add(new Move(j, i, j-2, i-1));} // Horizontal Knight moves
        if (i>=1 && j<=5 && board.isOppositePiece(j+2, i-1, piece)) {moves.add(new Move(j, i, j+2, i-1));}
        if (i<=6 && j>=2 && board.isOppositePiece(j-2, i+1, piece)) {moves.add(new Move(j, i, j-2, i+1));}
        if (i<=6 && j<=5 && board.isOppositePiece(j+2, i+1, piece)) {moves.add(new Move(j, i, j+2, i+1));}

        if (i>=2 && j>=1 && board.isOppositePiece(j-1, i-2, piece)) {moves.add(new Move(j, i, j-1, i-2));} // Vertical Knight moves
        if (i>=2 && j<=6 && board.isOppositePiece(j+1, i-2, piece)) {moves.add(new Move(j, i, j+1, i-2));}
        if (i<=5 && j>=1 && board.isOppositePiece(j-1, i+2, piece)) {moves.add(new Move(j, i, j-1, i+2));}
        if (i<=5 && j<=6 && board.isOppositePiece(j+1, i+2, piece)) {moves.add(new Move(j, i, j+1, i+2));}
        
        return moves;
    }

    private ArrayList<Move> generateBishopMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        // nw = j-1, i-1
        // ne = j+1, i-1
        // se = j+1, i+1
        // sw = j-1, i+1
        boolean dirCheck = true;
        int jOffset, iOffset, jSquare, iSquare;
        
        for (int dir = 0; dir < 4; dir++ ) {
            dirCheck = true;
            jOffset = (dir%2 == 1) ? 1 : -1;
            iOffset = (dir>1) ? 1 : -1;
            
            for ( int k = 1; k < 8; k++ ) { // K represents the distance
                jSquare = j+(k*jOffset);
                iSquare = i+(k*iOffset);
                if (dirCheck && jSquare>=0 && jSquare<=7 && iSquare>=0 && iSquare<=7) {
                    if (board.getPiece(jSquare, iSquare) == ' '){ 
                        moves.add(new Move(j, i, jSquare, iSquare));
                    }
                    else if (board.isOppositePiece(jSquare, iSquare, piece)) {
                        moves.add(new Move(j, i, jSquare, iSquare)); 
                        dirCheck = false;
                    }
                    else {dirCheck = false;}
                }
            }
        }

        return moves;
    }

    private ArrayList<Move> generateRookMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        // w = j-1
        // e = j+1
        // n = i-1
        // s = i+1
        boolean dirCheck = true;
        int jOffset, iOffset, jSquare, iSquare;
        
        for (int dir = 0; dir < 4; dir++ ) {
            dirCheck = true;
            jOffset = (dir<2) ? ((dir%2 == 1) ? 1 : -1) : 0;
            iOffset = (dir>1) ? ((dir%2 == 1) ? 1 : -1) : 0;
            
            for ( int k = 1; k < 8; k++ ) { // K represents the distance
                jSquare = j+(k*jOffset);
                iSquare = i+(k*iOffset);
                if (dirCheck && jSquare>=0 && jSquare<=7 && iSquare>=0 && iSquare<=7) {
                    if (board.getPiece(jSquare, iSquare) == ' '){ 
                        moves.add(new Move(j, i, jSquare, iSquare));
                    }
                    else if (board.isOppositePiece(jSquare, iSquare, piece)) {
                        moves.add(new Move(j, i, jSquare, iSquare)); 
                        dirCheck = false;
                    }
                    else {dirCheck = false;}
                }
            }
        }

        return moves;
    }

    private ArrayList<Move> generateQueenMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        // w  = j-1, i
        // e  = j+1, i
        // nw = j-1, i-1
        // se = j+1, i+1
        // ne = j+1, i-1
        // sw = j-1, i+1
        // n  = j  , i-1
        // s  = j  , i+1
        boolean dirCheck = true;
        int jOffset, iOffset, jSquare, iSquare;
        
        for (int dir = 0; dir < 8; dir++ ) {
            dirCheck = true;
            jOffset = (dir<6) ? ((dir>3) ? ((dir%2 == 1) ? 1 : -1) : ((dir%2 == 1) ? -1 : 1)) : 0;
            iOffset = (dir>1) ? ((dir%2 == 1) ? 1 : -1) : 0;
            
            for ( int k = 1; k < 8; k++ ) { // K represents the distance
                jSquare = j+(k*jOffset);
                iSquare = i+(k*iOffset);
                if (dirCheck && jSquare>=0 && jSquare<=7 && iSquare>=0 && iSquare<=7) {
                    if (board.getPiece(jSquare, iSquare) == ' '){ 
                        moves.add(new Move(j, i, jSquare, iSquare));
                    }
                    else if (board.isOppositePiece(jSquare, iSquare, piece)) {
                        moves.add(new Move(j, i, jSquare, iSquare)); 
                        dirCheck = false;
                    }
                    else {dirCheck = false;}
                }
            }
        }
        return moves;
    }

    private ArrayList<Move> generateKingMoves(int j, int i, char piece, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        // w  = j-1, i
        // e  = j+1, i
        // nw = j-1, i-1
        // se = j+1, i+1
        // ne = j+1, i-1
        // sw = j-1, i+1
        // n  = j  , i-1
        // s  = j  , i+1
        int jOffset, iOffset;
        
        for (int dir = 0; dir < 8; dir++ ) {
            jOffset = j + ((dir<6) ? ((dir>3) ? ((dir%2 == 1) ? 1 : -1) : ((dir%2 == 1) ? -1 : 1)) : 0);
            iOffset = i + ((dir>1) ? ((dir%2 == 1) ? 1 : -1) : 0);
            if (jOffset>=0 && jOffset<=7 && iOffset>=0 && iOffset<=7 && (board.getPiece(jOffset, iOffset) == ' ' || board.isOppositePiece(jOffset, iOffset, piece))) {
                moves.add(new Move(j, i, jOffset, iOffset));
            }
        }
        return moves;
    }

    public ArrayList<Move> generateEnemyMoves(int turnColour, ChessPieces board) {
        ArrayList<Move> moves = new ArrayList<Move>();

        for ( int j = 0; j < 8; j++ ) {
            for ( int i = 0; i < 8; i++ ) {
                if (board.getPiece(j, i) != ' ') {
                    moves.addAll(generateMovesForEnemyPiece(j, i, board));
                }
            }
        }

        return moves;
    }

}

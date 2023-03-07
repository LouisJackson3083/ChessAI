public class Move {
    public int[] startSquare = new int[2];
    public int[] targetSquare = new int[2];

    Move (int j1, int i1, int j2, int i2) {
        startSquare[0] = j1;
        startSquare[1] = i1;
        targetSquare[0] = j2;
        targetSquare[1] = i2;
    }

    public int[] getTargetSquare() {
        return targetSquare;
    }

    public int[] getStartSquare() {
        return startSquare;
    }
}

package lk.ijse.dep.service;

public class BoardImpl implements Board {
    private Piece[][] pieces;
    private BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[Board.NUM_OF_ROWS][Board.NUM_OF_COLS];

        for (int row = 0; row < Board.NUM_OF_ROWS; row++) {
            for (int col = 0; col < Board.NUM_OF_COLS; col++) {
                pieces[row][col] = Piece.EMPTY;
            }
        }
    }

    @Override
    public BoardUI getBoardUI() {

        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {

        for (int row = 0; row < Board.NUM_OF_ROWS; row++) {
            if (pieces[row][col] == Piece.EMPTY) {
                return row;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {

        return findNextAvailableSpot(col) != -1;
    }

    @Override
    public boolean existLegalMoves() {

        for (int col = 0; col < Board.NUM_OF_COLS; col++) {
            if (isLegalMove(col)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public Winner findWinner() {
        // horizontally  4 k conected d check
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            for (int col = 0; col < NUM_OF_COLS - 3; col++) {
                if (pieces[row][col] != Piece.EMPTY &&
                        pieces[row][col] == pieces[row][col + 1] &&
                        pieces[row][col] == pieces[row][col + 2] &&
                        pieces[row][col] == pieces[row][col + 3]) {
                    return new Winner(pieces[row][col], col, row, col + 3, row);
                }
            }
        }

        //  vertically 4 k conected d check
        for (int col = 0; col < NUM_OF_COLS; col++) {
            for (int row = 0; row < NUM_OF_ROWS - 3; row++) {
                if (pieces[row][col] != Piece.EMPTY &&
                        pieces[row][col] == pieces[row + 1][col] &&
                        pieces[row][col] == pieces[row + 2][col] &&
                        pieces[row][col] == pieces[row + 3][col]) {
                    return new Winner(pieces[row][col], col, row, col, row + 3);
                }
            }
        }

        return null;
    }


    // Add to the BoardImpl class
    @Override
    public int evaluateBoard() {
        int score = 0;
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            for (int col = 0; col < NUM_OF_COLS; col++) {
                if (pieces[row][col] == Piece.GREEN) {
                    score++;
                } else if (pieces[row][col] == Piece.BLUE) {
                    score--;
                }
            }
        }
        return score;
    }

    public void updateMove(int col, Piece move) {
        int row = findNextAvailableSpot(col);
        if(row != -1) {
            pieces[row][col] = move;
        }
    }

    public void undoMove(int col) {
        for (int row = NUM_OF_ROWS - 1; row >= 0; row--) {
            if (pieces[row][col] != Piece.EMPTY) {
                pieces[row][col] = Piece.EMPTY;
                break;
            }
        }
    }

}

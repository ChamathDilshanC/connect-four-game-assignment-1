package lk.ijse.dep.service;

public class AiPlayer extends Player {

    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
        int bestScore = Integer.MIN_VALUE;
        int bestCol = -1;
        for (int column = 0; column < Board.NUM_OF_COLS; column++) {
            if (board.isLegalMove(column)) {
                board.updateMove(column, Piece.GREEN);
                int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                ((BoardImpl) board).undoMove(column);
                if (score > bestScore) {
                    bestScore = score;
                    bestCol = column;
                }
            }
        }
        if (bestCol != -1) {
            board.updateMove(bestCol, Piece.GREEN);
            board.getBoardUI().update(bestCol, false);
        }
    }

    private int minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta) {
        Winner winner = board.findWinner();
        if (depth == 4 || winner != null || !board.existLegalMoves()) {
            if (winner != null) {
                if (winner.getWinningPiece() == Piece.GREEN) {
                    return Integer.MAX_VALUE - depth;
                } else if (winner.getWinningPiece() == Piece.BLUE) {
                    return Integer.MIN_VALUE + depth;
                }
                return 0;
            }
            return board.evaluateBoard();
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int column = 0; column < Board.NUM_OF_COLS; column++) {
                if (board.isLegalMove(column)) {
                    board.updateMove(column, Piece.GREEN);
                    int eval = minimax(board, depth + 1, false, alpha, beta);
                    ((BoardImpl) board).undoMove(column);
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int column = 0; column < Board.NUM_OF_COLS; column++) {
                if (board.isLegalMove(column)) {
                    board.updateMove(column, Piece.BLUE);
                    int eval = minimax(board, depth + 1, true, alpha, beta);
                    ((BoardImpl) board).undoMove(column);
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }
}

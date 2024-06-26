
package lk.ijse.dep.service;

public class HumanPlayer extends Player {

    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
        // legald check
        if (board.isLegalMove(col)) {
            // board update
            board.updateMove(col, Piece.BLUE);
            board.getBoardUI().update(col, true);

            //  winner d check
            Winner winner = board.findWinner();
            if (winner != null) {
                board.getBoardUI().notifyWinner(winner);
            } else {
                if (!board.existLegalMoves()) {
                    board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }
        } else {
            // prompt ekk
        }
    }
}
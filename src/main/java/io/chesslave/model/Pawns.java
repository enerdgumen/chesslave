package io.chesslave.model;

import javaslang.control.Option;

public final class Pawns {

    private Pawns() {
    }

    public static int direction(Color color) {
        return color == Color.WHITE ? +1 : -1;
    }

    public static boolean inPromotion(Color color, Square square) {
        return color == Color.WHITE
                ? square.row == 7
                : square.row == 0;
    }

    public static boolean isCapture(Movements.Regular pawnMove) {
        return pawnMove.enPassant || pawnMove.from.col != pawnMove.to.col;
    }

    public static boolean isEnPassantAvailable(Square pawnSquare, Position position) {
        Option<Piece> piece = position.at(pawnSquare);
        return piece.isDefined() && Piece.Type.PAWN.equals(piece.get().type)
                && (Color.WHITE.equals(piece.get().color) && pawnSquare.row == 4
                || Color.BLACK.equals(piece.get().color) && pawnSquare.row == 3)
                && (pawnSquare.translate(-1, 0).flatMap(position::at)
                    .contains(piece.get().color.opponent().pawn())
                    && pawnSquare.translate(-1, Pawns.direction(piece.get().color))
                    .flatMap(position::at).isEmpty()
                || pawnSquare.translate(+1, 0).flatMap(position::at)
                    .contains(piece.get().color.opponent().pawn())
                    && pawnSquare.translate(+1, Pawns.direction(piece.get().color))
                    .flatMap(position::at).isEmpty()
                );
    }
}

package io.chesslave.model.notations;

import io.chesslave.model.MoveDescription;
import io.chesslave.model.Piece;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

/**
 * Format a move description using the standard Algebraic Notation.
 */
public class StandardAlgebraicNotation implements MoveNotation {

    private static final String SHORT_CASTLING = "0-0";
    private static final String LONG_CASTLING = "0-0-0";
    private static final String CAPTURE_SYMBOL = "x";
    private static final Map<MoveDescription.Status, String> STATUS_SYMBOLS = HashMap.of(
            MoveDescription.Status.CHECK, "+",
            MoveDescription.Status.CHECKMATE, "#");
    private static final Map<Piece.Type, String> PIECE_NAMES = HashMap.of(
            Piece.Type.KNIGHT, "N",
            Piece.Type.BISHOP, "B",
            Piece.Type.ROOK, "R",
            Piece.Type.QUEEN, "Q",
            Piece.Type.KING, "K");

    @Override
    public String print(MoveDescription description) {
        return Match(description).of(
                Case(instanceOf(MoveDescription.Castling.class), mv -> {
                    final StringBuilder notation = new StringBuilder();
                    notation.append(mv.isShort() ? SHORT_CASTLING : LONG_CASTLING);
                    notation.append(statusNotation(mv.status()));
                    return notation.toString();
                }),
                Case(instanceOf(MoveDescription.Regular.class), mv -> {
                    final StringBuilder notation = new StringBuilder();
                    notation.append(squareNotation(mv.fromSquare()));
                    notation.append(mv.capture() ? CAPTURE_SYMBOL : "");
                    notation.append(squareNotation(mv.toSquare()));
                    // TODO: en passant
                    // TODO: promotion
                    notation.append(statusNotation(mv.status()));
                    return notation.toString();
                })
        );
    }

    private String squareNotation(MoveDescription.Square square) {
        final StringBuilder notation = new StringBuilder();
        square.piece().ifPresent(it -> notation.append(PIECE_NAMES.get(it).getOrElse("")));
        square.col().ifPresent(it -> notation.append(Character.toString((char) ('a' + it))));
        square.row().ifPresent(it -> notation.append(Character.toString((char) ('1' + it))));
        return notation.toString();
    }

    private String statusNotation(MoveDescription.Status status) {
        return STATUS_SYMBOLS.get(status).getOrElse("");
    }
}

package io.chesslave.model.notations;

import io.chesslave.model.Color;
import io.chesslave.model.Move;
import io.chesslave.model.Movements;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Rules;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;
import java.util.Optional;

/**
 * Base Algebraic Notation. This class can be used to implements variations of the algebraic notation.
 */
public abstract class BaseAlgebraicNotation implements MoveNotation {

    protected static final String SHORT_CASTLING = "0-0";
    protected static final String LONG_CASTLING = "0-0-0";

    private static final String CAPTURE_SYMBOL = "x";
    private static final String CHECK_SYMBOL = "+";
    private static final String CHECKMATE_SYMBOL = "#";
    private static final Map<Piece.Type, String> PIECE_NAMES = HashMap.of(
            Piece.Type.PAWN, "",
            Piece.Type.KNIGHT, "N",
            Piece.Type.BISHOP, "B",
            Piece.Type.ROOK, "R",
            Piece.Type.QUEEN, "Q",
            Piece.Type.KING, "K"
    );

    protected String pieceNotation(Piece piece) {
        return PIECE_NAMES.apply(piece.type);
    }

    protected String captureNotation(Movements.Regular move, Position position) {
        return move.enPassant || position.at(move.to).isDefined() ? CAPTURE_SYMBOL : "";
    }

    protected Optional<String> checkNotation(Move move, Position position, Color opponentColor) {
        final Position resultingPosition = move.apply(position);
        if (Rules.isKingSafe(resultingPosition, opponentColor)) {
            return Optional.empty();
        }
        final boolean checkmate = Rules.allMoves(resultingPosition, opponentColor).isEmpty();
        return Optional.of(checkmate ? CHECKMATE_SYMBOL : CHECK_SYMBOL);
    }
}
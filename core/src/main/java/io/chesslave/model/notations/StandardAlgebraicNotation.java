package io.chesslave.model.notations;

import io.chesslave.model.*;
import io.chesslave.model.Movements.LongCastling;
import io.chesslave.model.Movements.ShortCastling;
import io.chesslave.model.Piece.Type;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;
import static io.chesslave.model.Movements.Regular;
import static javaslang.API.Case;
import static javaslang.API.Match;
import static javaslang.Predicates.instanceOf;

/**
 * Implementation of the standard Algebraic Notation.
 */
public class StandardAlgebraicNotation implements MoveNotation {

    private static final String SHORT_CASTLING = "0-0";
    private static final String LONG_CASTLING = "0-0-0";
    private static final String CAPTURE_SYMBOL = "x";
    private static final String CHECK_SYMBOL = "+";
    private static final String CHECKMATE_SYMBOL = "#";
    private static final Map<Type, String> PIECE_NAMES = HashMap.of(
            Type.KNIGHT, "N",
            Type.BISHOP, "B",
            Type.ROOK, "R",
            Type.QUEEN, "Q",
            Type.KING, "K"
    );

    @Override
    public String print(Move move, Position position) {
        return Match(move).of(
                Case(instanceOf(ShortCastling.class), mv -> {
                    final StringBuilder notation = new StringBuilder();
                    notation.append(SHORT_CASTLING);
                    notation.append(checkNotation(move, position, mv.color.opponent()).getOrElse(""));
                    return notation.toString();
                }),
                Case(instanceOf(LongCastling.class), mv -> {
                    final StringBuilder notation = new StringBuilder();
                    notation.append(LONG_CASTLING);
                    notation.append(checkNotation(move, position, mv.color.opponent()).getOrElse(""));
                    return notation.toString();
                }),
                Case(instanceOf(Regular.class), mv -> {
                    final Piece piece = position.at(mv.from).get();
                    final StringBuilder notation = new StringBuilder();
                    notation.append(pieceNotation(piece).getOrElse(""));
                    notation.append(disambiguatingSymbol(mv, position).getOrElse(""));
                    notation.append(captureNotation(mv, position).getOrElse(""));
                    notation.append(mv.to.name());
                    notation.append(checkNotation(move, position, piece.color.opponent()).getOrElse(""));
                    return notation.toString();
                })
        );
    }

    private Option<String> disambiguatingSymbol(Regular move, Position position) {
        final Piece piece = position.at(move.from).get();
        final Set<Square> ambiguousSquares = ambiguousSquares(piece, move, position);
        if (!ambiguousSquares.isEmpty()) {
            if (ambiguousSquares.size() == 1) {
                return Option.of(ambiguousSquares.head().col != move.from.col
                        ? String.valueOf((char) ('a' + move.from.col))
                        : String.valueOf(move.from.row + 1));
            } else {
                return Option.of(move.from.name());
            }
        } else if (move.enPassant ||
                Type.PAWN.equals(piece.type) && position.at(move.to).isDefined()) {
            return Option.of(String.valueOf((char) ('a' + move.from.col)));
        }
        return Option.none();
    }

    private Set<Square> ambiguousSquares(Piece piece, Regular move, Position position) {
        return position.toSet()
                .filter(square -> !square._1.equals(move.from) && square._2.equals(piece))
                .flatMap(square -> Rules.moves(position, square._1))
                .filter(mv -> mv.to.equals(move.to))
                .map(mv -> mv.from);
    }

    private Option<String> pieceNotation(Piece piece) {
        return PIECE_NAMES.get(piece.type);
    }

    private Option<String> captureNotation(Movements.Regular move, Position position) {
        return move.enPassant || position.at(move.to).isDefined()
                ? Option.of(CAPTURE_SYMBOL)
                : Option.none();
    }

    private Option<String> checkNotation(Move move, Position position, Color opponentColor) {
        final Position resultingPosition = move.apply(position);
        if (Rules.isKingSafe(resultingPosition, opponentColor)) {
            return Option.none();
        }
        final boolean checkmate = Rules.allMoves(resultingPosition, opponentColor).isEmpty();
        return Option.of(checkmate ? CHECKMATE_SYMBOL : CHECK_SYMBOL);
    }
}

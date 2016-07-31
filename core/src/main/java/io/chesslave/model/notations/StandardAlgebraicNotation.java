package io.chesslave.model.notations;

import io.chesslave.model.*;
import io.chesslave.model.Movements.LongCastling;
import io.chesslave.model.Movements.ShortCastling;
import io.chesslave.model.Piece.Type;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.collection.Set;
import java.util.Optional;
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
            Piece.Type.PAWN, "",
            Piece.Type.KNIGHT, "N",
            Piece.Type.BISHOP, "B",
            Piece.Type.ROOK, "R",
            Piece.Type.QUEEN, "Q",
            Piece.Type.KING, "K"
    );

    @Override
    public String print(Move move, Position position) {
        return Match(move).of(
                Case(instanceOf(ShortCastling.class), mv -> {
                    final StringBuilder notation = new StringBuilder();
                    notation.append(SHORT_CASTLING);
                    notation.append(checkNotation(move, position, mv.color.opponent()).orElse(""));
                    return notation.toString();
                }),
                Case(instanceOf(LongCastling.class), mv -> {
                    final StringBuilder notation = new StringBuilder();
                    notation.append(LONG_CASTLING);
                    notation.append(checkNotation(move, position, mv.color.opponent()).orElse(""));
                    return notation.toString();
                }),
                Case(instanceOf(Regular.class), mv -> {
                    final Piece piece = position.at(mv.from).get();
                    final StringBuilder notation = new StringBuilder();
                    notation.append(pieceNotation(piece));
                    notation.append(disambiguatingSymbol(mv, position));
                    notation.append(captureNotation(mv, position));
                    notation.append(mv.to.name());
                    notation.append(checkNotation(move, position, piece.color.opponent()).orElse(""));
                    return notation.toString();
                })
        );
    }

    private String disambiguatingSymbol(Regular move, Position position) {
        final Piece movingPiece = position.at(move.from).get();
        final Set<Square> ambiguousSquares = ambiguousSquares(movingPiece, move, position);
        if (!ambiguousSquares.isEmpty()) {
            if (ambiguousSquares.size() == 1) {
                return ambiguousSquares.head().col != move.from.col ?
                        String.valueOf((char) ('a' + move.from.col)) :
                        String.valueOf(move.from.row + 1);
            } else {
                return move.from.name();
            }
        } else if (move.enPassant ||
                Type.PAWN.equals(movingPiece.type) && position.at(move.to).isDefined()) {
            return String.valueOf((char) ('a' + move.from.col));
        }
        return "";
    }

    private Set<Square> ambiguousSquares(Piece movingPiece, Regular regularMove, Position position) {
        return position.toSet()
                .filter(square -> !square._1.equals(regularMove.from) && square._2.equals(movingPiece))
                .flatMap(square -> Rules.moves(position, square._1))
                .filter(move -> move.to.equals(regularMove.to))
                .map(move -> move.from);
    }

    private String pieceNotation(Piece piece) {
        return PIECE_NAMES.apply(piece.type);
    }

    private String captureNotation(Movements.Regular move, Position position) {
        return move.enPassant || position.at(move.to).isDefined() ? CAPTURE_SYMBOL : "";
    }

    private Optional<String> checkNotation(Move move, Position position, Color opponentColor) {
        final Position resultingPosition = move.apply(position);
        if (Rules.isKingSafe(resultingPosition, opponentColor)) {
            return Optional.empty();
        }
        final boolean checkmate = Rules.allMoves(resultingPosition, opponentColor).isEmpty();
        return Optional.of(checkmate ? CHECKMATE_SYMBOL : CHECK_SYMBOL);
    }
}

package io.chesslave.model.notations;

import io.chesslave.model.*;
import io.chesslave.model.Piece.Type;
import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.Map;
import javaslang.collection.Set;
import java.util.Optional;
import java.util.function.Predicate;
import static io.chesslave.model.Movements.Regular;

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
        final StringBuilder notation = new StringBuilder();
        final Color color;
        if (move instanceof Movements.ShortCastling) {
            color = ((Movements.ShortCastling) move).color;
            notation.append(SHORT_CASTLING);
        } else if (move instanceof Movements.LongCastling) {
            color = ((Movements.LongCastling) move).color;
            notation.append(LONG_CASTLING);
        } else {
            final Regular regularMove = (Regular) move;
            final Piece movingPiece = position.at(regularMove.from).get();
            color = movingPiece.color;
            notation.append(pieceNotation(movingPiece));
            notation.append(disambiguatingSymbol(regularMove, position));
            notation.append(captureNotation(regularMove, position));
            notation.append(regularMove.to.name());
        }
        notation.append(checkNotation(move, position, color.opponent()).orElse(""));
        return notation.toString();
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
        final Predicate<Square> notSameSquare = sqr -> !sqr.equals(regularMove.from);
        switch (movingPiece.type) {
            case PAWN:
                return Pawns.isCapture(regularMove)
                        ? Rules.attackingPawnSquares(regularMove.to, movingPiece.color, position).filter(notSameSquare)
                        : HashSet.empty();
            case KNIGHT:
                return Rules.attackingKnightSquares(regularMove.to, movingPiece.color, position).filter(notSameSquare);
            case BISHOP:
                return Rules.attackingBishopSquares(regularMove.to, movingPiece.color, position).filter(notSameSquare);
            case ROOK:
                return Rules.attackingRookSquares(regularMove.to, movingPiece.color, position).filter(notSameSquare);
            case QUEEN:
                return Rules.attackingQueenSquares(regularMove.to, movingPiece.color, position).filter(notSameSquare);
            case KING:
            default:
                return HashSet.empty();
        }
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

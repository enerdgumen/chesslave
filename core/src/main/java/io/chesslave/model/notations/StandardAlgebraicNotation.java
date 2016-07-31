package io.chesslave.model.notations;

import io.chesslave.model.*;
import io.chesslave.model.Piece.Type;
import javaslang.collection.HashSet;
import javaslang.collection.Set;
import java.util.function.Predicate;
import static io.chesslave.model.Movements.Regular;

/**
 * Implementation of the standard Algebraic Notation.
 */
public class StandardAlgebraicNotation extends BaseAlgebraicNotation {

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
}

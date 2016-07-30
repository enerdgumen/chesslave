package io.chesslave.model.notations;

import static io.chesslave.model.Movements.Regular;

import io.chesslave.model.Color;
import io.chesslave.model.Move;
import io.chesslave.model.Movements;
import io.chesslave.model.Pawns;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import io.chesslave.model.Rules;
import io.chesslave.model.Square;
import javaslang.collection.HashSet;
import javaslang.collection.Set;

import java.util.function.Predicate;

/**
 * Implementation of the standard Algebraic Notation.
 */
public class StandardAlgebraicNotation extends BaseAlgebraicNotation {

    @Override
    public String print(Move move, Position position) {
        StringBuilder notationBuilder = new StringBuilder();
        Color color;
        if (move instanceof Movements.ShortCastling) {
            color = ((Movements.ShortCastling) move).color;
            notationBuilder.append(SHORT_CASTLING);
        } else if (move instanceof Movements.LongCastling) {
            color = ((Movements.LongCastling) move).color;
            notationBuilder.append(LONG_CASTLING);
        } else {
            Regular regularMove = (Regular) move;
            final Piece movingPiece = position.at(regularMove.from).get();
            color = movingPiece.color;

            notationBuilder.append(pieceNotation(movingPiece));
            notationBuilder.append(disambiguatingSymbol(regularMove, position));
            notationBuilder.append(captureNotation(regularMove, position));
            notationBuilder.append(regularMove.to.name());
        }
        notationBuilder.append(checkNotation(move, position, color.opponent()));
        return notationBuilder.toString();
    }

    private String disambiguatingSymbol(Regular regularMove, Position position) {
        final Piece movingPiece = position.at(regularMove.from).get();
        final Set<Square> ambiguousSquares = ambiguousSquares(movingPiece, regularMove, position);
        if (!ambiguousSquares.isEmpty()) {
            if (ambiguousSquares.size() == 1) {
                return ambiguousSquares.head().col != regularMove.from.col ?
                        String.valueOf((char) ('a' + regularMove.from.col)) :
                        String.valueOf(regularMove.from.row + 1);
            } else {
                return regularMove.from.name();
            }
        } else if (regularMove.enPassant ||
                Piece.Type.PAWN.equals(movingPiece.type) && position.at(regularMove.to).isDefined()) {
            return String.valueOf((char) ('a' + regularMove.from.col));
        }
        return "";
    }

    private Set<Square> ambiguousSquares(Piece movingPiece, Regular regularMove, Position position) {
        final Predicate<Square> notSameSquare = sqr -> !sqr.equals(regularMove.from);
        switch (movingPiece.type) {
            case PAWN:
                return Pawns.isCapture(regularMove) ?
                        Rules.attackingPawnSquares(regularMove.to, movingPiece.color, position).filter(notSameSquare) :
                        HashSet.empty();
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

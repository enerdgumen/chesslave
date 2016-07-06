package io.chesslave.model.notations;

import static io.chesslave.model.Movements.Regular;

import io.chesslave.model.Color;
import io.chesslave.model.Move;
import io.chesslave.model.Movements;
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
            notationBuilder.append(captureNotation(position.at(regularMove.to)));
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
        } else if (Piece.Type.PAWN.equals(movingPiece.type) && position.at(regularMove.to).isDefined()) {
            return String.valueOf((char) ('a' + regularMove.from.col));
        }
        return "";
    }

    private Set<Square> ambiguousSquares(Piece movingPiece, Regular regularMove, Position position) {
        final Predicate<Square> friendSamePieceType = sqr -> position.at(sqr).isDefined()
                && position.at(sqr).get().color.equals(movingPiece.color)
                && position.at(sqr).get().type.equals(movingPiece.type)
                && !sqr.equals(regularMove.from);
        switch (movingPiece.type) {
            case PAWN:
                return regularMove.from.col != regularMove.to.col ?
                        Rules.attackingSquaresForPawn(movingPiece.color, regularMove.to).filter(friendSamePieceType) :
                        HashSet.empty();
            case KNIGHT:
                return Rules.attackingSquaresForKnight(regularMove.to).filter(friendSamePieceType);
            case BISHOP:
                return Rules.attackingSquaresForBishop(regularMove.to, position).filter(friendSamePieceType);
            case ROOK:
                return Rules.attackingSquaresForRook(regularMove.to, position).filter(friendSamePieceType);
            case QUEEN:
                return Rules.attackingSquaresForQueen(regularMove.to, position).filter(friendSamePieceType);
            case KING:
            default:
                return HashSet.empty();
        }
    }
}

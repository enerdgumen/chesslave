package io.chesslave.model

import io.chesslave.model.Piece.Type
import javaslang.collection.List

/**
 * An immutable chess match tracker.
 */
class Game(val initialPosition: Position, val moves: List<Move>, val turn: Color) {

    /**
     * Applies the move to the current position.

     * @param move the move to apply to the position
     * *
     * @return the resulting game
     */
    fun move(move: Move): Game =
            Game(initialPosition, moves.append(move), turn.opponent())

    /**
     * @return the current position.
     */
    fun position(): Position =
            moves.foldLeft(initialPosition) { pos, move -> move.apply(pos) }

    /**
     * @return the color to move next
     */
    fun turn(): Color = turn

    /**
     * @return the list of moves
     */
    fun moves(): List<Move> = moves

    companion object {

        /**
         * @return a game from the initial position.
         */
        fun initialPosition(): Game {
            val position = Position.Builder()
                    .withPiece(Board.a1, Piece.whiteRook)
                    .withPiece(Board.b1, Piece.whiteKnight)
                    .withPiece(Board.c1, Piece.whiteBishop)
                    .withPiece(Board.d1, Piece.whiteQueen)
                    .withPiece(Board.e1, Piece.whiteKing)
                    .withPiece(Board.f1, Piece.whiteBishop)
                    .withPiece(Board.g1, Piece.whiteKnight)
                    .withPiece(Board.h1, Piece.whiteRook)
                    .withPiece(Board.a2, Piece.whitePawn)
                    .withPiece(Board.b2, Piece.whitePawn)
                    .withPiece(Board.c2, Piece.whitePawn)
                    .withPiece(Board.d2, Piece.whitePawn)
                    .withPiece(Board.e2, Piece.whitePawn)
                    .withPiece(Board.f2, Piece.whitePawn)
                    .withPiece(Board.g2, Piece.whitePawn)
                    .withPiece(Board.h2, Piece.whitePawn)
                    .withPiece(Board.a8, Piece.blackRook)
                    .withPiece(Board.b8, Piece.blackKnight)
                    .withPiece(Board.c8, Piece.blackBishop)
                    .withPiece(Board.d8, Piece.blackQueen)
                    .withPiece(Board.e8, Piece.blackKing)
                    .withPiece(Board.f8, Piece.blackBishop)
                    .withPiece(Board.g8, Piece.blackKnight)
                    .withPiece(Board.h8, Piece.blackRook)
                    .withPiece(Board.a7, Piece.blackPawn)
                    .withPiece(Board.b7, Piece.blackPawn)
                    .withPiece(Board.c7, Piece.blackPawn)
                    .withPiece(Board.d7, Piece.blackPawn)
                    .withPiece(Board.e7, Piece.blackPawn)
                    .withPiece(Board.f7, Piece.blackPawn)
                    .withPiece(Board.g7, Piece.blackPawn)
                    .withPiece(Board.h7, Piece.blackPawn)
                    .build()
            return Game(position, List.empty(), Color.WHITE)
        }
    }
}
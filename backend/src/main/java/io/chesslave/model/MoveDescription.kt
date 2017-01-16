package io.chesslave.model

import io.chesslave.extensions.component1
import io.chesslave.extensions.component2
import io.chesslave.extensions.defined

sealed class MoveDescription {

    data class Regular(val fromSquare: Square? = null,
                       val toSquare: Square,
                       val capture: Boolean = false,
                       val enPassant: Boolean = false,
                       val promotion: Piece.Type? = null,
                       val status: Status = Status.RELAX) : MoveDescription()

    data class Castling(val short: Boolean? = null,
                        val status: Status = Status.RELAX) : MoveDescription()

    data class Square(val piece: Piece.Type? = null,
                      val col: Int? = null,
                      val row: Int? = null) {
        init {
            if (piece == null && col == null && row == null) throw IllegalArgumentException("Wrong square description!")
        }
    }

    enum class Status {
        RELAX,
        CHECK,
        CHECKMATE
    }
}

/**
 * Describe a move in a minimal non-ambiguous way.
 *
 * @param move     the move to made
 * @param position the position before the move
 * @return the move's description
 */
fun describeMove(move: Move, position: Position): MoveDescription = when (move) {
    is Move.ShortCastling ->
        MoveDescription.Castling(short = true, status = status(move, position, move.color.opponent()))
    is Move.LongCastling ->
        MoveDescription.Castling(short = false, status = status(move, position, move.color.opponent()))
    is Move.Regular ->
        MoveDescription.Regular(
            fromSquare = fromSquareDescription(move, position),
            toSquare = toSquareDescription(move.to),
            capture = isCapture(move, position),
            enPassant = move.variation is Move.Regular.Variation.EnPassant,
            promotion = if (move.variation is Move.Regular.Variation.Promotion) move.variation.type else null,
            status = status(move, position, position.get(move.from).color.opponent())
        )
}

private fun fromSquareDescription(move: Move.Regular, position: Position): MoveDescription.Square {
    val piece = position.get(move.from)
    val ambiguousSquares = ambiguousSquares(piece, move, position)
    return when {
        ambiguousSquares.size() == 1 -> {
            if (ambiguousSquares.head().col == move.from.col)
                MoveDescription.Square(piece = piece.type, row = move.from.row)
            else
                MoveDescription.Square(piece = piece.type, col = move.from.col)
        }
        ambiguousSquares.size() > 1 ->
            MoveDescription.Square(piece = piece.type, row = move.from.row, col = move.from.col)
        move.variation is Move.Regular.Variation.EnPassant
            || Piece.Type.PAWN == piece.type && position.at(move.to).defined ->
            MoveDescription.Square(piece = piece.type, col = move.from.col)
        else ->
            MoveDescription.Square(piece = piece.type)
    }
}

private fun toSquareDescription(square: Square) =
    MoveDescription.Square(row = square.row, col = square.col)

private fun ambiguousSquares(movingPiece: Piece, move: Move.Regular, position: Position) =
    position.toSet()
        .filter { (square, piece) -> square != move.from && piece == movingPiece }
        .flatMap { (square, _) -> position.moves(square) }
        .filter { it.to == move.to }
        .map { it.from }

private fun isCapture(move: Move.Regular, position: Position) =
    move.variation is Move.Regular.Variation.EnPassant || position.at(move.to).defined

private fun status(move: Move, position: Position, opponentColor: Color) =
    move.apply(position).let { nextPosition ->
        when {
            nextPosition.isKingSafe(opponentColor) -> MoveDescription.Status.RELAX
            nextPosition.allMoves(opponentColor).isEmpty -> MoveDescription.Status.CHECKMATE
            else -> MoveDescription.Status.CHECK
        }
    }
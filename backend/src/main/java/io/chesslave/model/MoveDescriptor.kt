package io.chesslave.model

import io.chesslave.model.Piece.Type

class MoveDescriptor {

    /**
     * Describe a move in a minimal non-ambiguous way.

     * @param move     the move to made
     * *
     * @param position the position before the move
     * *
     * @return the move's description
     */
    fun describe(move: Move, position: Position): MoveDescription = when (move) {
        is Move.ShortCastling ->
            MoveDescription.Castling(short = true, status = status(move, position, move.color.opponent()))
        is Move.LongCastling ->
            MoveDescription.Castling(short = false, status = status(move, position, move.color.opponent()))
        is Move.Regular ->
            MoveDescription.Regular(
                fromSquare = fromSquareDescription(move, position),
                toSquare = toSquareDescription(move.to),
                capture = isCapture(move, position),
                enPassant = move.enPassant,
                promotion = move.promotion.getOrElse(null as Piece.Type?),
                status = status(move, position, position.at(move.from).get().color.opponent()))
        else -> TODO("Moves should be an algebraic data type")
    }

    private fun fromSquareDescription(move: Move.Regular, position: Position): MoveDescription.Square {
        val piece = position.at(move.from).get()
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
            move.enPassant || Type.PAWN == piece.type && position.at(move.to).isDefined ->
                MoveDescription.Square(piece = piece.type, col = move.from.col)
            else ->
                MoveDescription.Square(piece = piece.type)
        }
    }

    private fun toSquareDescription(square: Square) =
        MoveDescription.Square(row = square.row, col = square.col)

    private fun ambiguousSquares(piece: Piece, move: Move.Regular, position: Position) =
        position.toSet()
            .filter { it._1 != move.from && it._2 == piece }
            .flatMap { Rules.moves(position, it._1) }
            .filter { it.to == move.to }
            .map { it.from }

    private fun isCapture(move: Move.Regular, position: Position) =
        move.enPassant || position.at(move.to).isDefined

    private fun status(move: Move, position: Position, opponentColor: Color) =
        move.apply(position).let { nextPosition ->
            when {
                Rules.isKingSafe(nextPosition, opponentColor) -> MoveDescription.Status.RELAX
                Rules.allMoves(nextPosition, opponentColor).isEmpty -> MoveDescription.Status.CHECKMATE
                else -> MoveDescription.Status.CHECK
            }
        }
}

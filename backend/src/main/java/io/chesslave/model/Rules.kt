package io.chesslave.model

import io.chesslave.extensions.*
import io.chesslave.model.Move.Regular.Variation.EnPassant
import io.chesslave.model.Piece.Type
import javaslang.collection.HashSet
import javaslang.collection.List
import javaslang.collection.Set
import javaslang.collection.Stream

/**
 * TODO: Handle pawn promotion.

 * @return all the available moves (excluding castling) of the piece placed at the given square for the specified position.
 */
fun Position.moves(from: Square): Set<Move.Regular>
    = at(from)
    ?.let { piece ->
        val asRegular = { to: Square -> Move.Regular(from, to) }
        val isFreeOrWithOpponent = { move: Move.Regular -> at(move.to)?.let { piece.isOpponent(it) } ?: true }
        val isKingSafeAfterMove = { move: Move.Regular -> move.apply(this).isKingSafe(piece.color) }
        val isAvailable = isFreeOrWithOpponent and isKingSafeAfterMove
        when (piece.type) {
            Type.PAWN -> pawnMoves(from).filter(isKingSafeAfterMove)
            Type.KING -> from.kingSquares().map(asRegular).filter(isAvailable)
            Type.KNIGHT -> from.knightSquares().map(asRegular).filter(isAvailable)
            Type.BISHOP -> from.bishopSquaresIn(this).map(asRegular).filter(isAvailable)
            Type.ROOK -> from.rookSquaresIn(this).map(asRegular).filter(isAvailable)
            Type.QUEEN -> from.queenSquaresIn(this).map(asRegular).filter(isAvailable)
        }
    }
    ?: HashSet.empty()

/**
 * @return all the moves available for the specified color.
 */
fun Position.allMoves(color: Color): Stream<Move.Regular>
    = this.toSet().toStream()
    .filter { (_, piece) -> piece.color == color }
    .flatMap { (square) -> moves(square) }

/**
 * @return true if the king of the given color is not under attack in this position.
 */
fun Position.isKingSafe(color: Color): Boolean
    = !Square.all()
    .find { at(it) == Piece.kingOf(color) }.get()
    .isTargetForColor(this, color.opponent())

/**
 * @return true if this square is under attack or defence by pieces of the specified color.
 */
fun Square.isTargetForColor(position: Position, color: Color): Boolean
    = knightSquares().exists { position.at(it) == Piece.knightOf(color) }
    || bishopSquaresIn(position).exists { position.at(it) == Piece.bishopOf(color) }
    || rookSquaresIn(position).exists { position.at(it) == Piece.rookOf(color) }
    || queenSquaresIn(position).exists { position.at(it) == Piece.queenOf(color) }
    || kingSquares().exists { position.at(it) == Piece.kingOf(color) }
    || pawnCaptureSquares(color.opponent()).exists { position.at(it) == Piece.pawnOf(color) }

/**
 * @return the pieces candidates to promotion if this piece is promotable in the given square, null otherwise
 */
fun Piece.candidatesForPromotion(square: Square): List<Piece>? {
    val promotionRow = if (color === Color.WHITE) 7 else 0
    return if (type == Type.PAWN && square.row == promotionRow) List.of(Piece.queenOf(color), Piece.rookOf(color), Piece.knightOf(color), Piece.bishopOf(color))
    else null
}

private fun Square.kingSquares()
    = translateAll(
    Pair(+0, +1),
    Pair(+1, +1),
    Pair(+1, +0),
    Pair(+1, -1),
    Pair(+0, -1),
    Pair(-1, -1),
    Pair(-1, +0),
    Pair(-1, +1))

private fun Square.knightSquares()
    = translateAll(
    Pair(+1, +2),
    Pair(+2, +1),
    Pair(+2, -1),
    Pair(+1, -2),
    Pair(-1, -2),
    Pair(-2, -1),
    Pair(-2, +1),
    Pair(-1, +2))

private fun Square.bishopSquaresIn(position: Position): Set<Square>
    = HashSet.of(walk(+1, +1), walk(+1, -1), walk(-1, -1), walk(-1, +1))
    .flatMap(position::walkUntilPiece)

private fun Square.rookSquaresIn(position: Position): Set<Square>
    = HashSet.of(walk(0, +1), walk(+1, 0), walk(0, -1), walk(-1, 0))
    .flatMap(position::walkUntilPiece)

private fun Square.queenSquaresIn(position: Position): Set<Square>
    = bishopSquaresIn(position).addAll(rookSquaresIn(position))

private fun Position.walkUntilPiece(walk: BoardPath): BoardPath
    = walk
    .splitAt { at(it).defined }
    .map({ it }, { it.headOption() })
    .transform(BoardPath::appendAll)

private fun Square.pawnCaptureSquares(color: Color): Set<Square> {
    val direction = Pawns.direction(color)
    return translateAll(Pair(-1, direction), Pair(+1, direction))
}

private fun Position.pawnMoves(from: Square): Set<Move.Regular> {
    val piece = get(from)
    val direction = Pawns.direction(piece.color)
    val initialRow = if (piece.color === Color.WHITE) 1 else 6
    val push = if (from.row == initialRow) 2L else 1L
    val forward = from.walk(0, direction)
        .takeWhile { at(it).undefined }
        .take(push)
        .map { to -> Move.Regular(from, to) }
    val captures = from.pawnCaptureSquares(piece.color)
        .filter { to -> at(to).exists { piece.isOpponent(it) } }
        .map { to -> Move.Regular(from, to) }
    val enPassantRow = if (piece.color === Color.WHITE) 4 else 3
    val enPassantCaptures =
        if (from.row == enPassantRow)
            from.translateAll(Pair(-1, 0), Pair(+1, 0))
                .filter { side -> at(side) == Piece.pawnOf(piece.color.opponent()) }
                .map { side -> side.translate(0, direction)!! }
                .filter { to -> at(to).undefined }
                .map { to -> Move.Regular(from, to, EnPassant()) }
        else HashSet.empty()
    return forward.appendAll(captures).appendAll(enPassantCaptures).toSet()
}
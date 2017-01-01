package io.chesslave.model

import io.chesslave.extensions.*
import io.chesslave.model.Move.Regular.Variation.EnPassant
import io.chesslave.model.Piece.Type
import javaslang.collection.HashSet
import javaslang.collection.Set
import javaslang.collection.Stream

/**
 * TODO: Handle pawn promotion.

 * @return all the available moves (excluding castling) of the piece placed at the given square for the specified position.
 */
fun moves(pos: Position, from: Square): Set<Move.Regular>
    = pos.at(from)
    ?.let { piece ->
        val asRegular = { to: Square -> Move.Regular(from, to) }
        val isFreeOrWithOpponent = { move: Move.Regular -> pos.at(move.to)?.let { piece.isOpponent(it) } ?: true }
        val isKingSafeAfterMove = { move: Move.Regular -> isKingSafe(move.apply(pos), piece.color) }
        val isAvailable = isFreeOrWithOpponent and isKingSafeAfterMove
        when (piece.type) {
            Type.PAWN -> pawnMoves(pos, from).filter(isKingSafeAfterMove)
            Type.KING -> kingSquares(from).map(asRegular).filter(isAvailable)
            Type.KNIGHT -> knightSquares(from).map(asRegular).filter(isAvailable)
            Type.BISHOP -> bishopSquares(pos, from).map(asRegular).filter(isAvailable)
            Type.ROOK -> rookSquares(pos, from).map(asRegular).filter(isAvailable)
            Type.QUEEN -> queenSquares(pos, from).map(asRegular).filter(isAvailable)
        }
    }
    ?: HashSet.empty()

/**
 * @return all the moves available for the specified color.
 */
fun allMoves(position: Position, color: Color): Stream<Move.Regular>
    = position.toSet().toStream()
    .filter { (_, piece) -> piece.color == color }
    .flatMap { (square) -> moves(position, square) }

/**
 * @return true if the king of the given color is not under attack.
 */
fun isKingSafe(position: Position, color: Color): Boolean {
    val kingSquare = Square.all().find { position.at(it) == Piece.kingOf(color) }.get()
    return !isTargetForColor(position, kingSquare, color.opponent())
}

/**
 * @return true if the given square is under attack by pieces of the specified color.
 */
fun isTargetForColor(position: Position, square: Square, color: Color): Boolean
    = knightSquares(square).exists { position.at(it) == Piece.knightOf(color) }
    || bishopSquares(position, square).exists { position.at(it) == Piece.bishopOf(color) }
    || rookSquares(position, square).exists { position.at(it) == Piece.rookOf(color) }
    || queenSquares(position, square).exists { position.at(it) == Piece.queenOf(color) }
    || kingSquares(square).exists { position.at(it) == Piece.kingOf(color) }
    || attackingPawnSquares(square, color, position).exists { position.at(it) == Piece.pawnOf(color) }

private fun attackingPawnSquares(target: Square, attackingColor: Color, position: Position): Set<Square> {
    val pawnDirection = Pawns.direction(attackingColor.opponent())
    var squares = target.translateAll(Pair(-1, pawnDirection), Pair(+1, pawnDirection))
    val targetPiece = position.at(target)
    if (targetPiece != null && Type.PAWN == targetPiece.type) {
        squares = squares.addAll(target.translateAll(Pair(-1, 0), Pair(+1, 0))
            .filter { square -> Pawns.isEnPassantAvailable(square, position) })
    }
    return squares
}

private fun kingSquares(from: Square)
    = from.translateAll(
    Pair(+0, +1),
    Pair(+1, +1),
    Pair(+1, +0),
    Pair(+1, -1),
    Pair(+0, -1),
    Pair(-1, -1),
    Pair(-1, +0),
    Pair(-1, +1))

private fun knightSquares(from: Square)
    = from.translateAll(
    Pair(+1, +2),
    Pair(+2, +1),
    Pair(+2, -1),
    Pair(+1, -2),
    Pair(-1, -2),
    Pair(-2, -1),
    Pair(-2, +1),
    Pair(-1, +2))

private fun bishopSquares(position: Position, from: Square): Set<Square>
    = HashSet.of(from.walk(+1, +1), from.walk(+1, -1), from.walk(-1, -1), from.walk(-1, +1))
    .flatMap { walkUntilPiece(position, it) }

private fun rookSquares(position: Position, from: Square): Set<Square>
    = HashSet.of(from.walk(0, +1), from.walk(+1, 0), from.walk(0, -1), from.walk(-1, 0))
    .flatMap { walkUntilPiece(position, it) }

private fun queenSquares(position: Position, from: Square)
    = bishopSquares(position, from).addAll(rookSquares(position, from))

private fun walkUntilPiece(position: Position, walk: Stream<Square>)
    = walk
    .splitAt { position.at(it).defined }
    .map({ it }, { it.headOption() })
    .transform(Stream<Square>::appendAll)

private fun pawnMoves(position: Position, from: Square): Set<Move.Regular> {
    val piece = position.get(from)
    val direction = Pawns.direction(piece.color)
    val initialRow = if (piece.color === Color.WHITE) 1 else 6
    val push = if (from.row == initialRow) 2L else 1L
    val forward = from.walk(0, direction)
        .takeWhile { position.at(it).undefined }
        .take(push)
        .map { to -> Move.Regular(from, to) }
    val captures = from.translateAll(Pair(-1, direction), Pair(+1, direction))
        .filter { to -> position.at(to).exists { piece.isOpponent(it) } }
        .map { to -> Move.Regular(from, to) }
    val enPassantCaptures =
        if (Pawns.isEnPassantAvailable(from, position))
            from.translateAll(Pair(-1, 0), Pair(+1, 0))
                .filter { sq -> position.at(sq) == Piece.pawnOf(piece.color.opponent()) }
                .map { to -> to.translate(0, direction)!! }
                .map { to -> Move.Regular(from, to, EnPassant()) }
        else HashSet.empty()
    return forward.appendAll(captures).appendAll(enPassantCaptures).toSet()
}
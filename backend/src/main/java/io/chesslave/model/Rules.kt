package io.chesslave.model

import javaslang.Predicates
import javaslang.collection.HashSet
import javaslang.collection.Set
import javaslang.collection.Stream
import javaslang.control.Option
import java.util.function.Function
import java.util.function.Predicate

/**
 * Defines the chess logics.
 */
object Rules {

    /**
     * TODO: Handle pawn promotion.

     * @return all the available moves (excluding castling) of the piece placed at the given square for the specified position.
     */
    fun moves(pos: Position, from: Square): Set<Move.Regular> = pos.at(from)
        .map { piece ->
            val regular = { to: Square -> Move.Regular(from, to) }
            val isFreeOrWithOpponent = Predicate { move: Move.Regular -> isFreeOrWithOpponent(pos, move.to, piece) }
            val isKingSafeAfterMove = Predicate { move: Move.Regular -> isKingSafe(move.apply(pos), piece.color) }
            val isAvailable = Predicates.allOf(isFreeOrWithOpponent, isKingSafeAfterMove)
            when (piece.type) {
                Piece.Type.PAWN -> pawnMoves(pos, from).filter(isKingSafeAfterMove)
                Piece.Type.KING -> kingSquares(from).map(regular).filter(isAvailable)
                Piece.Type.KNIGHT -> knightSquares(from).map(regular).filter(isAvailable)
                Piece.Type.BISHOP -> bishopSquares(pos, from).map(regular).filter(isAvailable)
                Piece.Type.ROOK -> rookSquares(pos, from).map(regular).filter(isAvailable)
                Piece.Type.QUEEN -> queenSquares(pos, from).map(regular).filter(isAvailable)
            }
        }
        .getOrElse(HashSet.empty())

    /**
     * @return all the moves available for the specified color.
     */
    fun allMoves(position: Position, color: Color): Stream<Move.Regular> = position.toSet().toStream()
        .filter { it._2.color == color }
        .flatMap { Rules.moves(position, it._1) }

    /**
     * @return true if the king of the given color is not under attack.
     */
    fun isKingSafe(position: Position, color: Color): Boolean {
        val kingSquare = Square.all().find { position.at(it).exists({ it == color.king() }) }.get()
        return !isTargetForColor(position, kingSquare, color.opponent())
    }

    /**
     * @return true if the given square is under attack by pieces of the specified color.
     */
    fun isTargetForColor(position: Position, square: Square, color: Color): Boolean =
        attackingKnightSquares(square, color, position).nonEmpty()
            || attackingBishopSquares(square, color, position).nonEmpty()
            || attackingRookSquares(square, color, position).nonEmpty()
            || attackingQueenSquares(square, color, position).nonEmpty()
            || attackingKingSquares(square, color, position).nonEmpty()
            || attackingPawnSquares(square, color, position).nonEmpty()

    // TODO: check visibility
    fun attackingPawnSquares(target: Square, attackingColor: Color, position: Position): Set<Square> {
        val pawnDirection = Pawns.direction(attackingColor.opponent())
        var squares = target.translateAll(Pair(-1, pawnDirection), Pair(+1, pawnDirection))
        val targetPiece = position.at(target)
        if (targetPiece.isDefined && Piece.Type.PAWN == targetPiece.get().type) {
            squares = squares.addAll(target.translateAll(Pair(-1, 0), Pair(+1, 0))
                .filter { square -> Pawns.isEnPassantAvailable(square, position) })
        }
        return squares.filter(findPiece(Piece.Type.PAWN, attackingColor, position))
    }

    private fun attackingKingSquares(target: Square, attackingColor: Color, position: Position) =
        kingSquares(target).filter(findPiece(Piece.Type.KING, attackingColor, position))

    private fun attackingKnightSquares(target: Square, attackingColor: Color, position: Position) =
        knightSquares(target).filter(findPiece(Piece.Type.KNIGHT, attackingColor, position))

    private fun attackingBishopSquares(target: Square, attackingColor: Color, position: Position) =
        baseAttackingBishopSquares(target, position).filter(findPiece(Piece.Type.BISHOP, attackingColor, position))

    private fun attackingRookSquares(target: Square, attackingColor: Color, position: Position) =
        baseAttackingRookSquares(target, position).filter(findPiece(Piece.Type.ROOK, attackingColor, position))

    private fun attackingQueenSquares(target: Square, attackingColor: Color, position: Position) =
        baseAttackingBishopSquares(target, position)
            .addAll(baseAttackingRookSquares(target, position))
            .filter(findPiece(Piece.Type.QUEEN, attackingColor, position))

    private fun baseAttackingBishopSquares(target: Square, position: Position): Set<Square> {
        val pieceIsFound = { square: Square -> position.at(square).isDefined }
        val ne = target.walk(+1, +1).filter(pieceIsFound).headOption()
        val se = target.walk(+1, -1).filter(pieceIsFound).headOption()
        val sw = target.walk(-1, -1).filter(pieceIsFound).headOption()
        val nw = target.walk(-1, +1).filter(pieceIsFound).headOption()
        return HashSet.of(ne, se, sw, nw).flatMap { it }
    }

    private fun baseAttackingRookSquares(target: Square, position: Position): Set<Square> {
        val pieceIsFound = { square: Square -> position.at(square).isDefined }
        val n = target.walk(0, +1).filter(pieceIsFound).headOption()
        val s = target.walk(0, -1).filter(pieceIsFound).headOption()
        val w = target.walk(+1, 0).filter(pieceIsFound).headOption()
        val e = target.walk(-1, 0).filter(pieceIsFound).headOption()
        return HashSet.of(n, s, w, e).flatMap(Function.identity<Option<Square>>())
    }

    private fun isFreeOrWithOpponent(position: Position, square: Square, piece: Piece) =
        !position.at(square).exists { piece.isFriend(it) }

    private fun kingSquares(from: Square) = from.translateAll(
        Pair(+0, +1),
        Pair(+1, +1),
        Pair(+1, +0),
        Pair(+1, -1),
        Pair(+0, -1),
        Pair(-1, -1),
        Pair(-1, +0),
        Pair(-1, +1))

    private fun knightSquares(from: Square) = from.translateAll(
        Pair(+1, +2),
        Pair(+2, +1),
        Pair(+2, -1),
        Pair(+1, -2),
        Pair(-1, -2),
        Pair(-2, -1),
        Pair(-2, +1),
        Pair(-1, +2))

    private fun bishopSquares(position: Position, from: Square): Set<Square> {
        val piece = position.at(from).get()
        return HashSet.of(from.walk(+1, +1), from.walk(+1, -1), from.walk(-1, -1), from.walk(-1, +1))
            .flatMap { takeWhileFreeOrWithOpponent(position, piece, it) }
    }

    private fun rookSquares(position: Position, from: Square): Set<Square> {
        val piece = position.at(from).get()
        return HashSet.of(from.walk(0, +1), from.walk(+1, 0), from.walk(0, -1), from.walk(-1, 0))
            .flatMap { takeWhileFreeOrWithOpponent(position, piece, it) }
    }

    private fun queenSquares(position: Position, from: Square) =
        bishopSquares(position, from).addAll(rookSquares(position, from))

    private fun pawnMoves(position: Position, from: Square): Set<Move.Regular> {
        val piece = position.at(from).get()
        val direction = Pawns.direction(piece.color)
        val initialRow = if (piece.color === Color.WHITE) 1 else 6
        val push = if (from.row == initialRow) 2 else 1
        val forward = from.walk(0, direction)
            .takeWhile { position.at(it).isEmpty }
            .take(push.toLong())
            .map { to -> Move.Regular(from, to) }
        val captures = from.translateAll(Pair(-1, direction), Pair(+1, direction))
            .filter { to -> position.at(to).exists { piece.isOpponent(it) } }
            .map { to -> Move.Regular(from, to) }
        val enPassantCaptures: Set<Move.Regular> =
            if (Pawns.isEnPassantAvailable(from, position)) {
                from.translateAll(Pair(-1, 0), Pair(+1, 0))
                    .filter { sq -> position.at(sq).exists { it == piece.color.opponent().pawn() } }
                    .map { to -> to.translate(0, direction).get() }
                    .map { to -> Move.Regular(from, to, enPassant = true) }
            } else {
                HashSet.empty<Move.Regular>()
            }
        return forward.appendAll(captures).appendAll(enPassantCaptures).toSet()
    }

    private fun takeWhileFreeOrWithOpponent(position: Position, moving: Piece, walk: Stream<Square>) = walk
        .splitAt { position.at(it).isDefined }
        .map({ it }, { it.headOption().filter({ sq -> moving.isOpponent(position.at(sq).get()) }).toList() })
        .transform { obj, elements -> obj.appendAll(elements) }

    private fun findPiece(type: Piece.Type, color: Color, position: Position) = Predicate { square: Square ->
        position.at(square) == Option.some(Piece(type, color))
    }
}

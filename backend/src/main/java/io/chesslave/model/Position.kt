package io.chesslave.model

import io.vavr.Tuple
import io.vavr.Tuple2
import io.vavr.collection.HashMap
import io.vavr.collection.List
import io.vavr.collection.Map
import io.vavr.collection.Set
import io.vavr.kotlin.component1
import io.vavr.kotlin.component2
import io.vavr.kotlin.getOrNull
import io.vavr.kotlin.hashMap

/**
 * An immutable chess position.
 */
class Position(private val position: Map<Square, Piece>) {

    class Builder {

        private var position: Map<Square, Piece> = HashMap.empty()

        /**
         * Puts the piece at the specified square.
         */
        fun withPiece(square: Square, piece: Piece): Builder {
            if (position.containsKey(square)) throw IllegalArgumentException("cannot place $piece into $square: square already used")
            position = position.put(square, piece)
            return this
        }

        fun build(): Position {
            return Position(position)
        }
    }

    /**
     * @return The piece placed at the given square or null if the square is empty
     */
    fun at(square: Square): Piece? = position.getOrNull(square)

    /**
     * @return The piece placed at the given square
     * @throws IllegalAccessException if the square is empty
     */
    fun get(square: Square): Piece = position.get(square).getOrElseThrow {
        throw IllegalArgumentException("cannot move nothing from $square in $this")
    }

    /**
     * @return A new position having the given piece placed to the given square.
     */
    fun put(square: Square, piece: Piece): Position = Position(position.put(square, piece))

    /**
     * @return A new position gets removing the piece at the given square.
     */
    fun remove(square: Square): Position = Position(position.remove(square))

    /**
     * @return A new position gets moving the piece at the `from` square to the `to` square.
     */
    fun move(from: Square, to: Square): Position {
        val piece = position.apply(from)
        return Position(position.remove(from).put(to, piece))
    }

    /**
     * @return A map having as keys the filled squares and as values the relative pieces.
     */
    fun toMap(): Map<Square, Piece> = position

    /**
     * @return A set with all filled squares and the relative pieces.
     */
    fun toSet(): Set<Tuple2<Square, Piece>> = position.toSet()

    override fun toString(): String = positionToText(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false
        return this.position == other.position
    }

    override fun hashCode(): Int {
        return position.hashCode()
    }
}

private val pieceFromCode = hashMap(
    "P" to Piece.whitePawn,
    "N" to Piece.whiteKnight,
    "B" to Piece.whiteBishop,
    "R" to Piece.whiteRook,
    "Q" to Piece.whiteQueen,
    "K" to Piece.whiteKing,
    "p" to Piece.blackPawn,
    "n" to Piece.blackKnight,
    "b" to Piece.blackBishop,
    "r" to Piece.blackRook,
    "q" to Piece.blackQueen,
    "k" to Piece.blackKing)
private val codeFromPiece = pieceFromCode.toMap { (code, piece) -> Tuple.of(piece, code) }

/**
 * Creates a position from a textual human-readable representation of the board.
 */
fun positionFromText(
    row8: String,
    row7: String,
    row6: String,
    row5: String,
    row4: String,
    row3: String,
    row2: String,
    row1: String): Position {
    val position = List.of(row1, row2, row3, row4, row5, row6, row7, row8)
        .zipWithIndex()
        .flatMap { (rowText, rowIndex) ->
            List.ofAll(rowText.split("\\|".toRegex()))
                .map { colText -> pieceFromCode.get(colText) }
                .zipWithIndex()
                .flatMap { (colPiece, colIndex) ->
                    colPiece.map { piece -> Tuple.of(Square(colIndex.toInt(), rowIndex.toInt()), piece) }
                }
        }
        .toMap { it }
    return Position(position)
}

/**
 * @return A textual human-readable representation of the position.
 */
fun positionToText(position: Position): String =
    (Board.SIZE - 1 downTo 0).map { row ->
        Board.range.map { col ->
            position.at(Square(col, row))?.let { codeFromPiece.apply(it) } ?: " "
        }.joinToString("|")
    }.joinToString("\n")
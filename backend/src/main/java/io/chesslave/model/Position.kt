package io.chesslave.model

import javaslang.Tuple2
import javaslang.collection.HashMap
import javaslang.collection.Map
import javaslang.collection.Set
import javaslang.control.Option

/**
 * An immutable chess position.
 */
class Position(private val position: Map<Square, Piece>) {

    class Builder {

        private var position: Map<Square, Piece> = HashMap.empty<Square, Piece>()

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
     * @return The piece placed at the given square or nothing if the square is empty.
     */
    fun at(square: Square): Option<Piece> = position.get(square)

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

    override fun toString(): String = Positions.toText(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false
        return this.position == other.position
    }

    override fun hashCode(): Int {
        return position.hashCode()
    }
}

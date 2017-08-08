package io.chesslave.model

import io.chesslave.extensions.concat
import io.chesslave.extensions.nullableToSet
import io.vavr.collection.HashSet
import io.vavr.collection.Set
import io.vavr.collection.Stream
import io.vavr.kotlin.toVavrStream

object Board {

    const val SIZE = 8
    val range = 0 until Board.SIZE

    val a1 = Square(0, 0)
    val a2 = Square(0, 1)
    val a3 = Square(0, 2)
    val a4 = Square(0, 3)
    val a5 = Square(0, 4)
    val a6 = Square(0, 5)
    val a7 = Square(0, 6)
    val a8 = Square(0, 7)
    val b1 = Square(1, 0)
    val b2 = Square(1, 1)
    val b3 = Square(1, 2)
    val b4 = Square(1, 3)
    val b5 = Square(1, 4)
    val b6 = Square(1, 5)
    val b7 = Square(1, 6)
    val b8 = Square(1, 7)
    val c1 = Square(2, 0)
    val c2 = Square(2, 1)
    val c3 = Square(2, 2)
    val c4 = Square(2, 3)
    val c5 = Square(2, 4)
    val c6 = Square(2, 5)
    val c7 = Square(2, 6)
    val c8 = Square(2, 7)
    val d1 = Square(3, 0)
    val d2 = Square(3, 1)
    val d3 = Square(3, 2)
    val d4 = Square(3, 3)
    val d5 = Square(3, 4)
    val d6 = Square(3, 5)
    val d7 = Square(3, 6)
    val d8 = Square(3, 7)
    val e1 = Square(4, 0)
    val e2 = Square(4, 1)
    val e3 = Square(4, 2)
    val e4 = Square(4, 3)
    val e5 = Square(4, 4)
    val e6 = Square(4, 5)
    val e7 = Square(4, 6)
    val e8 = Square(4, 7)
    val f1 = Square(5, 0)
    val f2 = Square(5, 1)
    val f3 = Square(5, 2)
    val f4 = Square(5, 3)
    val f5 = Square(5, 4)
    val f6 = Square(5, 5)
    val f7 = Square(5, 6)
    val f8 = Square(5, 7)
    val g1 = Square(6, 0)
    val g2 = Square(6, 1)
    val g3 = Square(6, 2)
    val g4 = Square(6, 3)
    val g5 = Square(6, 4)
    val g6 = Square(6, 5)
    val g7 = Square(6, 6)
    val g8 = Square(6, 7)
    val h1 = Square(7, 0)
    val h2 = Square(7, 1)
    val h3 = Square(7, 2)
    val h4 = Square(7, 3)
    val h5 = Square(7, 4)
    val h6 = Square(7, 5)
    val h7 = Square(7, 6)
    val h8 = Square(7, 7)
}

/**
 * A sequence of board squares traversed by a piece.
 */
typealias BoardPath = Stream<Square>

/**
 * A square of a board.
 *
 * @param col A zero-based column index (0=a, ..., 7=h)
 * @param row A zero-based row index (0=1, ..., 7=8)
 */
data class Square(val col: Int, val row: Int) {

    init {
        if (col !in Board.range) throw IllegalArgumentException("illegal column $col")
        if (row !in Board.range) throw IllegalArgumentException("illegal row $row")
    }

    /**
     * The common name of the square column (a-h)
     */
    val columnName: String = ('a' + col).toString()

    /**
     * The common name of the square row (1-8)
     */
    val rowName: String = ('1' + row).toString()

    /**
     * The common name of the square.
     */
    val name: String = columnName concat rowName

    /**
     * @return The square related to this applying the given col/row offsets, or nothing if the resulting square is out of the board.
     */
    fun translate(col: Int, row: Int): Square? {
        val newCol = this.col + col
        val newRow = this.row + row
        return if (newCol in Board.range && newRow in Board.range) Square(newCol, newRow)
        else null
    }

    /**
     * @return All valid squares gotten applying the given translations.
     */
    fun translateAll(vararg translations: Pair<Int, Int>): Set<Square> =
        HashSet.ofAll(translations.flatMap { (col, row) -> translate(col, row).nullableToSet() })

    /**
     * @return A stream of all valid squares crossed from this square (excluded) applying repeatedly the translation.
     */
    fun walk(col: Int, row: Int): BoardPath =
        generateSequence(translate(col, row)) { it.translate(col, row) }.toVavrStream()

    override fun toString(): String = name

    companion object {

        /**
         * @return All the available squares on the board.
         */
        fun all(): Set<Square>
            = Board.range.flatMap { col -> Board.range.map { row -> Square(col, row) } }
            .let { HashSet.ofAll(it) }
    }
}

/**
 * @return The square for the specified human-readable coordinates (i.e. "a1", "c4", etc.)
 */
fun Square(coordinates: String): Square {
    if (coordinates.length != 2) throw IllegalArgumentException("bad coordinate $coordinates")
    val coo = coordinates.toLowerCase()
    return Square(coo[0] - 'a', coo[1] - '1')
}
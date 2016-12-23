package io.chesslave.model

import javaslang.collection.HashSet
import javaslang.collection.Set
import javaslang.collection.Stream
import javaslang.control.Option

/**
 * A square of a board.
 *
 * @param col A zero-based column index (0=a, ..., 7=h)
 * @param row A zero-based row index (0=1, ..., 7=8)
 */
class Square(val col: Int, val row: Int) {

    init {
        if (col < 0 || col >= Board.SIZE) throw IllegalArgumentException("illegal column $col")
        if (row < 0 || row >= Board.SIZE) throw IllegalArgumentException("illegal row $row")
    }

    /**
     * @return The common name of the square.
     */
    fun name(): String = StringBuilder()
        .append('a' + col)
        .append('1' + row)
        .toString()

    /**
     * @return The common name of the square column (a-h)
     */
    fun columnName(): String = ('a' + col).toString()

    /**
     * @return The common name of the square row (1-8)
     */
    fun rowName(): String = ('1' + row).toString()

    /**
     * @return The square related to this applying the given col/row offsets, or nothing if the resulting square is out of the board.
     */
    fun translate(col: Int, row: Int): Option<Square> {
        val newCol = this.col + col
        val newRow = this.row + row
        return if (0 <= newCol && newCol < Board.SIZE && 0 <= newRow && newRow < Board.SIZE)
            Option.some(Square(newCol, newRow))
        else
            Option.none()
    }

    fun translate2(col: Int, row: Int): Square? {
        val newCol = this.col + col
        val newRow = this.row + row
        return if (0 <= newCol && newCol < Board.SIZE && 0 <= newRow && newRow < Board.SIZE) Square(newCol, newRow)
        else null
    }

    /**
     * @return All valid squares gotten applying the given translations.
     */
    fun translateAll(vararg translations: Pair<Int, Int>): Set<Square> =
        HashSet.ofAll(translations.flatMap({ translate(it.first, it.second) }).toSet())

    /**
     * @return A stream of all valid squares crossed from this square (excluded) applying repeatedly the translation.
     */
    fun walk(col: Int, row: Int): Stream<Square> =
        Stream.iterate(translate2(col, row)) { it?.translate2(col, row) }
            .takeWhile { it != null }
            .map { it!! }

    override fun toString(): String = name()

    override fun hashCode(): Int = Board.SIZE * row + col

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Square) return false
        return this.col == other.col && this.row == other.row
    }

    companion object {

        /**
         * @return All the available squares on the board.
         */
        fun all(): Set<Square> {
            val range = 0 until Board.SIZE
            return HashSet.ofAll(range.flatMap { col -> range.map { row -> Square(col, row) } })
        }
    }
}

/**
 * @return The square for the specified human-readable coordinates (i.e. "a1", "c4", ect.)
 */
fun Square(coordinates: String): Square {
    if (coordinates.length != 2) throw IllegalArgumentException("bad coordinate $coordinates")
    val coo = coordinates.toLowerCase()
    return Square(coo[0] - 'a', coo[1] - '1')
}
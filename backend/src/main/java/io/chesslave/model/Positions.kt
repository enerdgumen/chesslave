package io.chesslave.model

import io.chesslave.model.Piece.Type
import javaslang.Tuple
import javaslang.collection.HashMap
import javaslang.collection.List

object Positions {

    private val PIECE_FROM_CODE = HashMap.of<String, Piece>(
        "P", Piece(Type.PAWN, Color.WHITE),
        "N", Piece(Type.KNIGHT, Color.WHITE),
        "B", Piece(Type.BISHOP, Color.WHITE),
        "R", Piece(Type.ROOK, Color.WHITE),
        "Q", Piece(Type.QUEEN, Color.WHITE),
        "K", Piece(Type.KING, Color.WHITE),
        "p", Piece(Type.PAWN, Color.BLACK),
        "n", Piece(Type.KNIGHT, Color.BLACK),
        "b", Piece(Type.BISHOP, Color.BLACK),
        "r", Piece(Type.ROOK, Color.BLACK),
        "q", Piece(Type.QUEEN, Color.BLACK),
        "k", Piece(Type.KING, Color.BLACK))

    private val CODE_FROM_PIECE = HashMap.of<Piece, String>(
        Piece(Type.PAWN, Color.WHITE), "P",
        Piece(Type.KNIGHT, Color.WHITE), "N",
        Piece(Type.BISHOP, Color.WHITE), "B",
        Piece(Type.ROOK, Color.WHITE), "R",
        Piece(Type.QUEEN, Color.WHITE), "Q",
        Piece(Type.KING, Color.WHITE), "K",
        Piece(Type.PAWN, Color.BLACK), "p",
        Piece(Type.KNIGHT, Color.BLACK), "n",
        Piece(Type.BISHOP, Color.BLACK), "b",
        Piece(Type.ROOK, Color.BLACK), "r",
        Piece(Type.QUEEN, Color.BLACK), "q",
        Piece(Type.KING, Color.BLACK), "k")

    /**
     * Creates a position from a textual human-readable representation of the board.
     */
    fun fromText(
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
            .flatMap { row ->
                List.ofAll(row._1.split("\\|".toRegex()))
                    .map { PIECE_FROM_CODE.get(it) }
                    .zipWithIndex()
                    .flatMap { col -> col._1.map { piece -> Tuple.of(Square(col._2.toInt(), row._2.toInt()), piece) } }
            }
            .toMap { it }
        return Position(position)
    }

    /**
     * @return A textual human-readable representation of the position.
     */
    fun toText(position: Position): String =
        (Board.SIZE - 1 downTo 0).map { row ->
            (0 until Board.SIZE).map { col ->
                position.at(Square(col, row))
                    .map { CODE_FROM_PIECE.apply(it) }
                    .getOrElse(" ")
            }.joinToString("|")
        }.joinToString("\n")
}

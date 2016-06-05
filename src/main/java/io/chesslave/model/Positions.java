package io.chesslave.model;

import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.control.Option;
import java.util.function.Function;

public class Positions {
    private static final Map<String, Piece> PIECE_FROM_CODE = HashMap.of(
            "P", Piece.of(Piece.Type.PAWN, Color.WHITE),
            "N", Piece.of(Piece.Type.KNIGHT, Color.WHITE),
            "B", Piece.of(Piece.Type.BISHOP, Color.WHITE),
            "R", Piece.of(Piece.Type.ROOK, Color.WHITE),
            "Q", Piece.of(Piece.Type.QUEEN, Color.WHITE),
            "K", Piece.of(Piece.Type.KING, Color.WHITE),
            "p", Piece.of(Piece.Type.PAWN, Color.BLACK),
            "n", Piece.of(Piece.Type.KNIGHT, Color.BLACK),
            "b", Piece.of(Piece.Type.BISHOP, Color.BLACK),
            "r", Piece.of(Piece.Type.ROOK, Color.BLACK),
            "q", Piece.of(Piece.Type.QUEEN, Color.BLACK),
            "k", Piece.of(Piece.Type.KING, Color.BLACK));

    private static final Map<Piece, String> CODE_FROM_PIECE = HashMap.of(
            Piece.of(Piece.Type.PAWN, Color.WHITE), "P",
            Piece.of(Piece.Type.KNIGHT, Color.WHITE), "N",
            Piece.of(Piece.Type.BISHOP, Color.WHITE), "B",
            Piece.of(Piece.Type.ROOK, Color.WHITE), "R",
            Piece.of(Piece.Type.QUEEN, Color.WHITE), "Q",
            Piece.of(Piece.Type.KING, Color.WHITE), "K",
            Piece.of(Piece.Type.PAWN, Color.BLACK), "p",
            Piece.of(Piece.Type.KNIGHT, Color.BLACK), "n",
            Piece.of(Piece.Type.BISHOP, Color.BLACK), "b",
            Piece.of(Piece.Type.ROOK, Color.BLACK), "r",
            Piece.of(Piece.Type.QUEEN, Color.BLACK), "q",
            Piece.of(Piece.Type.KING, Color.BLACK), "k");

    /**
     * Creates a position from a textual human-readable representation of the board.
     */
    public static Position fromText(
            String row8,
            String row7,
            String row6,
            String row5,
            String row4,
            String row3,
            String row2,
            String row1) {
        final Map<Square, Piece> position = List.of(row1, row2, row3, row4, row5, row6, row7, row8)
                .zipWithIndex()
                .flatMap(row -> List.of(row._1.split("\\|"))
                        .map(PIECE_FROM_CODE::get)
                        .zipWithIndex()
                        .flatMap(col -> col._1.map(piece ->
                                Tuple.of(new Square(col._2.intValue(), row._2.intValue()), piece))))
                .toMap(Function.identity());
        return new Position(position);
    }

    /**
     * @return A textual human-readable representation of the position.
     */
    public static String toText(Position position) {
        return List.rangeClosedBy(Board.SIZE - 1, 0, -1)
                .map(row -> List.range(0, Board.SIZE)
                        .map(col -> position.at(new Square(col, row))
                                .map(CODE_FROM_PIECE::apply)
                                .getOrElse(" "))
                        .mkString("|"))
                .mkString("\n");
    }
}

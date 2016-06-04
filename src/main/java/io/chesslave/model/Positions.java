package io.chesslave.model;

import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.Map;
import java.util.function.Function;

public class Positions {

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
        final Map<String, Piece> pieceFromCode = HashMap.ofAll(
                Tuple.of("P", Piece.of(Piece.Type.PAWN, Color.WHITE)),
                Tuple.of("N", Piece.of(Piece.Type.KNIGHT, Color.WHITE)),
                Tuple.of("B", Piece.of(Piece.Type.BISHOP, Color.WHITE)),
                Tuple.of("R", Piece.of(Piece.Type.ROOK, Color.WHITE)),
                Tuple.of("Q", Piece.of(Piece.Type.QUEEN, Color.WHITE)),
                Tuple.of("K", Piece.of(Piece.Type.KING, Color.WHITE)),
                Tuple.of("p", Piece.of(Piece.Type.PAWN, Color.BLACK)),
                Tuple.of("n", Piece.of(Piece.Type.KNIGHT, Color.BLACK)),
                Tuple.of("b", Piece.of(Piece.Type.BISHOP, Color.BLACK)),
                Tuple.of("r", Piece.of(Piece.Type.ROOK, Color.BLACK)),
                Tuple.of("q", Piece.of(Piece.Type.QUEEN, Color.BLACK)),
                Tuple.of("k", Piece.of(Piece.Type.KING, Color.BLACK)));
        final Map<Square, Piece> position = List.of(row1, row2, row3, row4, row5, row6, row7, row8)
                .zipWithIndex()
                .flatMap(row -> List.of(row._1.split("\\|"))
                        .map(pieceFromCode::get)
                        .zipWithIndex()
                        .flatMap(col -> col._1.map(piece -> Tuple.of(new Square(col._2, row._2), piece))))
                .toMap(Function.identity());
        return new Position(position);
    }

    /**
     * @return A textual human-readable representation of the position.
     */
    public static String toText(Position position) {
        final Map<Piece, String> codeFromPiece = HashMap.ofAll(
                Tuple.of(Piece.of(Piece.Type.PAWN, Color.WHITE), "P"),
                Tuple.of(Piece.of(Piece.Type.KNIGHT, Color.WHITE), "N"),
                Tuple.of(Piece.of(Piece.Type.BISHOP, Color.WHITE), "B"),
                Tuple.of(Piece.of(Piece.Type.ROOK, Color.WHITE), "R"),
                Tuple.of(Piece.of(Piece.Type.QUEEN, Color.WHITE), "Q"),
                Tuple.of(Piece.of(Piece.Type.KING, Color.WHITE), "K"),
                Tuple.of(Piece.of(Piece.Type.PAWN, Color.BLACK), "p"),
                Tuple.of(Piece.of(Piece.Type.KNIGHT, Color.BLACK), "n"),
                Tuple.of(Piece.of(Piece.Type.BISHOP, Color.BLACK), "b"),
                Tuple.of(Piece.of(Piece.Type.ROOK, Color.BLACK), "r"),
                Tuple.of(Piece.of(Piece.Type.QUEEN, Color.BLACK), "q"),
                Tuple.of(Piece.of(Piece.Type.KING, Color.BLACK), "k"));
        return List.rangeClosedBy(Board.SIZE - 1, 0, -1)
                .map(row -> List.range(0, Board.SIZE)
                        .map(col -> position.at(new Square(col, row))
                                .map(codeFromPiece::apply)
                                .orElse(" "))
                        .mkString("|"))
                .mkString("\n");
    }
}

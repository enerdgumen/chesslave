package io.chesslave.visual.rendering;

import io.chesslave.visual.Images;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.visual.model.BoardImage;
import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.Map;

import java.awt.image.BufferedImage;

public class ChessSet {
    private static final HashMap<Color, String> COLOR_TO_CODE = HashMap.<Color, String>of(
            Color.WHITE, "w",
            Color.BLACK, "b");
    private static final HashMap<Piece.Type, String> PIECE_TYPE_TO_CODE = HashMap.of(
            Piece.Type.BISHOP, "b",
            Piece.Type.KING, "k",
            Piece.Type.KNIGHT, "n",
            Piece.Type.PAWN, "p",
            Piece.Type.QUEEN, "q",
            Piece.Type.ROOK, "r");

    public final BoardImage board;
    public final Map<Piece, BufferedImage> pieces;

    private ChessSet(BoardImage board, Map<Piece, BufferedImage> pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    public static ChessSet read(String path) {
        final BoardImage board = new BoardImage(Images.read(path + "empty-board.png"));
        final Map<Piece, BufferedImage> pieces = Piece.all()
                .toMap(p -> Tuple.of(p, Images.read(path + ChessSet.name(p) + ".png")));
        return new ChessSet(board, pieces);
    }

    private static String name(Piece piece) {
        return COLOR_TO_CODE.apply(piece.color).concat(PIECE_TYPE_TO_CODE.apply(piece.type));
    }
}

package io.chesslave.visual.rendering;

import io.chesslave.model.Piece;
import io.chesslave.visual.Images;
import javaslang.Tuple;
import javaslang.collection.Map;
import java.awt.image.BufferedImage;

public class ChessSet {
    public final BufferedImage board;
    public final Map<Piece, BufferedImage> pieces;

    public ChessSet(BufferedImage board, Map<Piece, BufferedImage> pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    public static ChessSet read(String path) {
        final BufferedImage board = Images.read(path + "empty-board.png");
        final Map<Piece, BufferedImage> pieces = Piece.all()
                .toMap(p -> Tuple.of(p, Images.read(path + ChessSet.name(p) + ".png")));
        return new ChessSet(board, pieces);
    }

    private static String name(Piece piece) {
        return piece.color.code().concat(piece.type.code());
    }
}

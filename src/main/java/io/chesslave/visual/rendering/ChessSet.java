package io.chesslave.visual.rendering;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.visual.Images;
import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import java.awt.image.BufferedImage;
import java.nio.file.Path;

public class ChessSet {

    public final BufferedImage board;
    public final Map<Piece, BufferedImage> pieces;

    public ChessSet(BufferedImage board, Map<Piece, BufferedImage> pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    public static ChessSet read(Path path) {
        final BufferedImage board = Images.read(path.resolve("empty-board.png").toString());
        final Map<Piece, BufferedImage> pieces = Piece.all()
                .toMap(p -> Tuple.of(p, Images.read(path.resolve(name(p).concat(".png")).toString())));
        return new ChessSet(board, pieces);
    }

    private static String name(Piece piece) {
        final HashMap<Color, String> colors = HashMap.<Color, String>of(
                Color.WHITE, "w",
                Color.BLACK, "b");
        final HashMap<Piece.Type, String> types = HashMap.of(
                Piece.Type.BISHOP, "b",
                Piece.Type.KING, "k",
                Piece.Type.KNIGHT, "n",
                Piece.Type.PAWN, "p",
                Piece.Type.QUEEN, "q",
                Piece.Type.ROOK, "r");
        return colors.apply(piece.color).concat(types.apply(piece.type));
    }
}

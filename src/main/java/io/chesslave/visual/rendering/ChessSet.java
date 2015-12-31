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
        final HashMap<Color, String> colors = HashMap.ofAll(
                Tuple.of(Color.WHITE, "w"),
                Tuple.of(Color.BLACK, "b"));
        final HashMap<Piece.Type, String> types = HashMap.ofAll(
                Tuple.of(Piece.Type.BISHOP, "b"),
                Tuple.of(Piece.Type.KING, "k"),
                Tuple.of(Piece.Type.KNIGHT, "n"),
                Tuple.of(Piece.Type.PAWN, "p"),
                Tuple.of(Piece.Type.QUEEN, "q"),
                Tuple.of(Piece.Type.ROOK, "r"));
        return colors.apply(piece.color).concat(types.apply(piece.type));
    }
}

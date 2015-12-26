package io.chesslave.recognition;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import javaslang.Tuple;
import javaslang.collection.HashMap;
import javaslang.collection.List;
import javaslang.collection.Map;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Target;
import java.util.function.Function;

public class PieceTargetFactory implements Function<Piece, Target> {

    private static class Props {

        public final String code;
        public final int limit;

        public Props(String code, int limit) {
            this.code = code;
            this.limit = limit;
        }
    }

    private final Map<Color, String> sideCodes = HashMap.ofAll(
            Tuple.of(Color.BLACK, "b"),
            Tuple.of(Color.WHITE, "w"));
    private final Map<Type, Props> pieceProps = HashMap.ofAll(
            Tuple.of(Type.BISHOP, new Props("b", 2)),
            Tuple.of(Type.KING, new Props("k", 1)),
            Tuple.of(Type.KNIGHT, new Props("n", 2)),
            Tuple.of(Type.PAWN, new Props("p", 8)),
            Tuple.of(Type.QUEEN, new Props("q", 1)),
            Tuple.of(Type.ROOK, new Props("r", 2)));

    @Override
    public ImageTarget apply(Piece piece) {
        final Props conf = pieceProps.apply(piece.type);
        final String path = List.of("sample/", sideCodes.get(piece.color), conf.code, ".png").mkString();
        final ImageTarget target = new ImageTarget(getClass().getResource(path));
        target.setMinScore(.55);
        target.setLimit(conf.limit);
        return target;
    }
}

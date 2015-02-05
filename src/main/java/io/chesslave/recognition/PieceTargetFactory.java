package io.chesslave.recognition;

import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Piece.Type;
import io.chesslave.sugar.Strings;
import io.chesslave.sugar.Maps;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Target;
import java.util.Map;
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

    private final Map<Color, String> sideCodes = Maps.<Color, String>build()
            .add(Color.BLACK, "b")
            .add(Color.WHITE, "w")
            .get();
    private final Map<Type, Props> pieceProps = Maps.<Type, Props>build()
            .add(Type.BISHOP, new Props("b", 2))
            .add(Type.KING, new Props("k", 1))
            .add(Type.KNIGHT, new Props("n", 2))
            .add(Type.PAWN, new Props("p", 8))
            .add(Type.QUEEN, new Props("q", 1))
            .add(Type.ROOK, new Props("r", 2))
            .get();

    @Override
    public ImageTarget apply(Piece piece) {
        final Props conf = pieceProps.get(piece.type);
        final String path = Strings.concat("sample/", sideCodes.get(piece.color), conf.code, ".png");
        final ImageTarget target = new ImageTarget(getClass().getResource(path));
        target.setMinScore(.55);
        target.setLimit(conf.limit);
        return target;
    }
}

package io.chesslave.visual.recognition;

import io.chesslave.model.Piece;
import org.sikuli.script.Image;
import java.util.function.Function;

public class PieceTargetFactory implements Function<Piece, Image> {

    private final BoardConfiguration conf;

    public PieceTargetFactory(BoardConfiguration conf) {
        this.conf = conf;
    }

    @Override
    public Image apply(Piece piece) {
        return new Image(conf.pieces.apply(piece));
    }
}

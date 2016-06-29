package io.chesslave.eyes;

import io.chesslave.model.Piece;
import io.chesslave.visual.SquareImage;
import javaslang.collection.List;
import javaslang.control.Option;
import java.awt.image.BufferedImage;
import java.util.function.Function;

public class PieceRecogniser {

    private final Vision vision;
    private final BoardConfiguration config;

    public PieceRecogniser(Vision vision, BoardConfiguration config) {
        this.vision = vision;
        this.config = config;
    }

    public Option<Piece> recognise(SquareImage square, List<Piece> expectedPieces) {
        final Vision.Recogniser recogniser = vision.recognise(square.image());
        return expectedPieces.iterator()
                .map(piece -> {
                    final BufferedImage image = config.pieces.apply(piece);
                    return recogniser.match(image).<Piece>map(it -> piece);
                })
                .filter(Option::isDefined)
                .flatMap(Function.identity())
                .headOption();
    }
}

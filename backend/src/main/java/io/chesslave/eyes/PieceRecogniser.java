package io.chesslave.eyes;

import io.chesslave.model.Piece;
import io.chesslave.visual.model.SquareImage;
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

    /**
     * Detects the piece placed in the square.
     *
     * @param square         the image of the square
     * @param expectedPieces the list of the pieces to recognise
     * @return the detected piece or nothing if none piece was be recognised
     */
    public Option<Piece> piece(SquareImage square, List<Piece> expectedPieces) {
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

package io.chesslave.visual.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import javaslang.collection.Set;
import org.sikuli.script.Image;
import org.sikuli.script.Region;
import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PositionRecogniser implements Function<Region, Position> {

    private final Function<Piece, Image> targetFactory;
    private final BiFunction<Region, Image, Set<Board.Square>> pieceRecogniser;

    public PositionRecogniser(Function<Piece, Image> targetFactory,
                              BiFunction<Region, Image, Set<Board.Square>> pieceRecogniser) {
        this.targetFactory = targetFactory;
        this.pieceRecogniser = pieceRecogniser;
    }

    @Override
    public Position apply(Region region) {
        final Position.Builder position = new Position.Builder();
        for (Piece.Type type : EnumSet.allOf(Piece.Type.class)) {
            for (Color color : EnumSet.allOf(Color.class)) {
                final Piece piece = new Piece(type, color);
                final Image target = targetFactory.apply(piece);
                final Set<Board.Square> squares = pieceRecogniser.apply(region, target);
                for (Board.Square square : squares) {
                    position.withPiece(square, piece);
                }
            }
        }
        return position.build();
    }
}

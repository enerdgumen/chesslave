package io.chesslave.recognition;

import io.chesslave.model.Board;
import io.chesslave.model.Color;
import io.chesslave.model.Piece;
import io.chesslave.model.Position;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

public class PositionRecogniser implements Function<ScreenRegion, Position> {

    private final Function<Piece, Target> targetFactory = new PieceTargetFactory();
    private final BiFunction<ScreenRegion, Target, Set<Board.Square>> pieceRecogniser;

    @Override
    public Position apply(ScreenRegion region) {
        final Position.Builder position = new Position.Builder();
        for (Piece.Type type : EnumSet.allOf(Piece.Type.class)) {
            for (Color color : EnumSet.allOf(Color.class)) {
                final Piece piece = new Piece(type, color);
                final Target target = targetFactory.apply(piece);
                final Set<Board.Square> squares = pieceRecogniser.apply(region, target);
                for (Board.Square square : squares) {
                    position.withPiece(square, piece);
                }
            }
        }
        return position.build();
    }

    public PositionRecogniser(BiFunction<ScreenRegion, Target, Set<Board.Square>> pieceRecogniser) {
        this.pieceRecogniser = pieceRecogniser;
    }
}

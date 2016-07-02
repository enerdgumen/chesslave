package io.chesslave.eyes;

import io.chesslave.model.*;
import io.chesslave.support.Ensure;
import io.chesslave.visual.BoardImage;
import io.chesslave.visual.SquareImage;
import javaslang.Tuple;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.control.Option;

public class MoveRecogniserByImageDiff {

    private final Set<Square> whiteShortCastlingSquares = HashSet.of(
            Square.of("e1"), Square.of("f1"), Square.of("g1"), Square.of("h1"));
    private final Set<Square> whiteLongCastlingSquares = HashSet.of(
            Square.of("e1"), Square.of("d1"), Square.of("c1"), Square.of("a1"));
    private final Set<Square> blackShortCastlingSquares = HashSet.of(
            Square.of("e8"), Square.of("f8"), Square.of("g8"), Square.of("h8"));
    private final Set<Square> blackLongCastlingSquares = HashSet.of(
            Square.of("e8"), Square.of("d8"), Square.of("c8"), Square.of("a8"));
    private final PieceRecogniser pieceRecogniser;

    public MoveRecogniserByImageDiff(PieceRecogniser pieceRecogniser) {
        this.pieceRecogniser = pieceRecogniser;
    }

    /**
     * Detects the move by analyzing the differences between the two images.
     *
     * @return the detected move or nothing if none move was done
     * @throws RuntimeException if the detection fails
     */
    public Option<Move> detect(Position previousPosition, BoardImage previousImage, BoardImage currentImage) {
        final Set<SquareImage> changes = changedSquares(previousImage, currentImage);
        final Set<Square> squares = changes.map(it -> it.square());
        if (squares.equals(whiteShortCastlingSquares)) {
            return Option.of(Movement.shortCastling(Color.WHITE));
        }
        if (squares.equals(whiteLongCastlingSquares)) {
            return Option.of(Movement.longCastling(Color.WHITE));
        }
        if (squares.equals(blackShortCastlingSquares)) {
            return Option.of(Movement.shortCastling(Color.BLACK));
        }
        if (squares.equals(blackLongCastlingSquares)) {
            return Option.of(Movement.longCastling(Color.BLACK));
        }
        final EmptySquareRecogniser emptySquareRecogniser = new EmptySquareRecogniser();
        if (changes.size() == 2) {
            final SquareImage from = changes.find(emptySquareRecogniser::isEmpty).get();
            final SquareImage to = changes.remove(from).get();
            final Piece piece = previousPosition.at(from.square()).get();
            if (piece.type == Piece.Type.PAWN && Pawns.inPromotion(piece.color, to.square())) {
                final Piece promotedPiece = pieceRecogniser.piece(to,
                        List.of(piece.color.queen(), piece.color.rook(),
                                piece.color.knight(), piece.color.bishop()))
                        .getOrElseThrow(() -> new IllegalArgumentException(String.format("Cannot recognise the piece promoted in %s", to.square())));
                return Option.of(Movement.promotion(from.square(), to.square(), promotedPiece.type));
            }
            return Option.of(Movement.regular(from.square(), to.square()));
        }
        if (changes.size() == 3) {
            // en passant
            final Set<SquareImage> fromAndCaptured = changes.filter(emptySquareRecogniser::isEmpty);
            Ensure.isTrue(fromAndCaptured.size() == 2,
                    "Expected only two empty squares, found %s", fromAndCaptured.size());
            final SquareImage to = changes.diff(fromAndCaptured).get();
            final SquareImage from = fromAndCaptured.find(it -> it.square().col != to.square().col).get();
            final Piece movedPiece = previousPosition.at(from.square()).get();
            Ensure.isTrue(movedPiece.type == Piece.Type.PAWN,
                    "Expected pawn at %s, found %s", to.square(), movedPiece);
            final SquareImage captured = fromAndCaptured.find(it -> it.square().col == to.square().col).get();
            final Piece capturedPiece = previousPosition.at(captured.square()).get();
            Ensure.isTrue(capturedPiece.equals(movedPiece.color.opponent().pawn()),
                    "Expected en-passant of %s from %s to %s, found %s in %s",
                    movedPiece, from.square(), to.square(), capturedPiece, captured.square());
            return Option.of(Movement.enPassant(from.square(), to.square()));
        }
        // TODO: ensure that position is not changed
        return Option.none();
    }

    private Set<SquareImage> changedSquares(BoardImage previousImage, BoardImage currentImage) {
        return Square.all()
                .map(it -> Tuple.of(previousImage.squareImage(it), currentImage.squareImage(it)))
                .filter(it -> Images.areDifferent(it._1.image(), it._2.image()))
                .map(it -> it._2);
    }
}

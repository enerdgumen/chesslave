package io.chesslave.eyes

import io.chesslave.model.*
import io.chesslave.visual.Images
import io.chesslave.visual.model.BoardImage
import io.chesslave.visual.model.SquareImage
import javaslang.Tuple
import javaslang.collection.HashSet
import javaslang.collection.List
import javaslang.collection.Set
import javaslang.control.Option

class MoveRecogniserByImageDiff(private val pieceRecogniser: PieceRecogniser) {

    private val whiteShortCastlingSquares = HashSet.of(
        Square.of("e1"), Square.of("f1"), Square.of("g1"), Square.of("h1"))
    private val whiteLongCastlingSquares = HashSet.of(
        Square.of("e1"), Square.of("d1"), Square.of("c1"), Square.of("a1"))
    private val blackShortCastlingSquares = HashSet.of(
        Square.of("e8"), Square.of("f8"), Square.of("g8"), Square.of("h8"))
    private val blackLongCastlingSquares = HashSet.of(
        Square.of("e8"), Square.of("d8"), Square.of("c8"), Square.of("a8"))

    /**
     * Detects the move by analyzing the differences between the two images.

     * @return the detected move or nothing if none move was done
     * *
     * @throws RuntimeException if the detection fails
     */
    fun detect(previousPosition: Position, previousImage: BoardImage, currentImage: BoardImage): Option<Move> {
        val changes = changedSquares(previousImage, currentImage)
        val squares = changes.map { it.square() }
        if (squares == whiteShortCastlingSquares) {
            return Option.of(Movements.ShortCastling(Color.WHITE))
        }
        if (squares == whiteLongCastlingSquares) {
            return Option.of(Movements.LongCastling(Color.WHITE))
        }
        if (squares == blackShortCastlingSquares) {
            return Option.of(Movements.ShortCastling(Color.BLACK))
        }
        if (squares == blackLongCastlingSquares) {
            return Option.of(Movements.LongCastling(Color.BLACK))
        }
        if (changes.size() == 2) {
            val from = changes.find(EmptySquareRecogniser::isEmpty).get()
            val to = changes.remove(from).get()
            val piece = previousPosition.at(from.square()).get()
            if (piece.type === Piece.Type.PAWN && Pawns.inPromotion(piece.color, to.square())) {
                val promotedPiece: Piece = pieceRecogniser.piece(to,
                    List.of(piece.color.queen(), piece.color.rook(), piece.color.knight(), piece.color.bishop()))
                    .getOrElseThrow { IllegalArgumentException("Cannot recognise the piece promoted in ${to.square()}") }
                return Option.of(Movements.Regular(from.square(), to.square(), promotion = Option.some(promotedPiece.type)))
            }
            return Option.of(Movements.Regular(from.square(), to.square()))
        }
        if (changes.size() == 3) {
            // en passant
            val fromAndCaptured = changes.filter(EmptySquareRecogniser::isEmpty)
            assert(fromAndCaptured.size() == 2) { "Expected only two empty squares, found ${fromAndCaptured.size()}" }
            val to = changes.diff(fromAndCaptured).get()
            val from = fromAndCaptured.find { it.square().col != to.square().col }.get()
            val movedPiece = previousPosition.at(from.square()).get()
            assert(movedPiece.type === Piece.Type.PAWN) { "Expected pawn at ${to.square()}, found ${movedPiece}" }
            val captured = fromAndCaptured.find { it.square().col == to.square().col }.get()
            val capturedPiece = previousPosition.at(captured.square()).get()
            assert(capturedPiece == movedPiece.color.opponent().pawn()) { "Expected en-passant of $movedPiece from ${from.square()} to ${to.square()}, found $capturedPiece in ${captured.square()}" }
            return Option.of(Movements.Regular(from.square(), to.square(), enPassant = true))
        }
        // TODO: ensure that position is not changed
        return Option.none()
    }

    private fun changedSquares(previousImage: BoardImage, currentImage: BoardImage): Set<SquareImage> =
        Square.all()
            .map { Tuple.of(previousImage.squareImage(it), currentImage.squareImage(it)) }
            .filter { Images.areDifferent(it._1.image(), it._2.image()) }
            .map({ it._2 })
}

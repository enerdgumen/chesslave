package io.chesslave.eyes

import io.chesslave.model.*
import io.chesslave.model.Move.Regular.Variation.EnPassant
import io.chesslave.model.Move.Regular.Variation.Promotion
import io.chesslave.visual.Images
import io.chesslave.visual.model.BoardImage
import io.chesslave.visual.model.SquareImage
import javaslang.collection.HashSet
import javaslang.collection.List
import javaslang.collection.Set
import javaslang.control.Option

class MoveRecogniserByImageDiff(private val pieceRecogniser: PieceRecogniser) {

    private val whiteShortCastlingSquares = HashSet.of(
        Board.e1, Board.f1, Board.g1, Board.h1)
    private val whiteLongCastlingSquares = HashSet.of(
        Board.e1, Board.d1, Board.c1, Board.a1)
    private val blackShortCastlingSquares = HashSet.of(
        Board.e8, Board.f8, Board.g8, Board.h8)
    private val blackLongCastlingSquares = HashSet.of(
        Board.e8, Board.d8, Board.c8, Board.a8)

    /**
     * Detects the move by analyzing the differences between the two images.

     * @return the detected move or nothing if none move was done
     * *
     * @throws RuntimeException if the detection fails
     */
    fun detect(previousPosition: Position, previousImage: BoardImage, currentImage: BoardImage): Option<Move> {
        val changes = changedSquares(previousImage, currentImage)
        val squares = changes.map { it.square }
        if (squares == whiteShortCastlingSquares) {
            return Option.of(Move.ShortCastling(Color.WHITE))
        }
        if (squares == whiteLongCastlingSquares) {
            return Option.of(Move.LongCastling(Color.WHITE))
        }
        if (squares == blackShortCastlingSquares) {
            return Option.of(Move.ShortCastling(Color.BLACK))
        }
        if (squares == blackLongCastlingSquares) {
            return Option.of(Move.LongCastling(Color.BLACK))
        }
        if (changes.size() == 2) {
            val from = changes.find(EmptySquareRecogniser::isEmpty).get()
            val to = changes.remove(from).get()
            val piece = previousPosition.get(from.square)
            if (piece.type === Piece.Type.PAWN && Pawns.inPromotion(piece.color, to.square)) {
                val promotedPiece: Piece = pieceRecogniser.piece(to,
                    List.of(Piece.queenOf(piece.color), Piece.rookOf(piece.color), Piece.knightOf(piece.color), Piece.bishopOf(piece.color)))
                    .getOrElseThrow { IllegalArgumentException("Cannot recognise the piece promoted in ${to.square}") }
                return Option.of(Move.Regular(from.square, to.square, Promotion(promotedPiece.type)))
            }
            return Option.of(Move.Regular(from.square, to.square))
        }
        if (changes.size() == 3) {
            // en passant
            val fromAndCaptured = changes.filter(EmptySquareRecogniser::isEmpty)
            assert(fromAndCaptured.size() == 2) { "Expected only two empty squares, found ${fromAndCaptured.size()}" }
            val to = changes.diff(fromAndCaptured).get()
            val from = fromAndCaptured.find { it.square.col != to.square.col }.get()
            val movedPiece = previousPosition.get(from.square)
            assert(movedPiece.type === Piece.Type.PAWN) { "Expected pawn at ${to.square}, found ${movedPiece}" }
            val captured = fromAndCaptured.find { it.square.col == to.square.col }.get()
            val capturedPiece = previousPosition.get(captured.square)
            assert(capturedPiece == Piece.pawnOf(movedPiece.color.opponent())) { "Expected en-passant of $movedPiece from ${from.square} to ${to.square}, found $capturedPiece in ${captured.square}" }
            return Option.of(Move.Regular(from.square, to.square, EnPassant()))
        }
        // TODO: ensure that position is not changed
        return Option.none()
    }

    private fun changedSquares(previousImage: BoardImage, currentImage: BoardImage): Set<SquareImage> =
        Square.all()
            .map { Pair(previousImage.squareImage(it), currentImage.squareImage(it)) }
            .filter { (prev, curr) -> Images.areDifferent(prev.image, curr.image) }
            .map { (_, curr) -> curr }
}

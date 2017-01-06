package io.chesslave.eyes

import io.chesslave.model.Piece
import io.chesslave.visual.model.SquareImage
import javaslang.collection.List
import javaslang.control.Option

class PieceRecogniser(private val vision: Vision, private val config: BoardConfiguration) {

    /**
     * Detects the piece placed in the square.
     *
     * @param square         the image of the square
     * @param expectedPieces the list of the pieces to recognise
     * @return the detected piece or nothing if none piece was be recognised
     */
    fun piece(square: SquareImage, expectedPieces: List<Piece>): Option<Piece> {
        val recogniser = vision.recognise(square.image)
        return expectedPieces.iterator()
            .map { piece ->
                val image = config.pieces.apply(piece)
                recogniser.match(image).map { piece }
            }
            .filter { it.isDefined }
            .flatMap { it }
            .headOption()
    }
}

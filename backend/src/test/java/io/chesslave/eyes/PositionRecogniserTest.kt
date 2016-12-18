package io.chesslave.eyes

import io.chesslave.eyes.sikuli.SikuliVision
import io.chesslave.model.*
import io.chesslave.visual.model.BoardImage
import io.chesslave.visual.rendering.BoardRenderer
import io.chesslave.visual.rendering.ChessSet
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.Assert.assertEquals

//@Ignore
class PositionRecogniserTest(chessSet: ChessSet) : SinglePieceRecognitionTest(chessSet) {

    lateinit var recogniser: PositionRecogniser

    @Before
    fun setUp() {
        val initialPosition = Game.initialPosition().position()
        val initialBoard = BoardRenderer.using(chessSet, initialPosition).toBoardImage()
        val config = BoardAnalyzer().analyze(initialBoard.image())
        this.recogniser = PositionRecogniser(SikuliVision(), config)
    }

    @Test
    fun recognisePosition() {
        val position = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|B| |R")
        val board = BoardRenderer.using(chessSet, position).toBoardImage()
        val got = recogniser.position(board)
        assertEquals(position, got)
    }

    override fun withPieceOnSquare(square: Square, piece: Piece) {
        val position = Position.Builder().withPiece(square, piece).build()
        val board = BoardRenderer.using(chessSet, position).toBoardImage()
        val got = recogniser.position(board)
        assertEquals(position, got)
    }
}
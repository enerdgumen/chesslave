package io.chesslave.eyes

import io.chesslave.eyes.sikuli.SikuliVision
import io.chesslave.model.Board
import io.chesslave.model.Game
import io.chesslave.model.Piece
import io.chesslave.model.positionFromText
import io.chesslave.visual.rendering.BoardRenderer
import io.chesslave.visual.rendering.ChessSet
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.awt.Color.YELLOW

class MoveRecogniserByImageDiffTest(chessSet: ChessSet) : BaseRecognitionTest(chessSet) {

    lateinit var recogniser: MoveRecogniserByImageDiff

    @Before
    fun setUp() {
        val initialPosition = Game.initialPosition().position()
        val initialBoard = BoardRenderer(chessSet).withPosition(initialPosition).render()
        val config = analyzeBoardImage(initialBoard.image)
        this.recogniser = MoveRecogniserByImageDiff(PieceRecogniser(SikuliVision(), config))
    }

    @Test
    fun ignoreUnchangedPosition() {
        val position = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|B| |R")
        val image = BoardRenderer(chessSet).withPosition(position).render()
        val got = recogniser.detect(position, image, image)
        Assert.assertEquals(null, got)
    }

    @Test
    fun ignoreUnchangedPositionWithDifferentQuareBackground() {
        val position = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|B| |R")
        val got = recogniser.detect(position,
            BoardRenderer(chessSet).withPosition(position).render(),
            BoardRenderer(chessSet).withPosition(position).withBackground(Board.d4, YELLOW).render())
        Assert.assertEquals(null, got)
    }

    @Test
    fun recogniseSimpleRegularMove() {
        val before = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Board.d1, Board.d3)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseRegularMoveWithDifferentSquareBackground() {
        val before = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Board.d1, Board.d3)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).withBackground(Board.d1, YELLOW).withBackground(Board.d3, YELLOW).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseSimpleCapture() {
        val before = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Board.d1, Board.d4)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseCaptureWithDifferentSquareBackground() {
        val before = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Board.d1, Board.d4)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).withBackground(Board.d1, YELLOW).withBackground(Board.d4, YELLOW).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseWhiteShortCastling() {
        val before = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | |B|p|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|*|*|R")
        val after = before
            .move(Board.e1, Board.g1)
            .move(Board.h1, Board.f1)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseWhiteLongCastling() {
        val before = positionFromText(
            "r|n| |q|k|b|n|r",
            "p|p| |b| |p|p|p",
            " | | |p| | | | ",
            " | | | |p| |B| ",
            " | | |p|P| | | ",
            " | |N|Q| |N| | ",
            "P|P|P| | |P|P|P",
            "R| |*|*|K|B| |R")
        val after = before
            .move(Board.e1, Board.c1)
            .move(Board.a1, Board.d1)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseWhitePromotion() {
        val before = positionFromText(
            " |*| | |k| | | ",
            " |P| | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val after = before.remove(Board.b7).put(Board.b8, Piece.whiteQueen)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseBlackPromotion() {
        val before = positionFromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " |p| | | | | | ",
            " |*| | |K| | | ")
        val after = before.remove(Board.b2).put(Board.b1, Piece.blackQueen)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseWhiteEnPassant() {
        val before = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |*| | | | ",
            " | | |p|P| | | ",
            " | |B|p| | | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K| | |R")
        val after = before
            .move(Board.e5, Board.d6)
            .remove(Board.d5)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }

    @Test
    fun recogniseBlackEnPassant() {
        val before = positionFromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | |p| | | | | ",
            " | | | | | | | ",
            " | |B|p|P| | | ",
            " | | | |*|N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K| | |R")
        val after = before
            .move(Board.d4, Board.e3)
            .remove(Board.e4)
        val got = recogniser.detect(before,
            BoardRenderer(chessSet).withPosition(before).render(),
            BoardRenderer(chessSet).withPosition(after).render())
        Assert.assertEquals(after, got?.apply(before))
    }
}
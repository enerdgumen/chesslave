package io.chesslave.eyes

import io.chesslave.eyes.sikuli.SikuliVision
import io.chesslave.model.*
import io.chesslave.visual.rendering.BoardRenderer
import io.chesslave.visual.rendering.ChessSet
import javaslang.control.Option
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.awt.Color.YELLOW

class MoveRecogniserByImageDiffTest(chessSet: ChessSet) : BaseRecognitionTest(chessSet) {

    lateinit var recogniser: MoveRecogniserByImageDiff

    @Before
    fun setUp() {
        val initialPosition = Game.initialPosition().position()
        val initialBoard = BoardRenderer.using(chessSet, initialPosition).toBoardImage()
        val config = BoardAnalyzer().analyze(initialBoard.image)
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
        val image = BoardRenderer.using(chessSet, position).toBoardImage()
        val got = recogniser.detect(position, image, image)
        Assert.assertEquals(Option.none<Any>(), got)
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
            BoardRenderer.using(chessSet, position).toBoardImage(),
            BoardRenderer.using(chessSet, position)
                .withBackground(Board.d4, YELLOW)
                .toBoardImage())
        Assert.assertEquals(Option.none<Any>(), got)
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after)
                .withBackground(Board.d1, YELLOW)
                .withBackground(Board.d3, YELLOW)
                .toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after)
                .withBackground(Board.d1, YELLOW)
                .withBackground(Board.d4, YELLOW)
                .toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
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
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }
}
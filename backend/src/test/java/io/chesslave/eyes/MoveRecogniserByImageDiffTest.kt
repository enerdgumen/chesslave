package io.chesslave.eyes

import io.chesslave.eyes.sikuli.SikuliVision
import io.chesslave.model.Color
import io.chesslave.model.Game
import io.chesslave.model.Positions
import io.chesslave.model.Square
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
        val config = BoardAnalyzer().analyze(initialBoard.image())
        this.recogniser = MoveRecogniserByImageDiff(PieceRecogniser(SikuliVision(), config))
    }

    @Test
    fun ignoreUnchangedPosition() {
        val position = Positions.fromText(
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
        val position = Positions.fromText(
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
                .withBackground(Square.of("d4"), YELLOW)
                .toBoardImage())
        Assert.assertEquals(Option.none<Any>(), got)
    }

    @Test
    fun recogniseSimpleRegularMove() {
        val before = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Square.of("d1"), Square.of("d3"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseRegularMoveWithDifferentSquareBackground() {
        val before = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Square.of("d1"), Square.of("d3"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after)
                .withBackground(Square.of("d1"), YELLOW)
                .withBackground(Square.of("d3"), YELLOW)
                .toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseSimpleCapture() {
        val before = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Square.of("d1"), Square.of("d4"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseCaptureWithDifferentSquareBackground() {
        val before = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | | |p|P| | | ",
            " | | |*| |N| | ",
            "P|P|P|*| |P|P|P",
            "R|N|B|Q|K|B| |R")
        val after = before.move(Square.of("d1"), Square.of("d4"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after)
                .withBackground(Square.of("d1"), YELLOW)
                .withBackground(Square.of("d4"), YELLOW)
                .toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseWhiteShortCastling() {
        val before = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |p| | | | ",
            " | | | | | | | ",
            " | |B|p|P| | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K|*|*|R")
        val after = before
            .move(Square.of("e1"), Square.of("g1"))
            .move(Square.of("h1"), Square.of("f1"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseWhiteLongCastling() {
        val before = Positions.fromText(
            "r|n| |q|k|b|n|r",
            "p|p| |b| |p|p|p",
            " | | |p| | | | ",
            " | | | |p| |B| ",
            " | | |p|P| | | ",
            " | |N|Q| |N| | ",
            "P|P|P| | |P|P|P",
            "R| |*|*|K|B| |R")
        val after = before
            .move(Square.of("e1"), Square.of("c1"))
            .move(Square.of("a1"), Square.of("d1"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseWhitePromotion() {
        val before = Positions.fromText(
            " |*| | |k| | | ",
            " |P| | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | |K| | | ")
        val after = before.remove(Square.of("b7")).put(Square.of("b8"), Color.WHITE.queen())
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseBlackPromotion() {
        val before = Positions.fromText(
            " | | | |k| | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " | | | | | | | ",
            " |p| | | | | | ",
            " |*| | |K| | | ")
        val after = before.remove(Square.of("b2")).put(Square.of("b1"), Color.BLACK.queen())
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseWhiteEnPassant() {
        val before = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | | |*| | | | ",
            " | | |p|P| | | ",
            " | |B|p| | | | ",
            " | | | | |N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K| | |R")
        val after = before
            .move(Square.of("e5"), Square.of("d6"))
            .remove(Square.of("d5"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }

    @Test
    fun recogniseBlackEnPassant() {
        val before = Positions.fromText(
            "r|n|b|q|k|b|n|r",
            "p|p| | |p|p|p|p",
            " | |p| | | | | ",
            " | | | | | | | ",
            " | |B|p|P| | | ",
            " | | | |*|N| | ",
            "P|P|P| | |P|P|P",
            "R|N|B|Q|K| | |R")
        val after = before
            .move(Square.of("d4"), Square.of("e3"))
            .remove(Square.of("e4"))
        val got = recogniser.detect(before,
            BoardRenderer.using(chessSet, before).toBoardImage(),
            BoardRenderer.using(chessSet, after).toBoardImage())
        Assert.assertEquals(Option.of(after), got.map { it.apply(before) })
    }
}
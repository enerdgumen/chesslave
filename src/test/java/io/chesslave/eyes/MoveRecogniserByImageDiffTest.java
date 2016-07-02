package io.chesslave.eyes;

import io.chesslave.eyes.sikuli.SikuliVision;
import io.chesslave.model.*;
import io.chesslave.visual.model.BoardImage;
import io.chesslave.visual.rendering.BoardRenderer;
import javaslang.control.Option;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoveRecogniserByImageDiffTest extends BaseRecognitionTest {

    public static final java.awt.Color YELLOW = java.awt.Color.YELLOW;
    private MoveRecogniserByImageDiff recogniser;

    @Before
    public void setUp() throws Exception {
        final Position initialPosition = Game.initialPosition().position();
        final BoardImage initialBoard = BoardRenderer.using(chessSet, initialPosition).toBoardImage();
        final BoardConfiguration config = new BoardAnalyzer().analyze(initialBoard.image());
        this.recogniser = new MoveRecogniserByImageDiff(new PieceRecogniser(new SikuliVision(), config));
    }

    @Test
    public void ignoreUnchangedPosition() throws Exception {
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|B| |R");
        final BoardImage image = BoardRenderer.using(chessSet, position).toBoardImage();
        final Option<Move> got = recogniser.detect(position, image, image);
        Assert.assertEquals(Option.none(), got);
    }

    @Test
    public void ignoreUnchangedPositionWithDifferentQuareBackground() throws Exception {
        final Position position = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|B| |R");
        final Option<Move> got = recogniser.detect(position,
                BoardRenderer.using(chessSet, position).toBoardImage(),
                BoardRenderer.using(chessSet, position)
                        .withBackground(Square.of("d4"), YELLOW)
                        .toBoardImage());
        Assert.assertEquals(Option.none(), got);
    }

    @Test
    public void recogniseSimpleRegularMove() throws Exception {
        final Position before = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | |*| |N| | ",
                "P|P|P|*| |P|P|P",
                "R|N|B|Q|K|B| |R");
        final Position after = before.move(Square.of("d1"), Square.of("d3"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseRegularMoveWithDifferentSquareBackground() throws Exception {
        final Position before = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | |*| |N| | ",
                "P|P|P|*| |P|P|P",
                "R|N|B|Q|K|B| |R");
        final Position after = before.move(Square.of("d1"), Square.of("d3"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after)
                        .withBackground(Square.of("d1"), YELLOW)
                        .withBackground(Square.of("d3"), YELLOW)
                        .toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseSimpleCapture() throws Exception {
        final Position before = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | |*| |N| | ",
                "P|P|P|*| |P|P|P",
                "R|N|B|Q|K|B| |R");
        final Position after = before.move(Square.of("d1"), Square.of("d4"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseCaptureWithDifferentSquareBackground() throws Exception {
        final Position before = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | | |p|P| | | ",
                " | | |*| |N| | ",
                "P|P|P|*| |P|P|P",
                "R|N|B|Q|K|B| |R");
        final Position after = before.move(Square.of("d1"), Square.of("d4"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after)
                        .withBackground(Square.of("d1"), YELLOW)
                        .withBackground(Square.of("d4"), YELLOW)
                        .toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseWhiteShortCastling() throws Exception {
        final Position before = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |p| | | | ",
                " | | | | | | | ",
                " | |B|p|P| | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K|*|*|R");
        final Position after = before
                .move(Square.of("e1"), Square.of("g1"))
                .move(Square.of("h1"), Square.of("f1"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseWhiteLongCastling() throws Exception {
        final Position before = Positions.fromText(
                "r|n| |q|k|b|n|r",
                "p|p| |b| |p|p|p",
                " | | |p| | | | ",
                " | | | |p| |B| ",
                " | | |p|P| | | ",
                " | |N|Q| |N| | ",
                "P|P|P| | |P|P|P",
                "R| |*|*|K|B| |R");
        final Position after = before
                .move(Square.of("e1"), Square.of("c1"))
                .move(Square.of("a1"), Square.of("d1"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseWhitePromotion() throws Exception {
        final Position before = Positions.fromText(
                " |*| | |k| | | ",
                " |P| | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | | ");
        final Position after = before.remove(Square.of("b7")).put(Square.of("b8"), Color.WHITE.queen());
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseBlackPromotion() throws Exception {
        final Position before = Positions.fromText(
                " | | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " |p| | | | | | ",
                " |*| | |K| | | ");
        final Position after = before.remove(Square.of("b2")).put(Square.of("b1"), Color.BLACK.queen());
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseWhiteEnPassant() throws Exception {
        final Position before = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | | |*| | | | ",
                " | | |p|P| | | ",
                " | |B|p| | | | ",
                " | | | | |N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K| | |R");
        final Position after = before
                .move(Square.of("e5"), Square.of("d6"))
                .remove(Square.of("d5"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }

    @Test
    public void recogniseBlackEnPassant() throws Exception {
        final Position before = Positions.fromText(
                "r|n|b|q|k|b|n|r",
                "p|p| | |p|p|p|p",
                " | |p| | | | | ",
                " | | | | | | | ",
                " | |B|p|P| | | ",
                " | | | |*|N| | ",
                "P|P|P| | |P|P|P",
                "R|N|B|Q|K| | |R");
        final Position after = before
                .move(Square.of("d4"), Square.of("e3"))
                .remove(Square.of("e4"));
        final Option<Move> got = recogniser.detect(before,
                BoardRenderer.using(chessSet, before).toBoardImage(),
                BoardRenderer.using(chessSet, after).toBoardImage());
        Assert.assertEquals(Option.of(after), got.map(move -> move.apply(before)));
    }
}
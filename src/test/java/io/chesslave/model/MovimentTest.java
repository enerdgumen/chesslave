package io.chesslave.model;

import javaslang.control.Option;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MovimentTest {

    @Test
    public void shortCastleWhiteTest() {
        final Position position = Position.of(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | |K| | |R");
        Moviment.ShortCastling shortCastling = Moviment.shortCastling(Color.WHITE);
        final Position newPosition = shortCastling.apply(position);

        final Option<Piece> noPieceOnE1 = newPosition.at(Square.of("e1"));
        assertTrue(noPieceOnE1.isEmpty());
        final Option<Piece> whiteRook = newPosition.at(Square.of("f1"));
        assertTrue(whiteRook.isDefined());
        assertEquals(Piece.of(Piece.Type.ROOK, Color.WHITE), whiteRook.get());
        final Option<Piece> whiteKing = newPosition.at(Square.of("g1"));
        assertTrue(whiteKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.WHITE), whiteKing.get());
        final Option<Piece> noPieceOnH1 = newPosition.at(Square.of("h1"));
        assertTrue(noPieceOnH1.isEmpty());
    }

    @Test
    public void longCastleWhiteTest() {
        final Position position = Position.of(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "R| | | |K| | | ");
        Moviment.LongCastling longCastling = Moviment.longCastling(Color.WHITE);
        final Position newPosition = longCastling.apply(position);

        final Option<Piece> noPieceOnE1 = newPosition.at(Square.of("e1"));
        assertTrue(noPieceOnE1.isEmpty());
        final Option<Piece> whiteRook = newPosition.at(Square.of("d1"));
        assertTrue(whiteRook.isDefined());
        assertEquals(Piece.of(Piece.Type.ROOK, Color.WHITE), whiteRook.get());
        final Option<Piece> whiteKing = newPosition.at(Square.of("c1"));
        assertTrue(whiteKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.WHITE), whiteKing.get());
        final Option<Piece> noPieceOnB1 = newPosition.at(Square.of("b1"));
        assertTrue(noPieceOnB1.isEmpty());
        final Option<Piece> noPieceOnA1 = newPosition.at(Square.of("a1"));
        assertTrue(noPieceOnA1.isEmpty());
    }

    @Test
    public void shortCastleBlackTest() {
        final Position position = Position.of(
                " | | | |k| | |r",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        Moviment.ShortCastling shortCastling = Moviment.shortCastling(Color.BLACK);
        final Position newPosition = shortCastling.apply(position);

        final Option<Piece> noPieceOnE8 = newPosition.at(Square.of("e8"));
        assertTrue(noPieceOnE8.isEmpty());
        final Option<Piece> blackRook = newPosition.at(Square.of("f8"));
        assertTrue(blackRook.isDefined());
        assertEquals(Piece.of(Piece.Type.ROOK, Color.BLACK), blackRook.get());
        final Option<Piece> blackKing = newPosition.at(Square.of("g8"));
        assertTrue(blackKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.BLACK), blackKing.get());
        final Option<Piece> noPieceOnH8 = newPosition.at(Square.of("h8"));
        assertTrue(noPieceOnH8.isEmpty());
    }

    @Test
    public void longCastleBlackTest() {
        final Position position = Position.of(
                "r| | | |k| | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        Moviment.LongCastling longCastling = Moviment.longCastling(Color.BLACK);
        final Position newPosition = longCastling.apply(position);

        final Option<Piece> noPieceOnE8 = newPosition.at(Square.of("e8"));
        assertTrue(noPieceOnE8.isEmpty());
        final Option<Piece> blackRook = newPosition.at(Square.of("d8"));
        assertTrue(blackRook.isDefined());
        assertEquals(Piece.of(Piece.Type.ROOK, Color.BLACK), blackRook.get());
        final Option<Piece> blackKing = newPosition.at(Square.of("c8"));
        assertTrue(blackKing.isDefined());
        assertEquals(Piece.of(Piece.Type.KING, Color.BLACK), blackKing.get());
        final Option<Piece> noPieceOnB8 = newPosition.at(Square.of("b8"));
        assertTrue(noPieceOnB8.isEmpty());
        final Option<Piece> noPieceOnA8 = newPosition.at(Square.of("a8"));
        assertTrue(noPieceOnA8.isEmpty());
    }

    @Test
    public void enPassantWhiteTest() {
        final Position position = Position.of(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | |p|P| | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        Moviment.Regular enPassant = Moviment.enPassant(Square.of("d5"), Square.of("c6"));
        final Position newPosition = enPassant.apply(position);

        final Option<Piece> noPieceOnD5 = newPosition.at(Square.of("d5"));
        assertTrue(noPieceOnD5.isEmpty());
        final Option<Piece> noPieceOnD6 = newPosition.at(Square.of("d6"));
        assertTrue(noPieceOnD6.isEmpty());
        final Option<Piece> noPieceOnC5 = newPosition.at(Square.of("c5"));
        assertTrue(noPieceOnC5.isEmpty());
        final Option<Piece> whitePawn = newPosition.at(Square.of("c6"));
        assertTrue(whitePawn.isDefined());
        assertEquals(Piece.of(Piece.Type.PAWN, Color.WHITE), whitePawn.get());
    }

    @Test
    public void enPassantBlackTest() {
        final Position position = Position.of(
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | |P|p| ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        Moviment.Regular enPassant = Moviment.enPassant(Square.of("g4"), Square.of("f3"));
        final Position newPosition = enPassant.apply(position);

        final Option<Piece> noPieceOnG4 = newPosition.at(Square.of("g4"));
        assertTrue(noPieceOnG4.isEmpty());
        final Option<Piece> noPieceOnG3 = newPosition.at(Square.of("g3"));
        assertTrue(noPieceOnG3.isEmpty());
        final Option<Piece> noPieceOnF4 = newPosition.at(Square.of("f4"));
        assertTrue(noPieceOnF4.isEmpty());
        final Option<Piece> blackPawn = newPosition.at(Square.of("f3"));
        assertTrue(blackPawn.isDefined());
        assertEquals(Piece.of(Piece.Type.PAWN, Color.BLACK), blackPawn.get());
    }

    @Test
    public void promotionTest() {
        final Position position = Position.of(
                " | | | | | | | ",
                " |P| | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ");
        Moviment.Regular promotion = Moviment.promotion(Square.of("b7"), Square.of("b8"), Piece.Type.QUEEN);
        final Position newPosition = promotion.apply(position);

        final Option<Piece> noPieceOnB7 = newPosition.at(Square.of("b7"));
        assertTrue(noPieceOnB7.isEmpty());
        final Option<Piece> whiteQueen = newPosition.at(Square.of("b8"));
        assertTrue(whiteQueen.isDefined());
        assertEquals(Piece.of(Piece.Type.QUEEN, Color.WHITE), whiteQueen.get());
    }

    @Test
    public void regularTest() {
        final Position position = Position.of(
                "r|n|b|q|k|b|n|r",
                "p|p|p|p|p|p|p|p",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                " | | | | | | | ",
                "P|P|P|P|P|P|P|P",
                "R|N|B|Q|K|B|N|R");
        Moviment.Regular firstMove = Moviment.regular(Square.of("e2"), Square.of("e4"));
        final Position newPosition = firstMove.apply(position);

        final Option<Piece> noPieceOnE2 = newPosition.at(Square.of("e2"));
        assertTrue(noPieceOnE2.isEmpty());
        final Option<Piece> whitePawn = newPosition.at(Square.of("e4"));
        assertTrue(whitePawn.isDefined());
        assertEquals(Piece.of(Piece.Type.PAWN, Color.WHITE), whitePawn.get());
    }

    @Test
    public void captureTest() {
        final Position position = Position.of(
                "r| |b|q|k|b|n|r",
                " |p|p|p| |p|p|p",
                "p| |n| | | | | ",
                " |B| | |p| | | ",
                " | | | |P| | | ",
                " | | | | |N| | ",
                "P|P|P|P| |P|P|P",
                "R|N|B|Q|K| | |R");
        Moviment.Regular bishopTakesKnight = Moviment.regular(Square.of("b5"), Square.of("c6"));
        final Position newPosition = bishopTakesKnight.apply(position);

        final Option<Piece> noPieceOnB5 = newPosition.at(Square.of("b5"));
        assertTrue(noPieceOnB5.isEmpty());
        final Option<Piece> whiteBishop = newPosition.at(Square.of("c6"));
        assertTrue(whiteBishop.isDefined());
        assertEquals(Piece.of(Piece.Type.BISHOP, Color.WHITE), whiteBishop.get());
    }
}

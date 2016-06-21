package io.chesslave.model;

import javaslang.Tuple;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.collection.Stream;
import javaslang.control.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SquareTest {

    @Test
    public void constructorIllegalArgumentsTest() {
        try {
            new Square(-1, 0);
            fail("Should fail when column index is < 0");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("column -1"));
        }

        try {
            new Square(Board.SIZE, 0);
            fail("Should fail when column index >= board size");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("column " + Board.SIZE));
        }
        try {
            new Square(0, -1);
            fail("Should fail when row index is < 0");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("row -1"));
        }

        try {
            new Square(0, Board.SIZE);
            fail("Should fail when row index >= board size");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("row " + Board.SIZE));
        }
    }

    @Test
    public void factoryMethodIllegalArgumentsTest() {
        try {
            Square.of("");
            fail("Should fail when coordinates are empty");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("coordinate"));
        }

        try {
            Square.of("a");
            fail("Should fail when coordinates are incomplete");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("coordinate a"));
        }

        try {
            Square.of("a4c");
            fail("Should fail when coordinates are exceeded");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("coordinate a4c"));
        }

        try {
            Square.of("i3");
            fail("Should fail when column is out of the board");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("column"));
        }

        try {
            Square.of("h0");
            fail("Should fail when row is out of the board");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("row"));
        }
    }

    @Test
    public void nameTest() {
        final Square a1 = Square.of("a1");
        assertEquals("a1", a1.name());
        final Square g6 = Square.of("G6");
        assertEquals("g6", g6.name());
        final Square f3 = new Square(5, 2);
        assertEquals("f3", f3.name());
    }

    @Test
    public void translateTest() {
        final Option<Square> a1MovedToC2 = Square.of("a1").translate(2, 1);
        assertTrue(a1MovedToC2.isDefined());
        assertEquals(Square.of("c2"), a1MovedToC2.get());

        final Option<Square> g6MovedToG1 = Square.of("g6").translate(0, -5);
        assertTrue(g6MovedToG1.isDefined());
        assertEquals(Square.of("g1"), g6MovedToG1.get());

        final Option<Square> h8MovedOutOfBoard = Square.of("h8").translate(1, 0);
        assertTrue(h8MovedOutOfBoard.isEmpty());

        final Option<Square> e4MovedOutOfBoard = Square.of("e4").translate(-2, -5);
        assertTrue(e4MovedOutOfBoard.isEmpty());
    }

    @Test
    public void translateAllTest() {
        final Square a1 = Square.of("a1");
        final Set<Square> a1Translations = a1.translateAll(
                Tuple.of(2, 1),
                Tuple.of(7, 7),
                Tuple.of(0, 3),
                Tuple.of(-1, 0),
                Tuple.of(0, -1));
        assertEquals(3, a1Translations.size());
        assertThat(a1Translations, hasItem(Square.of("c2")));
        assertThat(a1Translations, hasItem(Square.of("h8")));
        assertThat(a1Translations, hasItem(Square.of("a4")));
    }

    @Test
    public void walkTest() {
        final Square a1 = Square.of("a1");
        final Stream<Square> a1Walk = a1.walk(1, 1);
        assertEquals(7, a1Walk.size());
        assertThat(a1Walk, hasItem(Square.of("b2")));
        assertThat(a1Walk, hasItem(Square.of("c3")));
        assertThat(a1Walk, hasItem(Square.of("d4")));
        assertThat(a1Walk, hasItem(Square.of("e5")));
        assertThat(a1Walk, hasItem(Square.of("f6")));
        assertThat(a1Walk, hasItem(Square.of("g7")));
        assertThat(a1Walk, hasItem(Square.of("h8")));

        final Square e4 = Square.of("e4");
        final Stream<Square> e4Walk = e4.walk(-2, 1);
        assertEquals(2, e4Walk.size());
        assertThat(e4Walk, hasItem(Square.of("c5")));
        assertThat(e4Walk, hasItem(Square.of("a6")));
    }

    @Test
    public void allTest() {
        final Set<Square> allSquares = Square.all();
        assertEquals(64, allSquares.size());
        List.range(0, Board.SIZE)
                .crossProduct(List.range(0, Board.SIZE))
                .forEach(t -> assertThat(allSquares, hasItem(new Square(t._1, t._2))));
    }

    @Test
    public void equalsTest() {
        final Square e1Square = Square.of("e1");
        assertTrue(e1Square.equals(e1Square));
        assertFalse(e1Square.equals(new Object()));
        assertTrue(e1Square.equals(Square.of("e1")));
    }

    @Test
    public void hashCodeTest() {
        final Square e1Square = Square.of("e1");
        final int e1SquareHash = e1Square.hashCode();
        assertEquals(e1SquareHash, e1Square.hashCode());
        assertNotEquals(e1SquareHash, new Object().hashCode());
        assertEquals(e1SquareHash, Square.of("e1").hashCode());
    }
}

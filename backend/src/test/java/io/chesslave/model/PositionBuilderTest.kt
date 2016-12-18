package io.chesslave.model

import io.chesslave.model.Piece.Type
import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.assertThat
import org.junit.Assert.fail

import org.junit.Test

class PositionBuilderTest {

    @Test
    fun withPieceIllegalArgumentTest() {
        val e2 = Square.of("e2")
        val whitePawn = Piece(Type.PAWN, Color.WHITE)
        val whiteKnight = Piece(Type.KNIGHT, Color.WHITE)
        val positionBuilder = Position.Builder().withPiece(e2, whitePawn)
        try {
            positionBuilder.withPiece(e2, whiteKnight)
            fail("Should fail when the square is already occupied")
        } catch (ex: IllegalArgumentException) {
            assertThat(ex.message, containsString(e2.toString()))
            assertThat(ex.message, containsString(whiteKnight.toString()))
            assertThat(ex.message, containsString("square already used"))
        }
    }
}

package io.chesslave.visual.rendering

import io.chesslave.model.Color
import io.chesslave.model.Piece
import io.chesslave.model.Piece.Type
import io.chesslave.visual.Images
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ChessSetTest(val chessSet: ChessSet, val chessSetPath: String) {

    companion object {

        @Parameterized.Parameters
        @JvmStatic fun data(): Collection<Array<Any>> =
            listOf("/images/set1/", "/images/set3/").map {
                arrayOf(ChessSet.read(it), it)
            }
    }

    @Test
    fun readTest() {
        val expectedBoardImage = Images.read("${chessSetPath}empty-board.png")
        assertTrue(Images.areEquals(chessSet.board.image, expectedBoardImage))
        chessSet.pieces.forEach { piece, image ->
            val expectedPieceImage = Images.read("$chessSetPath${getPieceBaseName(piece)}.png")
            assertTrue(Images.areEquals(image, expectedPieceImage))
        }
    }

    // TODO: this test has too much logic, it should be tested too!
    private fun getPieceBaseName(piece: Piece): String {
        val pieceName = StringBuilder()
        pieceName.append(if (piece.color === Color.WHITE) 'w' else 'b')
        pieceName.append(getPieceChar(piece.type))
        return pieceName.toString()
    }

    private fun getPieceChar(pieceType: Type) =
        when (pieceType) {
            Type.KING -> 'k'
            Type.QUEEN -> 'q'
            Type.ROOK -> 'r'
            Type.BISHOP -> 'b'
            Type.KNIGHT -> 'n'
            Type.PAWN -> 'p'
        }
}

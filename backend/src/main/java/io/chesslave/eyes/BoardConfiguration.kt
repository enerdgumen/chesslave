package io.chesslave.eyes

import io.chesslave.model.Piece
import io.chesslave.visual.Images
import io.chesslave.visual.model.BoardImage
import javaslang.collection.Map
import java.awt.image.BufferedImage
import java.io.File

class BoardConfiguration(
    val board: BoardImage,
    val pieces: Map<Piece, BufferedImage>,
    val characteristics: BoardConfiguration.Characteristics,
    val reversed: Boolean) {

    class Characteristics(val cellWidth: Int, val cellHeight: Int, val whiteColor: Int, val blackColor: Int)

    fun save(dir: File) {
        Images.write(board.image(), File(dir, "board.png"))
        pieces.forEach { (type, color), image -> Images.write(image, File(dir, "${type}_${color}.png")) }
    }
}

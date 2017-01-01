package io.chesslave.visual.rendering

import io.chesslave.model.Position
import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage
import io.chesslave.visual.model.SquareImage
import javaslang.collection.HashMap
import javaslang.collection.Map
import javaslang.control.Option
import org.apache.batik.dom.GenericDOMImplementation
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.svggen.SVGGraphics2DIOException
import org.apache.batik.transcoder.TranscoderException
import org.w3c.dom.DOMImplementation
import org.w3c.dom.Document

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.Dimension
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.StringWriter

/**
 * Given a position and a chess set, creates an image of the board.
 */
object BoardRenderer {

    private val SVG_NAMESPACE = "http://www.w3.org/2000/svg"

    fun using(chessSet: ChessSet) = Builder(chessSet)

    // TODO: check
    fun using(chessSet: ChessSet, position: Position) = using(chessSet).withPosition(position)

    private fun render(set: ChessSet, position: Option<Position>, backgrounds: Map<Square, Color>): BoardImage {
        val graphics = createGraphics()
        drawBoard(graphics, set.board.image)
        drawCustomBackgrounds(graphics, set, backgrounds)
        drawPieces(graphics, set, position)
        return graphicsToBoardImage(graphics)
    }

    private fun drawBoard(graphics: SVGGraphics2D, boardImg: BufferedImage) {
        graphics.svgCanvasSize = Dimension(boardImg.width, boardImg.height)
        graphics.drawImage(boardImg, 0, 0, null)
    }

    private fun drawCustomBackgrounds(graphics: SVGGraphics2D, set: ChessSet, backgrounds: Map<Square, Color>) {
        backgrounds.forEach { square, color ->
            graphics.color = color
            val squareImage = set.board.squareImage(square)
            graphics.fillRect(squareImage.left(), squareImage.top(), squareImage.size, squareImage.size)
        }
    }

    private fun drawPieces(graphics: SVGGraphics2D, set: ChessSet, position: Option<Position>) {
        if (position.isDefined) {
            position.get().toMap().forEach { square, piece ->
                val pieceImg = set.pieces.apply(piece)
                val squareImg = set.board.squareImage(square)
                val translation = AffineTransform.getTranslateInstance(
                    (squareImg.left() + (squareImg.size - pieceImg.width) / 2).toDouble(),
                    (squareImg.top() + (squareImg.size - pieceImg.height) / 2).toDouble())
                graphics.drawRenderedImage(pieceImg, translation)
            }
        }
    }

    private fun graphicsToBoardImage(graphics: SVGGraphics2D): BoardImage {
        val svg = graphicsToSvg(graphics)
        val transcoder = ChessTranscoder(svg)
        return ByteArrayInputStream(transcoder.toPng()).use { BoardImage(ImageIO.read(it)) }
    }

    private fun createGraphics(): SVGGraphics2D {
        val domImpl = GenericDOMImplementation.getDOMImplementation()
        val document = domImpl.createDocument(SVG_NAMESPACE, "svg", null)
        return SVGGraphics2D(document)
    }

    private fun graphicsToSvg(graphics: SVGGraphics2D): String {
        val svg = StringWriter()
        graphics.stream(svg, false)
        return svg.toString()
    }

    /**
     * Fluent interface to toBoardImage a chess position.
     */
    class Builder internal constructor(private val chessSet: ChessSet) {

        private var position: Position? = null
        private var customBackgrounds: Map<Square, Color> = HashMap.empty<Square, Color>()

        fun withPosition(position: Position): Builder {
            this.position = position
            return this
        }

        fun withBackground(square: Square, background: Color): Builder {
            customBackgrounds = customBackgrounds.put(square, background)
            return this
        }

        fun toBoardImage(): BoardImage = BoardRenderer.render(chessSet, Option.of(position), customBackgrounds)
    }
}

package io.chesslave.visual.rendering

import io.chesslave.model.Position
import io.chesslave.model.Square
import io.chesslave.visual.model.BoardImage
import javaslang.collection.HashMap
import javaslang.collection.Map
import org.apache.batik.dom.GenericDOMImplementation
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.awt.Color
import java.awt.Dimension
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.io.StringWriter
import javax.imageio.ImageIO

/**
 * Given a position and a chess set, creates an image of the board.
 */
class BoardRenderer(val chessSet: ChessSet) {

    private val SVG_NAMESPACE = "http://www.w3.org/2000/svg"
    private var position: Position? = null
    private var backgrounds: Map<Square, Color> = HashMap.empty<Square, Color>()

    fun withPosition(position: Position): BoardRenderer {
        this.position = position
        return this
    }

    fun withBackground(square: Square, background: Color): BoardRenderer {
        backgrounds = backgrounds.put(square, background)
        return this
    }

    fun render(): BoardImage {
        val graphics = createGraphics()
        drawBoard(graphics, chessSet.board.image)
        drawCustomBackgrounds(graphics, chessSet, backgrounds)
        position?.let { drawPieces(graphics, it) }
        return graphicsToBoardImage(graphics)
    }

    private fun createGraphics(): SVGGraphics2D {
        val domImpl = GenericDOMImplementation.getDOMImplementation()
        val document = domImpl.createDocument(SVG_NAMESPACE, "svg", null)
        return SVGGraphics2D(document)
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

    private fun drawPieces(graphics: SVGGraphics2D, position: Position) {
        position.toMap().forEach { square, piece ->
            val pieceImg = chessSet.pieces.apply(piece)
            val squareImg = chessSet.board.squareImage(square)
            val translation = AffineTransform.getTranslateInstance(
                (squareImg.left() + (squareImg.size - pieceImg.width) / 2).toDouble(),
                (squareImg.top() + (squareImg.size - pieceImg.height) / 2).toDouble())
            graphics.drawRenderedImage(pieceImg, translation)
        }
    }

    private fun graphicsToBoardImage(graphics: SVGGraphics2D): BoardImage {
        val svg = graphicsToSvg(graphics)
        val png = svgToPng(svg)
        return ByteArrayInputStream(png).use { BoardImage(ImageIO.read(it)) }
    }

    private fun graphicsToSvg(graphics: SVGGraphics2D): String {
        val svg = StringWriter()
        graphics.stream(svg, false)
        return svg.toString()
    }

    private fun svgToPng(svg: String): ByteArray
        = ByteArrayOutputStream()
        .use { os ->
            val input = TranscoderInput(StringReader(svg))
            val output = TranscoderOutput(os)
            PNGTranscoder().transcode(input, output)
            return os.toByteArray()
        }
}
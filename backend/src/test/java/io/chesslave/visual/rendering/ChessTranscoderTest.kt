package io.chesslave.visual.rendering

import org.apache.batik.dom.GenericDOMImplementation
import org.apache.batik.svggen.SVGGraphics2D
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import java.awt.Color
import java.awt.Dimension
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.io.StringWriter

class ChessTranscoderTest {

    private val SVG_NAMESPACE = "http://www.w3.org/2000/svg"
    private var svg: String? = null
    private var transcoder: ChessTranscoder? = null

    @Before
    fun setUp() {
        val domImpl = GenericDOMImplementation.getDOMImplementation()
        val document = domImpl.createDocument(SVG_NAMESPACE, "svg", null)
        val graphics = SVGGraphics2D(document)
        graphics.svgCanvasSize = Dimension(48, 48)
        graphics.color = Color.BLACK
        graphics.drawLine(0, 0, 48, 48)

        val writer = StringWriter()
        graphics.stream(writer, false)
        svg = writer.toString()
        transcoder = ChessTranscoder(svg!!)
    }

    @Test
    fun toPngTest() {
        ByteArrayOutputStream().use { os ->
            val input = TranscoderInput(StringReader(svg))
            val output = TranscoderOutput(os)
            PNGTranscoder().transcode(input, output)
            assertArrayEquals(os.toByteArray(), transcoder?.toPng())
        }
    }
}

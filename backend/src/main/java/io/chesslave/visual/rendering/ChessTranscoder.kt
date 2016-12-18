package io.chesslave.visual.rendering

import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.io.ByteArrayOutputStream
import java.io.StringReader

/**
 * Proxy class to control transcode behaviour.
 */
class ChessTranscoder(val svg: String) {

    fun toPng(): ByteArray = ByteArrayOutputStream().use { os ->
        val input = TranscoderInput(StringReader(svg))
        val output = TranscoderOutput(os)
        PNGTranscoder().transcode(input, output)
        return os.toByteArray()
    }
}

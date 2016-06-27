package io.chesslave.rendering;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * Proxy class to control transcode behaviour.
 */
public class ChessTranscoder {
    private final String svg;

    public ChessTranscoder(String svg) {
        this.svg = svg;
    }

    public byte[] toPng() throws IOException, TranscoderException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final TranscoderInput in = new TranscoderInput(new StringReader(svg));
            final TranscoderOutput out = new TranscoderOutput(baos);
            final Transcoder transcoder = new PNGTranscoder();
            transcoder.transcode(in, out);
            return baos.toByteArray();
        }
    }
}

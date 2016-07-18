package io.chesslave.visual.rendering;

import static org.junit.Assert.assertArrayEquals;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.Color;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class ChessTranscoderTest {
    private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";

    private String svg;
    private ChessTranscoder transcoder;

    @Before
    public void setUp() throws IOException {
        final DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        final Document document = domImpl.createDocument(SVG_NAMESPACE, "svg", null);
        final SVGGraphics2D graphics = new SVGGraphics2D(document);
        graphics.setSVGCanvasSize(new Dimension(48, 48));
        graphics.setColor(Color.BLACK);
        graphics.drawLine(0, 0, 48, 48);

        final StringWriter writer = new StringWriter();
        graphics.stream(writer, false);
        svg = writer.toString();
        transcoder = new ChessTranscoder(svg);
    }

    @Test
    public void toPngTest() throws IOException, TranscoderException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final TranscoderInput in = new TranscoderInput(new StringReader(svg));
            final TranscoderOutput out = new TranscoderOutput(baos);
            final Transcoder pngTranscoder = new PNGTranscoder();
            pngTranscoder.transcode(in, out);
            assertArrayEquals(baos.toByteArray(), transcoder.toPng());
        }
    }
}

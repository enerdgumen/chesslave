package io.chesslave.visual.rendering;

import io.chesslave.model.Position;
import io.chesslave.visual.BoardImageMap;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

public class BoardRenderer {

    public static BufferedImage render(Position position, ChessSet set) throws Exception {
        final SVGGraphics2D g = createGraphics();
        g.setSVGCanvasSize(new Dimension(set.board.getWidth(), set.board.getHeight()));
        g.drawImage(set.board, 0, 0, null);
        final BoardImageMap map = new BoardImageMap(set.board.getWidth());
        position.get().forEach((square, piece) -> {
            final BufferedImage pieceImg = set.pieces.apply(piece);
            final AffineTransform translation = AffineTransform.getTranslateInstance(
                    map.left(square) + (map.squareSize() - pieceImg.getWidth()) / 2,
                    map.top(square) + (map.squareSize() - pieceImg.getHeight()) / 2);
            g.drawRenderedImage(pieceImg, translation);
        });
        final String svg = graphicsToSvg(g);
        try (final InputStream img = svgToPng(svg)) {
            return ImageIO.read(img);
        }
    }

    private static SVGGraphics2D createGraphics() {
        final DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        final Document document = domImpl.createDocument("http://www.w3.org/2000/svg", "svg", null);
        return new SVGGraphics2D(document);
    }

    private static String graphicsToSvg(SVGGraphics2D g) throws SVGGraphics2DIOException {
        final StringWriter svg = new StringWriter();
        final boolean noCss = false;
        g.stream(svg, noCss);
        return svg.toString();
    }

    private static InputStream svgToPng(String svg) throws TranscoderException, IOException {
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            final TranscoderInput in = new TranscoderInput(new StringReader(svg));
            final TranscoderOutput out = new TranscoderOutput(os);
            final PNGTranscoder transcoder = new PNGTranscoder();
            transcoder.transcode(in, out);
            return new ByteArrayInputStream(os.toByteArray());
        }
    }
}

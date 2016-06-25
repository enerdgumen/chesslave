package io.chesslave.rendering;

import io.chesslave.model.Position;
import io.chesslave.model.Square;
import io.chesslave.visual.BoardImage;
import io.chesslave.visual.SquareImage;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Option;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Given a position and a chess set, creates an image of the board.
 */
public class BoardRenderer {

    private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";

    public static Builder using(ChessSet chessSet) {
        return new Builder(chessSet);
    }

    public static Builder using(ChessSet chessSet, Position position) {
        return using(chessSet).withPosition(position);
    }

    private static BoardImage render(ChessSet set, Option<Position> position, Map<Square, Color> backgrounds) throws IOException {
        final SVGGraphics2D graphics = createGraphics();
        drawBoard(graphics, set.board.image());
        drawCustomBackgrounds(graphics, set, backgrounds);
        drawPieces(graphics, set, position);
        return graphicsToBoardImage(graphics);
    }

    private static void drawBoard(SVGGraphics2D graphics, BufferedImage boardImg) {
        graphics.setSVGCanvasSize(new Dimension(boardImg.getWidth(), boardImg.getHeight()));
        graphics.drawImage(boardImg, 0, 0, null);
    }

    private static void drawCustomBackgrounds(SVGGraphics2D graphics, ChessSet set, Map<Square, Color> backgrounds) {
        backgrounds.forEach((square, color) -> {
            graphics.setColor(color);
            final SquareImage squareImage = set.board.squareImage(square);
            graphics.fillRect(squareImage.left(), squareImage.top(), squareImage.size(), squareImage.size());
        });
    }

    private static void drawPieces(SVGGraphics2D graphics, ChessSet set, Option<Position> position) {
        if (position.isDefined()) {
            position.get().toMap().forEach((square, piece) -> {
                final BufferedImage pieceImg = set.pieces.apply(piece);
                final SquareImage squareImg = set.board.squareImage(square);
                final AffineTransform translation = AffineTransform.getTranslateInstance(
                        squareImg.left() + (squareImg.size() - pieceImg.getWidth()) / 2,
                        squareImg.top() + (squareImg.size() - pieceImg.getHeight()) / 2);
                graphics.drawRenderedImage(pieceImg, translation);
            });
        }
    }

    private static BoardImage graphicsToBoardImage(SVGGraphics2D graphics) throws IOException {
        final String svg = graphicsToSvg(graphics);
        try (final InputStream img = svgToPng(svg)) {
            return new BoardImage(ImageIO.read(img));
        }
    }

    private static SVGGraphics2D createGraphics() {
        final DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        final Document document = domImpl.createDocument(SVG_NAMESPACE, "svg", null);
        return new SVGGraphics2D(document);
    }

    private static String graphicsToSvg(SVGGraphics2D graphics) throws SVGGraphics2DIOException {
        final StringWriter svg = new StringWriter();
        graphics.stream(svg, false);
        return svg.toString();
    }

    private static InputStream svgToPng(String svg) throws IOException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final TranscoderInput in = new TranscoderInput(new StringReader(svg));
            final TranscoderOutput out = new TranscoderOutput(baos);
            final Transcoder transcoder = new PNGTranscoder();
            transcoder.transcode(in, out);
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (TranscoderException te) {
            throw new IOException(te.getMessage(), te);
        }
    }

    /**
     * Fluent interface to toBoardImage a chess position.
     */
    public static class Builder {
        private final ChessSet chessSet;

        private Position position;
        private Map<Square, Color> customBackgrounds;

        private Builder(ChessSet chessSet) {
            this.chessSet = chessSet;
            customBackgrounds = HashMap.empty();
        }

        public Builder withPosition(Position position) {
            this.position = position;
            return this;
        }

        public Builder withBackground(Square square, Color background) {
            customBackgrounds = customBackgrounds.put(square, background);
            return this;
        }

        public BoardImage toBoardImage() throws IOException {
            return BoardRenderer.render(chessSet, Option.of(position), customBackgrounds);
        }
    }
}

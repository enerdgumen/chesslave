package io.chesslave.eyes.sikuli

import io.chesslave.eyes.Vision
import javaslang.Lazy
import javaslang.collection.Stream
import javaslang.control.Option
import org.sikuli.script.Finder
import org.sikuli.script.Image
import org.sikuli.script.Match
import java.awt.image.BufferedImage

class SikuliVision : Vision {

    private class SikuliRecogniser(private val source: BufferedImage) : Vision.Recogniser {

        override fun match(target: BufferedImage): Option<Vision.Match> {
            val matches = Finder(source)
            matches.find(Image(target))
            return Stream.ofAll(Iterable { matches }).map { SikuliMatch(source, it) as Vision.Match }.headOption()
        }

        override fun matches(target: BufferedImage): Stream<Vision.Match> {
            val matches = Finder(source)
            matches.findAll(Image(target))
            return Stream.ofAll(Iterable { matches }).map { SikuliMatch(source, it) }
        }
    }

    class SikuliMatch(private val source: BufferedImage, private val match: Match) : Vision.Match {

        private val image: BufferedImage by lazy {
            val rect = match.rect
            source.getSubimage(rect.x, rect.y, rect.width, rect.height)
        }

        override fun similarity() = match.score

        override fun region() = match.rect

        override fun source() = source

        override fun image() = image
    }

    override fun recognise(image: BufferedImage): Vision.Recogniser = SikuliRecogniser(image)
}

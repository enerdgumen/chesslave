package io.chesslave.eyes

import javaslang.collection.Stream
import javaslang.control.Option
import java.awt.Rectangle
import java.awt.image.BufferedImage

interface Vision {

    interface Recogniser {

        fun match(target: BufferedImage): Option<Match>

        fun matches(target: BufferedImage): Stream<Match>

        fun bestMatch(target: BufferedImage): Option<Match> =
                matches(target).toList().sortBy { it.similarity() }.lastOption()
    }

    interface Match {

        fun similarity(): Double

        fun region(): Rectangle

        fun source(): BufferedImage

        fun image(): BufferedImage
    }

    fun recognise(image: BufferedImage): Recogniser
}

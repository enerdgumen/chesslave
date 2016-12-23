package io.chesslave.eyes

import io.chesslave.visual.Images
import io.chesslave.visual.model.SquareImage
import java.awt.Color

object EmptySquareRecogniser {

    fun isEmpty(square: SquareImage): Boolean {
        val image = square.image()
        val example = Color(image.getRGB(image.width / 2, image.height / 2))
        return Images.sample(image, 1, 16).forAll { Colors.areSimilar(example, Color(it)) }
            && Images.sample(image, 16, 1).forAll { Colors.areSimilar(example, Color(it)) }
    }
}

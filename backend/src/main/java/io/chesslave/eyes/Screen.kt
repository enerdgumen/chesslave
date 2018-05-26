package io.chesslave.eyes

import io.reactivex.Single
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit

interface Screen {

    fun captureAll(): BufferedImage

    fun capture(region: Rectangle): BufferedImage

    fun select(message: String): Single<BufferedImage>

    fun highlight(region: Rectangle, time: Long, unit: TimeUnit)
}

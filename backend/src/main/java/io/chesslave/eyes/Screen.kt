package io.chesslave.eyes

import rx.Observable
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.util.concurrent.TimeUnit

interface Screen {

    fun captureAll(): BufferedImage

    fun capture(region: Rectangle): BufferedImage

    fun select(message: String): Observable<BufferedImage>

    fun highlight(region: Rectangle, time: Long, unit: TimeUnit)
}

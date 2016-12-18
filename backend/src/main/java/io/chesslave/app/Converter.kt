package io.chesslave.app

interface Converter<T> {

    fun asString(value: T): String

    fun fromString(text: String): T
}

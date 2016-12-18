package io.chesslave.hands

class MoverException : RuntimeException {

    constructor(cause: Throwable) : super(cause) {
    }

    constructor(message: String, cause: Throwable) : super(message, cause) {
    }

    companion object {
        private val serialVersionUID = 2338005287898395747L
    }
}

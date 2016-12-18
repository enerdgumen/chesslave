package io.chesslave.hands

import io.chesslave.model.Square

class MoverException(from: Square, to: Square, cause: RuntimeException)
    : RuntimeException("Cannot move piece from $from to $to", cause)

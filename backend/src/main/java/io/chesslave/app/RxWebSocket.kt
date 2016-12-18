package io.chesslave.app

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import org.slf4j.LoggerFactory
import rx.subjects.PublishSubject
import java.io.IOException

class RxWebSocket(private val converter: Converter<Event>) : WebSocketAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)
    val input: PublishSubject<Event>
    val output: PublishSubject<Event>

    init {
        this.input = PublishSubject.create<Event>()
        this.output = PublishSubject.create<Event>()
        this.output.subscribe { this.writeEvent(it) }
    }

    override fun onWebSocketConnect(session: Session) {
        logger.debug("Socket Connected: $session")
        super.onWebSocketConnect(session)
    }

    override fun onWebSocketText(message: String?) {
        logger.debug("Received TEXT message: $message")
        if (message == null) throw IllegalArgumentException("message should not be null")
        try {
            input.onNext(converter.fromString(message))
        } catch (ex: IOException) {
            input.onError(ex)
        }
    }

    override fun onWebSocketError(cause: Throwable?) {
        logger.error("Socket Error", cause)
        input.onError(cause)
    }

    override fun onWebSocketClose(statusCode: Int, reason: String?) {
        logger.debug("Socket Closed: [$statusCode] $reason")
        super.onWebSocketClose(statusCode, reason)
        input.onCompleted()
        output.onCompleted()
    }

    private fun writeEvent(event: Event) {
        try {
            val message = converter.asString(event)
            logger.debug("Sending TEXT message: {}", message)
            remote.sendString(message)
        } catch (ex: IOException) {
            output.onError(ex)
        }
    }
}
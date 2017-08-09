package io.chesslave.app

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.WebSocketAdapter
import org.eclipse.jetty.websocket.servlet.WebSocketCreator
import org.eclipse.jetty.websocket.servlet.WebSocketServlet
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory
import org.slf4j.LoggerFactory
import java.util.*

fun main(args: Array<String>) {
    val creator = WebSocketCreator { req, resp ->
        val socket = RxWebSocket(JsonEventConverter())
        val events = EventBus(socket.input, socket.output)
        Chesslave.configure(events)
        socket
    }
    val holder = ServletHolder("ws", EventServlet(creator))
    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.contextPath = "/"
    context.addServlet(holder, "/events/*")
    val server = Server(8080)
    server.handler = context
    server.start()
    server.join()
}

interface Converter<T> {

    fun asString(value: T): String

    fun fromString(text: String): T
}

class JsonEventConverter : Converter<Event> {

    private val mapper = ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    override fun asString(event: Event): String = mapper.writeValueAsString(event)

    override fun fromString(text: String): Event = mapper.readValue(text, Event::class.java)
}

data class Event(
    val address: String,
    val payload: Any? = null,
    val replyTo: String? = null
)

class EventBus(
    private val input: Observable<Event>,
    private val output: Observer<Event>
) {

    fun receive(address: String): Observable<Event> = input.filter { address == it.address }

    fun send(address: String, payload: Any? = null): Unit = output.onNext(Event(address, payload))

    fun sendAndReceive(address: String, payload: Any?): Observable<Event> {
        val replyTo = "$address.${UUID.randomUUID()}"
        output.onNext(Event(address, payload, replyTo))
        return receive(replyTo)
    }
}

class EventServlet(val creator: WebSocketCreator) : WebSocketServlet() {

    override fun configure(factory: WebSocketServletFactory) {
        factory.creator = creator
    }
}

class RxWebSocket(private val converter: Converter<Event>) : WebSocketAdapter() {

    private val logger = LoggerFactory.getLogger(javaClass)

    val input: PublishSubject<Event> = PublishSubject.create<Event>()
    val output: PublishSubject<Event> = PublishSubject.create<Event>().apply {
        subscribe { writeEvent(it) }
    }

    override fun onWebSocketConnect(session: Session) {
        logger.debug("Socket Connected: $session")
        super.onWebSocketConnect(session)
    }

    override fun onWebSocketText(message: String?) {
        logger.debug("Received TEXT message: $message")
        if (message == null) throw IllegalArgumentException("message should not be null")
        input.onNext(converter.fromString(message))
    }

    override fun onWebSocketError(cause: Throwable) {
        logger.error("Socket Error", cause)
    }

    override fun onWebSocketClose(statusCode: Int, reason: String?) {
        logger.debug("Socket Closed: [$statusCode] $reason")
        super.onWebSocketClose(statusCode, reason)
        input.onComplete()
        output.onComplete()
    }

    private fun writeEvent(event: Event) {
        val message = converter.asString(event)
        logger.debug("Sending TEXT message: {}", message)
        remote.sendString(message)
    }
}
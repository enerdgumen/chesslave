package io.chesslave.app

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.websocket.servlet.WebSocketCreator

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

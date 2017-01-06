package io.chesslave.app

import org.eclipse.jetty.websocket.servlet.WebSocketCreator
import org.eclipse.jetty.websocket.servlet.WebSocketServlet
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory

class EventServlet(val creator: WebSocketCreator) : WebSocketServlet() {

    override fun configure(factory: WebSocketServletFactory) {
        factory.creator = creator
    }
}

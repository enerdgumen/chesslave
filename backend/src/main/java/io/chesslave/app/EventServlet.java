package io.chesslave.app;

import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class EventServlet extends WebSocketServlet {

    private final WebSocketCreator creator;

    public EventServlet(WebSocketCreator creator) {
        this.creator = creator;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator(creator);
    }
}

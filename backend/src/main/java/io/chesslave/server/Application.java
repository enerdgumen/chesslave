package io.chesslave.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    public static void main(String[] args) throws Exception {
        final Logger logger = LoggerFactory.getLogger(Application.class);
        final WebSocketCreator creator = (req, resp) -> {
            final RxWebSocket socket = new RxWebSocket(new JsonEventConverter());
            socket.input().subscribe(e -> {
                logger.info("Received event: {}", e.name);
            });
            return socket;
        };
        final ServletHolder holder = new ServletHolder("ws", new EventServlet(creator));
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(holder, "/events/*");
        final Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
    }
}

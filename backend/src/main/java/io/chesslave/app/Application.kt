package io.chesslave.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class Application {

    public static void main(String[] args) throws Exception {
        final WebSocketCreator creator = (req, resp) -> {
            final RxWebSocket socket = new RxWebSocket(new JsonEventConverter());
            final EventBus events = new EventBus(socket.input(), socket.output());
            Chesslave.configure(events);
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

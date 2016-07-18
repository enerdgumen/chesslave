package io.chesslave.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Application {

    public static void main(String[] args) throws Exception {
        final EventSocket socket = new EventSocket(new JsonEventConverter());
        socket.input().subscribe(e -> socket.output().onNext(e)); // echo
        final ServletHolder holder = new ServletHolder("ws-events", new EventServlet(socket));
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(holder, "/events/*");
        final Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
    }
}

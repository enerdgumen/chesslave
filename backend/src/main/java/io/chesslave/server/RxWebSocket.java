package io.chesslave.server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Observer;
import rx.subjects.PublishSubject;
import java.io.IOException;

public class RxWebSocket extends WebSocketAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RxWebSocket.class);
    private final Converter<Event> converter;
    private final PublishSubject<Event> input;
    private final PublishSubject<Event> output;

    public RxWebSocket(Converter<Event> converter) {
        this.converter = converter;
        this.input = PublishSubject.create();
        this.output = PublishSubject.create();
        this.output.subscribe(this::writeEvent);
    }

    public Observable<Event> input() {
        return input;
    }

    public Observer<Event> output() {
        return output;
    }

    @Override
    public void onWebSocketConnect(Session session) {
        logger.debug("Socket Connected: {}", session);
        super.onWebSocketConnect(session);
    }

    @Override
    public void onWebSocketText(String message) {
        logger.debug("Received TEXT message: {}", message);
        try {
            input.onNext(converter.fromString(message));
        } catch (IOException ex) {
            input.onError(ex);
        }
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        logger.error("Socket Error", cause);
        input.onError(cause);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        logger.debug("Socket Closed: [{}] {}", statusCode, reason);
        super.onWebSocketClose(statusCode, reason);
        input.onCompleted();
        output.onCompleted();
    }

    private void writeEvent(Event event) {
        try {
            final String message = converter.asString(event);
            logger.debug("Sending TEXT message: {}", message);
            getRemote().sendString(message);
        } catch (IOException ex) {
            output.onError(ex);
        }
    }
}
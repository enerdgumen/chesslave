package io.chesslave.app;

public class Event {

    public String name;
    public Object payload;

    public static Event of(String name, Object payload) {
        final Event event = new Event();
        event.name = name;
        event.payload = payload;
        return event;
    }

    public static Event of(String name) {
        return of(name, null);
    }
}

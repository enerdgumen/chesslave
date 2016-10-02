package io.chesslave.app;

import rx.Observable;
import rx.Observer;

public class EventBus {

    private final Observable<Event> input;
    private final Observer<Event> output;

    public EventBus(Observable<Event> input, Observer<Event> output) {
        this.input = input;
        this.output = output;
    }

    public Observable<Event> on(String event) {
        return input.filter(e -> event.equals(e.name));
    }

    public void fire(Event event) {
        output.onNext(event);
    }
}

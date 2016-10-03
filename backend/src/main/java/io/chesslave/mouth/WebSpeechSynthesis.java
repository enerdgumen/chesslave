package io.chesslave.mouth;


import io.chesslave.app.Event;
import io.chesslave.app.EventBus;

public class WebSpeechSynthesis implements SpeechSynthesis {

    private final EventBus events;

    public WebSpeechSynthesis(EventBus events) {
        this.events = events;
    }

    @Override
    public void speak(Utterance utterance) {
        events.fire(Event.of("speak", utterance));
    }
}

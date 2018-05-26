package io.chesslave.mouth

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.eventbus.EventBus

class WebSpeechSynthesis(
    private val events: EventBus
) : SpeechSynthesis {

    override fun speak(utterance: Utterance) {
        events.publish("speak", JsonObject.mapFrom(utterance))
    }
}

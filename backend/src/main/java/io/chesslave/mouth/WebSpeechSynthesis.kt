package io.chesslave.mouth

import io.chesslave.app.EventBus

class WebSpeechSynthesis(val events: EventBus) : SpeechSynthesis {

    override fun speak(utterance: Utterance) = events.send("speak", utterance)
}

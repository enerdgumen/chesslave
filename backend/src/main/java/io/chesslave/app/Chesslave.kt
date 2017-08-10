package io.chesslave.app

import io.chesslave.eyes.BoardAnalyzer
import io.chesslave.eyes.BoardConfiguration
import io.chesslave.eyes.sikuli.SikuliScreen
import io.chesslave.mouth.Utterance
import io.chesslave.mouth.WebSpeechSynthesis
import io.reactivex.Observable

object Chesslave {

    fun configure(events: EventBus) {
        val screen = SikuliScreen()
        val speechSynthesis = WebSpeechSynthesis(events)
        val boardConfig: Observable<BoardConfiguration> = events
            .consume<Any?>("select-board")
            .flatMap { screen.select("Select board...").map { BoardAnalyzer().analyze(it) } }
            .doOnError { error ->
                log.error("Board selection failed", error)
                events.publish("board-selection-failed")
            }
            .retry()
        boardConfig.subscribe {
            log.info("Board selected")
            events.publish("board-selected")
            speechSynthesis.speak(Utterance("Scacchiera selezionata"))
        }
    }
}

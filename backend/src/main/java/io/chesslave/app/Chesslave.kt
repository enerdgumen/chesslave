package io.chesslave.app

import io.chesslave.eyes.BoardConfiguration
import io.chesslave.eyes.BoardObserver
import io.chesslave.eyes.analyzeBoardImage
import io.chesslave.eyes.sikuli.SikuliScreen
import io.chesslave.model.Color
import io.chesslave.mouth.Utterance
import io.chesslave.mouth.WebSpeechSynthesis
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom

data class StartGameEvent(val turn: Color, val white: String, val black: String)

object Chesslave {

    fun configure(events: EventBus) {
        val screen = SikuliScreen()
        val speechSynthesis = WebSpeechSynthesis(events)

        // board selection
        val boardConfig: Observable<BoardConfiguration> = events
            .consume("select-board")
            .flatMap { screen.select("Select board...").map(::analyzeBoardImage) }
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

        // game starting
        events.consume("start-game", StartGameEvent::class.java)
            .withLatestFrom(boardConfig) { event, config ->
                BoardObserver(config, screen).start(event.turn)
            }
            .flatMap { it }
            .subscribe { log.info("Current game: $it") }
    }
}

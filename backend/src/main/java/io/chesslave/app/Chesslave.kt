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
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.eventbus.EventBus

data class StartGameCommand(val turn: Color, val white: String, val black: String)

object Chesslave {

    fun configure(events: EventBus) {
        val screen = SikuliScreen()
        val speechSynthesis = WebSpeechSynthesis(events)

        // board selection
        val boardConfig: Observable<BoardConfiguration> = events
            .consumer<Unit>("select-board")
            .toObservable()
            .flatMapSingle { message ->
                screen.select("Select board...")
                    .map(::analyzeBoardImage)
                    .doOnError { error ->
                        log.error("Board selection failed", error)
                        message.reply(false)
                    }
                    .doOnSuccess {
                        log.info("Board selected")
                        message.reply(true)
                        speechSynthesis.speak(Utterance("Scacchiera selezionata"))
                    }
            }

        events
            .consumer<Unit>("test")
            .toObservable()
            .subscribe { it.reply("ok") }

        // game starting
        events.consumer<JsonObject>("start-game")
            .toObservable()
            .map { message -> message.body().mapTo(StartGameCommand::class.java) }
            .withLatestFrom(boardConfig) { cmd, config ->
                BoardObserver(config, screen).start(cmd.turn)
            }
            .flatMap { it }
            .subscribe { log.info("Current position: ${it.position()}") }
    }
}

package io.chesslave.app

import rx.Observable
import rx.Observer

class EventBus(
    private val input: Observable<Event>,
    private val output: Observer<Event>
) {

    fun on(event: String): Observable<Event> = input.filter { event == it.name }

    fun fire(event: Event): Unit = output.onNext(event)
}

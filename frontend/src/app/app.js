const { Observable } = require('rxjs')
const EventBus = require('vertx3-eventbus-client')
const events = new EventBus("http://localhost:8080/events")

Object.defineProperty(events, 'consumerRx', { value: Observable.bindNodeCallback(events.registerHandler) })
Object.defineProperty(events, 'sendRx', { value: Observable.bindNodeCallback(events.send) })

events.onopen = function() {
    window.jQuery = require('jquery') // needed by boostrap :-(
    require('bootstrap')

    const boardSelector = require('./board-selector').create(events)
    const game = require('./game').create(events)
    const speech = require('./speech')

    const container = document.querySelector('.container')
    container.appendChild(boardSelector.el)
    container.appendChild(game.el)

    events.consumerRx('speak').subscribe(message => speech.speak(message.body))
    events.consumerRx('log').subscribe(message => console.log(`${message.headers.level}: ${message.body}`))
}
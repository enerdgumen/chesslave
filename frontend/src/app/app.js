window.jQuery = require('jquery') // needed by boostrap :-(
require('bootstrap')

const EventBus = require('./events')
const events = new EventBus()
const boardSelector = require('./board-selector').create(events)
const game = require('./game').create(events)

const container = document.querySelector('.container')
container.appendChild(boardSelector.el)
container.appendChild(game.el)

events.on('select-board')
    .subscribe(() => {
        boardSelector.disable()
    })
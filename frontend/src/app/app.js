window.jQuery = require('jquery') // needed by boostrap :-(
require('bootstrap')

const events = require('./events')
const boardSelector = require('./board-selector').create(events)
const game = require('./game').create()

const container = document.querySelector('.container')
container.appendChild(game.el)
container.appendChild(boardSelector.el)

events.on('select-board')
    .subscribe(() => {
        boardSelector.disable()
    })

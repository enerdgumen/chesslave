const events = require('./events')
const boardSelector = require('./board-selector')

document.querySelector('.container')
    .appendChild(boardSelector)

events.on('select-board')
    .subscribe(() => console.log("Selecting..."))

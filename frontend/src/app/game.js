const players = require('./player')
const startGame = require('./start-game')

function create(events) {
    const whitePlayer = players.create({
        label: 'White player:',
        active: players.HUMAN
    })
    const blackPlayer = players.create({
        label: 'Black player:',
        active: players.COMPUTER
    })
    const starter = startGame.create()
    const el = document.createElement('div')
    el.innerHTML = `
        <div class="panel panel-primary">
            <div class="panel-heading">Game</div>
            <div class="panel-body"></div>
        </div>`
    const body = el.querySelector('.panel-body')
    body.appendChild(whitePlayer.el)
    body.appendChild(blackPlayer.el)
    body.appendChild(starter.el)
    starter.action
        .withLatestFrom(whitePlayer.status, blackPlayer.status,
            (turn, white, black) => events.fire('start-game', {turn, white, black}))
        .subscribe()
    return {el}
}

module.exports = {create}

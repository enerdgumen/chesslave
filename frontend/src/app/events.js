const rx = require('rxjs/Rx')
const bus = new rx.Subject()

function fire(event, payload) {
    bus.next({ event, payload })
}

function on(event) {
    return bus
        .filter(it => it.event === event)
        .map(it => it.payload)
}

module.exports = { fire, on }

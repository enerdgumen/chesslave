const rx = require('rxjs/Rx')

class EventBus {

    constructor(bus) {
        this.bus = bus
    }

    on(event) {
        return this.bus
            .filter(it => it.name === event)
            .map(it => it.payload)
    }
}

class LocalEventBus extends EventBus {

    fire(name, payload) {
        this.bus.next({name, payload})
    }
}

class RemoteEventBus extends EventBus {

    constructor(bus) {
        super(bus)
        this.socket = new WebSocket("ws://localhost:8080/events")
        this.socket.onerror = err => {
            console.log("socket error", err)
        }
        this.socket.onopen = e => {
            console.log("socket open")
        }
        this.socket.onmessage = e => {
            console.log("socket message", e.data)
            this.bus.next(JSON.parse(e.data))
        }
        this.socket.onclose = e => {
            console.log("socket closed")
        }
    }

    fire(name, payload) {
        this.socket.send(JSON.stringify({name, payload}))
    }
}

class GlobalEventBus extends EventBus  {

    constructor() {
        super(new rx.Subject())
        this.local = new LocalEventBus(this.bus)
        this.remote = new RemoteEventBus(this.bus)
    }

    fire(name, payload) {
        this.local.fire(name, payload)
        this.remote.fire(name, payload)
    }
}

module.exports = GlobalEventBus

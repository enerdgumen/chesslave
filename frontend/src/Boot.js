import 'sockjs-client'
import React from 'react'
import { Provider } from 'mobx-react'
import { EventBus } from 'vertx3-eventbus-rx-client'
import { filter } from 'rxjs/operators'
import App from './App'
import { registerSpeaker } from './Speaker'
import { registerLogger } from './Logger'

const events = EventBus.create('http://localhost:8080/events')
const isOpen = state => state === 1
events.state$.pipe(filter(isOpen)).subscribe(() => {
  registerLogger(events)
  registerSpeaker(events)
})

const Boot = () => (
  <Provider events={events}>
    <App />
  </Provider>
)

export default Boot

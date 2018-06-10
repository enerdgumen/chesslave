/* global speechSynthesis, SpeechSynthesisUtterance */

function speak(utterance) {
  const utter = new SpeechSynthesisUtterance()
  utter.text = utterance.text
  utter.lang = 'it-IT'
  utter.rate = 1.2
  speechSynthesis.speak(utter)
}

function registerSpeaker(events) {
  events.rxConsumer('speak')
        .subscribe(({ body }) => speak(body))
}

export { registerSpeaker, speak }

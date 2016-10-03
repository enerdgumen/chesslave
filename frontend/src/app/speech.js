/* global speechSynthesis, SpeechSynthesisUtterance */

function speak(utterance) {
    const utter = new SpeechSynthesisUtterance()
    utter.text = utterance.text
    utter.lang = 'it-IT'
    utter.rate = 1.2
    speechSynthesis.speak(utter)
}

module.exports = {speak}
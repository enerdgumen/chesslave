const events = require('./events')

const el = document.createElement('div')
el.innerHTML = '<button type="button" class="btn btn-success">Select board</button>'
el.querySelector('button').addEventListener('click',  () => events.fire('select-board'))

module.exports = el

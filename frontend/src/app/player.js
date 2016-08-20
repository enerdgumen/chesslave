const rx = require('rxjs/Rx')

const HUMAN = 'human'
const COMPUTER = 'computer'

function create(options) {
    const el = document.createElement('div')
    const { label, active } = options
    el.innerHTML = `
        ${label}
        <div class="btn-group btn-group-sm" data-toggle="buttons">
          <label class="btn btn-default ${active === HUMAN ? 'active' : ''}">
            <input type="radio" name="player" value="${HUMAN}">
            Human
          </label>
          <label class="btn btn-default ${active === COMPUTER ? 'active' : ''}"">
            <input type="radio" name="player" value="${COMPUTER}">
            Computer
          </label>
        </div>`
    const status = new rx.BehaviorSubject(active)
    el.querySelectorAll('label')
        .forEach(it => it.addEventListener('click', e => status.next(e.target.querySelector('input').value)))
    return {
        el,
        status
    }
}

module.exports = {create, HUMAN, COMPUTER}

const Observable = require('rxjs/Observable').Observable

function create() {
    const el = document.createElement('div')
    el.innerHTML = `
        <div class="btn-group">
          <button type="button" class="btn btn-primary">Start with...</button>
          <button type="button" class="btn btn-primary dropdown-toggle"
                  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <span class="caret"></span>
            <span class="sr-only">Toggle Dropdown</span>
          </button>
          <ul class="dropdown-menu">
            <li><a href="#" data-turn="white">White</a></li>
            <li><a href="#" data-turn="black">Black</a></li>
          </ul>
        </div>`
    const button = el.querySelector('button')
    const action = Observable.fromEvent(el.querySelectorAll('a'), 'click').map(e => e.target.dataset.turn)
    return {
        el,
        action,
        enable: () => button.removeAttribute('disabled'),
        disable: () => button.setAttribute('disabled', true)
    }
}

module.exports = {create}

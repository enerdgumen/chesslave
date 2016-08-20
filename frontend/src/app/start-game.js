function create(events) {
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
            <li><a href="#">White</a></li>
            <li><a href="#">Black</a></li>
          </ul>
        </div>`
    const button = el.querySelector('button')
    button.addEventListener('click',  () => events.fire('start-game'))
    return {
        el,
        enable: () => button.removeAttribute('disabled'),
        disable: () => button.setAttribute('disabled', true)
    }
}

module.exports = {create}

const $ = require('jquery')

function create(events) {
    const el = document.createElement('div')
    el.innerHTML = `
    <div class="panel panel-success">
        <div class="panel-heading">Board</div>
        <div class="panel-body">
            <button type="button" class="btn btn-success">Select...</button>
        </div>
    </div>
    `
    const button = el.querySelector('button')
    const enable = () => button.removeAttribute('disabled')
    const disable = () => button.setAttribute('disabled', true)
    button.addEventListener('click', () => {
        disable()
        $(button).tooltip('destroy')
        events.publish('select-board')
    })
    events.consumerRx('board-selection-failed').subscribe(() => {
        enable()
        $(button)
            .tooltip({ 
                title: 'Selection failed, please retry!',
                placement: 'right'
            })
            .tooltip('show')
    })
    return { el }
}

module.exports = {create}

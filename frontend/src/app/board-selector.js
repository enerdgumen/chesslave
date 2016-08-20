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
    button.addEventListener('click',  () => events.fire('select-board'))
    return {
        el,
        enable: () => button.removeAttribute('disabled'),
        disable: () => button.setAttribute('disabled', true)
    }
}

module.exports = {create}

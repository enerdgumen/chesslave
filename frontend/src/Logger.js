function registerLogger(events) {
  events.rxConsumer('log').subscribe((message) => {
    /* eslint-disable no-console */
    const { body, headers: { level } } = message
    console.log(`${level}: ${body}`)
    /* eslint-enable no-console */
  })
}

export { registerLogger }

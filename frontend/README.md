# Introduction

The application is based on [Electron](http://electron.atom.io/).
At the startup it starts the Java backend service and iteracts with it via web sockets.

## Development

1. Install the dependencies with `mvn install`
2. Start the backend service
3. Start the application with `./gulp serve`

We code using the [Atom](https://atom.io) editor with the following plugins:
 * [linter](https://atom.io/packages/linter)
 * [linter-eslint](https://atom.io/packages/linter-eslint)
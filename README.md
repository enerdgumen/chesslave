[![Build Status](https://travis-ci.org/chesslave/chesslave.png)](https://travis-ci.org/chesslave/chesslave)
[![Coverage](https://codecov.io/gh/chesslave/chesslave/branch/master/graph/badge.svg)](https://codecov.io/gh/chesslave/chesslave)

# Chesslave

[![Join the chat at https://gitter.im/chesslave/chesslave](https://badges.gitter.im/chesslave/chesslave.svg)](https://gitter.im/chesslave/chesslave?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

**Under active development - Contributions are welcome!**

_Chesslave_ is an application helping you to play chess in a better way, using a real chessboard.

Basically it gives you a slave that stands for you in front at the screen and
 * sees the opponent moves analyzing the board image,
 * pronounces them,
 * listens for your moves and
 * performs the move for you on the screen.

These actions are implemented emulating the four organs involved in the process, that is the eyes to see, the mouth to speak, the hears to listen and the hand to move.

### Build instructions

Compile Chesslave with
```bash
    mvn clean package [-P platform]
```
Option `-P` allows you to specify the build environment. `platform` may be one of:
- _linux_
- _macosx_
- _windows_

See `POM.xml` and [javacpp-presets](https://github.com/bytedeco/javacpp-presets) for more details.
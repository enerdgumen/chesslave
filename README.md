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
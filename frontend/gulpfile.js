const gulp = require('gulp')
const electron = require('electron-connect').server.create()

gulp.task('serve', function() {
    electron.start()
    // Currently the restarter works bad!
    // gulp.watch('src/main.js', electron.restart)
    gulp.watch(['src/app/**/*'], electron.reload)
})

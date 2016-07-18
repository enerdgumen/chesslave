const gulp = require('gulp')
const electron = require('electron-connect').server.create()

gulp.task('serve', function() {
    electron.start()
    gulp.watch('src/main.js', electron.restart)
    gulp.watch(['src/app.js', 'src/index.html'], electron.reload)
})

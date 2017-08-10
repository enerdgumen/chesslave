const gulp = require('gulp')
const electron = require('electron-connect').server.create()

gulp.task('less', function() {
    const less = require('gulp-less')
    return gulp.src('src/app/*.less')
        .pipe(less())
        .pipe(gulp.dest('src/app'))
})

gulp.task('serve', ['less'], function() {
    electron.start()
    // Currently the restarter works bad!
    // gulp.watch('src/main.js', electron.restart)
    gulp.watch('src/app/*.less', ['less'])
    gulp.watch('src/app/**/*', electron.reload)
})

gulp.task('default', ['less'])
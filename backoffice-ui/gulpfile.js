var pug = require('gulp-pug');
var gulp = require('gulp');
var plugins = require('gulp-load-plugins')(); // loads all packages with 'gulp-...'
var notify = require("gulp-notify");
var sass = require('gulp-sass');

var rename = require('gulp-rename');
var merge = require('merge-stream');
var replace = require('gulp-replace');
var touch = require('gulp-touch-cmd');
var clean = require('gulp-clean');
var concat = require('gulp-concat');
var del = require('del');
var buffer = require('vinyl-buffer');

var autoprefixer = require('gulp-autoprefixer');
var cleanCSS = require('gulp-clean-css');
var cssnano = require('gulp-cssnano');
var uncss = require('gulp-uncss');

var csso = require('gulp-csso');
var uglify = require('gulp-uglify');
var cheerio = require('gulp-cheerio');

var spritesmith = require('gulp.spritesmith');
var svgSprite = require('gulp-svg-sprite');


var imagemin = require('gulp-imagemin');
const imageminJpegRecompress = require('imagemin-jpeg-recompress');

const server = require('mock-json-server');
const app = server("data/data.json", 8888);

let libs = [
    'src-dev/libs/common/popper.js/dist/umd/popper.js',
    'src-dev/libs/common/bootstrap/js/dist/util.js',
    'src-dev/libs/common/bootstrap/js/dist/modal.js',
    'src-dev/libs/common/svg4everybody/lib/svg4everybody.js',
    'src-dev/libs/common/bootstrap/js/dist/tab.js',
    'src-dev/libs/common/bootstrap/js/dist/collapse.js',
    'src-dev/libs/common/bootstrap/js/dist/dropdown.js'
];

gulp.task('json-server', function() {
    app.start();
});

    
gulp.task('watch', function () {
    gulp.watch('src-dev/pug/routes/**/*.svelte')
        .on("change", function (file) {
            file = file.replace(/\\/g,'/');
            let dst = file.replace('src-dev/pug', 'src');
            dst = dst.substring(0, dst.lastIndexOf('/'));
            file = file.replace('[', '\\[');
            file = file.replace(']', '\\]');
            console.log('File:' + file);
            console.log('Dest:' + dst);
            gulp.src(file)
                .pipe(pug({ pretty: false }))
                .on("error", notify.onError("Error: <%= error.message %>"))
                .pipe(rename({  extname: '.svelte' }))
                .pipe(gulp.dest(dst));

        });

    gulp.watch('src-dev/pug/components/**/*.svelte')
        .on("change", function (file) {
            file = file.replace(/\\/g,'/');
            let dst = file.replace('src-dev/pug', 'src');
            dst = dst.substring(0, dst.lastIndexOf('/'));
            file = file.replace('[', '\\[');
            file = file.replace(']', '\\]');
            console.log('File:' + file);
            console.log('Dest:' + dst);

            gulp.src(file)
                .pipe(pug({ pretty: false }))
                .on("error", notify.onError("Error: <%= error.message %>"))
                .pipe(rename({  extname: '.svelte' }))
                .pipe(gulp.dest(dst));

        });
    
    gulp.watch('src-dev/pug/service/**/*.js', gulp.series(['pug-copy-js']));
    gulp.watch('src-dev/pug/routes/**/*.js', gulp.series(['pug-copy-js-from-route']));
    gulp.watch('src-dev/scss/**/*.scss', gulp.series(['sass','touch']));

    gulp.watch('src-dev/img/icons/sprite/svg/source/**/*.svg', gulp.series(['cheerio']));
    gulp.watch('src-dev/img/icons/sprite/svg/symbol/**/*.svg', gulp.series('svg-sprite:build'));

    gulp.watch('src-dev/scss/templates/*.scss', gulp.series('png-sprite:build'));
    gulp.watch('src-dev/img/**/*', gulp.series([ 'img', 'png-sprite:build', 'svg-sprite:build']));

});

gulp.task('touch', function () {
    return gulp.src('src/routes/index.svelte').pipe(touch());

});

gulp.task('pug-routes', function () {
    return gulp.src('src-dev/pug/routes/**/*.svelte')
        .pipe(pug({doctype: 'html', pretty: true, basedir: __dirname + 'pug/routes' }))
        .on("error", notify.onError("Error: <%= error.message %>"))
        .pipe(rename({  extname: '.svelte' }))
        .pipe(gulp.dest('src/routes'));
});

gulp.task('pug-copy-js', function () {
    return gulp.src('src-dev/pug/service/**')
        .pipe(gulp.dest('src/service'));
});

gulp.task('pug-copy-js-from-route', function () {
    return gulp.src('src-dev/pug/routes/**/*.js')
        .pipe(gulp.dest('src/routes'));
});

gulp.task('pug-components', function () {
    return gulp.src('src-dev/pug/components/**/*.svelte')
        .pipe(pug({ doctype: 'html', pretty: true, basedir:__dirname + 'pug/components' }))
        .on("error", notify.onError("Error: <%= error.message %>"))
        .pipe(rename({  extname: '.svelte' }))
        .pipe(gulp.dest('src/components'));
});

gulp.task('img', function () {
    return gulp.src(['src-dev/img/**/*', '!src-dev/img/icons/sprite/**/*'])
        .pipe(gulp.dest('static/static/img'))
});


gulp.task('img:build', function () {
    return gulp.src(['src-dev/img/**/*', '!src-dev//img/icons/sprite/**/*'])
        .pipe(imagemin([
            imageminJpegRecompress({progressive: true,quality: 87}),
            imagemin.svgo(),
        ]))
        .pipe(gulp.dest('static/static/img'))
});

gulp.task('svg-sprite:build', function () {
    return gulp.src(['src-dev/img/icons/sprite/svg/symbol/flat/*.svg',
        'src-dev/img/icons/sprite/svg/symbol/multi-layered/*.svg'])
        .pipe(svgSprite({
            shape: {
                dimension: {
                    maxWidth: 24,
                    maxHeight: 24
                }
            },
            mode: {
                symbol: {
                    dest: ".",
                    sprite: "sprite.svg",
                    example: true
                }
            }
        }))
        .pipe(gulp.dest('static/static/img/icons/sprite/svg/'))
});

gulp.task('png-sprite:build', function () {
    var spriteData = gulp.src('src-dev/img/icons/sprite/png/**/*.png')
        .pipe(spritesmith({
            imgName: 'sprite.png',
            cssName: '_png-sprite.scss',
            cssTemplate: 'scss/templates/png-template.scss'
        }));
    var imgStream = spriteData.img
        .pipe(buffer())
        .pipe(gulp.dest('static/static/img/icons/sprite/png/'));
    var cssStream = spriteData.css
        .pipe(gulp.dest('scss/modules/'));
    return merge(imgStream, cssStream);
});


gulp.task('clean-static', function () {
    return gulp.src('static/static/', { read: false , allowEmpty: true})
        .pipe(clean({force: true}))
});

gulp.task('clean-svelte-routes', function () {
    return gulp.src('src/routes/', { read: false , allowEmpty: true})
        .pipe(clean({force: true}))
});

gulp.task('clean-svelte-components', function () {
    return gulp.src('src/components/', { read: false , allowEmpty: true})
        .pipe(clean({force: true}))
});


gulp.task('js-libs-copy', function () {
    return gulp.src(['src-dev/libs/components/**/*'])
        .pipe(gulp.dest('static/static/libs'))
});

gulp.task('js-libs-compose-and-copy', () => {
    return gulp.src(libs)
        .pipe(uglify())
        .pipe(concat('libs.min.js'))
        .pipe(gulp.dest('static/static/js/'));
});

gulp.task('sass', function () {
    return gulp.src('src-dev/scss/main.scss')
        .pipe(sass())
        .on("error", notify.onError("Error: <%= error.message %>"))
        .pipe(rename({ basename: "styles", suffix: '.min', prefix: '' }))
        .pipe(gulp.dest('static/static'));
});
gulp.task('sass:build', function () {
    return gulp.src('src-dev/scss/main.scss')
        .pipe(sass())
        .on("error", notify.onError("Error: <%= error.message %>"))
        .pipe(uncss({
        	html: ["src/routes/**/*.svelte", "src/components/**/*.svelte"],
        	ignore: [
        	    ".main-article-block *",
        		".modal.show .modal-dialog",
        		/\.modal-open/,
        		/\.modal-backdrop/,
        		".dropdown-menu.show",
        		".collapsing",
                ".collapse.show",
                '.dropdown-menu[x-placement^="top"]',
				'.dropdown-menu[x-placement^="right"]',
				'.dropdown-menu[x-placement^="bottom"]',
				'.dropdown-menu[x-placement^="left"]',
                '.bgcy','.bgcb','.bgcr','.bgcg','.bgclb','.bgcp','.bgcpi',
               '.bgcb5-hf','.df', '.aic' ,'.py16','.px24','.tdn-h', '.bb','.bl','.blw4',
                '.bgcb5','.fw7','.cr','.blcr','.bbctt',
                '.ci','.bbcb4','.blctt', '.cb', '.cr'
        	]
        }))
        .pipe(rename({ basename: "styles", suffix: '.min', prefix: '' }))
        .pipe(autoprefixer())
        .pipe(cleanCSS())
        .pipe(csso())
        .pipe(cssnano())
        .pipe(gulp.dest('static/static'));
});

gulp.task('cheerio', function () {
    return gulp.src('src-dev/img/icons/sprite/svg/source/**/*.svg')
        .pipe(cheerio({
            run: function ($) {
                $('[fill]').removeAttr('fill');
                $('[stroke]').removeAttr('stroke');
                $('[style]').removeAttr('style');
                $('[class]').removeAttr('class');
            },
            parserOptions: { xmlMode: true }
        }))
        .pipe(replace('&gt;', '>'))
        .pipe(gulp.dest('src-dev/img/icons/sprite/svg/symbol/flat'))
});

gulp.task('npmrundev', plugins.shell.task(['npm run dev']));
gulp.task('npmbuild', plugins.shell.task(['npm run build']));
gulp.task('npmstart', plugins.shell.task(['npm start']));


gulp.task('pug',
   gulp.parallel('pug-routes', 'pug-components' )
);


gulp.task('default',
    gulp.series(['clean-static', 'clean-svelte-routes', 'clean-svelte-components', 'js-libs-copy', 'js-libs-compose-and-copy','img','cheerio', 'svg-sprite:build',  'png-sprite:build', 'pug-copy-js','pug-copy-js-from-route', 'pug', 'sass' , gulp.parallel('watch', 'npmrundev', 'json-server')
    ]));

gulp.task('prod-build',
    gulp.series(['clean-static', 'clean-svelte-routes', 'clean-svelte-components', 'js-libs-copy', 'js-libs-compose-and-copy','img:build', 'cheerio', 'svg-sprite:build', 'png-sprite:build',  'pug-copy-js', 'pug-copy-js-from-route', 'pug', 'sass:build', 'npmbuild']));

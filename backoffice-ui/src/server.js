// https://gist.github.com/tcrowe/c9a11fca78447ba78c5de2e7b9ba10d2
import sirv from 'sirv';
import polka from 'polka';
import compression from 'compression';
import * as sapper from '@sapper/server';
import { apiUrl , basePath} from "./service/config";
import { buildUrl } from "./service/UrlUtils";

const { PORT, NODE_ENV } = process.env;
const dev = true;

const proxy = require("express-http-proxy");
const apiProxy = proxy(apiUrl, {limit: "15mb"});


// assign server variable
const server = polka();


// ⚠️ hack to make the proxy work with polka
// express-http-proxy is expecting these methods
server.use(function(req, res, next) {
    // console.log(req.url);
    try {
        if (req.query ) {
            let queryString = buildQueryString(req.query);
            if (queryString != '') {
                req.url = req.path + "?" + queryString;
            }
        }
    } catch (e) {
    }
    // console.log(req.url);
    res.status = code => (res.statusCode = code);
    res.set = res.setHeader.bind(res);
    next();
});

function buildQueryString(obj) {
    var str = "";
    for (var key in obj) {
        if (str != "") {
            str += "&";
        }
        str += encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]);
    }
    return str;
}


// proxy to api
server.use(buildUrl('api'), apiProxy);

// do normal sapper server stuff
server
    
    .use(
        basePath,
        compression({ threshold: 0 }),
        sirv("static", { dev }),
        // async (req, res, next) => {
        //     next();
        // },
        // sapper.middleware({
        //     session: (req, res) => ({
        //         user: req.user 
        //     }) 
        // })
        sapper.middleware()
    )
    .listen(PORT, err => {
        if (err) console.log("error", err);
    });


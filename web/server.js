const express = require('express');
const proxy = require('http-proxy-middleware');

const app = express();
app.use('/', express.static(`${__dirname}/dist/`));

// api转发
app.use('/api', proxy({
    target: 'http://mqtt.bsight.com:8010',
    changeOrigin: true,
    pathRewrite: {
        '^/api': ''
    }
}));

app.get('/*', (req, res) => {
    res.sendFile(`${__dirname}/dist/index.html`);
})

app.listen('80', function () { })
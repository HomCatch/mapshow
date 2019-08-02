const express = require('express');
const proxy = require('http-proxy-middleware');

const app = express();
app.use('/', express.static(`${__dirname}/dist/`));

// api转发
app.use('/api', proxy({
    target: 'http://localhost:8686',
    changeOrigin: true,
    pathRewrite: {
        '^/api': ''
    }
}));

app.get('/*', (req, res) => {
    res.sendFile(`${__dirname}/dist/index.html`);
})

app.listen('8585', function () { })
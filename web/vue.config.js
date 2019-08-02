// 接口地址baseUrl
const configList = require('./config')
const proxy = {}
// 组装proxy
configList.forEach(item => {
    proxy[item.devApi] = {
        target: item.target,
        ws: true,
        changeOrigin: true,
        pathRewrite: {
            [`^${item.devApi}`]: ''
        }
    }
})
module.exports = {
    baseUrl: process.env.NODE_ENV === 'production' ? '/' : '/', // 部署应用程序的路径，如果为根路径就设置为'/'否则设置为对应目录，设置为空''时表示使用相对路径
    devServer: {
        proxy
    },
    productionSourceMap: false, // 不生成sourceMap文件
    configureWebpack: {
        externals: {
            "BMap": "BMap",
            "BMapLib": "BMapLib",
            "returnCitySN": "returnCitySN",
            //     "$": "$",
        },
    },
    transpileDependencies: [
        /\/node_modules\/vue-echarts\//,
        /\/node_modules\/echarts\//,
        // /\/node_modules\/resize-detector\//
    ],
    chainWebpack: config => {
        config
            .plugin('html')
            .tap(args => {
                args[0].chunksSortMode = "none"
                return args
            })
    }
}
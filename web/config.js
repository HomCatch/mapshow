module.exports = [
  {
    devApi: "/api",
    // target: "http://10.0.1.167:9193" // 开发环境代理地址gmq
    // target: 'http://10.0.1.213:9193'    // 开发环境代理地址wzq
    target: "http://iotsvr.he-live.com:9193" // 开发环境代理地址
  },
  {
    devApi: "/api1",
    target: "target1"
  }
];
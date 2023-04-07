const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    createProxyMiddleware('/api', {
      target: "https://j8c206.p.ssafy.io/api/v1", //접속하려는 서버의 루트 URL
      changeOrigin: true,
    })
  );
};
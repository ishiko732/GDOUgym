const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  devServer: {
      port: 8080,
      proxy: {
          '/api': {
              target: "http://127.0.0.1:8080",
              pathRewrite: {
                  '^/api': ''
              },
          }
      }
  },
}

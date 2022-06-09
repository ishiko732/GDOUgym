const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  devServer: {
      port: 8080,
      proxy: {
          '/api': {
              target: "http://1.15.187.187:8080",
              pathRewrite: {
                  '^/api': ''
              },
          }
      }
  },
}

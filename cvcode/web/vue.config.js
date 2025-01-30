const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
  	port: 8080,
  	proxy: {
  		"/api": {
  			target: "http://127.0.0.1:8081",
  			pathRewrite: {
  				'^/api': ''
  			},
  			ws: true,
  			changeOrigin: true
  		},
  	}
  }
})

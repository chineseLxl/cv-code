import {
	createApp
} from 'vue'
import App from './App.vue'
import {
	router
} from '@/js/router.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(router).use(ElementPlus).use(ElementPlusIconsVue).mount('#app')

// 重定向自定义
// router.beforeEach(async (to, from, next) => {
// 	if (localStorage.getItem('username') == null && to.path != '/login') {
// 		router.replace("/login")
// 	}
// 	next()
// })

if (localStorage.getItem("followList") == null || localStorage.getItem("followList") == undefined || localStorage.getItem("followList") == "") {
	localStorage.setItem("followList", JSON.stringify([]))
}


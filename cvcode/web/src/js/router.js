import { createWebHistory, createRouter } from 'vue-router'

import IndexView from '@/views/IndexView.vue'
import Login from '@/views/LoginView.vue'
import ArticleCard from '@/components/ArticleCard.vue'
import CardInfo from '@/views/CardInfo.vue'
import Editor from '@/views/Editor'
import Test from '@/views/Test'
const routes = [
	{ path: '/', name: '首页', component: IndexView },
	{ path: '/login', name: '登录', component: Login},
	{ path: '/test', name: '测试', component: ArticleCard},
	{ path: '/cardInfo', name: '博客详细页', component: CardInfo},
	{ path: '/test', name: '测试', component: Test},
	{ path: '/editor', name: '发布文章', component: Editor},
]

var router = createRouter({
	history: createWebHistory(),
	routes,
});

export {
	router
}

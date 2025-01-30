<template>
	<el-menu class="menu-top" :default-active="activeIndex" mode="horizontal" :ellipsis="false" @select="handleSelect">
		<el-menu-item @click="toIndex()">
			logo
		</el-menu-item>
		<el-menu-item @click="toIndex()">
			博客
		</el-menu-item>
		<el-menu-item>
			<el-popover placement="bottom" :width="300" trigger="hover" class="popover-card">
				<template #reference>
					<span>用户名:{{ username }}</span>
				</template>
				<ul class="popover-ul">
					<li>个人中心</li>
					<li @click="exitLogin()">退出</li>
				</ul>
			</el-popover>
		</el-menu-item>
		<el-menu-item>
			<span @click.stop="toXiaoxi()">消息</span>
		</el-menu-item>
	</el-menu>

</template>

<script>
	import {
		ref
	} from 'vue'
	import {
		router
	} from '@/js/router.js'
	import search from "@/components/Search.vue"
	import LoginCard from "@/components/LoginCard.vue"
	import { http } from "@/js/http.js"
	export default {
		props: {
			menuForm: {

			}
		},
		data() {
			return {
				activeIndex: ref(''),
				activeIndex2: ref('1'),
				username: "",
			}
		},
		components: {
			search,
			LoginCard
		},
		methods: {
			handleSelect(key, keyPath) {
				console.log(key, keyPath)
			},
			toIndex() {
				router.replace("/")
			},
			exitLogin() {
				localStorage.clear()
				location.replace("/login")
			},
			isLogin() {
				if (localStorage.getItem("token") != null) {
					http.get("/api/api/getNickName",{
						
					}).then(res => {
						this.username = res.data.data.nickname
					}).catch(e => {
						console.log(e);
					})
				} else {
					location.replace("/login")
				}
			},
			toLogin() {
				if (this.loginStatus) {
					return false;
				} else {
					console.log("未登录")
					let routeData = router.resolve(({
						path: '/login', // path 要跳转的路由地址
					}))
					window.open(routeData.href, '_blank')
					return true;
				}
			},
			toXiaoxi() {
				if (this.toLogin()) {
					return
				}
				console.log("test");
			},
			toLishi() {
				if (this.toLogin()) {
					return
				}
			},
			toFabu() {
				if (this.toLogin()) {
					return
				}
				location.assign("/editor")
				console.log("发布")
			},
		},
		mounted() {
			this.isLogin();
		},
	}
</script>

<style scoped>
	body {
		margin: 0%;
	}

	.el-menu--horizontal>.el-menu-item:nth-child(2) {
		margin-right: 80%;
	}

	.el-menu-item {
		--el-menu-hover-bg-color: "";
		--el-menu-hover-text-color: "";
	}

	.popover-card {}

	.popover-ul {
		list-style-type: none;

		li {
			margin-top: 16px;
			cursor: pointer;
		}
	}

	.menu-top {
		position: sticky;
		top: 0;
		z-index: 1;
	}
</style>
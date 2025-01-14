<template>
	<el-menu class="menu-top" :default-active="activeIndex" mode="horizontal" :ellipsis="false" @select="handleSelect">
		<el-menu-item @click="toIndex()">
			logo
		</el-menu-item>
		<el-menu-item @click="toIndex()">
			博客
		</el-menu-item>
		<el-menu-item @click="toIndex()">
			社区
		</el-menu-item>
		<el-menu-item @click="">
			下载
		</el-menu-item>
		<search></search>
		<el-menu-item>
			<el-button v-if="!loginStatus" type="info" @click.stop="loginCardStatus = true" circle>
				登录
			</el-button>
			<span v-if="loginStatus">
				<el-popover placement="bottom" :width="300" trigger="hover" class="popover-card">
					<template #reference>
						<span>用户名:{{ username }}</span>
					</template>
					<ul class="popover-ul">
						<li>个人中心</li>
						<li @click="exitLogin()">退出</li>
					</ul>
				</el-popover>
			</span>

		</el-menu-item>
		<el-menu-item>
			<span @click.stop="toXiaoxi()">消息</span>
		</el-menu-item>
		<el-menu-item>
			<span @click.stop="toLishi()">历史</span>
		</el-menu-item>
		<el-menu-item>
			<el-button type="primary" @click.stop="toFabu()" round>
				发布
			</el-button>
		</el-menu-item>
		<LoginCard v-model:status="loginCardStatus"></LoginCard>
		<!-- <el-menu-item index="1" @click="outLogin()">退出登录</el-menu-item> -->
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
	export default {
		props: {
			menuForm: {
				type: Object,
				required: true,
			}
		},
		data() {
			return {
				activeIndex: ref(''),
				activeIndex2: ref('1'),
				username: "",
				loginStatus: false,
				loginCardStatus: false,
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
				location.reload()
			},
			isLogin() {
				if (localStorage.getItem("username") != null) {
					this.username = localStorage.getItem("username")
					this.loginStatus = true;
				} else {
					this.loginStatus = false;
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

<style>
	body {
		margin: 0%;
	}

	.el-menu--horizontal>.el-menu-item:nth-child(4) {
		margin-right: auto;
	}

	.el-menu-item {
		--el-menu-hover-bg-color: "";
		--el-menu-hover-text-color: "";
	}
	
	.popover-card {
		
	}
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
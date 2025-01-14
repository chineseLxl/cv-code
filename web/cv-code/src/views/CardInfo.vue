<template>
	<MenuVue class="menu-top" :menuForm="menuForm"></MenuVue>
	<el-row class="main" :gutter="20" style="margin: 0px;">
		<el-col :span="4">
			<el-card style="text-align: center;">
				<p>{{ data.name }}</p>
				<p>点赞：{{ data.up }}</p>
				<el-button type="primary" round @click="followBtn" v-if="!follow">关注</el-button>
				<el-button round @click="cancelFollow" v-if="follow">取消关注</el-button>
			</el-card>
		</el-col>
		<el-col :span="16" style="justify-content: center;">
			<el-card>
				<div>{{ data.title }}</div>
				<div>{{ data.info }}</div>
			</el-card>
		</el-col>
		<el-col :span="4">
			<el-card>
				<p>目录</p>
				<p v-for="item,key in data.menu" :key="key" class="text item">{{ item }}</p>
			</el-card>
		</el-col>
	</el-row>
</template>

<script>
	import {
		router
	} from '@/js/router.js'
	import MenuVue from "@/components/Menu.vue"
	import {
		ElMessage
	} from 'element-plus'
	export default {
		data() {
			return {
				menuForm: {
					username: sessionStorage.getItem("username")
				},
				data: JSON.parse(sessionStorage.getItem("dataList"))[this.$route.query.id - 1],
				follow: true,
			}
		},
		components: {
			MenuVue,
		},
		methods: {
			isFollow() {
				let followData = JSON.parse(localStorage.getItem("followList"))
				for (let i = 0; i < followData.length; i++) {
					if (followData[i].name == this.data.name) {
						return true;
					}
				}
				return false;
			},
			followBtn() {
				let followData = JSON.parse(localStorage.getItem("followList"))
				followData.push({
					name: this.data.name
				})
				localStorage.setItem("followList", JSON.stringify(followData))
				ElMessage({
					message: '您已成功关注此博主',
					type: 'success',
				})
				this.follow = this.isFollow();
			},
			cancelFollow() {
				let followData = JSON.parse(localStorage.getItem("followList"))
				for (let i = 0; i < followData.length; i++) {
					if (followData[i].name == this.data.name) {
						followData.splice(i,1)
					}
				}
				ElMessage({
					message: '您取消关注关注此博主',
					type: 'info',
				})
				localStorage.setItem("followList", JSON.stringify(followData))
				this.follow = this.isFollow();
			}
		},
		mounted() {
			this.follow = this.isFollow();
			console.log(this.data)
		}
	}
</script>

<style>
	body {
		margin: 0px;
		background-color: #f8f8f8;
	}

	.main {
		padding-top: 1rem;
	}

	.menu-top {
		position: sticky;
		top: 0;
		z-index: 1;
	}
</style>
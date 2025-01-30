<template>
	<MenuVue class="menu-top"></MenuVue>
	<el-row class="main" :gutter="20" style="margin: 0px;">
		<el-col :span="4">
			<el-card style="text-align: center;">
				<p>{{ data.nickName }}</p>
				<el-row :gutter="20" class="user-info-card">
					<el-col :span="8">
						<p>1</p>
						<div>文章</div>
					</el-col>
					<el-col :span="8">
						<p>1</p>
						<div>点赞</div>
					</el-col>
					<el-col :span="8">
						<p>1</p>
						<div>粉丝</div>
					</el-col>
				</el-row>
				<el-button type="primary" round @click="followBtn" v-if="!follow">关注</el-button>
				<el-button round @click="cancelFollow" v-if="follow">取消关注</el-button>
				<el-button round @click="followBtn" v-if="!follow">私信</el-button>
			</el-card>
		</el-col>
		<el-col :span="16" style="justify-content: center;">
			<el-card>
				<h2 style="text-align: center;">{{ data.title }}</h2>
				<div id="articleInfoDiv"></div>
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
		http
	} from "@/js/http.js"
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
				// data: JSON.parse(sessionStorage.getItem("dataList"))[this.$route.query.id - 1],
				data: [],
				follow: true,
			}
		},
		components: {
			MenuVue,
		},
		methods: {
			isFollow() {
				// let followData = JSON.parse(localStorage.getItem("followList"))
				// for (let i = 0; i < followData.length; i++) {
				// 	if (followData[i].name == this.data.name) {
				// 		return true;
				// 	}
				// }
				// return false;
			},
			followBtn() {
				// let followData = JSON.parse(localStorage.getItem("followList"))
				// followData.push({
				// 	name: this.data.name
				// })
				// localStorage.setItem("followList", JSON.stringify(followData))
				// ElMessage({
				// 	message: '您已成功关注此博主',
				// 	type: 'success',
				// })
				// this.follow = this.isFollow();
			},
			cancelFollow() {
				// let followData = JSON.parse(localStorage.getItem("followList"))
				// for (let i = 0; i < followData.length; i++) {
				// 	if (followData[i].name == this.data.name) {
				// 		followData.splice(i,1)
				// 	}
				// }
				// ElMessage({
				// 	message: '您取消关注关注此博主',
				// 	type: 'info',
				// })
				// localStorage.setItem("followList", JSON.stringify(followData))
				// this.follow = this.isFollow();
			}
		},
		mounted() {
			console.log(this.$route.query.id);
			this.follow = this.isFollow();
			http.post("/api/api/getArticleInfo", {
				"id": this.$route.query.id,
			}).then(res => {
				this.data = res.data.data
				console.log(res.data.data.main);
				document.getElementById("articleInfoDiv").innerHTML = res.data.data.main
			}).catch(e => {
				console.log(e);
			})
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
		position: sticky !important;
		top: 0;
		z-index: 1;
	}
	
	.user-info-card {
		margin-bottom: 2rem;
	}
</style>
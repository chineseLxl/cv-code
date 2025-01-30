<template v-loading="loading">
	<MenuVue class="menu-top"></MenuVue>
	<div class="main">
		<el-row :gutter="5" justify="center" class="img-main">
			<el-col :span="14">
				<el-carousel :interval="5000" class="my-carousel">
					<el-carousel-item v-for="item in 4" :key="item">
						<h3 text="2xl" justify="center">假装这里有张图片</h3>
					</el-carousel-item>
				</el-carousel>
			</el-col>
			<el-col :span="7">
				<div class="hotList">
					<el-card>
						<p>当前热搜</p>
						<div class="hotList-item" v-for="(item,key) in cardList">
							<div v-if="setHotList(key)">
								<a @click="toCardInfo($event)" :id="item.id">{{ item.title }}</a>
								<p>{{ item.info }}</p>
							</div>
						</div>
					</el-card>
				</div>
			</el-col>
		</el-row>
		<el-row justify="center">
			<el-col :span="21">
				<el-tabs class="my-tabs" v-model="activeName" @tab-click="handleClick">
					<el-tab-pane label="推荐" name="tuijian">
						<div>
							<ArticleCardVue :id="key" :cardForm="item" v-for="(item,key) in cardList"></ArticleCardVue>
						</div>
					</el-tab-pane>
					<el-tab-pane label="关注" name="guanzhu">
						<div v-if="followData.length != 0">
							<ArticleCardVue :cardForm="item" v-for="(item,key) in followData"></ArticleCardVue>
						</div>
						<div v-if="followData.length == 0">
							<span>暂无关注</span>
						</div>
					</el-tab-pane>
				</el-tabs>
			</el-col>
			<div id="returnTop" @click="returnTop()">
				<ArrowUpBold style="width: 1.3em; height: 1.3em; margin-right: 8px" />
				<p>返回顶部</p>
			</div>
		</el-row>
	</div>

</template>
<script>
	import {
		ref
	} from 'vue'
	import {
		router
	} from '@/js/router.js'
	import {
		http
	} from "@/js/http.js"
	import ArticleCardVue from "@/components/ArticleCard.vue"
	import MenuVue from "@/components/Menu.vue"

	export default {
		data() {
			return {
				followList: [],
				followData: [],
				activeName: 'tuijian',
				cardList: [],
				mid: 0,
				postStatus: true,
			}
		},
		components: {
			ArticleCardVue,
			MenuVue,
		},
		methods: {
			handleSelect(key, keyPath) {
				console.log(key, keyPath)
			},
			handleClick(tab, event) {
				console.log(tab, event);
			},
			setHotList(key) {
				if (key < 5) {
					return true;
				} else {
					return false;
				}
			},
			toCardInfo(event) {
				let routeData = router.resolve(({
					path: '/cardInfo', // path 要跳转的路由地址
					// query 要传递的参数
					query: {
						id: event.target.getAttribute("id"),
					}
				}))
				window.open(routeData.href, '_blank')
			},
			// 页面滑动到达底部触发事件
			handleScroll(event) {
				if (this.activeName != "tuijian") {
					return
				}
				const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
				const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
				const clientHeight = document.documentElement.clientHeight || window.innerHeight
				if (scrollTop + clientHeight >= scrollHeight - 5) {
					if (this.postStatus) {
						this.postStatus = false
						setTimeout(() => {
							http.get("/api/api/getArticlePage", {
								params: {
									page: 1,
									mid: this.mid,
								}
							}).then((res) => {
								let resList = res.data.data.articleList;
								console.log(res);
								for (let i = 0; i < resList.length; i++) {
									this.cardList.push(resList[i])
								}
								this.mid = res.data.data.mid
								this.postStatus = true
							})
						}, 100)
					}
				}
			},
			// 返回顶部
			returnTop() {
				document.body.scrollTop = 0;
				document.documentElement.scrollTop = 0;
			},
			// 获取关注的博主发帖数据
			setFollowData() {
				this.followData = [];
				this.followList = JSON.parse(localStorage.getItem("followList"));
				for (let i = 0; i < this.followList.length; i++) {
					for (let j = 0; j < this.cardList.length; j++) {
						if (this.followList[i].name == this.cardList[j].name) {
							this.followData.push(this.cardList[j]);
						}
					}
				}
			}
		},
		mounted() {
			window.addEventListener('scroll', this.handleScroll)
			// sessionStorage.setItem("dataList", JSON.stringify(this.cardList))
			// this.setFollowData()
			this.handleScroll()
		},
		watch: {
			// "cardList.length"(New, Old) {
			// 	console.log(this.cardList[New - 1].name)
			// 	for (let i = 0; i < this.followList.length; i++) {
			// 		if (this.followList[i].name == this.cardList[New - 1].name) {
			// 			this.followData.push(this.cardList[New - 1])
			// 		}
			// 	}
			// },
			// activeName(New, Old) {
			// 	this.setFollowData()
			// }
		}
	}
</script>

<style scoped>
	body {
		margin: 0%;

	}

	.my-tabs {
		--el-index-normal: -1;
	}

	.el-menu--horizontal>.el-menu-item:nth-child(1) {
		margin-right: auto;
	}

	.el-carousel__item h3 {
		color: #475669;
		opacity: 0.75;
		line-height: 200px;
		margin: 0;
		text-align: center;
	}

	.el-carousel__item:nth-child(2n) {
		background-color: #99a9bf;
	}

	.el-carousel__item:nth-child(2n + 1) {
		background-color: #d3dce6;
	}

	.my-carousel {
		--el-index-normal: -1;
	}

	.img-main {
		margin-top: 16px;
		margin-bottom: 2rem;
		margin-left: 0px !important;
		margin-right: 0px !important;
	}

	#returnTop {
		position: fixed;
		right: 20px;
		text-align: center;
		font-size: 0.8rem;
		cursor: pointer;
	}

	.hotList {
		padding-left: 2rem;
		max-height: 250px;
	}

	.hotList-item {
		max-height: 40px;
		color: #a8afb7;
		font-size: 10px;

		a {
			font-size: 12px;
			cursor: pointer;
			color: black;
		}

		a:hover {
			text-decoration: underline;
		}
	}

	.menu-top {
		position: sticky;
		top: 0;
		z-index: 1;
	}
</style>
<template>
	<div id="tagCard">
		<el-card>
			<h3 class="tag-title">标签</h3>
			<el-input v-model="search"></el-input>
			<div style="max-height: 300px;min-width: 150px;max-width: 150px;overflow-y: auto;float: left;">
				<ul>
					<li v-for="(item,key) in menuList" :key="item.id" :range="key" @click="clickMenuFun($event)">
						{{ item.name }}
					</li>
				</ul>
			</div>
			<div class="menu-tags">
				<el-tag v-if="nowTagList.indexOf(item) == -1" v-for="(item,key) in tagList" :key="item.id"
					class="my-tag" @click="addArticleTag($event,key)">
					<p>{{ item.name }}</p>
				</el-tag>
			</div>
		</el-card>
	</div>

</template>

<script>
	import {
		ref,
		toRaw
	} from 'vue'
	import {
		router
	} from '@/js/router.js'
	import {
		http
	} from "@/js/http.js"
	export default {
		props: {
			nowTagList: {
				default: [],
				type: Array,
			}
		},
		data() {
			return {
				menuList: [],
				tagList: [],
				item: "",
				search: "",
			}
		},
		components: {

		},
		methods: {
			// 点击标签目录触发事件
			clickMenuFun(e) {
				// 切换标签列表
				this.tagList = this.menuList[e.target.getAttribute("range")].tags
			},
			// 添加文章标签
			addArticleTag(e,index) {
				if (this.nowTagList.indexOf(e.target.innerText) == -1) {
					this.nowTagList.push(e.target.innerText)
					// document.querySelectorAll(".my-tag")[index].classList.add("is-change-tage")
				}
			},
		},
		mounted() {
			http.post("/api/api/getArticleMenu", {

			}).then(res => {
				console.log(res.data.data);
				this.menuList = res.data.data;
				this.tagList = this.menuList[0].tags;
			})
		},
		watch: {
			nowTagList(newVal,oldVal) {
				console.log(newVal);
			}
		}
	}
</script>

<style>
	#tagCard {
		position: absolute;
		bottom: 40px;
		top: auto;

		ul {
			list-style-type: none;
			margin: 0px;
			padding: 0px;

			li {
				cursor: pointer;
			}
		}
	}

	.tag-title {
		text-align: center;
		border-bottom: #c4c4c4 solid 1px;
	}

	.menu-tags {
		max-height: 300px;
		min-height: 300px;
		min-width: 400px;
		max-width: 400px;
		overflow-y: auto;
		float: right;
	}

	.my-tag {
		cursor: pointer;
		margin-left: 10px;
	}
	
	.is-change-tage {
		background-color: #409EFF !important;
	}
</style>
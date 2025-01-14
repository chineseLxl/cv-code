<template>
	<el-input v-model="search" @keyup.enter="clickSearch()" class="input-search" placeholder="搜索文章">
		<template #append>
			<el-button class="search-btn" @click="clickSearch">搜索</el-button>
		</template>
	</el-input>
</template>

<script>
import {
		ref
	} from 'vue'
	import {
		router
	} from '@/js/router.js'
	export default {
		props: {
			
		},
		data() {
			return {
				search: "",
			}
		},
		methods: {
			clickSearch() {
				let routeData = router.resolve(({
					path: '/productSheet', // path 要跳转的路由地址
					// query 要传递的参数
					query: {
						query: this.search,
					}
				}))
				window.open(routeData.href, '_blank')
			}
		},
		mounted() {

		},
	}
</script>

<style scoped>
	body {
		margin: 0%;
	}
	.input-search {
		max-width: 700px;
		min-width: 700px;
		position: fixed;
		margin-top: 1rem;
		right: 30%;
		top: -10px;
		height: 3rem;
		--el-color-primary-light-9: rgb(242 214 72 / 12%);
		--el-menu-hover-bg-color: rgb(242 214 72 / 12%);
		--el-fill-color-light: #409eff;
		--el-color-info: white;
		--el-border-radius-base: 25px;
	}
	.my-menu {
		--el-menu-active-color: #ff9046;
		--el-color-primary: #ff9046;
		--el-color-primary-light-9: rgb(242 214 72 / 12%);
		--el-menu-hover-bg-color: rgb(242 214 72 / 12%);
		--el-menu-hover-text-color: #ff9046;
		--el-menu-horizontal-height: 30px;
	}
	:deep .el-input-group__append {
		--el-input-border-color: #409eff;
		width: 60px;
		box-shadow: "";
	}
	

</style>
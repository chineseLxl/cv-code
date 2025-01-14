<template>
	<EditorMenu></EditorMenu>
	<div class="editor-menu">
		<div class="editor-menu-item" id="fontColor" @click.stop="displayItemCard('fontColor')">
			<el-icon>
				<EditPen />
			</el-icon>
			<span class="editor-menu-btn">颜色</span>
			<div @click.stop="" class="item-card">
				<div class="color-item">
					<el-color-picker @click.stop="itemHideStatus = true" :focus="itemHideStatus = false"
						v-model="textColor"></el-color-picker>
				</div>
				<span @click="changeTextColor" class="color-item" v-for="(item,key) in textColorList" :value=item
					:style="'background-color:'+item+';'"></span>
			</div>
		</div>
	</div>
	<el-row :gutter="20" class="editor-div">
		<el-col :span="5">
			<el-card style="max-width: 480px">
				<template #header>
					<div class="card-header">
						<span>Card name</span>
					</div>
				</template>
				<p v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</p>
				<template #footer>Footer content</template>
			</el-card>
		</el-col>
		<el-col :span="14">
			<div id="articles">
				<div class="editor-title">
					<input type="text" v-model="title" placeholder="请输入文章标题(5~100个字)" class="input-title"
						maxlength="100" @input="titleLength=title.length" />
					<span class="title-count">{{ titleLength }} / 100</span>
				</div>
				<div class="editor-main">
					<div ref="inputDiv" contenteditable="true" id="input-main" class="placeholder" @focus="inputFocus" @blur="inputBlur"
						@keydown="inputEditor" @compositionstart="inputChineseStart" data-placeholder="在此开始编辑你的文章吧"
						@compositionend.stop="inputChinese">
					</div>
				</div>
			</div>
		</el-col>
		<el-col :span="5">
			<div class="grid-content ep-bg-purple" />
		</el-col>
	</el-row>
	<div class="editor-bottom">
		<div class="editor-bottom-main">
			<el-button round>保存草稿</el-button>
			<el-button type="primary" round @click="publishBtn()">发布博客</el-button>
		</div>
	</div>
</template>

<script>
	import {
		router
	} from '@/js/router.js'
	import {
		ref,
		toRaw
	} from 'vue';
	import {
		http
	} from "@/js/http.js"
	import EditorMenu from '@/components/EditorMenu'
	import editor from "@/js/editor.js"
	import {
		ElMessage
	} from 'element-plus'

	export default {
		data() {
			return {
				title: "",
				titleLength: 0,
				inputDiv: ref(null),
				df: ref(null),
				textColor: "#409EFF",
				textColorList: [
					"#939393",
					"#000000",
					"#ff5500",
					"#ff0000",
					"#00a6ed",
				],
				itemHideStatus: false,
			};
		},
		components: {
			EditorMenu,
		},
		methods: {
			inputFocus() {
				editor.inputFocus(this.$refs.inputDiv, this.$refs.df)
			},
			inputBlur() {
				editor.inputBlur(this.$refs.inputDiv, this.$refs.df)
			},
			inputEditor(e) {
				editor.inputEditor(e, this.$refs.inputDiv)
			},
			inputChineseStart(e) {
				editor.inputChineseStart(e, this.$refs.inputDiv)
			},
			inputChinese(e) {
				console.log(e.cancelable)
				editor.inputChinese(e, this.$refs.inputDiv)
			},
			displayItemCard(str) {
				let dom = document.getElementById(str).getElementsByClassName("item-card")[0]
				if (dom.style.display == "none" || dom.style.display == "") {
					dom.style.display = "block"
				} else {
					dom.style.display = "none"
				}
				// this.hideItemCard(str)
				console.log(dom);
			},
			hideItemCard(str) {
				let domList = document.getElementsByClassName("item-card");
				// 隐藏编辑头上的选择列表
				for (let i = 0; i < domList.length; i++) {
					if (domList[i].parentNode.id != str) {
						domList[i].style.display = "none"
					}
				}
			},
			changeTextColor(e) {
				console.log(e.target.getAttribute("value"))
				this.textColor = e.target.getAttribute("value")
				this.hideItemCard()
			},
			// 发布博客
			publishBtn() {
				if (this.title.length < 5) {
					ElMessage.error('文章标题应为5~100个字')
					return
				}
				if (this.$refs.inputDiv.innerText.trim().length == 0) {
					ElMessage.error('文章内容不能为空')
				}
			},
		},
		mounted() {
			window.addEventListener("click", (e) => {
				// 避免使用颜色选择框时关闭
				if (this.itemHideStatus) {
					return
				}
				this.hideItemCard()
			})
		},
		watch: {
			textColor(newVal, OldVal) {
				editor.setTextColor(newVal, this.$refs.inputDiv)
			}
		}
	};
</script>

<style>
	.editor-bottom-main {
		text-align: center;
		margin-top: 1.5%;
	}

	#articles {
		margin: 0% 15%;
		padding: 16px;
		background-color: white;
		min-height: 1500px;
	}

	.editor-title {
		border-bottom: 1px #d9d9d9 solid;
		margin-bottom: 5%;
	}

	.input-title {
		width: 85%;
		min-height: 80px;
		font-size: 26px !important;
		border: 0px;
		color: ##606266;
		outline: none !important;
	}

	.editor-main {
		caret-color: auto;
	}

	#input-main {
		min-width: 100%;
		min-height: 300px;
		outline: none;
	}
	.placeholder::before {
	    content: attr(data-placeholder);
	    color: #aaa;
	    position: absolute;
	    pointer-events: none; /* Allow clicks to pass through */
	}

	#input-main p {
		outline: none;
	}

	.title-count {
		margin-left: 2%;
		min-width: 12%;
		max-width: 12%;
		max-height: 80px;
		min-height: 80px;
	}

	.editor-div {
		margin-top: 90px;
		margin-left: 0px !important;
		margin-right: 0px !important;
	}

	.editor-menu {
		text-align: center;
		position: fixed;
		top: 50;
		z-index: 1;
		min-height: 50px;
		max-height: 50px;
		width: 100%;
		border-bottom: 0.5px #d9d9d9 solid;
		display: flex;
		padding: 0px 10%;
		background-color: #f0f0f0;
		padding-top: 10px;
	}

	.editor-menu-item {
		cursor: pointer;
		max-width: 50px;
		min-width: 50px;
		max-height: 50px;
		min-height: 50px;
	}

	.editor-menu-btn {
		display: block;
	}

	.item-card {
		padding: 16px;
		margin-top: 10px;
		min-width: 100px;
		min-height: 100px;
		background-color: white;
		border: #b3b3b3 1px solid;
		border-radius: 5px;
		display: none;
	}

	.color-item {
		min-width: 32px;
		max-width: 32px;
		min-height: 32px;
		max-height: 32px;
		border-radius: 5px;
		margin-left: 3px;
		display: inline-flex;
	}

	.editor-bottom {
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 80px;
		background-color: white;
		border-top: #d9d9d9 1px solid;
	}
</style>
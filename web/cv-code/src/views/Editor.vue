<template>
	<EditorMenu></EditorMenu>
	<InsCodeEditor v-model:status="codeCardStatus"></InsCodeEditor>
	<div class="editor-menu">
		<div class="editor-menu-item" id="fontColor" @click.stop="displayItemCard('fontColor')">
			<el-icon>
				<EditPen />
			</el-icon>
			<span class="editor-menu-btn">颜色</span>
			<div @click.stop="" class="item-card">
				<!-- 有bug触发条件未知 -->
				<!-- 				<div class="color-item">
					<el-color-picker @click.stop="itemHideStatus = true" :focus="itemHideStatus = false"
						v-model="textColor"></el-color-picker>
				</div> -->
				<span @click="changeTextColor" class="color-item" v-for="(item,key) in textColorList" :value=item
					:style="'background-color:'+item+';'"></span>
			</div>
		</div>
		<div class="editor-menu-item" @click="insCodeCardStatus()">
			<el-icon>
				<Edit />
			</el-icon>
			<span class="editor-menu-btn">代码</span>
		</div>
		<div class="editor-menu-item" @click="insCodeCardStatus()">
			<span>
				<B>B</B>
			</span>
			<span class="editor-menu-btn">加粗</span>
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
					<div ref="inputDiv" contenteditable="true" id="input-main" class="placeholder" @focus="inputFocus"
						@blur="inputBlur" @keydown="inputEditor" @compositionstart="inputChineseStart"
						data-placeholder="在此开始编辑你的文章吧" @compositionend.stop="inputChinese">
					</div>
				</div>
			</div>
			<div id="uploadForm">
				<el-form ref="uploadFormRef" :model="uploadForm" :rules="rules" label-width="auto" class="demo-ruleForm"
					:size="formSize" status-icon>
					<el-form-item label="文章标签" prop="tags" id="tags">
						<div @click="tagStatus = true">
							<TagListCard v-model:nowTagList="uploadForm.tags" v-show="tagStatus"></TagListCard>
							<el-tag v-for="(item,key) in uploadForm.tags" :key="key" class="article-tag" closable
								@close="rmNowTag(key)">
								{{ item }}
							</el-tag>
							<el-button>添加文章标签</el-button>
						</div>
					</el-form-item>
					<el-form-item label="文章封面" prop="imageUrl">
						<el-upload class="avatar-uploader" action="/api/api/uploadDirect" :show-file-list="false"
							:data="{'openId':openId}"
							:on-success="handleAvatarSuccess" :headers="imgHeaders">
							<img v-if="uploadForm.imageUrl" :src="uploadForm.imageUrl" class="avatar">
							<div v-else class="upload-icon">
								<Upload />
								<p>添加文章封面</p>
							</div>
						</el-upload>
					</el-form-item>
					<el-form-item label="文章类型" prop="">
						<el-radio v-for="(item,key) in articleTypeList" v-model="uploadForm.type"
							:label="item.id">{{item.name}}</el-radio>
					</el-form-item>
					<el-form-item label="可见类型" prop="">
						<el-radio v-for="(item,key) in articleRangeList" v-model="uploadForm.range"
							:label="item.id">{{item.name}}</el-radio>
					</el-form-item>
				</el-form>
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
	import InsCodeEditor from "@/components/InsCodeEditor"
	import TagListCard from "@/components/TagListCard"
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
					"#ffffff",
				],
				itemHideStatus: false,
				codeCardStatus: false,
				articleRangeList: [],
				articleTypeList: [],
				uploadFormRef: ref(null),
				tagStatus: false,
				uploadForm: {
					tags: [],
					imageUrl: "",
					range: 0,
					type: 0,
				},
				openId: new Date().getTime(),
				rules: {
					tags: [{
						type: 'array',
						required: true,
						message: "请选择文章标签",
						trigger: 'change',
					}],
					imageUrl: [{
						required: true,
						message: "请上传文章封面"
					}]
				},
				imgHeaders: {
					'token': localStorage.getItem('token')
				},
			};
		},
		components: {
			EditorMenu,
			InsCodeEditor,
			TagListCard,
		},
		methods: {
			// 删除文章标签
			rmNowTag(index) {
				this.uploadForm.tags.splice(index, 1)
			},
			handleAvatarSuccess(res, file) {
				this.uploadForm.imageUrl = res + "?" + new Date().getTime();
			},
			beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isJPG) {
					this.$message.error('上传头像图片只能是 JPG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return isJPG && isLt2M;
			},

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
					return
				}
				// 获取文章主体html元素
				let mainDomList = this.$refs.inputDiv.querySelectorAll("*")
				let mainStr = ""
				for (let i = 0; i < mainDomList.length; i++) {
					// 删除dom元素中可编辑属性
					mainDomList[i].removeAttribute("contenteditable")
					mainStr += mainDomList[i].outerHTML
				}
				this.$refs["uploadFormRef"].validate((valid) => {
					if (valid) {
						http.post("/api/api/uploadArticle", {
							title: this.title,
							main: mainStr,
							tags: this.uploadForm.tags,
							coverPath: this.uploadForm.imageUrl,
							openId: this.openId,
						}).then(res => {
							console.log(res.data);
							if (res.data.code == "1") {
								this.$message({
									message: '文章已提交审核，请耐心等待审核通过',
									type: 'success'
								})
							}
						}).catch(e => {
							console.log(e);
						})
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			// 插入代码输入块显示状态
			insCodeCardStatus() {
				this.codeCardStatus = true
			},
			editorWindowClick(e) {
				// 避免使用颜色选择框时关闭
				// if (this.itemHideStatus) {
				// 	return
				// }
				this.hideItemCard()
				// 鼠标点击时不在文章标签列表范围内时隐藏标签列表
				if (!document.getElementById('tags').contains(e.target)) {
					this.tagStatus = false
				}
			}
		},
		created() {

		},
		unmounted() {
			window.removeEventListener("click", this.editorWindowClick)
		},
		mounted() {
			// 网页全局点击事件
			window.addEventListener("click", this.editorWindowClick)
			// 获取文章可见范围列表
			http.post("/api/api/getArticleRange", {

			}).then(res => {
				this.articleRangeList = res.data.data
			})
			http.post("/api/api/getArticleType", {

			}).then(res => {
				this.articleTypeList = res.data.data
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
	.avatar {
		width: 10em !important;
		height: 10em !important;
		border-radius: 6px;
		border: 1px solid #d9d9d9;
	}

	.avatar:hover {
		border: #098bdb 1px solid;
	}

	.upload-icon {
		width: 10em !important;
		height: 10em !important;
		color: #757575 !important;
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		text-align: center;

		svg {
			margin-top: 2.5em;
			width: 2em;
			height: 2em;
		}

		p {
			margin: 0px;
			padding: 0px;
		}
	}

	.upload-icon:hover {
		border: #098bdb 1px dashed;
	}

	.editor-bottom-main {
		text-align: center;
		margin-top: 1.5%;
	}

	#articles {
		margin: 0% 15%;
		padding: 16px;
		background-color: white;
		min-height: 1000px;
	}

	#uploadForm {
		margin: 2.5% 15% 10% 15%;
		padding-top: 50px;
		padding-left: 50px;
		background-color: white;
		min-height: 350px;
		max-height: 350px;
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
		pointer-events: none;
		/* Allow clicks to pass through */
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
		border: 1px solid #bababa;
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
		max-height: 10%;
	}

	.article-tag {
		margin-right: 10px;
		height: 32px !important;
	}
</style>
<template>
	<el-card class="code-editor-card" v-show="status">
		<template #header>
			<div class="card-header">
				<span>插入代码</span>
				<el-icon @click="exitCodeCard()" style="float: right;cursor: pointer;">
					<Close />
				</el-icon>
			</div>
		</template>
		<textarea name="t1" id="inpuit-code" cols="30" rows="10" v-model="code"></textarea>
		<div style="margin-top: 20px;float: right;">
			<el-button type="primary" round @click="insCodeEditorFun()">确定</el-button>
			<el-button round @click="exitCodeCard()">取消</el-button>
		</div>
	</el-card>
</template>

<script>
	import {
		h
	} from 'vue';
	import {
		ref
	} from 'vue';
	import CodeEditor from "@/components/CodeEditor"
	import {
		createApp
	} from 'vue'
	import editor from "@/js/editor.js"
	export default {
		props: {
			status: {
				default: false,
				type: Boolean,
			}
		},
		components: {
			CodeEditor
		},
		data: () => ({
			inputCodeDiv: ref(null),
			code: "",
		}),
		methods: {
			exitCodeCard() {
				this.$emit("update:status", false)
			},
			insP(dom, str) {
				editor.insP(dom, str)
			},
			insCodeEditorFun() {
				this.$emit("update:status", false)
				let dom = document.getElementById("input-main")
				let codeNode = document.createElement("div")
				codeNode.setAttribute("contenteditable", false)
				dom.append(codeNode)
				this.insP(dom, "")
				CodeEditor.data = () => ({
					code: this.code,
					lineNumbers: true, // true 显示行号   false 不显示行号
					readonlyType: true //true不可编辑   false 可编辑
				})
				let myApp = createApp(CodeEditor)
				myApp.mount(codeNode)
				dom.setAttribute("data-placeholder", "")
			},
		},
		mounted() {

		}
	};
</script>
<style>
	.code-editor-card {
		position: fixed;
		top: 15%;
		left: 31%;
		z-index: 1;
		min-height: 650px;
		max-height: 650px;
		min-width: 720px;
		max-width: 720px;
	}

	#inpuit-code {
		background-color: #272E39;
		min-width: 670px;
		max-width: 670px;
		min-height: 480px;
		max-height: 480px;
		color: white;
	}
</style>
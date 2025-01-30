<template>
	<prism-editor class="my-editor height-300" v-model="code" :highlight="highlighter" :line-numbers="lineNumbers"
		:readonly="readonlyType"></prism-editor>
<!-- 	code----为需要高亮显示的代码内容
	highlighter----定义在methods中的一个方法，用于把code高亮显示
	lineNumbers----是否可编辑标识 -->
</template>

<script>
	import {
		PrismEditor
	} from "vue-prism-editor";
	import "vue-prism-editor/dist/prismeditor.min.css"; // import the styles somewhere

	// import highlighting library (you can use any library you want just return html string)
	import {
		highlight,
		languages
	} from "prismjs/components/prism-core";
	import "prismjs/components/prism-clike";
	import "prismjs/components/prism-javascript";
	import "prismjs/themes/prism-tomorrow.css"; // import syntax highlighting styles
	export default {
		components: {
			PrismEditor
		},
		data: () => ({
			code: 'console.log("Hello World")',
			lineNumbers: true, // true 显示行号   false 不显示行号
			readonlyType: false //true不可编辑   false 可编辑
		}),
		methods: {
			highlighter(code) {
				return highlight(code, languages.js); //returns html
			}
		}
	};
</script>
<style>
/* required class */
.my-editor {
 background: #2d2d2d;
 color: #ccc;
 
 font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
 font-size: 14px;
 line-height: 1.5;
 padding: 5px;
}
 
/* optional */
.prism-editor__textarea:focus {
 outline: none;
}
 
/* not required: */
.height-300 {
 height: 300px;
}
</style>
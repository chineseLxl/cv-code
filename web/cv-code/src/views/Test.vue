<template>
  <div id="app">
    <div contenteditable="true" ref="inputDiv" 
         @focus="inputFocus" @blur="inputBlus"
         @keydown="inputEditor" @compositionstart="inputChineseStart" 
         @compositionend="inputChinese">
      <span contenteditable="true" ref="df">在此开始编辑你的文章吧</span>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isComposing: false,  // 标记是否在输入中文
      pendingText: "",  // 存储待插入的中文内容
    };
  },
  methods: {
    // 文章编辑
    inputEditor(e) {
      // 阻止默认行为，防止在中文输入时触发默认行为
      if (this.isComposing) {
        e.preventDefault();
      }
    },
    // 文章中开始输入中文
    inputChineseStart(e) {
      this.isComposing = true;  // 标记中文输入开始
      this.pendingText = "";    // 清空待插入内容
    },
    // 文章中文输入结束
    inputChinese(e) {
      this.isComposing = false;  // 中文输入结束

      // 阻止默认行为，防止浏览器在输入过程中插入内容
      e.preventDefault();

      // 获取中文输入内容
      const inputText = e.data;

      // 如果内容为空或没有变化，则不进行插入
      if (!inputText || this.pendingText === inputText) {
        return;
      }

      // 获取当前光标位置
      const selection = window.getSelection();
      const range = selection.getRangeAt(0);

      // 删除光标所在位置的内容
      range.deleteContents();

      // 创建一个 <span> 元素，并插入输入的中文内容
      const span = document.createElement("span");
      span.style.color = "#409EFF";  // 设置自定义颜色
      span.textContent = inputText;  // 设置中文内容

      // 将中文内容插入到光标位置
      range.insertNode(span);

      // 更新光标位置，确保光标位于新插入的 span 后面
      range.setStartAfter(span);
      range.setEndAfter(span);

      // 清空选择范围，确保光标状态更新
      selection.removeAllRanges();
      selection.addRange(range);

      // 存储已插入的文本
      this.pendingText = inputText;
    },
    // 输入框获得焦点
    inputFocus() {
      console.log("输入框聚焦");
    },
    // 输入框失去焦点
    inputBlus() {
      console.log("输入框失焦");
    }
  }
};
</script>

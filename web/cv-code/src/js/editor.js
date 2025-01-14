const notPrint = [
	"Backspace", "Control", "Shift"
]
const chinesePrint = {
	"Digit1": "1",
}

let color = ""
let colorStatus = false

// 设置光标位置
function setRange(dom) {
	if (!(dom instanceof Element)) {
		console.error('Invalid DOM element provided');
		return;
	}
	var range = document.createRange();
	range.selectNodeContents(dom);
	range.collapse(false);
	var sel = window.getSelection();
	sel.removeAllRanges();
	sel.addRange(range);
	console.log(sel);
}

function getRange(dom) {
	var sel = window.getSelection()
	var range = sel.getRangeAt(0)
	return range
}

function addP(str) {
	let p = document.createElement("p");
	p.setAttribute("contenteditable", true)
	// p.setAttribute("tabindex", "-1")
	console.log(str)
	p.innerText = str
	return p
}

function addSpan(str) {
	let span = document.createElement("span");
	span.setAttribute("contenteditable", true)
	// span.setAttribute("tabindex", "-1")
	span.textContent = str || " "
	
	return span
}

function inputColorText(str) {
	let span = document.createElement("span");
	span.setAttribute("contenteditable", true)
	// span.setAttribute("tabindex", "-1")
	span.style.color = color
	// span.style.minWidth = "10px"
	// span.style.minHeight = "10px"
	// span.textContent = str || " "
	span.innerHTML = "&nbsp"
	return span
}
class Editor {
	// 鼠标焦点
	inputFocus(dom, defaultFont) {
		if (dom.children[0] == defaultFont) {
			dom.removeChild(defaultFont)
		}
	}
	// 失去焦点
	inputBlur(dom, defaultFont) {
		if (dom.children.length == 0) {
			dom.appendChild(defaultFont)
		}
	}
	// 文章编辑
	inputEditor(e, dom) {
		// 阻止默认行为
		console.log(notPrint.indexOf(e.key));
		if (notPrint.indexOf(e.key) != -1) {
			return
		}
		// 中文
		if (e.key == "Process") {
			return
		}
		if (dom.children.length == 0) {
			// 阻止默认行为
			e.preventDefault();
			// 添加文本
			let p = addP(e.key)
			p.appendChild(span)
			dom.appendChild(p)
			setRange(p)
			return
		}

	}
	// 文章中开始输入中文
	inputChineseStart(e, dom) {
		// let spanList = e.target.getElementsByTagName("span");
		// if (spanList.length != 0) {
		// 	setRange(spanList[spanList.length - 1])
		// }
		e.preventDefault()
		console.log("中文输入开始")
	}
	inputChinese() {
		
	}
	// 文章编辑中文
	inputChinese(e, dom) {
		console.log("中文输入结束");
		// 阻止默认行为
		e.preventDefault();
		if (notPrint.indexOf(e.key) != -1) {
			return
		}
		// 首次输入
		if (dom.children.length == 0) {
			dom.innerText = ""
			// 添加文本
			let p = addP(e.data)
			dom.appendChild(p)
			// 设置光标位置
			setRange(p)
			return
		}
	}
	// 更改文字颜色
	setTextColor(c, dom) {
		color = c
		colorStatus = true
		let domList = dom.getElementsByTagName("p")
		if (domList.length == 0) {
			// 添加文本
			let p = addP("")
			let span = inputColorText("")
			p.appendChild(span)
			dom.appendChild(p)
			setRange(span)
			return
		} else {
			let span = inputColorText("")
			domList[domList.length - 1].appendChild(span)
			setRange(span)
		}
	}
};

const editor = new Editor();

export default editor;
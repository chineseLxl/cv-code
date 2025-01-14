<template>
	<div id="overlay" v-show="status">
		<div class="popup">
			<el-row :gutter="20">
				<el-col :span="6">
					<div class="grid-content ep-bg-purple" />
				</el-col>
				<el-col :span="6">
					<div class="grid-content ep-bg-purple" />
					<div class="login-card">
						<el-card>
							<template #header>
								<div class="card-header login-header">
									<span>欢迎使用</span>
									<el-icon @click="hidePopup()"><Close /></el-icon>
								</div>
							</template>
							<el-form :model="ruleForm" :rules="rules" ref="ruleForm">
								<el-form-item prop="username">
									<el-input v-model="ruleForm.username" placeholder="账号" clearable />
								</el-form-item>
								<el-form-item prop="password">
									<el-input v-model="ruleForm.password" placeholder="密码" show-password />
								</el-form-item>
							</el-form>
							<el-form>
								<el-button type="primary" @click="submitLogin('ruleForm')">登录</el-button>
							</el-form>
						</el-card>
					</div>
				</el-col>
				<el-col :span="6">
					<div class="grid-content ep-bg-purple" />
				</el-col>
			</el-row>

		</div>
	</div>
</template>

<script>
	import {
		ref,toRaw
	} from 'vue'
	import {
		router
	} from '@/js/router.js'
	import { http } from "@/js/http.js"
	export default {
		props: {
			status: {
				default: false,
				type: Boolean,
			}
		},
		data() {
			return {
				ruleForm: {
					"username": "",
					"password": "",
				},
				rules: {
					username: [{
						required: true,
						message: '请输入用户名',
						trigger: 'blur'
					}],
					password: [{
						required: true,
						message: '请输入用户名',
						trigger: 'blur'
					}],
				}
			}
		},
		components: {

		},
		methods: {
			showPopup() {
				var overlay = document.getElementById("overlay");
				overlay.style.display = "block";
			},
			hidePopup() {
				this.$emit("update:status", false)
			},
			submitLogin(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						let formData = toRaw(this.ruleForm)
						http.post("/api/api/login", {
							username: formData.username,
							password: formData.password,
						}).then(res => {
							console.log(res.data)
							if(res.data.code == 200) {
								localStorage.setItem("username", formData.username);
								localStorage.setItem("token", res.data.data.token);
								this.$emit("update:status", false)
								location.reload()
							}
						}).catch(e => {
							console.log(e)
						})
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			}
		},
		mounted() {

		},
	}
</script>

<style>
	/* 遮罩层 */
	#overlay {
		position: fixed;
		left: 0px;
		top: 0px;
		width: 100%;
		height: 100%;
		font-size: 16px;
		/* IE9以下不支持rgba模式 */
		background-color: rgba(0, 0, 0, 0.5);
		/* 兼容IE8及以下 */
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#7f000000, endColorstr=#7f000000);
		display: block;
	}

	.login-card {
		position: fixed;
		top: 30%;
		left: 42%;
		text-align: center;

			max-width: 350px;
			min-width: 350px;

		z-index: 0;
	}
	.login-header {
		i {
			float: right;
			cursor: pointer;
		}
	}
</style>
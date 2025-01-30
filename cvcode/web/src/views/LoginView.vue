<template>
	<div>
		<div ref="vantaRef" style="width:100%;height:100vh"></div>
		<div class="my_title">CV-CODE</div>
		<el-card class="loginDiv">
			<el-tabs v-model="activeName" @tab-click="handleClick">
				<el-tab-pane label="登录" name="login">
					<el-form :model="ruleForm" :rules="rules" ref="ruleForm">
						<el-form-item prop="username">
							<el-input v-model="ruleForm.username" placeholder="账号" clearable />
						</el-form-item>
						<el-form-item prop="password">
							<el-input v-model="ruleForm.password" placeholder="密码" show-password />
						</el-form-item>
						<el-button type="primary" @click="submitLogin('ruleForm')">登录</el-button>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="注册" name="registered">
					<el-form-item prop="nickname">
						<el-input v-model="ruleForm.nickname" placeholder="昵称" clearable />
					</el-form-item>
					<el-form-item prop="username">
						<el-input v-model="ruleForm.username" placeholder="账号" clearable />
					</el-form-item>
					<el-form-item prop="password">
						<el-input v-model="ruleForm.password" placeholder="密码" show-password />
					</el-form-item>
					<el-form-item prop="email">
						<el-input v-model="ruleForm.email" placeholder="邮箱" clearable />
					</el-form-item>
					<el-button type="primary" @click="submitRegistered('ruleForm')">注册</el-button>
				</el-tab-pane>
			</el-tabs>
		</el-card>
	</div>

</template>

<script>
	import * as THREE from 'three'
	// import BIRDS from 'vanta/src/vanta.birds'
	import RINGS from 'vanta/src/vanta.rings'
	import {
		router
	} from '@/js/router.js'
	import {
		toRaw
	} from 'vue';
	import {
		http
	} from "@/js/http.js"

	export default {
		data() {
			return {
				activeName: "login",
				ruleForm: {
					"nickname": "",
					"username": "",
					"password": "",
					"email": "",
				},
				rules: {
					username: [{
						required: true,
						message: '请输入昵称',
						trigger: 'blur'
					}],
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
					email: [{
						required: true,
						message: '请输入邮箱',
						trigger: 'blur'
					}],
				}
			};
		},
		methods: {
			submitLogin(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						let formData = toRaw(this.ruleForm)
						http.post("/api/api/login", {
							username: formData.username,
							password: formData.password,
						}).then(res => {
							if(res.data.code == 200) {
								localStorage.setItem("token", res.data.data.token);
								location.assign("/")
							}
						}).catch(e => {
							console.log(e)
						})
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			submitRegistered(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						let formData = toRaw(this.ruleForm)
						http.post("/api/api/registered", {
							nickname: formData.nickname,
							username: formData.username,
							password: formData.password,
							email: formData.email,
						}).then(res => {
							if(res.data.code == 200) {
								localStorage.setItem("token", res.data.data.token);
								location.assign("/")
							}
						}).catch(e => {
							console.log(e)
						})
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
		},
		mounted() {
			this.vantaEffect = RINGS({
				el: this.$refs.vantaRef,
				THREE: THREE,
			})
		},
		beforeDestroy() {
			if (this.vantaEffect) {
				this.vantaEffect.destroy()
			}
		},

	};
</script>

<style scoped>
	.my_title {
		z-index: 999;
		position: fixed;
		top: 40%;
		left: 5%;
		color: #4682b4;
		font-size: 80px;
		font-weight: bolder;
	}

	.loginDiv>>>.el-card__body {
		z-index: 999;
		position: fixed;
		top: 35%;
		left: 55%;
		font-weight: bolder;
		background-color: rgba(255, 255, 255, 0.9);
		min-width: 350px;
		text-align: center;
		border-radius: 5px;
	}

	body {
		margin: 0px;
	}
</style>
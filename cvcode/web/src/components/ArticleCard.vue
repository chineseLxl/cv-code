<template>
	<div>
		<el-card @click.stop="toCardInfo">
			<el-row class="card-div" :id="cardForm.id">
				<el-col :span="4" class="card-img">
					<img :src="cardForm.coverPath"/>
				</el-col>
				<el-col :span="16">
					<el-row><span class="card-title"><a>{{ cardForm.title }}</a></span></el-row>
					<el-row class="card-info"><el-text size="small" type="info">{{ cardForm.info }}</el-text></el-row>
					<el-row>
						<span class="card-footer" @click.stop="clickUpFun">
							<svg v-if="cardForm.clickUp==false" t="1733391337993" class="icon" viewBox="0 0 1024 1024"
								version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3073" width="14" height="14">
								<path
									d="M64 483.04V872c0 37.216 30.144 67.36 67.36 67.36H192V416.32l-60.64-0.64A67.36 67.36 0 0 0 64 483.04zM857.28 344.992l-267.808 1.696c12.576-44.256 18.944-83.584 18.944-118.208 0-78.56-68.832-155.488-137.568-145.504-60.608 8.8-67.264 61.184-67.264 126.816v59.264c0 76.064-63.84 140.864-137.856 148L256 416.96v522.4h527.552a102.72 102.72 0 0 0 100.928-83.584l73.728-388.96a102.72 102.72 0 0 0-100.928-121.824z"
									p-id="3074" fill="#e6e6e6"></path>
							</svg>
							<svg v-if="cardForm.clickUp==true" t="1733391337993" class="icon" viewBox="0 0 1024 1024"
								version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3073" width="14" height="14">
								<path
									d="M64 483.04V872c0 37.216 30.144 67.36 67.36 67.36H192V416.32l-60.64-0.64A67.36 67.36 0 0 0 64 483.04zM857.28 344.992l-267.808 1.696c12.576-44.256 18.944-83.584 18.944-118.208 0-78.56-68.832-155.488-137.568-145.504-60.608 8.8-67.264 61.184-67.264 126.816v59.264c0 76.064-63.84 140.864-137.856 148L256 416.96v522.4h527.552a102.72 102.72 0 0 0 100.928-83.584l73.728-388.96a102.72 102.72 0 0 0-100.928-121.824z"
									p-id="3074" fill="#30e4ed"></path>
							</svg>
							<span>{{ cardForm.up }}赞</span>
						</span>
						<span class="card-footer" @click.stop="clickDownFun">
							<svg v-if="cardForm.down == false" t="1733391454807" class="icon" viewBox="0 0 1024 1024"
								version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4556" width="14" height="14">
								<path
									d="M0.324216 544.126266V99.856667c0-42.502075 34.439126-76.943007 76.950232-76.943007h69.252138v597.416871l-69.252138 0.727905C34.779598 621.060242 0.324216 586.621116 0.324216 544.126266zM906.401774 701.805334c-101.960909-0.643013-203.927236-1.295057-305.889952-1.936264 14.372063 50.5578 21.64389 95.465755 21.64389 135.014667 0 89.738243-78.615563 177.607053-157.128172 166.202603-69.225045-10.057012-76.829216-69.88612-76.829216-144.858545v-67.689761c0-86.871778-72.916951-160.897746-157.451485-169.040169l-11.119068 0.106567V22.915466h602.566394c56.362979 0 104.7768 40.090776 115.278141 95.47298 28.068601 148.097091 56.146233 296.181539 84.21664 444.269599 12.045656 63.672736-29.79715 125.053384-93.46808 137.09904a116.62016 116.62016 0 0 1-21.819092 2.048249z"
									fill="#e6e6e6" p-id="4557"></path>
							</svg>
							<svg v-if="cardForm.down == true" t="1733391454807" class="icon" viewBox="0 0 1024 1024"
								version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4556" width="14" height="14">
								<path
									d="M0.324216 544.126266V99.856667c0-42.502075 34.439126-76.943007 76.950232-76.943007h69.252138v597.416871l-69.252138 0.727905C34.779598 621.060242 0.324216 586.621116 0.324216 544.126266zM906.401774 701.805334c-101.960909-0.643013-203.927236-1.295057-305.889952-1.936264 14.372063 50.5578 21.64389 95.465755 21.64389 135.014667 0 89.738243-78.615563 177.607053-157.128172 166.202603-69.225045-10.057012-76.829216-69.88612-76.829216-144.858545v-67.689761c0-86.871778-72.916951-160.897746-157.451485-169.040169l-11.119068 0.106567V22.915466h602.566394c56.362979 0 104.7768 40.090776 115.278141 95.47298 28.068601 148.097091 56.146233 296.181539 84.21664 444.269599 12.045656 63.672736-29.79715 125.053384-93.46808 137.09904a116.62016 116.62016 0 0 1-21.819092 2.048249z"
									fill="#30e4ed" p-id="4557"></path>
							</svg>
							<span>{{ cardForm.down }}踩</span>
						</span>
						<span class="card-footer">作者：{{ cardForm.nickName }}</span>
					</el-row>
				</el-col>
			</el-row>
		</el-card>
	</div>

</template>

<script>
	import {
		router
	} from '@/js/router'

	export default {
		props: {
			cardForm: {
				type: Object,
				required: true,
			}
		},
		data() {
			return {}
		},
		methods: {
			clickUpFun(event) {
				if (this.cardForm.clickUp == false) {
					this.cardForm.up = Number(this.cardForm.up) + 1
					this.cardForm.clickUp = true
				} else {
					this.cardForm.up = Number(this.cardForm.up) - 1
					this.cardForm.clickUp = false
				}
			},
			clickDownFun(event) {
				console.log(this.cardForm.clickDown)
				if (this.cardForm.clickDown == false) {
					this.cardForm.down = Number(this.cardForm.down) + 1
					this.cardForm.clickDown = true
				} else {
					this.cardForm.down = Number(this.cardForm.down) - 1
					this.cardForm.clickDown = false
				}
			},
			toCardInfo(event) {
				console.log(this.cardForm.id)
				let routeData = router.resolve(({
					path: '/cardInfo', // path 要跳转的路由地址
					// query 要传递的参数
					query: {
						id: this.cardForm.id,
					}
				}))
				window.open(routeData.href, '_blank')
			}
		},
		mounted() {
			
		},
	}
</script>

<style>
	.card-title {}

	.card-title a:hover {
		cursor: pointer;
		text-decoration: underline;
	}

	.card-info {
		color: #b3b3b3;
		font-size: 0.8rem;
		min-height: 6rem;
		max-height: 6rem;
	}

	.card-footer {
		color: #939393;
		font-size: 0.8rem;
		margin-right: 16px;
		cursor: pointer
	}

	.icon {
		margin-right: 6px;
	}

	.card-div {
		max-height: 10rem;
	}
	
	.card-img img {
		margin: 0.3rem;
		width:13rem;
		height: 10rem;
		object-fit: cover;
	}
</style>
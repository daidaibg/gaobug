<template>
	<div class="contents">
		<div class="search-form">
			<div class="search-form-item">
				<label>网关ID:</label>
				<el-input size="small" placeholder="请输入网关ID" v-model="search.gatewayno" clearable></el-input>
			</div>
			<div class="search-form-item">
				<label>线路名称:</label>
				<el-input size="small" placeholder="请输入线路名称" v-model="search.circuit" clearable></el-input>
			</div>
			<div class="search-form-item time">
				<label>时间选择:</label>
				<el-date-picker size="small" v-model="search.date" type="datetimerange" range-separator="至"
					unlink-panels :default-value='val.defaultDate' start-placeholder="开始日期" end-placeholder="结束日期"
					@change="changeDate" value-format='yyyy-MM-dd HH:mm:ss' :picker-options="endDatePicker">
				</el-date-picker>
			</div>
			<div class="flex align-center rdx-p-l-20">
				<el-button size="mini" clearable :loading="loading" type="primary" @click='getdata'>查询</el-button>
				<el-button size="mini" @click="reset"> 重置</el-button>
			</div>
		</div>
		<div class="flex flex-direction bg-white box-shadow-item rdx-p-tb-20 rdx-p-l-20 rdx-p-r-10 rdx-m-t-16">
			<span class="font-strong">雷击强度分析</span>
			<div class="" ref="statisticsId" style="width: 100%; height: 310px;"></div>
		</div>
		<div class="flex rdx-m-t-16">
			<yh-table-pag :data="getData.tableList.list" style="width: 100%" class="my_table" @getData="getdata"
				:row-class-name="rowClassName" ref="myTable" size="small" :loading="loading"
				:total="getData.tableList.totalCount" :currentPage="pagData.currentPage" :height="heights">
<!-- 				<el-table-column type="selection" align="center" width="55">
				</el-table-column> -->
				<el-table-column prop="number" label="序号" width="56" align="center">
					<template slot-scope="scope">
						<div>
							{{ scope.$index + 1 + (pagData.currentPage - 1) * pagData.pageSize }}
						</div>
					</template>
				</el-table-column>
				<el-table-column prop="gatewayno" label="网关ID" align="center">
				</el-table-column>
				<el-table-column prop="lineName" label="终端ID" align="center">
				</el-table-column>
				<el-table-column prop="companyName" label="雷击强度" align="center">
				</el-table-column>
				<el-table-column prop="alertname" label="相线" align="center">
				</el-table-column>
				<el-table-column prop="createtime" label="雷击时间" align="center">
				</el-table-column>
			</yh-table-pag>
		</div>
	</div>
</template>

<script>
	import {
		regionList
	} from "api/modules/sys";
	import {
		tableApi,
		alertconfigApi
	} from "api/modules/warningData";
	export default {
		data() {
			return {
				loading: false,
				heights: null,
				getData:{
					tableList:''
				},
				pagData: {
					pageSize: 10,
					currentPage: 1,
				},
				search: {
					gatewayno: '', //网关编号（网关ID）
					date: [], //时间选择
					circuit:'',//线路名称
				},
				val: {
					defaultDate: new Date(),
					defaultWeek: new Date()
				},
				endDatePicker: this.endTime()
			}
		},
		mounted() {
			this.heights = this.$tableHeight(this.$refs.myTable.$el)
			this.val.defaultDate.setTime(this.val.defaultDate.getTime() - 3600 * 1000 * 24 * 30)
			this.val.defaultWeek.setTime(this.val.defaultWeek.getTime() - 3600 * 1000 * 24 * 7)
			this.search.date = [this.val.defaultWeek, new Date()]
			this.statisticsOption = echarts.init(this.$refs.statisticsId)
			this.gatStatistics()
		},
		methods: {
			//获取图表数据
			gatStatistics() {
				var option = {
					tooltip: {
						trigger: 'axis',
						backgroundColor: 'rgba(0,0,0,0.7)',
						textStyle: {
							color: '#ffffff'
						},
						borderColor: 'rgba(0,0,0,0.7)'
					},
					legend: [{
						right: 30,
						top: 0,
						icon: 'roundRect',
						itemWidth: 27,
						itemHeight: 12,
						textStyle: {
							fontSize: 14,
							color: '#999999',
						},
						data: ['终端1', '终端2', '终端3']
					}, {
						right: 30,
						top: 26,
						icon: 'roundRect',
						itemWidth: 27,
						itemHeight: 12,
						textStyle: {
							fontSize: 14,
							color: '#999999',
						},
						data: ['终端4', '终端5', '终端6'],
					}],
					grid: {
						top: 60,
						bottom: 20,
						left: 50,
						right: 30
					},
					xAxis: {
						type: 'category',
						boundaryGap: false,
						data: ['2021.01.05', '2021.02.05', '2021.03.05', '2021.04.05'],
					},
					yAxis: {
						type: 'value',
						name:'(强度)',
						nameTextStyle:{
							align:'right'
						},
					},
					series: [{
							color:'#1890FF',
							data: ['100', '200', '300', '400'],
							name: '终端1',
							type: 'line',
							smooth: true,
							symbol: "none",
							lineStyle: {
								shadowColor:'#1890FF',
							    shadowBlur: 8,
							}
						},
						{
							color:'#56C5F9',
							data: ['130', '240', '350', '460'],
							name: '终端2',
							type: 'line',
							smooth: true,
							symbol: "none",
							lineStyle: {
								shadowColor:'#56C5F9',
							    shadowBlur: 8,
							}
						},
						{
							color:'#736FC4',
							data: ['170', '280', '390', '400'],
							name: '终端3',
							type: 'line',
							smooth: true,
							symbol: "none",
							lineStyle: {
								shadowColor:'#736FC4',
							    shadowBlur: 8,
							}
						}, {
							color:'#31CB6B',
							data: ['220', '330', '440', '550'],
							name: '终端4',
							type: 'line',
							smooth: true,
							symbol: "none",
							lineStyle: {
								shadowColor:'#31CB6B',
							    shadowBlur: 8,
							}
						},
						{
							color:'#3AD8C4',
							data: ['130', '266', '377', '488'],
							name: '终端5',
							type: 'line',
							smooth: true,
							symbol: "none",
							lineStyle: {
								shadowColor:'#3AD8C4',
							    shadowBlur: 8,
							}
						},
						{
							color:'#ED792C',
							data: ['222', '460', '444', '555'],
							name: '终端6',
							type: 'line',
							smooth: true,
							symbol: "none",
							lineStyle: {
								shadowColor:'#ED792C',
							    shadowBlur: 8,
							}
						}
					]
				}
				this.$nextTick(() => {
					this.statisticsOption.clear()
					this.statisticsOption.setOption(option)
				})
			},
			//获取表格数据
			getdata() {},
			//重置
			reset() {},
			//获取时间
			changeDate(e) {
				this.search.date = e
			},
			//设置时间
			endTime() {
				return {
					disabledDate(time) {
						return (
							time.getTime() > Date.now() ||
							time.getTime() < Date.now() - 1000 * 3600 * 24 * 90
						)
					}
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	.search-form .search-form-item label {
		text-align: right;
		padding-right: 6px;
	}
</style>
<template>
	<div class="contents">
		<div class="search-form">
			<div class="search-form-item">
				<label>网关ID:</label>
				<el-input size="small" placeholder="请输入网关ID" v-model="search.gatewayno" clearable></el-input>
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
		<div class="flex flex-direction bg-white box-shadow-item rdx-p-20 rdx-m-t-16">
			<span class="font-strong">雷击次数统计<i class="color-remark rdx-font-14">(雷击总数:666次)</i></span>
<!-- 			<span class="position-ab rdx-font-14 color-remark" style="top: 66px; left: 26px;">(次数)</span> -->
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
				<el-table-column prop="companyName" label="相线" align="center">
				</el-table-column>
				<el-table-column prop="alertname" label="雷击次数" align="center">
				</el-table-column>
				<el-table-column prop="createtime" label="时间" align="center">
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
	import {
		treeDataTranslate
	} from "@/lib/currency";
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
				},
				val: {
					defaultDate: new Date(),
					defaultWeek: new Date(),//提前一周
				},
				endDatePicker: this.endTime()
			}
		},
		mounted() {
			this.heights = this.$tableHeight(this.$refs.myTable.$el)
			this.val.defaultWeek.setTime(this.val.defaultWeek.getTime() - 3600 * 1000 * 24 * 7)
			this.search.date = [this.val.defaultWeek, new Date()]
			this.val.defaultDate.setTime(this.val.defaultDate.getTime() - 3600 * 1000 * 24 * 30)
			this.statisticsOption = echarts.init(this.$refs.statisticsId)
			this.gatStatistics()
		},
		methods: {
			//获取图表数据
			gatStatistics() {
				var option = {
					color: ['#1890FF', '#56C5F9', '#736FC4', '#31CB6B', '#3AD8C4', '#ED792C'],
					tooltip: {
						trigger: 'axis',
						backgroundColor: 'rgba(0,0,0,0.7)',
						formatter:function(data){	
							var str = ''
							var total = 0
							var strS = ''
							data.forEach((item)=>{
								var name = item.axisValue +'<br>'
								str += '<div class="toolDiv"><span style="width:50%; text-align: right; padding-right:10px;">' + item.marker + item.seriesName + '</span><span style="width:50%; text-align: right;">' + item.value +'次</span></div>'
								total += Number(item.value)
								var totalS = '<div class="toolDiv"><span style="width:50%; text-align: right; padding-right:10px;">总数</span><span style="width:50%; text-align: right;">' + total + '次</span></div>'
								strS = name + str + totalS
							})
							return strS
						},
						textStyle: {
							color: '#ffffff'
						},
						borderColor: 'rgba(0,0,0,0.7)'
					},
					legend: [{
						right: 0,
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
						right: 0,
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
						left: 40,
						right: 0
					},
					xAxis: {
						type: 'category',
						// boundaryGap: false,
						data: ['2021.12.09', '2021.12.10', '2021.12.11', '2021.12.12'],
					},
					yAxis: {
						type: 'value',
						name: "(次数)",
						nameTextStyle:{
							align:'right'
						}
					},
					series: [{
							data: ['100', '200', '300', '400'],
							name: '终端1',
							type: 'bar',
							barWidth: 16,
							smooth: true,
							symbol: "none",
						},
						{
							data: ['130', '240', '350', '460'],
							name: '终端2',
							type: 'bar',
							barWidth: 16,
							smooth: true,
							symbol: "none",
						},
						{
							data: ['170', '280', '390', '400'],
							name: '终端3',
							type: 'bar',
							barWidth: 16,
							smooth: true,
							symbol: "none",
						}, {
							data: ['220', '330', '440', '550'],
							name: '终端4',
							type: 'bar',
							barWidth: 16,
							smooth: true,
							symbol: "none",
						},
						{
							data: ['100', '200',"0", '400'],
							name: '终端5',
							type: 'bar',
							barWidth: 16,
							smooth: true,
							symbol: "none",
						},
						{
							data: ['100', '200', '300', '400'],
							name: '终端6',
							type: 'bar',
							barWidth: 16,
							smooth: true,
							symbol: "none",
						}
					]
				}
				this.$nextTick(() => {
					this.statisticsOption.clear()
					this.statisticsOption.setOption(option)
				})
			},
			//获取表格数据
			getdata() {
			},
			//重置
			reset() {},
			//获取时间
			changeDate(e) {
				this.search.date = e
				console.log("eeee",e)
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
	/deep/.toolDiv{width: 120px!important; display: flex;}
</style>

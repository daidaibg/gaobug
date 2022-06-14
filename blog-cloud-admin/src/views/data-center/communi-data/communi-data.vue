<template>
	<div class="contents left-tree-content">
		<div class="search-form">
			<div class="search-form-item">
				<label>发送方:</label>
				<el-select size='small' clearable v-model="search.sender" placeholder="请选择">
					<el-option v-for="item in getData.sender" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</div>
			<div class="search-form-item">
				<label>网关ID:</label>
				<el-input size="small" placeholder="请输入网关ID" v-model="search.gatewayno" clearable></el-input>
			</div>
			<div class="search-form-item">
				<label>线路名称:</label>
				<el-input size="small" placeholder="请输入线路名称" v-model="search.lineName" clearable></el-input>
			</div>
			<div class="search-form-item time">
				<label>时间选择:</label>
				<el-date-picker size="small" v-model="search.date" type="datetimerange" range-separator="至"
					start-placeholder="开始日期" end-placeholder="结束日期" value-format='yyyy-MM-dd HH:mm:ss' unlink-panels
					:default-value='val.defaultDate' :picker-options="endDatePicker" @change="changeDate">
				</el-date-picker>
			</div>
			<div class="flex align-center rdx-p-l-20">
				<el-button size="mini" clearable :loading="loading" type="primary" @click='getdata(' search')'>查询
				</el-button>
				<el-button size="mini" @click="reset"> 重置</el-button>
			</div>
		</div>
		<div class="myTable-top-action" v-if="isAuth('biz:communi:delete') ||isAuth('biz:communi:export')">
			<div class="left">
				<el-button type="primary" v-if="isAuth('biz:communi:delete')">删除</el-button>
				<el-button type="dc" v-if="isAuth('biz:communi:export')" @click="onExport">导出</el-button>
			</div>
		</div>
		<yh-table-pag :data="getData.tableList.list" style="width: 100%" class="my_table" @getData="getdata"
			:row-class-name="rowClassName" ref="myTable" size="small" :loading="loading"
			:total="getData.tableList.totalCount" :currentPage="pagData.currentPage" :height="heights">
			<el-table-column type="selection" align="center" width="55">
			</el-table-column>
			<el-table-column prop="number" label="序号" width="56" align="center">
				<template slot-scope="scope">
					<div>
						{{ scope.$index + 1 + (pagData.currentPage - 1) * pagData.pageSize }}
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="gatewayno" label="网关ID" align="center">
			</el-table-column>
			<el-table-column prop="lineName" label="杆号" align="center">
			</el-table-column>
			<el-table-column prop="commutime" label="通信时间" align="center">
			</el-table-column>
			<el-table-column prop="sender" label="方向" align="center">
				<template slot-scope="{ row }">
					{{row.sender == 1 ? '终端' : '服务端'}}
				</template>
			</el-table-column>
			<el-table-column prop="datatype" label="数据类型" align="center">
			</el-table-column>
			<el-table-column prop="datadetail" label="数据包内容" align="center">
			</el-table-column>
		</yh-table-pag>
	</div>
</template>

<script>
	import {
		communiDataApi,
		communiExportApi
	} from "api/modules/warningData";
	import {
		exportFile
	} from "@/lib/currency";
	import DDmoment from "@/lib/dd-moment";
	export default {
		data() {
			return {
				loading: false,
				total: 0,
				heights: null,
				getData: {
					tableList: [],
					sender: [{
							id: '1',
							name: '终端'
						},
						{
							id: '2',
							name: '服务端'
						}
					], //发送方
				},
				search: {
					gatewayno: '', //网关id
					sender: '', //发送方
					lineName: '', //线路名称
					date: [DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
						DDmoment().format("yyyy-MM-dd HH:mm:ss")
					], //时间选择
				},
				pagData: {
					pageSize: 10,
					currentPage: 1,
				},
				val: {
					defaultDate: new Date(),
				},
				endDatePicker: this.endTime()
			}
		},
		mounted() {
			this.heights = this.$tableHeight(this.$refs.myTable.$el)
			this.val.defaultDate.setTime(this.val.defaultDate.getTime() - 3600 * 1000 * 24 * 30)
		},
		methods: {
			//导出数据
			onExport() {
				this.loading = true
				var dateNew = DDmoment().format("yyyy_MM_dd")
				communiExportApi({
					size: this.pagData.pageSize,
					current: this.pagData.currentPage,
					sender: this.search.sender,
					commutimeStart: this.search.date[0],
					commutimeEnd: this.search.date[1],
					gatewayno: this.search.gatewayno,
					lineName: this.search.lineName
				}).then((res) => {
					this.loading = false
					if (this.search.gatewayno != '') {
						exportFile(res, '通信数据_' + this.search.gatewayno + '_' + dateNew).then((data) => {
							if (!data.success) {
								this.$message.error(data.msg)
							}
						})
					} else {
						exportFile(res, '通信数据_' + dateNew).then((data) => {
							if (!data.success) {
								this.$message.error(data.msg)
							}
						})
					}
				})
			},
			//获取表格数据
			getdata(pageData) {
				if (pageData == 'search') {
					this.pagData.currentPage = 1
				}
				if (pageData && pageData != 'search') {
					this.pagData = {
						currentPage: pageData.currentPage,
						pageSize: pageData.pageSize,
					};
				}
				this.loading = true
				communiDataApi({
					size: this.pagData.pageSize,
					current: this.pagData.currentPage,
					sender: this.search.sender,
					commutimeStart: this.search.date[0],
					commutimeEnd: this.search.date[1],
					gatewayno: this.search.gatewayno,
					lineName: this.search.lineName
				}).then((res) => {
					this.loading = false
					if (res.success) {
						this.getData.tableList = res.data
					} else {
						this.$message(res.msg)
					}
				})
			},
			//获取时间
			changeDate(e) {
				this.search.date = e
			},
			//设置时间
			endTime() {
				return {
					disabledDate(time) {
						return time.getTime() > Date.now()
					}
				}
			},
			//重置
			reset() {
				this.pagData.pageSize = 10,
					this.pagData.currentPage = 1,
					this.search.sender = '',
					this.search.date = [DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
						DDmoment().format("yyyy-MM-dd HH:mm:ss")
					]
				this.search.gatewayno = '',
					this.search.lineName = ''
				this.getdata()
			}
		}
	}
</script>

<style lang="scss" scoped>
	.search-form .search-form-item label {
		text-align: right;
		padding-right: 6px;
	}
</style>

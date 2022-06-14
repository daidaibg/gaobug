<template>
  <div class="contents left-tree-content">
    <div class="search-form">
      <div class="search-form-item">
        <label>告警类型:</label>
        <el-select
          size="small"
          clearable
          v-model="search.alertcode"
          placeholder="请选择"
        >
          <el-option
            v-for="item in getData.warnType"
            :key="item.alertcode"
            :label="item.alertname"
            :value="item.alertcode"
          ></el-option>
        </el-select>
      </div>
      <div class="search-form-item">
        <label>网关ID:</label>
        <el-input
          size="small"
          placeholder="请输入网关ID"
          v-model="search.gatewayno"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>终端ID:</label>
        <el-input
          size="small"
          placeholder="请输入终端ID"
          v-model="search.terminalno"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>线路名称:</label>
        <el-input
          size="small"
          placeholder="请输入线路名称"
          v-model="search.circuit"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>归属公司:</label>
        <el-input
          size="small"
          placeholder="请输入归属公司"
          v-model="search.company"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>管理人员:</label>
        <el-input
          size="small"
          placeholder="请输入管理人员"
          v-model="search.manage"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item time">
        <label>时间选择:</label>
        <el-date-picker
          size="small"
          v-model="search.date"
          type="datetimerange"
          range-separator="至"
          unlink-panels
          :default-value="val.defaultDate"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="changeDate"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="endDatePicker"
        >
        </el-date-picker>
      </div>
      <div class="flex align-center rdx-p-l-20">
        <el-button
          size="mini"
          clearable
          :loading="loading"
          type="primary"
          @click="getdata('search')"
          >查询</el-button
        >
        <el-button size="mini" @click="reset"> 重置</el-button>
      </div>
    </div>
    <div
      class="myTable-top-action"
      v-if="
        isAuth('biz:warning:export') ||
        isAuth('biz:warning:delete') ||
        isAuth('biz:warning:relieve')
      "
    >
      <div class="left">
        <el-button type="primary" v-if="isAuth('biz:warning:delete')"
          >删除</el-button
        >
        <el-button
          type="dc"
          v-if="isAuth('biz:warning:export')"
          @click="onExport"
          >导出</el-button
        >
        <el-button type="warning" v-if="isAuth('biz:warning:relieve')"
          >报警解除</el-button
        >
      </div>
    </div>
    <yh-table-pag
      :data="getData.tableList.list"
      style="width: 100%"
      class="my_table"
      @getData="getdata"
      :row-class-name="rowClassName"
      ref="myTable"
      size="small"
      :loading="loading"
      :total="getData.tableList.totalCount"
      :currentPage="pagData.currentPage"
      :height="heights"
    >
      <el-table-column type="selection" align="center" width="55">
      </el-table-column>
      <el-table-column prop="number" label="序号" width="56" align="center">
        <template slot-scope="scope">
          <div>
            {{
              scope.$index + 1 + (pagData.currentPage - 1) * pagData.pageSize
            }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gatewayno" label="网关ID" align="center">
      </el-table-column>
      <el-table-column prop="lineName" label="线路名称" align="center">
      </el-table-column>
      <el-table-column prop="companyName" label="公司名称" align="center">
      </el-table-column>
      <el-table-column prop="alertname" label="告警类型" align="center">
      </el-table-column>
      <el-table-column prop="alertdetail" label="告警内容" align="center">
      </el-table-column>
      <el-table-column prop="createtime" label="告警时间" align="center">
      </el-table-column>
      <el-table-column label="是否短信通知" align="center">
        <template slot-scope="{ row }">
          {{ row.ismessage == 0 ? "否" : "是" }}
        </template>
      </el-table-column>
      <el-table-column prop="alertname" label="类型" align="center">
      </el-table-column>
    </yh-table-pag>
  </div>
</template>

<script>
import {
  tableApi,
  alertconfigApi,
  warnExportApi,
} from "api/modules/warningData";

import {  exportFile } from "@/lib/currency";
import DDmoment from "@/lib/dd-moment";
export default {
  data() {
    return {
      loading: false,
      heights: null,
      getData: {
        tableList: [],
        warnType: [], //告警类型
      },
      search: {
        gatewayno: "", //网关编号（网关ID）
        terminalno: "", //设备id
        alertcode: "", //告警类型
        circuit: "", //线路名称
        company: "", //归属公司
        manage: "", //管理人员
        date: [
          DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
          DDmoment().format("yyyy-MM-dd HH:mm:ss"),
        ], //时间选择
      },
      pagData: {
        pageSize: 10,
        currentPage: 1,
      },
      val: {
        defaultDate: new Date(),
      },
      endDatePicker: this.endTime(),
    };
  },
  mounted() {
    this.heights = this.$tableHeight(this.$refs.myTable.$el);
    this.val.defaultDate.setTime(
      this.val.defaultDate.getTime() - 3600 * 1000 * 24 * 30
    );
    this.getType();
  },
  methods: {
    //导出数据
    onExport() {
      this.loading = true;
      var dateNew = DDmoment().format("yyyy_MM_dd");
      warnExportApi({
        size: this.pagData.pageSize,
        current: this.pagData.currentPage,
        createtimeStart: this.search.date[0],
        createtimeEnd: this.search.date[1],
        realName: this.search.manage,
        alertcode: this.search.alertcode,
        terminalno: this.search.terminalno,
        gatewayno: this.search.gatewayno,
        lineName: this.search.circuit,
        companyName: this.search.company,
      }).then((res) => {
        this.loading = false;
        if (this.search.gatewayno != "") {
          exportFile(
            res,
            "告警数据_" + this.search.gatewayno + "_" + dateNew
          ).then((data) => {
            if (!data.success) {
              this.$message.error(data.msg);
            }
          });
        } else {
          exportFile(res, "告警数据_" + dateNew).then((data) => {
            if (!data.success) {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    //获取表格数据
    getdata(pageData) {
	  if(pageData == 'search'){
	  	this.pagData.currentPage = 1
	  }
	  if (pageData && pageData != 'search') {
	  	this.pagData = {
	  	    currentPage: pageData.currentPage,
	  	    pageSize: pageData.pageSize,
	  	};
	  }
      this.loading = true;
      tableApi({
        size: this.pagData.pageSize,
        current: this.pagData.currentPage,
        createtimeStart: this.search.date[0],
        createtimeEnd: this.search.date[1],
        realName: this.search.manage,
        alertcode: this.search.alertcode,
        terminalno: this.search.terminalno,
        gatewayno: this.search.gatewayno,
        lineName: this.search.circuit,
        companyName: this.search.company,
      }).then((res) => {
        this.loading = false;
        if (res.success) {
          this.getData.tableList = res.data;
        } else {
          this.$message(res.msg);
        }
      });
    },
    //重置
    reset() {
      (this.pagData.pageSize = 10),
        (this.pagData.currentPage = 1),
        (this.search.date = [
          DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
          DDmoment().format("yyyy-MM-dd HH:mm:ss"),
        ]);
      (this.search.manage = ""),
        (this.search.alertcode = ""),
        (this.search.terminalno = ""),
        (this.search.gatewayno = ""),
        (this.search.circuit = ""),
        (this.search.company = "");
      this.getdata();
    },
    //获取告警类型
    getType() {
      alertconfigApi({}).then((res) => {
        if (res.success) {
          this.getData.warnType = res.data;
        } else {
          this.$message(res.msg);
        }
      });
    },
    //获取时间
    changeDate(e) {
      this.search.date = e;
    },
    //设置时间
    endTime() {
      return {
        disabledDate(time) {
          return (
            time.getTime() > Date.now() ||
            time.getTime() < Date.now() - 1000 * 3600 * 24 * 90
          );
        },
      };
    },
  },
};
</script>

<style lang="scss" scoped></style>

<template>
  <div class="contents left-tree-content">
    <div class="search-form">
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
          v-model="search.lineName"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>线路/杆塔:</label>
        <el-input
          size="small"
          placeholder="请输入杆塔号"
          v-model="search.poleName"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>在线状态:</label>
        <el-select
          v-model="search.onlineState"
          placeholder="请选择在线状态"
          size="small"
          clearable
        >
          <el-option
            v-for="item in onlinestateSelect"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
      <el-table-column prop="terminalno" label="终端ID" align="center">
      </el-table-column>

      <el-table-column prop="onlinestate" label="在线状态" align="center">
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.onlinestate == 1 ? 'success' : 'danger'"
            >{{ row.onlinestate == 1 ? "在线" : "离线" }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="poleName" label="线路/杆塔" align="center">
        <template slot-scope="{ row }">
          <div>{{ row.lineName }}/{{ row.poleName }}</div>
        </template>
      </el-table-column>
      <template v-for="item in zdlabelList">
        <el-table-column
          prop="simno"
          :label="item.itemname"
          align="center"
          :key="item.itemcode"
        >
          <template slot-scope="{ row }">
            <div>
              {{ row.data && row.data[item.itemcode] | montionFilter }}
            </div>
          </template>
        </el-table-column>
      </template>
      <el-table-column prop="uploadtime" label="上传时间" align="center" min-width="140">
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        fixed="right"
        min-width="100px"
      >
        <template slot-scope="{ row }">
          <div class="my_table_innerOperation">
            <span @click="history(row)" class="edit">历史数据</span>
          </div>
        </template>
      </el-table-column>
    </yh-table-pag>
  </div>
</template>

<script>
import { terminalDataApi, terminalDelApi } from "api/modules/warningData";
import { monitoritemList } from "api/modules";

import { onlinestateSelect } from "@/config/sys-config";
export default {
  data() {
    return {
      loading: false,
      total: 0,
      heights: null,
      getData: {
        tableList: [],
      },
      zdlabelList: [], //监测列表
      search: {
        onlineState: "", //在线状态
        gatewayno: "", //网关id
        terminalno: "", //终端id
        poleName: "", //线路/杆塔
        lineName: "", //线路名称
      },
      pagData: {
        pageSize: 10,
        currentPage: 1,
      },
      onlinestateSelect: onlinestateSelect,
    };
  },
  created() {
    monitoritemList({ deviceType: 2 }).then((res) => {
      if (res.success) {
        this.zdlabelList = res.data;
      } else {
        this.$message({
          message: res.msg,
          type: "error",
        });
      }
    });
  },
  mounted() {
    this.heights = this.$tableHeight(this.$refs.myTable.$el);
  },
  methods: {
    //点击到历史数据
    history(row) {
      this.$router.push({
        path: "/data-center/terminal-history-data",
        query: {
          terminalno: row.terminalno,
        },
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
      terminalDataApi({
        size: this.pagData.pageSize,
        current: this.pagData.currentPage,
        onlineState: this.search.onlineState,
        gatewayno: this.search.gatewayno,
        terminalno: this.search.terminalno,
        poleName: this.search.poleName,
        lineName: this.search.lineName,
      }).then((res) => {
        this.loading = false;
        if (res.success) {
          this.getData.tableList = res.data;
          this.$nextTick(() => {
            this.$refs.myTable && this.$refs.myTable.doLayout();
          });
        } else {
          this.$message(res.msg);
        }
      });
    },
    //重置
    reset() {
      (this.pagData.pageSize = 10),
        (this.pagData.currentPage = 1),
        (this.search.sender = ""),
        (this.search.date = "");
      (this.search.gatewayno = ""), (this.search.lineName = "");
      this.getdata();
    },
  },
};
</script>

<style lang="scss" scoped>
.search-form .search-form-item label {
  text-align: right;
  padding-right: 6px;
}
</style>

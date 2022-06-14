<!--
 * @Author: daidai
 * @Date: 2021-12-22 11:05:07
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-17 14:10:21
 * @FilePath: \web-pc\src\views\data-center\device-data\device-data.vue
-->
<template>
  <div class="contents">
    <div class="search-form">
      <div class="search-form-item">
        <label>网关ID:</label>
        <el-input
          size="small"
          v-model="dataForm.gatewayno"
          clearable
          placeholder=""
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>线路名称:</label>
        <el-input
          size="small"
          v-model="dataForm.lineName"
          clearable
          placeholder=""
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>杆塔号:</label>
        <el-input
          size="small"
          v-model="dataForm.poleName"
          clearable
          placeholder=""
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>归属公司:</label>
        <el-input
          size="small"
          v-model="dataForm.companyName"
          clearable
          placeholder=""
        ></el-input>
      </div>

      <div class="search-form-item">
        <label>管理人员:</label>
        <el-input
          size="small"
          v-model="dataForm.realName"
          clearable
          placeholder=""
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>电话卡号:</label>
        <el-input
          size="small"
          v-model="dataForm.simno"
          clearable
          placeholder=""
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>在线状态:</label>
        <el-select
          v-model="dataForm.onlineState"
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

      <div class="action flex-center">
        <el-button
          size="mini"
          @click="search"
          clearable
          :loading="loading"
          type="primary"
          >查询</el-button
        >
        <el-button size="mini" @click="reset"> 重置</el-button>
      </div>
    </div>
    <yh-table-pag
      :data="tableData"
      @getData="getData"
      :total="total"
      size="small"
      :loading="loading"
      class="my_table"
      :height="heights"
      ref="myTable"
    >
      <el-table-column prop="idnex" label="序号" width="56" align="center">
        <template slot-scope="scope">
          <div>
            {{ scope.$index + 1 + (pageData.current - 1) * pageData.size }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gatewayno" label="网关ID" align="center">
      </el-table-column>
      <!-- <el-table-column prop="lineName" label="线路名称" align="center">
      </el-table-column> -->
      <el-table-column prop="poleName" label="杆号" align="center">
      </el-table-column>
      <!-- <el-table-column prop="companyName" label="归属公司" align="center">
      </el-table-column>
      <el-table-column prop="realName" label="管理人" align="center">
      </el-table-column>
      <el-table-column prop="simno" label="电话卡号" align="center">
      </el-table-column> -->
      <template v-for="item in labelList">
        <el-table-column
          prop="simno"
          :label="item.itemname"
          align="center"
          :key="item.itemcode"
        >
          <template slot-scope="{ row }">
            <div>
              {{ row.data[item.itemcode] }}
            </div>
          </template>
        </el-table-column>
      </template>
      <el-table-column prop="onlinestate" label="网关状态" align="center">
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.onlinestate == 1 ? 'success' : 'danger'"
            >{{ row.onlinestate == 1 ? "在线" : "离线" }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="isAlert" label="网关报警" align="center">
        <template slot-scope="{ row }">
          <el-tag size="mini" :type="row.isAlert == 1 ? 'success' : 'danger'">{{
            row.isAlert == 1 ? "开启" : "关闭"
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="uploadtime"
        label="上传时间（设备）"
        min-width="140px"
        align="center"
      >
      </el-table-column>
      <!-- <el-table-column
        prop="address"
        label="入库时间"
        min-width="90px"
        align="center"
      >
      </el-table-column> -->
      <el-table-column
        prop="operation"
        label="操作"
        align="center"
        fixed="right"
        min-width="110px"
      >
        <template slot-scope="{ row }">
          <div class="my_table_innerOperation">
            <span @click="info(row)" class="edit">详情</span>
            <span @click="history(row)" class="edit">历史数据</span>
          </div>
        </template>
      </el-table-column>
    </yh-table-pag>
    <DeviceDataInfo ref="DeviceDataInfo" />
  </div>
</template>

<script>
import DDmoment from "@/lib/dd-moment";
import { shortcuts } from "@/lib/currency";

import { getMonitordatagateway } from "api/modules/sjzx";
import { monitoritemList } from "api/modules";

import { onlinestateSelect } from "@/config/sys-config";
import DeviceDataInfo from "./device-data-info.vue";
export default {
  components: { DeviceDataInfo },
  data() {
    return {
      total: 0,
      tableData: [],
      pageData: {
        current: 1,
        size: 10,
      },
      loading: false,
      dataForm: {
        gatewayno: "",
        poleName: "",
        lineName: "",
        realName: "",
        simno: "",
        onlineState: "",
        companyName: "",
      },
      pickerOptions: {
        shortcuts: shortcuts,
        disabledDate: function (time) {
          return (
            time.getTime() > Date.now() ||
            time.getTime() < DDmoment().subtract(1, "years").getTime()
          );
        },
      },
      onlinestateSelect: onlinestateSelect,
      labelList: [],
      heights: null,
    };
  },
  created() {
    monitoritemList({ deviceType: 1 }).then((res) => {
      if (res.success) {
        this.labelList = res.data;
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
    getData(pageData) {
      if (pageData) {
        this.pageData = {
          current: pageData.currentPage,
          size: pageData.pageSize,
        };
      }
      this.loading = true;
      getMonitordatagateway({
        ...this.pageData,
        ...this.dataForm,
      }).then((res) => {
        this.loading = false;
        if (res.success) {
          this.tableData = res.data.list;
          this.total = res.data.totalCount;
          this.$nextTick(() => {
            this.$refs.myTable && this.$refs.myTable.doLayout();
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    history(row) {
      console.log(row);
      this.$router.push({
        path: "/data-center/device-history-data",
        query: {
          deviceID: row.gatewayno,
        },
      });
    },
    info(row) {
      this.$refs.DeviceDataInfo.init(row, this.labelList);
    },
    search() {
      this.pageData.current = 1;
      this.getData();
    },
    add(type, row) {
      this.$refs.AddOrUpDate.init(type, row);
    },
    reset() {
      for (let i in this.dataForm) {
        this.dataForm[i] = "";
      }
      this.search();
    },
  },
};
</script>
<style lang="scss" scoped></style>

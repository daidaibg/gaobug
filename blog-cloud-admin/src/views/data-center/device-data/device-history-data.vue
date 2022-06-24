<!--
 * @Author: daidai
 * @Date: 2021-12-23 11:32:30
 * @LastEditors: daidai
 * @LastEditTime: 2022-03-30 17:25:40
 * @FilePath: \web-pc\src\views\data-center\device-data\device-history-data.vue
-->

<template>
  <div class="contents">
    <div class="search-form">
      <div class="search-form-item">
        <label>设备ID:</label>
        <el-input
          size="small"
          v-model="dataForm.gatewayno"
          clearable
          placeholder=""
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>数据类型:</label>
        <el-select
          v-model="dataForm.dataType"
          placeholder="请选择设备类型"
          size="small"
        >
          <el-option
            v-for="item in deviceoptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
      <div class="search-form-item time">
        <label>时间选择:</label>
        <el-date-picker
          size="small"
          value-format="yyyy-MM-dd HH:mm:ss"
          v-model="dataForm.time"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          :picker-options="pickerOptions"
          end-placeholder="结束日期"
        >
        </el-date-picker>
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

    <div class="myTable-top-action" v-if="isAuth('biz:gatewaydata:export')">
      <div class="left">
        <!-- <el-button type="primary">删除</el-button> -->
        <el-button
          @click="dc()"
          type="dc"
          :loading="loadings.dc"
          v-if="isAuth('biz:gatewaydata:export')"
          >导出</el-button
        >
      </div>
    </div>
    <yh-table-pag
      :data="tableData"
      @getData="getData"
      :total="total"
      size="small"
      :loading="loading"
      :inPageGetData="false"
      class="my_table"
      :currentPage="pageData.current"
      ref="myTable"
      :height="heights"
    >
      <el-table-column prop="idnex" label="序号" width="56" align="center">
        <template slot-scope="scope">
          <div>
            {{ scope.$index + 1 + (pageData.current - 1) * pageData.size }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gatewayno" label="设备ID" align="center">
      </el-table-column>
      <!-- <el-table-column prop="lineName" label="线路名称" align="center">
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

      <el-table-column prop="address" label="网关状态" align="center">
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.onlinestate == 1 ? 'success' : 'danger'"
            >{{ row.onlinestate == 1 ? "在线" : "离线" }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        prop="uploadtime"
        label="上传时间（设备）"
        min-width="90px"
        align="center"
      >
      </el-table-column>
      <!-- <el-table-column
        prop="createtime"
        label="入库时间"
        min-width="90px"
        align="center"
      >
      </el-table-column> -->
    </yh-table-pag>
  </div>
</template>

<script>
import DDmoment from "@/lib/dd-moment";

import { shortcuts } from "@/lib/currency";

import { GetMonitordatagatewayhistory } from "api/modules/sjzx";
import { monitoritemList, currencyExport } from "api/modules";
import { onlinestateSelect } from "@/config/sys-config";
export default {
  data() {
    return {
      total: 0,
      tableData: [],
      pageData: {
        current: 1,
        size: 10,
      },
      loading: false,
      loadings: {
        dc: false,
      },
      dataForm: {
        gatewayno: "",
        lineName: "",
        dataType: "",
        time: [
          DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
          DDmoment().format("yyyy-MM-dd HH:mm:ss"),
        ],
      },
      onlinestateSelect: onlinestateSelect,
      pickerOptions: {
        shortcuts: shortcuts,
        disabledDate: function (time) {
          if (time.getTime() > Date.now()) {
            return true;
          } else if (
            time.getTime() < DDmoment().subtract(91, "days").getTime()
          ) {
            return true;
          } else {
            return false;
          }
        },
      },
      deviceoptions: [
        {
          value: "",
          label: "全部",
        },
      ],
      labelList: [],
      heights: null,
    };
  },
  created() {

    monitoritemList({ deviceType: 1 }).then((res) => {
      if (res.success) {
        this.labelList = res.data;
        this.getData();
      } else {
        this.$message({
          message: res.msg,
          type: "error",
        });
      }
    });
  },

  mounted() {
        let id = this.$route.query.deviceID;
    if (id) {
      this.dataForm.gatewayno = id;
      this.id = id;
    }
  },
  methods: {
    getData(pageData) {
      if (pageData) {
        this.pageData = {
          current: pageData.currentPage,
          size: pageData.pageSize,
        };
      }
      let param = {
        ...this.dataForm,
        ...this.pageData,
      };
      if (this.dataForm.time) {
        param.startTime = this.dataForm.time[0];
        param.endTime = this.dataForm.time[1];
      }
      delete param.time;
      GetMonitordatagatewayhistory(param).then((res) => {
        if (res.success) {
          this.tableData = res.data.list;
          this.total = res.data.totalCount;
          this.$nextTick(() => {
            if (!this.heights) {
              this.heights = this.$tableHeight(this.$refs.myTable.$el);
            }
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    dc() {
      let param = {
        ...this.dataForm,
      };
      if (this.dataForm.time) {
        param.startTime = this.dataForm.time[0];
        param.endTime = this.dataForm.time[1];
        let cha =
          DDmoment(param.endTime).time.getTime() -
          DDmoment(param.startTime).time.getTime();
        let chaDay = cha / 1000 / 60 / 60 / 24;
        if (chaDay > 30) {
          this.$message.warning("导出日期相差不能超过30天！");
          return;
        }
      } else {
        this.$message.warning("请先选择日期范围，相差不能超过30天！");
        return;
      }
      delete param.time;
      this.loadings.dc = true;

      let str = "";
      if (param.gatewayno) {
        str += param.gatewayno + "-";
      }
      // if( param.startTime){
      //   str+= param.startTime+'-'+ param.endTime+'-'
      // }
      currencyExport(7, param, str + "网关历史数据").then((data) => {
        this.loadings.dc = false;
        if (!data.success) {
          this.$message.error(data.msg);
        }
      });
    },
    search() {
      console.log(this.dataForm);
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
      if (this.id) {
        this.dataForm.gatewayno = this.id;
      }
      this.dataForm.time = [
        DDmoment().subtract(1, "months").format("yyyy-MM-dd HH:mm:ss"),
        DDmoment().format("yyyy-MM-dd HH:mm:ss"),
      ];
      this.search();
    },
  },
};
</script>
<style lang='scss' scoped>
</style>
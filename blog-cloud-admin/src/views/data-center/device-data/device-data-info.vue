<!--
 * @Author: daidai
 * @Date: 2022-01-07 09:48:44
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-11 15:29:00
 * @FilePath: \web-pc\src\views\data-center\device-data\device-data-info.vue
-->
<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
    :visible.sync="visible"
    v-dialogDrag
    width="1080px"
  >
    <el-descriptions class="margin-top" :column="4" size="small" border>
      <el-descriptions-item label="网关" :labelStyle="{ width: '80px' }">
        {{ info.gatewayno }}
      </el-descriptions-item>
      <el-descriptions-item label="电话卡号" :labelStyle="{ width: '80px' }">
        {{ info.simno }}
      </el-descriptions-item>
  
      <el-descriptions-item>
        <template slot="label"> 在线状态 </template>
        <el-tag
          size="mini"
          :type="info.onlinestate == 1 ? 'success' : 'danger'"
          >{{ info.onlinestate == 1 ? "在线" : "离线" }}</el-tag
        >
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"> 管理人 </template>
        {{ info.realName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label"> 线路名称 </template>
        {{ info.lineName }}
      </el-descriptions-item>
      <el-descriptions-item label="杆塔名称">
        {{ info.poleName }}
      </el-descriptions-item>

      <el-descriptions-item >
        <template slot="label"> 公司 </template>
        {{ info.companyName }}
      </el-descriptions-item>
          <el-descriptions-item label="更新时间" :labelStyle="{ width: '80px' }">
        {{ info.uploadtime }}
      </el-descriptions-item>
      <el-descriptions-item :span="3" label="监测数据" contentClassName="jcsj-wrap">
        <ul class="jcsj flex flex-wrap">
          <li
            v-for="(item, index) in labelList"
            :key="index"
            class="flex flex-direction"
          >
            <span class="labels">{{ item.itemname }}</span>
            <span class="datas"> {{ info.data[item.itemcode] |montionFilter }}</span>
          </li>
        </ul>
      </el-descriptions-item>
      <!-- <el-descriptions-item v-for="(item, index) in labelList" :key="index">
        <template slot="label"> {{item.itemname}} </template>
        {{ info.data[item.itemcode] }}
      </el-descriptions-item> -->
    </el-descriptions>
    <h2 class="left_shu_h2">终端实时数据</h2>
    <el-table
      :data="tableData"
      size="mini"
      border
      v-loading="loading.zdLoading"
      element-loading-text="拼命加载中..."
      element-loading-spinner="el-icon-loading"
      class="my_table"
      style="width: 100%; margin-bottom: 16px"
    >
      <el-table-column prop="terminalno" label="终端ID" align="center">
      </el-table-column>
      <el-table-column prop="phase" label="相线" align="center" width="50">
      </el-table-column>
      <el-table-column
        prop="onlinestate"
        align="center"
        label="在线状态"
        width="70"
      >
        <template slot-scope="{ row }">
          <div>
            <el-tag
              size="mini"
              :type="row.onlinestate == 1 ? 'success' : 'danger'"
              >{{ row.onlinestate == 1 ? "在线" : "离线" }}</el-tag
            >
          </div>
        </template>
      </el-table-column>
      <template v-for="item in zdlabelList">
        <el-table-column
          prop="simno"
          :label="item.itemname"
          align="center"
          :key="item.itemcode"
          :min-width="item.itemname.length > 5 ? item.itemname.length * 15.8 +'px': ''"
        >
          <template slot-scope="{ row }">
            <div>
              {{ row.data && row.data[item.itemcode] | montionFilter }}
            </div>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <!-- <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取消</el-button>
      <el-button
        type="primary"
        @click="dataFormSubmit()"
        size="small"
        :loading="loading"
        >确定</el-button
      >
    </span> -->
  </el-dialog>
</template>

<script>
import { terminalShowApi } from "api/modules/home";
import { monitoritemList } from "api/modules";

export default {
  data() {
    return {
      visible: false,
      title: "详情",
      info: {},
      labelList: [],
      tableData: [],
      zdlabelList: [],
      loading: {
        zdLoading: false,
      },
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

  mounted() {},
  methods: {
    init(row, labelList) {
      // console.log(row, labelList);
      this.visible = true;
      this.title = row.gatewayno + "-实时数据详情";
      this.info = { ...row };
      this.labelList = labelList;
      this.getZd(row.deviceId);
    },
    getZd(deviceId) {
      this.loading.zdLoading = true;
      terminalShowApi({ deviceId }).then((res) => {
        // console.log('终端列表',res)
        this.loading.zdLoading = false;
        if (res.success) {
          this.tableData = res.data;
          
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
  },
};
</script>
<style lang='scss' scoped>
.jcsj {
  li {
    font-size: 14px;
    box-sizing: border-box;
    padding: 0 4px 4px 0;
    flex: auto;
    text-align: center;
    .labels {
      // color: #000;
    }
    .datas {
      margin-top: 6px;
      font-weight: 900;
    }
    & + li {
      border-left: solid 1px rgba($color: #ebeef5, $alpha: 1);
    }
  }
}
.jcsj-wrap{

}
</style>

<!--
 * @Author: daidai
 * @Date: 2021-12-13 16:35:02
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 16:03:22
-->
<template>
  <div class="contents terminal">
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
          v-model="dataForm.onlinestate"
          placeholder="请选择"
          size="small"
          @change="search"
        >
          <el-option
            v-for="item in isactiveOp"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
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
        <label>所属公司:</label>
        <el-input
          size="small"
          v-model="dataForm.companyName"
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
        <label>杆号名称:</label>
        <el-input
          size="small"
          v-model="dataForm.poleName"
          clearable
          placeholder=""
        ></el-input>
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
    <div
      class="myTable-top-action"
      v-if="
        isAuth('biz:gateway:save') ||
        isAuth('biz:gateway:delete') ||
        isAuth('biz:gateway:export') ||
        isAuth('biz:gateway:import')
      "
    >
      <div class="left">
        <el-button
          @click="add('add')"
          v-if="isAuth('biz:gateway:save')"
          type="primary"
          plain
          >添加</el-button
        >
        <el-button
          @click="delets('all')"
          v-if="isAuth('biz:gateway:delete')"
          type="primary"
          plain
          >批量删除</el-button
        >
        <el-button
          @click="dc()"
          type="dc"
          :loading="loadings.dc"
          v-if="isAuth('biz:gateway:export')"
          >导 出</el-button
        >
        <el-button
          @click="dr()"
          type="dr"
          :loading="loadings.dr"
          v-if="isAuth('biz:gateway:import')"
          >导 入</el-button
        >
      </div>
    </div>
    <yh-table-pag
      :data="tableData"
      @getData="getdata"
      :total="total"
      style="width: 100%"
      class="my_table"
      :row-class-name="rowClassName"
      ref="myTable"
      :loading="loading"
      loading-background="rgba(255, 255, 255, 0.4)"
      size="small"
      :height="heights"
      :currentPage="currentPage"
      @selection-change="handleSelectionChange"
      :span-method="objectSpanMethod"
    >
      <el-table-column type="selection" align="center" width="55">
      </el-table-column>
      <el-table-column prop="number" label="序号" width="56" align="center">
        <template slot-scope="scope">
          <div>
            {{ scope.$index + 1 + (currentPage - 1) * pageSize }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gatewayno" label="网关ID" align="center">
      </el-table-column>
      <el-table-column prop="terminalNum" label="终端数量" align="center">
        <template slot-scope="{ row }">
          <div class="terminalNum_class" @click="goterminal(row)">
            {{ row.terminalNum }}
          </div>
        </template>
      </el-table-column>

      <el-table-column
        prop="simno"
        label="电话卡号"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="lineName"
        label="线路名称"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="poleName"
        label="杆号"
        align="center"
      ></el-table-column>
      <!-- <el-table-column prop="x" label="经度" align="center"></el-table-column>
      <el-table-column prop="y" label="纬度" align="center"></el-table-column> -->
      <el-table-column
        prop="companyName"
        label="归属公司"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="realName"
        label="管理人员"
        align="center"
      ></el-table-column>
      <!-- <el-table-column prop="isactive" label="激活状态" align="center">
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.isactive == 0 ? 'success' : 'danger'"
            >{{ row.isactive == 0 ? "已激活" : "未激活" }}</el-tag
          >
        </template>
      </el-table-column> -->
      <el-table-column prop="onlinestate" label="在线状态" align="center">
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.onlinestate == 1 ? 'success' : 'danger'"
            >{{ row.onlinestate == 1 ? "在线" : "离线" }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="isAlert" label="网关报警" align="center">
        <template slot-scope="{ row, $index }">
          <template v-if="isAuth('biz:gateway:alarm')">
            <el-switch
              v-model="row.isAlert"
              class="switchStyle"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="1"
              :inactive-value="0"
              active-text="开"
              inactive-text="关"
              @change="(flag) => updateStatus(flag, row, $index)"
            >
            </el-switch>
          </template>
          <template v-else>
            <el-tag
              size="mini"
              :type="row.isAlert == 1 ? 'success' : 'danger'"
              >{{ row.isAlert == 1 ? "开启" : "关闭" }}</el-tag
            >
          </template>
        </template>
      </el-table-column>
      <el-table-column prop="imgUrlList" label="图片" align="center">
        <template slot-scope="{ row }">
          <el-image
            v-if="row.imgUrlList.length > 0"
            :src="row.imgUrlList[0].filePath"
            :preview-src-list="srclist(row.imgUrlList)"
            fit="cover"
            class="imggs"
          >
          </el-image>
        </template>
      </el-table-column>
      <el-table-column
        prop="remark"
        label="备注"
        align="center"
        min-width="100px"
      ></el-table-column>
      <el-table-column
        prop="createtime"
        label="创建时间"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="operation"
        label="操作"
        align="center"
        fixed="right"
        width="110px"
      >
        <template slot-scope="scope">
          <div class="my_table_innerOperation">
            <span @click="devaiceparam(scope.row)" class="edit">设备参数</span>
            <span
              @click="add('edit', scope.row)"
              class="edit"
              v-if="isAuth('biz:gateway:update')"
              style="color: #18d1ff"
              >编辑</span
            >
            <!-- <el-tooltip
              class="item icon"
              effect="dark"
              content="编辑"
              placement="top"
            >
              <span
                @click="add('edit', scope.row)"
                class="edit el-icon-edit icon"
                v-if="isAuth('biz:gateway:update')"
                style="color: #18d1ff; font-size: 14px"
              ></span>
            </el-tooltip> -->
            <span @click="history(scope.row)" class="edit">历史数据</span>
            <span
              v-if="isAuth('biz:gateway:delete')"
              @click="delets('one', scope.row)"
              class="del"
              >删除</span
            >
            <!-- <el-tooltip
              class="item icon"
              effect="dark"
              content="删除"
              placement="top"
            >
              <span
                v-if="isAuth('biz:gateway:delete')"
                @click="delets('one', scope.row)"
                class="del el-icon-delete "
                style="font-size: 14px"
              ></span>
            </el-tooltip> -->
          </div>
        </template>
      </el-table-column>
    </yh-table-pag>
    <AddOrUpDate ref="AddOrUpDate" />
    <Terminal ref="terminal" />
    <DeviceParam ref="deviceParam" />
    <UploadXlsx
      class="lineupload"
      ref="UploadXlsx"
      :action="action"
      @success="uploadsuccess"
      @error="uploaderror"
      @progress="progress"
    />
  </div>
</template>

<script>
import {
  currentList,
  currentDelete,
  currencyExport,
} from "api/modules";
import { deletsFun } from "@/lib/modules/table-list.js";
import { onlinestateSelect } from "@/config/sys-config";
import { uploadgatewayUrl } from "api/modules/upload";
import { gatewayOpenOrCloseAlert } from "api/modules/device-msg";

// 组件
import AddOrUpDate from "./device-add-or-update.vue";
import Terminal from "../terminal/terminal.vue";
import DeviceParam from "./device-parma.vue";
import UploadXlsx from "@/components/uploads/upload-xlsx";
export default {
  components: { AddOrUpDate, Terminal, DeviceParam, UploadXlsx },
  data() {
    return {
      dataForm: {
        simno: "",
        gatewayno: "",
        onlinestate: "",
        realName: "",
        companyName: "",
        lineName: "",
        poleName: "",
      },
      currentPage: 1,
      pageSize: 10,
      loading: false,
      total: 0,
      tableData: [],
      heights: null,
      isactiveOp: onlinestateSelect,
      multipleSelection: [],
      loadings: {
        dc: false,
        dr: false,
      },
      action: "",
    };
  },
  created() {},

  mounted() {
    this.heights = this.$tableHeight(this.$refs.myTable.$el);
  },
  methods: {
    // 是否开启报警
    updateStatus(flag, row) {
      let str = "开启";
      let type = "open";
      // console.log(row);
      if (row.isAlert == 1) {
        row.isAlert = 0;
      } else {
        row.isAlert = 1;
        str = "禁用";
        type = "close";
      }
      // console.log(flag, row, str);
      this.$confirm(`确定${str + row.gatewayno}网关报警吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: type == "open" ? "warning" : "error",
      })
        .then((res) => {
          // console.log(res);
          this.loading = true;
          gatewayOpenOrCloseAlert({
            id: row.id,
            isAlert: flag,
          }).then((res) => {
            // console.log(res)
            if (res.success) {
              this.getdata();
              this.$message({
                message: str + "成功",
                type: "success",
              });
            } else {
              this.loading = false;
              this.$message({
                message: res.msg,
                type: "error",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消" + str + "账号",
          });
        });
    },
    dr() {
      this.action = uploadgatewayUrl();
      document.querySelector(".lineupload input").click();
    },
    progress() {
      this.loadings.dr = true;
    },
    uploadsuccess(res) {
      // this.loadings.dr = false;
      this.getdata();
      this.$message({
        message: res.msg,
        type: "success",
      });
    },
    uploaderror() {
      this.loadings.dr = false;
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      // console.log( row, column, rowIndex, columnIndex )
      // return {
      //     rowspan: 2,
      //     colspan: 1
      //   };
    },
    history(row) {
      this.$router.push({
        path: "/data-center/device-history-data",
        query: {
          deviceID: row.gatewayno,
        },
      });
    },
    //终端列表
    goterminal(row) {
      this.$refs.terminal.init(row);
    },
    srclist(imgUrlList) {
      let arr = [];
      imgUrlList.map((item) => {
        arr.push(item.filePath);
      });
      return arr;
    },
    dc() {
      this.loadings.dc = true;
      let str = "";
      if (this.dataForm.companyName !== "") {
        str += this.dataForm.companyName + "-";
      }
      currencyExport(
        1,
        {
          size: this.pageSize,
          current: this.currentPage,
          ...this.dataForm,
        },
        str + "网关列表"
      ).then((res) => {
        this.loadings.dc = false;
        if (!res.success) {
          this.$message.error(res.msg);
        }
      });
    },
    async getdata(pageData) {
      try {
        this.loading = true;
        this.multipleSelection = [];
        // console.log(pageData);
        if (pageData) {
          this.pageSize = pageData.pageSize;
          this.currentPage = pageData.currentPage;
        }
        await currentList(1, {
          size: this.pageSize,
          current: this.currentPage,
          ...this.dataForm,
        }).then((res) => {
          if (res.success) {
            this.tableData = res.data.list;
            this.total = res.data.totalCount;
          } else {
            this.$message.error(res.msg);
          }
        });
      } catch (error) {}
      this.loading = false;
    },
    devaiceparam(row) {
      this.$refs.deviceParam.init(row);
    },
    add(type, row) {
      this.$refs.AddOrUpDate.init(type, row);
    },
    search() {
      this.currentPage = 1;
      this.getdata();
    },
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSelection = val;
    },
    //删除
    delets(type, row) {
      // console.log(row)
      deletsFun(
        {
          name: "gatewayno",
          id: "id",
          type: 1,
          fun: currentDelete,
          isMessage: true,
        },
        row,
        this.multipleSelection
      ).then((res) => {
        if (res.success) {
          this.getdata();
        }
      });
    },
    reset() {
      for (let i in this.dataForm) {
        this.dataForm[i] = "";
      }
      this.currentPage = 1;
      this.getdata();
    },
  },
};
</script>
<style lang='scss' scoped>
.terminal {
  position: relative;
}
.logs {
  height: 100%;
}
.imggs {
  width: 50px;
  height: 50px;
  border-radius: 6px;
}
.terminalNum_class {
  color: $primary-color;;
  cursor: pointer;
  font-size: 14px;
  font-weight: 900;
}
</style>
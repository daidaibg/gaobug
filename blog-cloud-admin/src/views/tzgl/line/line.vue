<!--
 * @Author: daidai
 * @Date: 2021-12-13 16:35:02
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 15:44:32
-->
<template>
  <div class="contents lines">
    <div>
      <div class="search-form">
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
          <label>所属区域:</label>
          <RegionTreeCascader
            style="width: 100%"
            @handleNodeClick="handleNodeClick"
            ref="regionTree"
            :isDelOne="false"
            size="small"
          />
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
          <label>线路管理员:</label>
          <el-input
            size="small"
            v-model="dataForm.realName"
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
          isAuth('biz:line:save') ||
          isAuth('biz:line:delete') ||
          isAuth('biz:line:export') ||
          isAuth('biz:line:import')
        "
      >
        <div class="left">
          <!-- icon="el-icon-circle-plus-outline" -->

          <el-button
            @click="add('add')"
            v-if="isAuth('biz:line:save')"
            type="primary"
            plain
            >添加</el-button
          >
          <!-- icon="el-icon-circle-close" -->
          <el-button
            @click="delets('all')"
            v-if="isAuth('biz:line:delete')"
            type="primary"
            plain
            >批量删除</el-button
          >
          <el-button
            @click="dc()"
            type="dc"
            plain
            :loading="loadings.dcloading"
            v-if="isAuth('biz:line:export')"
            >导 出</el-button
          >
          <el-button
            @click="dr()"
            type="dr"
            plain
            :loading="loadings.drloading"
            v-if="isAuth('biz:line:import')"
            >导 入</el-button
          >
        </div>
        <div class="right"></div>
      </div>
      <yh-table-pag
        :data="tableData"
        @getData="getData"
        :total="total"
        style="width: 100%"
        class="my_table"
        :row-class-name="rowClassName"
        ref="myTable"
        :loading="loading"
        @selection-change="handleSelectionChange"
        loading-background="rgba(255, 255, 255, 0.4)"
        size="small"
        :height="heights"
        :currentPage="currentPage"
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
        <el-table-column
          prop="name"
          label="线路名称"
          align="center"
          min-width="100"
        ></el-table-column>
        <el-table-column prop="poleNum" label="杆号数" align="center">
          <template slot-scope="{ row }">
            <div class="terminalNum_class" @click="gotpoleNum(row)">
              {{ row.poleNum }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="companyName"
          label="归属公司"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="provinceName"
          label="所属区域"
          align="center"
          min-width="120px"
        >
          <template slot-scope="{ row }">
            <div>
              <span v-if="row.provinceName"> {{ row.provinceName }} </span>
              <span v-if="row.cityName"> /{{ row.cityName }} </span>
              <span v-if="row.countyName"> /{{ row.countyName }} </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="startname"
          label="线路起点"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="endname"
          label="线路终点"
          align="center"
        ></el-table-column>

        <el-table-column
          prop="deviceNum"
          label="设备数"
          align="center"
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
          min-width="100px"
          v-if="isAuth('biz:line:update') || isAuth('biz:line:delete')"
        >
          <template slot-scope="{ row }">
            <div class="my_table_innerOperation">
              <span
                @click="add('edit', row)"
                v-if="isAuth('biz:line:update')"
                class="edit"
                >编辑</span
              >
              <span
                v-if="isAuth('biz:line:delete')"
                @click="delets('one', row)"
                class="del"
              >
                删除</span
              >
            </div>
          </template>
        </el-table-column>
      </yh-table-pag>
    </div>
    <PoleList ref="PoleList" />
    <LineAdd ref="add" />
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
import { currentList, currentDelete, currencyExport } from "api/modules";
import RegionTreeCascader from "@/components/trees/region-cascader.vue";
import PoleList from "./pole-list.vue";
import LineAdd from "./line-add.vue";
import { deletsFun } from "@/lib/modules/table-list.js";
import UploadXlsx from "@/components/uploads/upload-xlsx";
import { uploadLineUrl } from "api/modules/upload";

export default {
  components: { RegionTreeCascader, PoleList, LineAdd, UploadXlsx },
  data() {
    return {
      dataForm: {
        companyName: "",
        lineName: "",
        realName: "",
        cityid: "", //市
        countyid: "", //区
        provinceid: "", //省
      },
      shqy: [],
      currentPage: 1,
      pageSize: 10,
      loading: false,
      total: 0,
      tableData: [],
      heights: null,
      multipleSelection: [],
      pathLabels: null,
      loadings: {
        drloading: false,
        dcloading: false,
      },
      action: "",
    };
  },
  created() {},

  mounted() {
    this.heights = this.$tableHeight(this.$refs.myTable.$el);
  },
  methods: {
    //去杆塔列表
    gotpoleNum(row) {
      this.$refs.PoleList.init(row);
    },
    getData(pageData) {
      //   console.log(pageData);
      this.multipleSelection = [];
      if (pageData) {
        this.pageSize = pageData.pageSize;
        this.currentPage = pageData.currentPage;
      }
      this.loading = true;
      currentList(2, {
        size: this.pageSize,
        current: this.currentPage,
        ...this.dataForm,
      }).then((res) => {
        this.loading = false;
        // console.log(res);
        if (res.success) {
          this.tableData = res.data.list;
          this.total = res.data.totalCount;
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    dr() {
      this.action = uploadLineUrl();
      document.querySelector(".lineupload input").click();
    },
    progress() {
      this.loadings.drloading = true;
    },
    uploadsuccess(res) {
      this.loadings.drloading = false;
      this.getData();
      this.$message({
        message: res.msg,
        type: "success",
      });
    },
    uploaderror() {
      this.loadings.drloading = false;
    },
    dc() {
      let str = "";
      if (this.pathLabels) {
        str += this.pathLabels.join("-");
        str += "-";
      }
      this.loadings.dcloading = true;
      currencyExport(2,
        {
          size: this.pageSize,
          current: this.currentPage,
          ...this.dataForm,
        },
        str + "线路列表"
      ).then((data) => {
        // console.log(data)
        this.loadings.dcloading = false;
        if (!data.success) {
          this.$message.error(data.msg);
        }
      });
    },
    add(type, row) {
      this.$refs.add.init(type, row);
    },
    delets(type, row) {
      deletsFun(
        {
          name: "name",
          id: "id",
          fun: currentDelete,
          isMessage: true,
          type: 2,
        },
        row,
        this.multipleSelection
      )
        .then((res) => {
          // console.log(res);
          if (res.success) {
            this.getData();
          }
        })
        .catch((err) => {});
    },
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSelection = val;
    },
    handleNodeClick(val, node) {
      // console.log(node);
      (this.dataForm.provinceid = val[1] || ""), //省
        (this.dataForm.cityid = val[2] || ""), //市
        (this.dataForm.countyid = val[3] || ""); //区
      this.pathLabels = node ? node.pathLabels : null;
      this.getData();
    },
    search() {
      this.currentPage = 1;
      this.getData();
    },
    reset() {
      this.dataForm = {
        companyName: "",
        lineName: "",
        realName: "",
      };
      this.$refs.regionTree.clearn();
      this.currentPage = 1;
      this.getData();
    },
  },
};
</script>
<style lang='scss' scoped>
.lines {
  position: relative;
}
.logs {
  height: 100%;
}
.terminalNum_class {
  color: $primary-color;;
  cursor: pointer;
  font-size: 14px;
  font-weight: 900;
}
</style>
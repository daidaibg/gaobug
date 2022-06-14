<template>
  <transition name="yh-zoom-in-right">
    <div v-show="visible" class="edit_line">
      <h1>
        <i class="el-icon-back" @click="visible = false">返回</i> / {{ title }}
      </h1>
      <!-- <h2><div></div>杆塔列表</h2> -->
      <div
        class="myTable-top-action"
        v-if="
          isAuth('biz:pole:save') ||
          isAuth('biz:pole:delete') ||
          isAuth('biz:pole:export') ||
          isAuth('biz:pole:import')
        "
      >
        <div class="left">
          <el-button
            @click="addorUpdate('add')"
            v-if="isAuth('biz:pole:save')"
            type="primary"
            plain
            >添加</el-button
          >
          <el-button
            @click="delets('all')"
            v-if="isAuth('biz:pole:delete')"
            type="primary"
            plain
            >批量删除</el-button
          >
          <el-button
            @click="dc()"
            type="dc"
            plain
            :loading="loadings.dcloading"
            v-if="isAuth('biz:pole:export')"
            >导 出</el-button
          >

          <el-button
            @click="dr()"
            type="dr"
            plain
            :loading="loadings.drloading"
            v-if="isAuth('biz:pole:import')"
            >导 入</el-button
          >
        </div>
      </div>
      <yh-table-pag
        :data="tableData"
        @getData="getPole"
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
        :inPageGetData="false"
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
          label="杆塔名称/号别"
          align="center"
          min-width="100"
        ></el-table-column>
        <el-table-column
          prop="type"
          label="结构形式"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="model"
          label="型号规格"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="runtime"
          label="投运时间"
          align="center"
        ></el-table-column>
        <!-- <el-table-column
          prop="sort"
          label="排序"
          align="center"
        ></el-table-column> -->
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
                @click="addorUpdate('edit', row)"
                class="edit"
                v-if="isAuth('biz:pole:update')"
                >编辑</span
              >
              <span
                v-if="isAuth('biz:pole:delete')"
                @click="delets('one', row)"
                class="del"
                >删除</span
              >
            </div>
          </template>
        </el-table-column>
      </yh-table-pag>

      <PoleAddOrUpdate ref="addUpdate" />
      <UploadXlsx
        ref="UploadXlsx"
        :action="action"
        @success="uploadsuccess"
        @error="uploaderror"
        @progress="uploadprogress"
      />
    </div>
  </transition>
</template>

<script>
import { currencyExport,currentPage, currentDelete  } from "api/modules";
import PoleAddOrUpdate from "./pole-add-or-update.vue";
import { uploadPoleUrl } from "api/modules/upload";
import UploadXlsx from "@/components/uploads/upload-xlsx";
import { deletsFun } from "@/lib/modules/table-list.js";

export default {
  components: { PoleAddOrUpdate, UploadXlsx },
  data() {
    return {
      visible: false,
      title: "编辑",
      loading: false,
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 10,
      multipleSelection: [],
      row: {},
      heights: null,
      action: "",
      loadings: {
        drloading: false,
        dcloading: false,
      },
    };
  },
  mounted() {},
  methods: {
    init(row) {
      this.visible = true;
      // console.log(row);
      this.title = row.name + "-杆塔列表";
      this.row = { ...row };
      this.getPole();
    },
    eaitGetPole() {
      this.getPole();
      this.$parent.getData();
    },

    //获取杆塔列表
    getPole(pageData) {
      this.multipleSelection = [];
      if (pageData) {
        this.pageSize = pageData.pageSize;
        this.currentPage = pageData.currentPage;
      }
      currentPage(6,{
        size: this.pageSize,
        current: this.currentPage,
        lineId: this.row.id,
      }).then((res) => {
        // console.log(res)
        if (res.success) {
          this.tableData = res.data.list;
          this.total = res.data.totalCount;
          if (!this.heights) {
            this.$nextTick(() => {
              this.heights = this.$tableHeight(this.$refs.myTable.$el);
            });
          }
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    dr() {
      this.action = uploadPoleUrl(this.row.id);
      document.querySelector(".upload-xlsx input").click();
    },
    uploadprogress() {
      this.loadings.drloading = true;
    },
    uploadsuccess(res) {
      this.loadings.drloading = false;
      this.eaitGetPole();

      this.$message({
        message: res.msg,
        type: "success",
      });
    },
    uploaderror() {
      this.loadings.drloading = false;
      this.eaitGetPole();
    },
    dc() {
      this.loadings.dcloading = true;
      currencyExport(6, {
        lineId: this.row.id,
      },"杆塔列表").then((data) => {
          // console.log(data)
          this.loadings.dcloading = false;

          if (!data.success) {
            this.$message.error(data.msg);
          }
        });
    },
    delets(type, row) {
      deletsFun(
        {
          name: "name",
          id: "id",
          fun: currentDelete,
          isMessage: true,
          type:6
        },
        row,
        this.multipleSelection
      ).then((res) => {
        if (res.success) {
          this.eaitGetPole();
        }
      });
    },
    addorUpdate(type, row) {
      this.$nextTick(() => {
        this.$refs.addUpdate.init(type, row);
      });
    },
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSelection = val;
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/css/modules/device-terminal";
</style>
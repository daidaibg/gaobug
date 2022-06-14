<!--
 * @Author: daidai
 * @Date: 2021-12-22 09:31:24
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 15:33:28
 * @FilePath: \web-pc\src\views\sys\upgrade-package\upgrade-package.vue
-->
<template>
  <div class=" contents">
    <div class="search-form">
      <div class="search-form-item">
        <label>版本名称:</label>
        <el-input
          size="small"
          v-model="dataForm.gatewayno"
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
        isAuth('biz:upgradepackage:save') || isAuth('biz:upgradepackage:delete')
      "
    >
      <div class="left">
        <el-button
          @click="add('add')"
          v-if="isAuth('biz:upgradepackage:save')"
          type="primary"
          plain
          >添加</el-button
        >
        <el-button
          @click="delets('all')"
          v-if="isAuth('biz:upgradepackage:delete')"
          type="primary"
          plain
          >批量删除</el-button
        >
      </div>
    </div>
    <yh-table-pag
      :data="tableData"
      @getData="getData"
      :total="total"
      size="small"
      :loading="loading"
      :height="heights"
      class="my_table"
      @selection-change="handleSelectionChange"
      ref="myTable"
    >
      <el-table-column type="selection" align="center" width="55">
      </el-table-column>
      <el-table-column prop="number" label="序号" width="56" align="center">
        <template slot-scope="scope">
          <div>
            {{ scope.$index + 1 + (pageData.current - 1) * pageData.size }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="versionName" label="版本名称" align="center">
      </el-table-column>
      <el-table-column prop="fileName" label="升级包文件名" align="center">
      </el-table-column>
      <el-table-column prop="fileSize" label="大小" align="center">
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center">
      </el-table-column>
      <el-table-column prop="realName" label="更新人" align="center">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center">
      </el-table-column>
      <el-table-column
        prop="operation"
        label="操作"
        align="center"
        fixed="right"
        min-width="100px"
        v-if="isAuth('biz:upgradepackage:delete')"
      >
        <template slot-scope="scope">
          <div class="my_table_innerOperation">
            <span @click="delets('one', scope.row)" class="del">删除</span>
          </div>
        </template>
      </el-table-column>
    </yh-table-pag>
    <Update ref="AddOrUpDate" />
  </div>
</template>

<script>
import Update from "./update.vue";
import { currentList, currentDelete } from "api/modules";

import { deletsFun } from "@/lib/modules/table-list.js";
export default {
  components: { Update },
  data() {
    return {
      total: 0,
      tableData: [],
      pageData: {
        current: 1,
        size: 10,
      },
      loading: false,
      dataForm: {},
      multipleSelection: [],
      heights:null
    };
  },
  created() {},

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
      this.multipleSelection = [];
      this.loading = true;
      currentList(5, {
        ...this.pageData,
      }).then((res) => {
        this.loading = false;
        if (res.success) {
          this.tableData = res.data.list;
          this.total = res.data.totalCount;
        } else {
          this.$message({
            message: res.msg,
            type:  res.type|| "error",
          });
        }
      });
    },
    //删除
    delets(type, row) {
      deletsFun({
        name:"versionName",
        id:"id",
        type:5,
        fun:currentDelete
      },row,this.multipleSelection).then(res=>{
        console.log(res)
         if (res.success) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.getData();
            } else {
              this.$message({
                type: "error",
                message: res.msg,
              });
            }
      }).catch(err=>{
          this.$message.info("取消删除")
      })
    },
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSelection = val;
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
<style lang='scss' scoped>

</style>
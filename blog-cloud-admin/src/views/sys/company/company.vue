<template>
  <div class="setting_region contents left-tree-content">
    <div class="search-form">
      <div class="search-form-item">
        <label>公司名称:</label>
        <el-input size="small" v-model="companyName" clearable></el-input>
      </div>
      <div class="search-form-item">
        <label>区域名称:</label>
        <el-input size="small" v-model="regionName" clearable></el-input>
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
    <div class="tree-content-list">
      <div class="tree-content-list_left">
        <div class="title">公司列表</div>
        <el-tree
          :data="treeData"
          :props="{
            value: 'id',
            label: 'name',
            children: 'subs',
          }"
          node-key="id"
          @node-click="handleNodeClick"
          :default-expanded-keys="[CompanyData.id]"
          highlight-current
          :expand-on-click-node="false"
          ref="treeData"
          v-loading="treeloading"
          element-loading-text="拼命加载中..."
          element-loading-spinner="el-icon-loading"
        ></el-tree>
      </div>
      <div class="tree-content-list_right">
        <div
          class="myTable-top-action"
          v-if="isAuth('biz:company:save') || isAuth('biz:company:delete')"
        >
          <div class="left">
            <el-button
              size="mini"
              type="primary"
              plain
              @click="add('add')"
              v-if="isAuth('biz:company:save')"
            >
              添加</el-button
            >
            <el-button
              @click="delets('all')"
              v-if="isAuth('biz:company:delete')"
              type="primary"
              plain
              >批量删除</el-button
            >
          </div>
          <div class="rights"></div>
        </div>
        <yh-table-pag
          :data="tableData"
          style="width: 100%"
          class="my_table"
          @getData="getdata"
          :row-class-name="rowClassName"
          :height="heights"
          ref="myTable"
          size="small"
          :loading="loading"
          :total="total"
          :currentPage="pagData.currentPage"
          :pageSize="pagData.pageSize"
          :inPageGetData="false"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" align="center" width="50">
          </el-table-column>
          <el-table-column prop="number" label="序号" width="56" align="center">
            <template slot-scope="scope">
              <div>
                {{
                  scope.$index +
                  1 +
                  (pagData.currentPage - 1) * pagData.pageSize
                }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="名称" width="180" align="center">
          </el-table-column>
          <el-table-column prop="regionName" label="所属区域" align="center">
            <template slot-scope="{ row }">
              <div>
                <span v-if="row.provinceid">
                  {{ row.provinceName }}
                </span>
                <span v-if="row.cityid"> /{{ row.cityName }} </span>
                <span v-if="row.countyid"> /{{ row.countyName }} </span>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="createtime" label="创建时间" align="center">
          </el-table-column>
          <el-table-column
            prop="date"
            label="操作"
            align="center"
            width="120"
            v-if="isAuth('biz:company:update') || isAuth('biz:company:delete')"
          >
            <template slot-scope="scope">
              <div class="my_table_innerOperation">
                <span
                  @click="add('edit', scope.row)"
                  class="edit"
                  v-if="isAuth('biz:company:update')"
                  >编辑</span
                >
                <span
                  v-if="isAuth('biz:company:delete')"
                  @click="delets('one', scope.row)"
                  class="del"
                  >删除</span
                >
              </div>
            </template>
          </el-table-column>
        </yh-table-pag>
        <CompanyAddOrUpdate ref="add" />
      </div>
    </div>
  </div>
</template>

<script>
import CompanyAddOrUpdate from "./company-add-or-update.vue";
import { regionListTree, companyDelete, companyList } from "api/modules/sys";
import { regionTypeSelect } from "@/config/sys-config";
import { currentSelectList } from "api/modules";
import { treeDataTranslate } from "@/lib/currency";
import { deletsFun } from "@/lib/modules/table-list.js";

export default {
  data() {
    return {
      companyName: "",
      regionName: "",
      tableData: [],
      heights: null,
      multipleSelection: "",
      pagData: {
        pageSize: 10,
        currentPage: 1,
      },
      total: 0,
      loading: false,
      treeloading: false,
      regionTypeSelect: regionTypeSelect,
      treeData: [],
      CompanyData: {
        id: "",
      }, //点击左侧选择内容
    };
  },
  components: {
    CompanyAddOrUpdate,
  },
  created() {},
  mounted() {


    this.getTreeData();

      this.heights = this.$tableHeight(this.$refs.myTable.$el);
  },
  methods: {
    search() {
      this.currentPage = 1;
      this.getdata();
    },
    getTreeData(isReset) {
      this.treeloading = true;
      currentSelectList(10).then((res) => {
        this.treeloading = false;

        // console.log(res);
        if (res.success) {
          this.treeData = treeDataTranslate(res.data, "id", "parentid");
          if (isReset) {
            this.$nextTick(() => {
              this.$refs.treeData.setCurrentKey(this.CompanyData.id);
            });
            return;
          }
          if (this.treeData.length > 0) {
            this.$nextTick(() => {
              this.CompanyData = this.treeData[0];
              this.$refs.treeData.setCurrentKey(this.treeData[0].id);
              this.getdata();
            });
          }
        } else {
          this.$message.error(res.msg);
        }
      });
    },

    getAllData() {
      this.getTreeData(true);
      this.getdata();
    },
    //获取列表数据
    getdata(pagData) {
      if (pagData) {
        this.pagData = { ...pagData };
      }
      this.loading = true;
      this.multipleSelection = [];
      companyList({
        size: this.pagData.pageSize,
        current: this.pagData.currentPage,
        companyName: this.companyName,
        regionName: this.regionName,
        parentid: this.CompanyData.id,
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
    reset() {
      this.companyName = "";
      this.CompanyData = this.treeData[0] || {};
      this.pagData.currentPage = 1;
      this.getdata();
    },
    add(type, row) {
      this.$refs.add.init(type, row);
    },
    // 点击树
    handleNodeClick(val) {
      // console.log(val);
      this.CompanyData = val;
      this.getdata();
    },

    //行政级别过滤
    levFormatter(data) {
      let str = "";
      try {
        this.regionTypeSelect.map((item) => {
          // console.log(item);
          if (item.value === data.lev) {
            str = item.label;
            throw new Error("end");
          }
        });
      } catch (error) {
        // console.log(error)
      }
      return str;
    },
    //删除
    delets(type, row) {
      deletsFun(
        {
          name: "name",
          id: "id",
          fun: companyDelete,
          isMessage: true,
        },
        row,
        this.multipleSelection
      ).then((res) => {
        if (res.success) {
          this.getTreeData();
        }
      });
    },
    getRowKey(row) {
      if (row.id == 0) {
        return row.id + "one";
      } else {
        return row.id;
      }
    },

    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSelection = val;
    },
    rowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "lightColour";
      } else {
        return "DarkColor";
      }
    },
  },
};
</script>

<style lang="scss" scoped>
</style>
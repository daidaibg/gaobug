<template>
  <div class="setting_region contents left-tree-content">
    <div class="search-form">
      <div class="search-form-item">
        <label>区域名称:</label>
        <el-input size="small" v-model="regionName" clearable></el-input>
      </div>
      <div class="action flex-center" style="width: 140px">
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
        <div class="title">区域列表</div>
        <el-tree
          :data="treeData"
          :props="{
            value: 'id',
            label: 'name',
            children: 'subs',
          }"
          node-key="id"
          @node-click="handleNodeClick"
          :default-expanded-keys="[-1]"
          highlight-current
          :expand-on-click-node="false"
          ref="treeData"
        ></el-tree>
      </div>
      <div class="tree-content-list_right">
        <div class="myTable-top-action">
          <div class="left">
            <el-button
              size="mini"
              type="primary"
              plain
              @click="add('add')"
              v-if="isAuth('tzgl:region:save')"
            >
              添加</el-button
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
          ref="myTable"
          size="small"
          :loading="loading"
          :total="total"
          :currentPage="pagData.currentPage"
          :height="heights"
        >
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

          <el-table-column
            prop="lev"
            label="行政级别"
            align="center"
            :formatter="levFormatter"
          >
          </el-table-column>
          <el-table-column prop="parentName" label="所属区域" align="center">
          </el-table-column>
          <el-table-column prop="createtime" label="创建时间" align="center">
          </el-table-column>
          <el-table-column
            prop="date"
            label="操作"
            align="center"
            width="120"
            v-if="isAuth('tzgl:region:update') || isAuth('tzgl:region:delete')"
          >
            <template slot-scope="scope">
              <div class="my_table_innerOperation">
                <span
                  @click="add('edit', scope.row)"
                  class="edit"
                  v-if="isAuth('tzgl:region:update')"
                  >编辑</span
                >
                <span
                  v-if="isAuth('tzgl:region:delete')"
                  @click="delets('one', scope.row)"
                  class="del"
                  >删除</span
                >
              </div>
            </template>
          </el-table-column>
        </yh-table-pag>
        <RegionAddOrUpdate ref="add" />
      </div>
    </div>
  </div>
</template>

<script>
import RegionAddOrUpdate from "./region-add-or-update.vue";
import { regionList, regionDelete, regionPage } from "api/modules/sys";
import { treeDataTranslate } from "@/lib/currency";
import { regionTypeSelect } from "@/config/sys-config";

import { deletsFun } from "@/lib/modules/table-list.js";

export default {
  data() {
    return {
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
      regionTypeSelect: regionTypeSelect,
      treeData: [],
      regionData: {
        id: -1,
        lev: 0,
      }, //点击左侧选择内容
    };
  },
  components: {
    RegionAddOrUpdate,
  },
  mounted() {
    this.getTreeData();
    //分页与页面padding 63 || 边框 10

    this.heights = this.$tableHeight(this.$refs.myTable.$el);
  },
  methods: {
    // 若当前组件有父节点 展开其所有祖宗节点
    expandParents(node) {
      // 展开所有祖宗节点
      node.expanded = true;
      if (node.parent) {
        this.expandParents(node.parent);
      }
    },
    //编辑后回显
    Echo() {
      this.getTreeData();
      this.getdata();
    },
    search() {
      this.currentPage = 1;
      this.getdata();
    },
    getTreeData() {
      this.loading = true;
      regionList().then((data) => {
        // console.log(data)
        this.loading = false;
        if (data.success) {
          let menus = treeDataTranslate(data.data, "id", "parentid");
          // console.log(menus);
          this.treeData = menus;
          this.$nextTick(() => {
            this.$refs.treeData.setCurrentKey(this.regionData.id);
            let selected = this.$refs.treeData.getNode(this.regionData.id);
            if (selected && selected.parent ) {
               selected.expanded = true
              this.expandParents(selected.parent);
            }else{
              
            }
          });
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //获取列表数据
    getdata(pagData) {
      if (pagData) {
        this.pagData = pagData;
      }
      this.loading = true;
      regionPage({
        size: this.pagData.pageSize,
        current: this.pagData.currentPage,
        regionName: this.regionName,
        parentid: this.regionData.id,
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
      this.regionName = "";
      this.regionData = {
        id: -1,
        lev: 0,
      };
      this.pagData.currentPage = 1;
      this.Echo();
    },
    add(type, row) {
      this.$refs.add.init(type, row);
    },
    // 点击树
    handleNodeClick(val) {
      // console.log(val);
      this.regionData = val;
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
          fun: regionDelete,
          isMessage: true,
        },
        row,
        this.multipleSelection
      )
        .then((res) => {
          // console.log(res);
          if (res.success) {
            this.Echo();
          }
        })
        .catch((err) => {});
    },
    getRowKey(row) {
      if (row.id == 0) {
        return row.id + "one";
      } else {
        return row.id;
      }
    },
    //size发生变化
    handleSizeChange() {},
    //页数发生变化
    handleCurrentChange() {
      this.getdata();
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
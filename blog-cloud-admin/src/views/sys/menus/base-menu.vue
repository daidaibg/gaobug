<template>
  <div class="setting_menu contents">
    <div class="myTable-top-action">
      <div class="left">
        <el-button
          size="mini"
          type="primary"
          plain
          @click="add('add')"
          v-if="isAuth('sys:menu:save')"
        >
          添加</el-button
        >
      </div>
      <div class="rights"></div>
    </div>
    <el-table
      :data="tableData"
      style="width: 100%"
      class="my_table"
      :row-class-name="rowClassName"
      :height="heights"
      ref="myTable"
      row-key="menuId"
      :tree-props="{
        children: 'subs',
        hasChildren: '',
      }"
      border
      size="small"
      v-loading="loading"
      element-loading-text="拼命加载中..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(255, 255, 255, 0.4)"
    >
      <!-- @selection-change="handleSelectionChange"

            <el-table-column type="selection" align="center" width="55"> </el-table-column> -->

      <el-table-column
        prop="name"
        label="名称"
        align="left"
        width="180"
        header-align="center"
      >
      </el-table-column>
      <el-table-column prop="name" label="图标" align="center" width="60">
        <template slot-scope="{row}">
          <i :class="row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column prop="parentName" label="所属菜单" align="center">
      </el-table-column>
      <el-table-column prop="url" label="路径" align="center">
      </el-table-column>
      <el-table-column prop="perms" label="授权" align="center">
      </el-table-column>
      <el-table-column prop="orderNum" label="排序" align="center">
      </el-table-column>
      <el-table-column
        prop="date"
        label="操作"
        align="center"
        width="120"
        v-if="isAuth('sys:menu:update') || isAuth('sys:user:delete')"
      >
        <template slot-scope="scope">
          <div class="my_table_innerOperation">
            <!-- <el-tooltip content="编辑" placement="top" :enterable="false">
              <span
                @click="add('edit', scope.row)"
                class="el-icon-edit"
                v-if="isAuth('sys:menu:update')"
              ></span>
            </el-tooltip>
            <el-tooltip content="删除" placement="top" :enterable="false">
              <span
                v-if="isAuth('sys:menu:delete')"
                @click="delets('one', scope.row)"
                class="el-icon-delete"
              ></span>
            </el-tooltip> -->
               <span
                @click="add('edit', scope.row)"
                class="edit"
                v-if="isAuth('sys:menu:update')"
              >编辑</span>
              <span
                v-if="isAuth('sys:menu:delete')"
                @click="delets('one', scope.row)"
                class="del"
              >删除</span>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <MenuAdd ref="add" />
  </div>
</template>

<script>
import MenuAdd from './menu-add'
import { menuList, menuDelete } from "api/modules/sys";
import { treeDataTranslate } from "@/lib/currency";
export default {
  data() {
    return {
      username: "",
      contactPerson: "",
      phone: "",
      tableData: [],
      currentPage: 1,
      heights: null,
      multipleSelection: "",
      total: 0,
      pageSize: 12,
      loading: false,
    };
  },
  components: {
    MenuAdd
  },
  mounted() {
    this.getdata();
    this.heights =
      window.innerHeight - this.$refs.myTable.$el.offsetTop -15 - 10;
  },
  methods: {
    search() {
      this.currentPage = 1;
      this.getdata();
    },
    getdata() {
      this.loading = true;
      menuList().then((data) => {
        // console.log(data)
        this.loading = false;
        if (data.success) {
          let menus = treeDataTranslate(
            data.data,
            "menuId",
            "parentId",
            "orderNum"
          );
          this.tableData = menus;
          // this.total = data.data.page.totalCount;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    reset() {
      this.username = "";
      this.getdata();
    },
    add(type, row) {
      this.$refs.add.init(type, row);
    },
    //删除
    delets(type, row) {
      let str = "",
        appnum = "";
      if (type == "one") {
        str = row.name;
        appnum = row.menuId;
      } else {
        if (this.multipleSelection.length == 0) {
          this.$message.warning("请选择最少一条数据!");
          return;
        }
        this.multipleSelection.map((item) => {
          str += item.name + "、";
          appnum.push(item.menuId);
        });
        str = str.substr(0, str.length - 1);
      }

      this.$confirm(`确认永久删除：${str}, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          menuDelete(appnum).then((data) => {
            // console.log(data.data);
            this.getdata();
            if (data.success) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            } else {
              this.$message({
                type: "error",
                message: data.msg,
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
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
.setting_menu {
}
</style>
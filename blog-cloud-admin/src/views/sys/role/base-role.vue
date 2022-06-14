<template>
  <div class="setting_role contents">
    <div class="search-form">
      <div class="search-form-item">
        <label>角色名:</label>
        <el-input size="small" v-model="roleName" clearable></el-input>
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
      v-if="isAuth('sys:role:save') || isAuth('sys:role:delete')"
    >
      <div class="left">
        <el-button
          @click="add('add')"
          size="mini"
          plain
          type="primary"
          v-if="isAuth('sys:role:save')"
          >添加</el-button
        >
        <el-button
          size="mini"
          type="primary"
          plain
          @click="delets('all')"
          v-if="isAuth('sys:role:delete')"
          >批量删除</el-button
        >
      </div>
    </div>
    <yh-table-pag
      :data="tableData"
      style="width: 100%"
      class="my_table"
      :row-class-name="rowClassName"
      :height="heights"
      ref="myTable"
      size="small"
      @selection-change="handleSelectionChange"
      :loading="loading"
      loading-background="rgba(255, 255, 255, 0.4)"
      @getData="getData"
      :total="total"
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

      <el-table-column prop="roleName" label="角色名" align="center">
      </el-table-column>
      <el-table-column prop="roleType" label="角色类型" align="center">
        <template slot-scope="{row}">
          <div>
            {{roleTypeData[row.roleType]}}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" align="center">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        align="center"
        width=""
      >
      </el-table-column>
      <el-table-column
        prop="date"
        label="操作"
        align="center"
        width="120"
        v-if="isAuth('sys:role:delete') || isAuth('sys:role:update')"
      >
        <template slot-scope="scope">
          <div class="my_table_innerOperation">
            <span
              v-if="isAuth('sys:role:update')"
              @click="add('edit', scope.row)"
              class="edit"
              >编辑</span
            >
            <span
              v-if="isAuth('sys:role:delete')"
              @click="delets('one', scope.row)"
              class="del"
              >删除</span
            >
          </div>
        </template>
      </el-table-column>
    </yh-table-pag>

    <RoleAdd ref="roleAdd" />
  </div>
</template>

<script>
import RoleAdd from "./role-add";
import { roleList, roleDelete } from "api/modules/sys";
import { currentList } from "api/modules";

export default {
  data() {
    return {
      roleName: "",
      tableData: [],
      currentPage: 1,
      heights: null,
      multipleSelection: "",
      total: 0,
      pageSize: 10,
      loading: false,
      roleTypeList: [],
      roleTypeData:{

      }
    };
  },
  components: {
    RoleAdd,
  },
  created() {
    //获取角色类型
    currentList(9, { busType: "roletype" }).then((res) => {
      // console.log(res)
      if (res.success) {
        this.roleTypeList = res.data;
        let obj = {}
        res.data.map(otem=>{
          obj[otem.code] = otem.name
        })
       this.roleTypeData =obj
      } else {
        this.$message.error(res.msg);
      }
    });
  },
  mounted() {
     this.heights = this.$tableHeight(this.$refs.myTable.$el);
  },
  methods: {
    search() {
      this.currentPage = 1;
      this.getData();
    },
    getData(pagData) {
      // console.log(pagData)
      this.loading = true;
      if (pagData) {
        this.pageSize = pagData.pageSize;
        this.currentPage = pagData.currentPage;
      }
      let param = {
        size: this.pageSize,
        current: this.currentPage,
        roleName: this.roleName,
      };
      // console.log(param);
      roleList(param).then((data) => {
        // console.log(data.data)
        this.loading = false;
        if (data.success) {
          this.tableData = data.data.list;
          this.total = data.data.totalCount;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    reset() {
      this.roleName = "";
      this.currentPage = 1;
      this.getData();
    },
    add(type, row) {
      this.$refs.roleAdd.init(type, row);
    },

    //删除
    delets(type, row) {
      let str = "",
        appnum = [];
      if (type == "one") {
        str = row.roleName;
        appnum.push(row.roleId);
      } else {
        if (this.multipleSelection.length == 0) {
          this.$message.warning("请选择最少一条数据!");
          return;
        }
        this.multipleSelection.map((item) => {
          str += item.roleName + "、";
          appnum.push(item.roleId);
        });
        str = str.substr(0, str.length - 1);
      }

      this.$confirm(`确认永久删除：${str}, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          roleDelete(appnum).then((data) => {
            // console.log(data.data);
            this.getData();
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.getdata();
    },
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

<style lang="scss">
.setting_role {
}
</style>
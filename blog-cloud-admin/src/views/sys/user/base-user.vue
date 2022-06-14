<template>
  <div class="contents">
    <div class="search-form">
      <div class="search-form-item">
        <label>姓名:</label>
        <el-input
          size="small"
          v-model="dataForm.realName"
          clearable
          placeholder="请输入姓名"
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>手机号:</label>
        <el-input
          size="small"
          v-model="dataForm.mobile"
          clearable
          placeholder="请输入手机号"
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>所属公司：</label>
          <el-input
          size="small"
          v-model="dataForm.companyName"
          clearable
          placeholder="请输入所属公司"
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
    <div class="myTable-top-action"  v-if="isAuth('sys:user:save')||isAuth('sys:user:delete')">
      <div class="left">
        <el-button
          @click="add('add')"
          v-if="isAuth('sys:user:save')"
          type="primary"
          plain
          >添加</el-button
        >
        <el-button
          @click="delets('all')"
          v-if="isAuth('sys:user:delete')"
          type="primary"
          plain
          >批量删除</el-button
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
      @selection-change="handleSelectionChange"
      :loading="loading"
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
      <el-table-column prop="companyName" label="所属公司" align="center">
      </el-table-column>
      <el-table-column prop="username" label="手机号" align="center">
      </el-table-column>
      <el-table-column prop="realName" label="姓名" align="center">
      </el-table-column>

      <!-- <el-table-column prop="role" label="角色" align="center">
        <template slot-scope="scope">
          <div>{{ scope.row.role && scope.row.role.roleName }}</div>
        </template>
      </el-table-column> -->
      <el-table-column prop="email" label="邮箱" align="center" width="200px">
      </el-table-column>
      <!-- <el-table-column prop="mobile" label="手机号" align="center">
      </el-table-column> -->
      <el-table-column prop="status" label="用户状态" align="center">
        <template slot-scope="{ row, $index }">
          <template v-if="isAuth('sys:user:update')">
            <el-switch
              v-model="row.status"
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
              :type="row.status == 1 ? 'success' : 'danger'"
              >{{ row.status == 1 ? "正常" : "禁用" }}</el-tag
            >
          </template>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        align="center"
        width="140"
      >
        <!-- <template slot-scope="scope">
                    <div>
                            {{scope.row.createTime?$moment(scope.row.createTime,"YYYYMMDDHHmmss").format("YYYY-MM-DD HH:mm:ss"):""}}
                    </div>
                </template> -->
      </el-table-column>
      <el-table-column
        prop="date"
        label="操作"
        align="center"
        width="120"
        v-if="isAuth('sys:user:delete') || isAuth('sys:user:update')"
      >
        <template slot-scope="scope">
          <div class="my_table_innerOperation">
            <span
              @click="add('edit', scope.row)"
              class="edit"
              v-if="isAuth('sys:user:update')"
              >编辑</span
            >
            <span
              v-if="isAuth('sys:user:delete')"
              @click="delets('one', scope.row)"
              class="del"
              >删除</span
            >
            <!-- <el-tooltip content="重置密码" placement="top" :enterable="false">
              <span
                v-if="isAuth('sys:user:rest')"
                @click="resetPass(scope.row)"
                class="el-icon-refresh-left"
              ></span>
            </el-tooltip> -->
          </div>
        </template>
      </el-table-column>
    </yh-table-pag>
    <UserAdd ref="userAdd" />
  </div>
</template>

<script>
import UserAdd from "./user-add";
import { userApi } from "api";

export default {
  data() {
    return {
      dataForm: {
        mobile: "",
        realName: "",
        companyName: "",
      },
      contactPerson: "",
      phone: "",
      tableData: [],
      currentPage: 1,
      heights: null,
      multipleSelection: [],
      total: 0,
      pageSize: 10,
      loading: false,
      companyloading: false,
    };
  },
  components: {
    UserAdd,
  },
  mounted() {
    // this.getdata();
    this.heights = this.$tableHeight(this.$refs.myTable.$el);
  },
  computed: {},
  methods: {
    search() {
      this.currentPage = 1;
      this.getdata();
    },
    getdata(pagData) {
      this.loading = true;
      this.multipleSelection = [];
      if (pagData) {
        (this.pageSize = pagData.pageSize),
          (this.currentPage = pagData.currentPage);
      }
      let param = {
        size: this.pageSize,
        current: this.currentPage,
        // realName:this.dataForm.realName
        ...this.dataForm,
      };
      // console.log(param);
      userApi.userList(param).then((data) => {
        // console.log(data.data);
        this.loading = false;
        if (data.success) {
          let list = data.data.list;

          // list.map((item) => {
          //   if (item.status == 1) {
          //     item.statusFlag = true;
          //   } else {
          //     item.statusFlag = false;
          //   }
          //   return item;
          // });
          this.tableData = list;
          this.total = data.data.totalCount;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    reset() {
      for (let k in this.dataForm) {
        this.dataForm[k] = "";
      }
      this.getdata();
    },

    add(type, row) {
      this.$refs.userAdd.init(type, row);
    },
    // 更新账号状态
    updateStatus(flag, row) {
      let str = "开启";
      let type = "open";
      if (row.status == 1) {
        row.status = 0;
      } else {
        row.status = 1;
        str = "禁用";
        type = "close";
      }
      // console.log(flag, row, str);
      this.$confirm(`确定${str + row.username}账号吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: type == "open" ? "warning" : "error",
      })
        .then((res) => {
          console.log(res);
          this.loading = true;
          userApi
            .userUpdateStatus({
              userId: row.userId,
              status: flag,
            })
            .then((res) => {
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
    resetPass(row) {
      this.$confirm(`确认重置用户'${row.realName}密码，是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          userApi.passwordRest(row.userId).then((data) => {
            // console.log(data.data);
            if (data.success) {
              this.$message({
                type: "success",
                message: "重置成功!",
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
    //删除
    delets(type, row) {
      // console.log(row)
      let str = "",
        appnum = [];
      if (type == "one") {
        str = row.username;
        appnum.push(row.userId);
      } else {
        if (this.multipleSelection.length == 0) {
          this.$message.warning("请选择最少一条数据!");
          return;
        }
        this.multipleSelection.map((item) => {
          str += item.username + "、";
          appnum.push(item.userId);
        });
        str = str.substr(0, str.length - 1);
      }
      this.$confirm(`确认永久删除${str}, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          userApi.userDelete(appnum).then((data) => {
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
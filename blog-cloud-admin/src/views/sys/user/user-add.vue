<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="680px"
    :close-on-click-modal="false"
    class="user_add"
    top="60px"
  >
    <FormSkeleton v-if="skeletonLoading" :row="9"> </FormSkeleton>
    <el-form
      v-show="!skeletonLoading"
      label-position="right"
      label-width="100px"
      :model="formData"
      :rules="rules"
      ref="user_add"
      size="small"
    >
      <el-form-item label="手机号：" prop="username">
        <el-input v-model="formData.username" maxlength="11"></el-input>
      </el-form-item>
      <el-form-item
        label="密码："
        prop="password"
        :rules="this.type == 'add' ? rules.password : rules.password2"
      >
        <el-input v-model="formData.password" type="password"></el-input>
      </el-form-item>
      <el-form-item label="姓名：" prop="realName">
        <el-input v-model="formData.realName"></el-input>
      </el-form-item>

      <!-- <el-form-item label="手机号：" prop="mobile">
        <el-input v-model="formData.mobile"></el-input>
      </el-form-item> -->
      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="formData.email"></el-input>
      </el-form-item>
      <el-form-item label="公司：" prop="companyId">
        <CompanyTree v-model="formData.companyId" ref="companyTree" />
      </el-form-item>
      <el-form-item label="角色：" class="user_add_role" prop="roleIdList">
        <el-checkbox-group v-model="formData.roleIdList">
          <el-checkbox
            :label="item.roleId"
            name="type"
            v-for="item in roleList"
            :key="item.roleId"
            >{{ item.roleName }}</el-checkbox
          >
        </el-checkbox-group>
        <!-- <div class="checkbox_wrap">
          <el-radio
            v-model="formData.roleId"
            :label="item.roleId"
            v-for="item in roleList"
            :key="item.roleId"
            >{{ item.roleName }}</el-radio
          >
        </div> -->
      </el-form-item>
      <div class="flex-sb">
        <el-form-item label="接收短信：" prop="isReceiveSms">
          <el-radio-group v-model="formData.isReceiveSms">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态：" prop="status" style="margin-right: 120px">
          <el-radio-group v-model="formData.status">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">正常</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="subItem" :loading="loading" size="small"
        >确 定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import { userApi } from "api";
import { roleSelect } from "api/modules/sys";
import FormSkeleton from "@/components/current-public/form-Skeleton";
import CompanyTree from "@/components/trees/company-tree.vue";

import {
  validatePhone,
  validatePassword,
  validatePasswordSimple,
  validatePasswordOrEmpty,
  validatePasswordOrEmptySimple,
} from "@/constants/rules";
export default {
  components: { FormSkeleton, CompanyTree },
  data() {
    return {
      visible: false,
      title: "编辑",
      formData: {
        username: "",
        password: "",
        email: "",
        // mobile: "",
        companyId: "",
        realName: "",
        status: "1",
        isReceiveSms: "1",
        roleIdList: [],
      },
      rules: {
        username: [
          { required: true, validator: validatePhone, trigger: "change" },
        ],
        status: [{ required: true, message: "请输入名称", trigger: "blur" }],
        companyId: [
          { required: false, message: "请选择公司", trigger: "blur" },
        ],
        isReceiveSms: [
          { required: true, message: "请选择是否接收短信", trigger: "blur" },
        ],
        email: [{ required: false, message: "请输入邮箱", trigger: "blur" }],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur",
          },
          // { validator: validatePasswordSimple, trigger: "change" },
        ],
        password2: [
          // {
          //   validator: validatePasswordOrEmptySimple,
          //   trigger: "change",
          //   required: false,
          // },
        ],
        realName: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        roleIdList: [
          { required: true, message: "请选择角色", trigger: "blur" },
        ],
        mobile: [
          { required: true, validator: validatePhone, trigger: "change" },
        ],
      },
      skeletonLoading: false,
      type: "edit",
      loading: false,
      roleList: [],
      companyloading: false,
    };
  },
  mounted() {
    this.getrolelist();
  },
  computed: {},
  methods: {
    init(type, row) {
      console.log(type, row);
      this.visible = true;
      this.loading = false;
      this.type = type;

      this.$nextTick(() => {
        this.$refs["user_add"] && this.$refs["user_add"].resetFields();
        if (type == "add") {
          this.title = "添加用户";
          for (let i in this.formData) {
            this.formData[i] = "";
          }
          this.formData.status = 1;
          this.formData.isReceiveSms = 1;
          this.formData.roleIdList = [];
        } else {
          this.title = row.realName + "-编辑";
          this.skeletonLoading = true;
          userApi.userInfo(row.userId).then((res) => {
            this.skeletonLoading = false;
            // console.log("用户详情",res);
            if (res.success) {
              this.row = res.data;
              for (let i in this.formData) {
                this.formData[i] = res.data[i];
              }
              this.formData.password = "";
            } else {
              this.$message({
                message: res.msg,
                type: "error",
              });
            }
          });
        }
      });
    },
    //获取角色列表
    getrolelist() {
      roleSelect().then((data) => {
        // console.log(data.data)
        if (data.success) {
          this.roleList = data.data;
        } else {
          this.$message.error(data.msg);
        }
      });
    },


    subItem() {
      console.log(this.formData);
      this.$refs.user_add.validate((valid) => {
        if (!valid) {
          return;
        }
        let param = { ...this.formData };
        // console.log(param);
        let fun = userApi.userSave;
        let str = "添加用户";
        if (this.type == "edit") {
          fun = userApi.userUpdate;
          param.userId = this.row.userId;
          str = "编辑";
        }
        console.log(param);
        this.loading = true;
        fun(param).then((data) => {
          this.loading = false;
          // console.log(data.data);
          if (data.success) {
            this.$message.success(str + "成功");
            this.$parent.getdata();
            this.visible = false;
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
  },
};
</script>

<style lang="scss">
.user_add {
  .el-input__inner {
    width: 100%;
  }
  .user_add_role {
    .el-form-item__content {
      .el-checkbox-group {
        max-height: 222px;
        overflow-y: auto;
        .el-checkbox {
          width: 120px;
          margin-right: 12px;
          height: 26px;
          line-height: 26px;
        }
      }
      .checkbox_wrap {
        display: flex;
        flex-wrap: wrap;
        .el-radio {
          width: 120px;
          margin-right: 12px;
          height: 26px;
          line-height: 26px;
        }
      }
    }
  }
}
.company_pla {
  text-align: center;
  font-size: 12px;
  margin-top: 6px;
  color: rgb(145, 145, 145);
}
.company_select {
  width: 265px;
}
</style>
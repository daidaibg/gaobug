<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="700px"
    :close-on-click-modal="false"
    class="role_add"
  >
    <FormSkeleton v-if="skeletonLoading"> </FormSkeleton>
    <el-form
      v-show="!skeletonLoading"
      label-position="right"
      label-width="92px"
      :model="formData"
      :rules="rules"
      ref="forms"
      size="small"
      v-loading="loading"
      element-loading-spinner="el-icon-loading"
    >
      <el-form-item label="角色名：" prop="roleName">
        <el-input v-model="formData.roleName"></el-input>
      </el-form-item>

      <el-form-item label="备注：" prop="remark">
        <el-input v-model="formData.remark" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="类型：" prop="roleType">
        <el-radio-group v-model="formData.roleType">
          <el-radio
            class="rdx-m-b-10"
            :label="item.code"
            v-for="item in roleTypeList"
            :key="item.code"
            >{{ item.name }}</el-radio
          >
        </el-radio-group>
      </el-form-item>
      <el-form-item label="功能：" prop="remark">
        <MenuSelectTree @handleNodeClick="handleNodeClick" ref="tree" />
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="subItem" size="small" :loading="loading"
        >确 定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import { roleSave, roleUpdate, roleInfo } from "api/modules/sys";
import MenuSelectTree from "@/components/trees/menu-select-tree";
import FormSkeleton from "@/components/current-public/form-Skeleton";

export default {
  data() {
    return {
      visible: false,
      title: "编辑",
      formData: {
        roleName: "",
        remark: "",
        roleType: "",
      },
      rules: {
        roleName: [
          { required: true, message: "请输入角色名！", trigger: "blur" },
        ],
        roleType: [
          { required: true, message: "请选择角色类型！", trigger: "blur" },
        ],
      },
      type: "edit",
      loading: false,
      menuIdList: [],
      skeletonLoading: false,
    };
  },
  computed: {
    roleTypeList() {
      return this.$parent.roleTypeList;
    },
  },
  components: {
    MenuSelectTree,
    FormSkeleton,
  },
  created() {},
  mounted() {},
  methods: {
    init(type, row) {
      this.visible = true;
      this.type = type;
      this.menuIdList = [];
      this.$nextTick(() => {
        this.$refs.tree.init([]);
        this.$refs.forms.resetFields()
        if (type == "add") {
          this.title = "添加";
          for (let i in this.formData) {
            this.formData[i] = "";
          }
        } else {
          // console.log(row);
          this.title = row.roleName + "-编辑";
          this.skeletonLoading = true;
          roleInfo(row.roleId).then((res) => {
            // console.log(res);
            this.skeletonLoading = false;
            if (res.success) {
              this.menuIdList = res.data.menuIdList;
              this.row = res.data;
              for (let i in this.formData) {
                this.formData[i] = row[i];
              }
              this.$nextTick(() => {
                this.$refs.tree.init(this.menuIdList);
              });
            }
          });
        }
      });
    },

    //菜单权限
    handleNodeClick(data) {
      this.menuIdList = data;
      // console.log(data)
    },
    subItem() {
      this.$refs.forms.validate((valid) => {
        if (!valid) {
          return;
        }
        let param = { ...this.formData };
        // console.log(param);
        let fun = roleSave;
        let str = "添加";
        if (this.type == "edit") {
          fun = roleUpdate;
          param.roleId = this.row.roleId;
          str = "编辑";
        }
        param.menuIdList = this.menuIdList;
        this.loading = true;
        // console.log(param)
        fun(param).then((data) => {
          this.loading = false;
          // console.log(data.data);
          if (data.success) {
            this.$message.success(str + "成功");
            this.$parent.getData();
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

<style lang="scss" scoped>
.role_add {
  .role_add_qx {
    width: 100%;
    display: flex;
    .el-form-item {
      width: 100%;
    }
  }
}
</style>
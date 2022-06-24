<!--
 * @Author: daidai
 * @Date: 2021-12-16 09:59:34
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 15:46:50
 * @FilePath: \web-pc\src\views\tzgl\line\line-form.vue
-->
<template>
  <el-form
    label-position="right"
    label-width="100px"
    :model="formData"
    :rules="rules"
    ref="user_add"
    size="small"
    v-loading="loading"
    element-loading-spinner="el-icon-loading"
  >
    <div class="flex_line">
      <el-form-item label="线路名称：" prop="name">
        <el-input v-model="formData.name"></el-input>
      </el-form-item>
      <el-form-item label="起点：" prop="startname">
        <el-input v-model="formData.startname"></el-input>
      </el-form-item>
      <el-form-item label="终点：" prop="endname">
        <el-input v-model="formData.endname"></el-input>
      </el-form-item>
    </div>
    <div class="flex_line">
      <el-form-item label="归属公司：" prop="companyid">
        <CompanyTree
          v-model="formData.companyid"
          ref="companyTree"
        />
      </el-form-item>
      <el-form-item label="管理人员：" prop="userid">
        <el-select
          v-model="formData.userid"
          filterable
          placeholder="请选择人员"
          style="width: 100%"
        >
          <el-option
            v-for="item in useroptions"
            :key="item.userId"
            :label="item.name"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </div>
  </el-form>
</template>

<script>
import {  userQueryUserList } from "api/modules/sys";
import { currentSave, currentUpdate } from "api/modules";

import CompanyTree from "@/components/trees/company-tree.vue";

export default {
  components:{CompanyTree},
  data() {
    return {
      loading: false,
      formData: {
        companyid: "",
        name: "",
        startname: "",
        endname: "",
        userid: "",
      },

      rules: {
        name: [
          { required: true, message: "请输入线路名称！", trigger: "blur" },
        ],
        companyid: [
          { required: true, message: "请选择公司！", trigger: "blur" },
        ],
        userid: [
          { required: false, message: "请选择管理人员！", trigger: "blur" },
        ],
      },
      companyloading: false,
      useroptions: [],
      type: "add",
    };
  },
  created() {
    this.geuserLsit();
  },

  mounted() {},
  methods: {
    init(type, row) {
      this.type = type;
      if (type == "add") {
        for (let i in this.formData) {
          this.formData[i] = "";
        }
      } else {
        // console.log(row);
        this.row = { ...row };
        for (let i in this.formData) {
          this.formData[i] = row[i];
        }
      }
    },
    subItem() {
      this.$refs.user_add.validate((valid) => {
        if (!valid) {
          return;
        }
        let param = { ...this.formData };
        let fun = currentSave;
        let str = "添加";
        if (this.type == "edit") {
          fun = currentUpdate;
          param.id = this.row.id;
          str = "编辑";
        }
        this.loading = true;
        // console.log(param)
        fun(2,param).then((data) => {
          this.loading = false;
          // console.log(data.data);
          if (data.success) {
            this.$message.success(str + "成功");
            this.$emit("close");
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },

    //获取用户列表
    geuserLsit() {
      userQueryUserList().then((res) => {
        if (res.success) {
          this.useroptions = res.data;
        } else {
          this.$message.error(res.msg);
        }
      });
    },
  },
};
</script>
<style lang='scss' scoped>
.company_pla {
  text-align: center;
  font-size: 12px;
  margin-top: 6px;
  color: rgb(145, 145, 145);
}
.edit_form {
  box-sizing: border-box;
  padding: 0 10px;
  .el-form-item {
    width: 340px;
    margin-right: 60px;
    min-width: 340px;
    &:last-child {
      margin-right: 0px;
    }
  }
  .flex_line {
    display: flex;
    flex-wrap: wrap;
  }
}
@import "@/assets/css/modules/medis-form";

</style>
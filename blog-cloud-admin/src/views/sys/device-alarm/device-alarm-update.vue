<!--
 * @Author: daidai
 * @Date: 2021-12-22 17:07:10
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 15:33:23
 * @FilePath: \web-pc\src\views\sys\device-alarm\device-alarm-update.vue
-->

<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="460px"
    :close-on-click-modal="false"
    class="role_add"
    top="100px"
  >
    <el-form
      v-if="type == 'sbgjcs'"
      label-position="right"
      label-width="100px"
      :model="sbgjcsform"
      :rules="rules"
      ref="user_add"
      size="small"
      v-loading="loading"
      
      element-loading-spinner="el-icon-loading"
    >
      <el-form-item label="告警类型：" prop="name">
        <el-input v-model="sbgjcsform.alertname" clearable></el-input>
      </el-form-item>
      <el-form-item label="最大值：" prop="maxvalue">
        <el-input v-model="sbgjcsform.maxvalue" clearable></el-input>
      </el-form-item>
         <el-form-item label="最小值：" prop="">
        <el-input v-model="sbgjcsform.minvalue" clearable></el-input>
      </el-form-item>
      <el-form-item label="自动取消时间大小(天)：" prop="autocanceltime" clearable>
        <el-input v-model="sbgjcsform.autocanceltime"></el-input>
      </el-form-item>
      <el-form-item label="是否开启自动取消：" prop="isautocancel" >
        <el-radio-group v-model="sbgjcsform.isautocancel">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
          <el-form-item label="是否激活：" prop="isactive">
        <el-radio-group v-model="sbgjcsform.isactive">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <!-- <el-form
      v-else-if="type == 'sbgjzdqxpz'"
      label-position="right"
      label-width="120px"
      :model="sbgjzdqxpzform"
      :rules="sbgjzdqxpzrules"
      ref="user_add"
      size="small"
      v-loading="loading"
      element-loading-spinner="el-icon-loading"
    >
      <el-form-item label="告警类型：" prop="name">
        <el-input v-model="sbgjzdqxpzform.name"></el-input>
      </el-form-item>
      <el-form-item label="时间大小(天)：" prop="time">
        <el-input v-model="sbgjzdqxpzform.time"></el-input>
      </el-form-item>
      <el-form-item label="是否开启：" prop="isOpen">
        <el-radio-group v-model="sbgjzdqxpzform.isOpen">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form> -->
    <span class="dialog-footer" slot="footer">
      <el-button @click="visible = false" size="mini">取 消</el-button>
      <el-button type="primary" @click="subItem" size="mini" :loading="loading"
        >保 存</el-button
      >
    </span>
  </el-dialog>
</template>
<script>
import {  currentUpdate } from "api/modules";

import { validateTreeNumrule, validatenumrule } from "@/constants/rules";
export default {
  data() {
    return {
      visible: false,
      title: "",
      loading: false,
      sbgjcsform: {
        alertname: "",
        maxvalue: "",
        minvalue: "",
        username: "",
        isautocancel: "",
        isactive: "",
        autocanceltime: "",
      },
      sbgjzdqxpzform: {
        name: "",
        isOpen: "",
        time: "",
      },
      rules: {
        alertname: [{ required: true, message: "请输入名称！", trigger: "blur" }],
        num: [
          {
            required: true,
            validator: validateTreeNumrule("告警值"),
            trigger: "change",
          },
        ],
      },
      sbgjzdqxpzrules: {
        name: [
          { required: true, message: "请输入告警类型！", trigger: "blur" },
        ],
        isOpen: [
          { required: true, message: "请选择是否开启", trigger: "blur" },
        ],
        time: [
          {
            required: true,
            validator: validatenumrule("时间大小"),
            trigger: "change",
          },
        ],
      },
    };
  },
  props: {
    type: {
      type: String,
      default: () => "",
    },
  },
  mounted() {},
  methods: {
    init(row, name) {
      this.visible = true;
      console.log(row, this.type);
      this.row = { ...row };
      if (this.type == "sbgjcs") {
        this.title = "编辑-" + row.alertname;
        for (let i in this.sbgjcsform) {
          this.sbgjcsform[i] = row[i];
        }
      } else if (this.type == "sbgjzdqxpz") {
        this.title = "编辑" + name + "-" + row.name;
        console.log(this.sbgjzdqxpzform);
        for (let i in this.sbgjzdqxpzform) {
          this.sbgjzdqxpzform[i] = row[i];
        }
      }
    },

    subItem() {
      this.$refs.user_add.validate((valid) => {
        if (!valid) {
          return;
        }
        let param = { ...this.sbgjcsform };
        param.id = this.row.id;
        this.loading = true;
        // console.log(param)
        currentUpdate(4, param).then((data) => {
          this.loading = false;
          // console.log(data.data);
          if (data.success) {
            this.$message.success("编辑" + "成功");
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
.imgs {
  display: flex;
  label {
    text-align: right;
    vertical-align: middle;
    color: #606266;
    padding: 0 12px 0 0;
    box-sizing: border-box;
    min-height: 32px;
    line-height: 32px;
    width: 120px;
    min-width: 120px;
  }
}
.flex-sb {
  .el-form-item {
    width: 50%;
  }
  /deep/ .el-input-group__append {
    padding: 0px;
    // color: $primary-color;;
    cursor: pointer;
    // color:  rgba($color: $primary-color;, $alpha: 0.7);
    //  background: rgba($color: $primary-color;, $alpha: 0.1);
    .el-icon-location {
      font-size: 18px;
      width: 30px;
      text-align: center;
      line-height: 30px;
      display: block;
    }
    &:hover {
      color: $primary-color;;
      background: rgba($color: $primary-color, $alpha: 0.1);
    }
  }
}
</style>
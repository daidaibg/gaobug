<!--
 * @Author: daidai
 * @Date: 2021-12-27 10:16:38
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 15:33:32
 * @FilePath: \web-pc\src\views\tzgl\device\device-parma.vue
-->


<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="1000px"
    :close-on-click-modal="false"
    class="deviceparam"
    top="100px"
  >
    <div slot="title">
      <h2 class="left_shu_h2">{{ title }}</h2>
    </div>
    <el-form
      label-position="right"
      label-width="120px"
      :model="paramForm"
      :rules="rules"
      ref="upate"
      size="small"
      v-loading="loading"
      class="border-box flex flex-wrap"
      element-loading-spinner="el-icon-loading"
    >
      <div style="width: 100%" class="border-box">
   
        <el-button
          type="warning"
          @click="subItem"
          size="mini"
          :loading="loading"
          >读定值</el-button
        >
             <el-button @click="visible = false" size="mini" type="primary"
          >写定值</el-button
        >
      </div>
      <el-form-item label="设置的采样率：" prop="name">
        <el-input v-model="paramForm.alertname" clearable></el-input>
      </el-form-item>
      <el-form-item label="气压单位间隔：" prop="maxvalue">
        <el-input v-model="paramForm.maxvalue" clearable></el-input>
      </el-form-item>
      <el-form-item label="温度单位间隔：" prop="">
        <el-input v-model="paramForm.minvalue" clearable></el-input>
      </el-form-item>
          <el-form-item label="温度单位间隔：" prop="autocanceltime" clearable>
        <el-input v-model="paramForm.autocanceltime"></el-input>
      </el-form-item>
      <el-form-item label="超时时间间隔：" prop="autocanceltime" clearable>
        <el-input v-model="paramForm.autocanceltime"></el-input>
      </el-form-item>
  
        <el-form-item label="湿度时间间隔：" prop="autocanceltime" clearable>
        <el-input v-model="paramForm.autocanceltime"></el-input>
      </el-form-item>
    </el-form>
    <h2 class="left_shu_h2">版本信息</h2>
    <el-form
      label-position="right"
      label-width="120px"
      :model="edition"
      :rules="editionRules"
      ref="edition"
      size="small"
      v-loading="loading"
      class="border-box flex flex-wrap"
      element-loading-spinner="el-icon-loading"
    >
      <div style="width: 100%" class="border-box">
        <el-button @click="visible = false" size="mini" type="primary"
          >召 测</el-button
        >
        <el-button
          type="warning"
          @click="subItem"
          size="mini"
          :loading="loading"
          >升 级</el-button
        >
      </div>
      <el-form-item label="软件版本：" prop="name">
        <el-input v-model="edition.alertname" clearable></el-input>
      </el-form-item>
      <el-form-item label="硬件版本：" prop="maxvalue">
        <el-input v-model="edition.maxvalue" clearable></el-input>
      </el-form-item>
      <el-form-item label="SIM卡序列号：" prop="">
        <el-input v-model="edition.minvalue" clearable></el-input>
      </el-form-item>
      <el-form-item label="版本选择：" prop="autocanceltime" clearable>
        <el-select v-model="edition.bbvalue" placeholder="请选择" filterable clearable>
          <el-option
            v-for="item in bboptions"
            :key="item.id"
            :label="item.versionName"
            :value="item.id"
            
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
import { currentUpdate,currentSelect } from "api/modules";

import { validateTreeNumrule, validatenumrule } from "@/constants/rules";
export default {
  data() {
    return {
      visible: false,
      title: "",
      loading: false,
      paramForm: {
        alertname: "",
        maxvalue: "",
        minvalue: "",
        username: "",
        isautocancel: "",
        isactive: "",
        autocanceltime: "",
      },
      edition: {},
      rules: {
        alertname: [
          { required: true, message: "请输入名称！", trigger: "blur" },
        ],
        num: [
          {
            required: true,
            validator: validateTreeNumrule("告警值"),
            trigger: "change",
          },
        ],
      },
      editionRules: {},
      bboptions: [],
    };
  },
  mounted() {
      currentSelect(5).then(res=>{
          // console.log(res)
          if(res.success){
                this.bboptions = res.data
          }else{
              this.$message.error(res.msg)
          }
      })
  },
  methods: {
    init(row) {
      this.visible = true;
      console.log(row);
      this.row = { ...row };
      this.title = "设备参数设置-" + row.gatewayno;
    },
    subItem() {
      this.$refs.user_add.validate((valid) => {
        if (!valid) {
          return;
        }
        let param = { ...this.paramForm };
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
.deviceparam {
  /deep/ .el-dialog {
    background: $content-background;

    .el-dialog__body {
      box-sizing: border-box;
      padding: 0px 12px 12px;
    }
    .el-dialog__header {
      padding: 2px 10px;
      background: $content-background;
    }
    .el-form {
      background: #fff;
      border-radius: 8px;
      .el-form-item {
        width: 320px;
      }
    }
  }
}
.upate {
  background: #fff;
}
</style>
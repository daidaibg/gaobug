<template>
  <el-dialog
    v-dialogDrag
    append-to-body
    :title="title"
    :visible.sync="visible"
    width="760px"
    :close-on-click-modal="false"
    class="user_add"
    top="70px"
  >
    <el-form
      label-position="right"
      label-width="128px"
      :model="formData"
      :rules="rules"
      ref="form"
      size="small"
    >
      <h2 class="left_shu_h2">
        杆塔信息
      </h2>
      <div class="flex-sb">
        <el-form-item label="杆塔名称/号别：" prop="name">
          <el-input v-model="formData.name"></el-input>
        </el-form-item>
        <el-form-item label="结构形式：" prop="type">
          <el-input v-model="formData.type"></el-input>
        </el-form-item>
      </div>
      <div class="flex-sb">
        <el-form-item label="型号规格：" prop="model">
          <el-input v-model="formData.model"></el-input>
        </el-form-item>
     
          <el-form-item label="投运时间：" prop="runtimeStr">
          <el-date-picker
            v-model="formData.runtimeStr"
            align="right"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          >
          </el-date-picker>
        </el-form-item>
      </div>
      <div class="flex-sb">
        <!-- <el-form-item label="排序号：" prop="sort">
          <el-input v-model="formData.sort"></el-input>
        </el-form-item> -->
           <el-form-item label="厂家：" prop="manufacturer">
          <el-input v-model="formData.manufacturer"></el-input>
        </el-form-item>
      </div>
      <h2 class="left_shu_h2">
        绝缘子信息
      </h2>
      <div class="flex-sb">
        <el-form-item label="绝缘子材质：" prop="insulatertype">
          <el-input v-model="formData.insulatertype"></el-input>
        </el-form-item>
        <el-form-item label="绝缘子型号规格：" prop="insulatermodel">
          <el-input v-model="formData.insulatermodel"></el-input>
        </el-form-item>
      </div>
      <div class="flex-sb">
        <el-form-item label="绝缘子厂商：" prop="insulatermanu">
          <el-input v-model="formData.insulatermanu"></el-input>
        </el-form-item>
        <el-form-item label="缘子投运日期：" prop="insulatertimeStr">
          <el-date-picker
            v-model="formData.insulatertimeStr"
            align="right"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          >
          </el-date-picker>
        </el-form-item>
      </div>
      <el-form-item
        label="更换与维护记录："
        prop="insulatercheck"
        style="width: 100%"
      >
        <el-input v-model="formData.insulatercheck" type="textarea"></el-input>
      </el-form-item>
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
import { currentSave, currentUpdate } from "api/modules";
import { pickerOptions } from "@/config/element-config.js";
import DDmoment from "@/lib/dd-moment";
export default {
  data() {
    return {
      visible: false,
      title: "编辑",
      formData: {
        name: "",
        type: "",
        model: "",
        manufacturer: "",
        sort: "",
        runtimeStr: "",
        insulatertype: "",
        insulatermodel: "",
        insulatermanu: "",
        insulatercheck: "",
        insulatertimeStr: "",
      },
      pickerOptions: pickerOptions,
      rules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
      },
      type: "edit",
      loading: false,
    };
  },
  mounted() {},
  computed: {},
  methods: {
    init(type, row) {
      console.log(type, row, this.$parent.row);
      this.visible = true;
      this.loading = false;
      this.type = type;
      this.$nextTick(() => {
        this.$refs["form"] && this.$refs["form"].resetFields();
        if (type == "add") {
          this.title = "添加杆塔";
          for (let i in this.formData) {
            this.formData[i] = "";
          }
          this.formData.insulatertimeStr = DDmoment().format("YYYY-MM-dd");
          this.formData.runtimeStr = DDmoment().format("YYYY-MM-dd");
        } else {
          this.title = row.name + "-编辑";
          this.row = { ...row };
          for (let i in this.formData) {
            this.formData[i] = row[i];
          }
           this.formData.insulatertimeStr = row.insulatertime;
          this.formData.runtimeStr = row.runtime;
        }
      });
    },

    subItem() {
      //   console.log(this.formData);
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return;
        }
        let param = { ...this.formData };
        // console.log(param);
        let fun = currentSave;
        let str = "添加";
        if (this.type == "edit") {
          fun = currentUpdate;
          param.id = this.row.id;
          str = "编辑";
        }
        console.log(param);
        param.lineid = this.$parent.row.id;
        this.loading = true;
        fun(6,param).then((data) => {
          this.loading = false;
          // console.log(data.data);
          if (data.success) {
            this.$message.success(str + "成功");
            this.$parent.eaitGetPole();
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
.user_add {
  .el-input__inner {
    width: 100%;
  }
  .flex-sb {
    display: flex;
  }
  .el-form {
    /deep/ .el-form-item {
      width: 48%;
      .el-form-item__label {
        padding-bottom: 0;
      }
    }
  }
}
</style>
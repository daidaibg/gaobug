<!--
 * @Author: daidai
 * @Date: 2021-12-22 10:42:43
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-27 09:04:07
 * @FilePath: \web-pc\src\views\sys\upgrade-package\update.vue
-->

<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="500px"
    :close-on-click-modal="false"
    class="role_add"
    top="100px"
  >
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
      <el-form-item label="版本名称：" prop="versionName">
        <el-input v-model="formData.versionName"></el-input>
      </el-form-item>
      <el-form-item label="备注：" prop="remark">
        <el-input v-model="formData.remark" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="文件：" prop="remark">
        <el-upload
          class="uploadpage"
          :action="uploadurl"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :on-exceed="handleExceed"
          :on-change="beforeUpload"
          :limit="1"
          :auto-upload="false"
          accept=".bin"
          ref="uploadpage"
          :data="formData"
          :headers="headers"
          :on-success="onsuccess"
          :on-error="onerror"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip">(选完文件后不自动上传,后缀为.bin)</div>
        </el-upload>
      </el-form-item>
    </el-form>

    <span class="dialog-footer" slot="footer">
      <el-button @click="visible = false" size="mini">取 消</el-button>
      <el-button type="primary" @click="subItem" size="mini" :loading="loading"
        >保 存</el-button
      >
    </span>
  </el-dialog>
</template>
<script>
import { updatapageData } from "api/modules/upload";
import { validatePhone, validateNum } from "@/constants/rules";
export default {
  data() {
    return {
      visible: false,
      title: "",
      loading: false,
      type: "add",
      formData: {
        versionName: "",
        remark: "",
      },
      headers: updatapageData.header(),
      rules: {
        versionName: [
          { required: true, message: "请输入版本名称！", trigger: "blur" },
        ],
      },
      uploadurl: updatapageData.url,
      uploadshow: true,
    };
  },
  mounted() {},
  methods: {
    init(type, row) {
      this.visible = true;
      // console.log(row);
      this.type = type;
      if (type == "add") {
        this.title = "添加升级包";
        for (let i in this.formData) {
          this.formData[i] = "";
        }
        this.$refs.uploadpage && this.$refs.uploadpage.clearFiles();
      } else {
        this.title = "编辑升级包-" + row.gatewayno;
        // console.log(row);
        this.row = { ...row };

        for (let i in this.formData) {
          this.formData[i] = row[i];
        }
      }
    },
    onsuccess(response) {
      // console.log(response)
      this.loading = false;
      if (response.success) {
        this.$message({
          message: "上传成功",
          type: "success",
        });
        this.visible = false;
        this.$parent.getData();
      } else {
        this.$message({
          message: response.msg,
          type: "error",
        });
      }
    },

    onerror() {
      this.$message({
        message: "未知异常，上传失败！",
        type: "error",
      });
    },
    //上传前验证
    beforeUpload(file) {
      var FileExt = file.name.replace(/.+\./, "");
      if (["bin"].indexOf(FileExt.toLowerCase()) === -1) {
        this.$message({
          type: "warning",
          message: "请上传后缀名为.bin的附件！",
        });
        this.$refs.uploadpage.clearFiles();
        this.uploadshow = true;
      } else {
        this.uploadshow = false;
      }
      // this.isLt2k = file.size / 1024 < 200 ? "1" : "0";
      // if (this.isLt2k === "0") {
      //   this.$message({
      //     message: "上传文件大小不能超过200k!",
      //     type: "error",
      //   });
      // }
      // return this.isLt2k === "1" ? true : false;
    },

    handleRemove(file, fileList) {
      // console.log(file, fileList);
    },
    //点击文件列表中已上传的文件时的钩子
    handlePreview(file) {
      // console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    subItem() {
      if (this.$refs.uploadpage.uploadFiles.length == 0) {
        this.$message({
          message: "请先选择升级包！",
          type: "warning",
        });
        return;
      }
      this.$refs.user_add.validate((valid) => {
        if (!valid) {
          return;
        }
        this.loading = true;
        this.$refs.uploadpage.submit();
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.uploadpage {
  /deep/.el-upload {
    width: 100%;
    .el-upload-dragger {
      width: 100%;
      height: 140px;
    }
    .el-icon-upload {
      margin-top: 10px;
    }
    .el-upload__tip {
      margin-top: -10px;
      font-size: 12px;
      color: rgb(104, 103, 103);
    }
  }
}
</style>
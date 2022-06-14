<!--
 * @Author: daidai
 * @Date: 2022-01-04 17:44:43
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-12 09:56:56
 * @FilePath: \web-pc\src\components\uploads\upload-xlsx.vue
-->

<template>
  <el-upload
    class="upload-xlsx"
    :action="action"
    accept=".xls,.xlsx"
    :show-file-list="false"
    :class="{ isHide: isHide }"
    :headers="headers"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeUpload"
    :on-error="uploadError"
    :on-progress="progress"
    :http-request="httprequest"
    ref="upload"
  >
    <i class="el-icon-upload"></i>
    <div class="el-upload__tip" slot="tip">只能上传xlsx/xls文件。</div>
  </el-upload>
</template>

<script>
import { delFile } from "api/modules/upload.js";
import { getToken } from "@/lib/currency";
import {uploadXlsx} from 'api/modules/upload'
function noop() {}

export default {
  data() {
    return {
      showViewer: false,
      ViewerimgList: [],
      initialIndex: 0,
    };
  },
  props: {
    action: {
      type: String,
      default: () => "",
    },
    autoUpload: {
      type: Boolean,
      default: () => true,
    },
    isHide: {
      type: Boolean,
      default: () => true,
    },
      onProgress: {
      type: Function,
      default: noop,
    },
  },
  computed: {
    headers() {
      return {
        token: getToken(),
      };
    },
  },
  created() {},

  mounted() {},
  methods: {
    httprequest(param){
      return uploadXlsx(param)
    },
    preview(item, i) {
      this.initialIndex = i;
      let newArr = [];
      this.imglist.forEach((item, i) => {
        newArr.push(item.filePath);
      });
      this.ViewerimgList = newArr;
      this.showViewer = true;
    },
    handleAvatarSuccess(res) {
      if (res.success) {
        if(res.msg==null|| res.msg &&res.msg.indexOf("成功") != -1){
           this.$emit("success", res);
        }else{
          this.$message.warning(res.msg);
           this.$emit("error", {
             success:false,
             msg:res.msg,
             type:'warning'
           });
        }
      } else {
        this.$emit("error", res);
        this.$message.error(res.msg);
      }
    },
    beforeUpload(file) {
      // console.log(file)
      this.importLoading = true;
    },
    progress(file){
      this.$emit("progress")
    },
    uploadError() {
      this.$emit("error");
      this.importLoading = false;
      this.$message.error("未知异常，请联系管理员");
    },
    dele(item, i) {
      console.log(item);
      this.$confirm("是否永久删除该图片", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          delFile(item.id).then((res) => {
            if (res.success) {
              this.imglist.splice(i, 1);
              this.$message({
                message: "删除成功",
                type: "success",
              });
            } else {
              this.$message({
                message: res.msg,
                type: "error",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            message: "取消删除",
            type: "info",
          });
        });
    },
    success(res, file, fileList) {
      //   this.$refs.uplodimgs.clearFiles();
      this.$refs.uplodimgs.handleRemove(file);
      if (res.success) {
        // console.log(this.$refs.uplodimgs);
        this.$emit("success", res.data);
        this.$message({
          message: "上传成功",
          type: "success",
        });
      } else {
        this.$message({
          message: res.msg,
          type: "error",
        });
      }
    },
    handleRemove(file) {
      // console.log(file);
      // 实现缩略图模板时删除文件
      //   let fileList = this.$refs.upload.uploadFiles;
      //   let index = fileList.findIndex((fileItem) => {
      //     return fileItem.uid === file.uid;
      //   });
      //   fileList.splice(index, 1);
    },
    error(err, file, fileList) {
      this.$refs.uplodimgs.clearFiles();
      this.$message({
        message: "未知异常，上传失败，请联系管理员！",
        type: "error",
      });
    },
    filelength(){
      this.$refs.upload.uploadFiles.length
    }
  },
};
</script>
<style lang='scss' scoped>
.isHide {
  display: none;
}
</style>
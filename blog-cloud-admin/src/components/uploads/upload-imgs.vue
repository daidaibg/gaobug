<!--
 * @Author: daidai
 * @Date: 2022-01-06 14:10:05
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-13 15:00:23
 * @FilePath: \web-pc\src\components\uploads\upload-imgs.vue
-->

<template>
  <div class="uplodimg_wrap">
    <yh-upload-img
      :action="''"
      :multiple="false"
      :autoUpload="autoUpload"
      @deleteFile="deleteFile"
      @imageViewer="imageViewer"
      @onRemove="uploadstop"
      :before-upload="beforeUpload"
      :on-success="success"
      :on-error="onError"
      :on-exceed="onExceed"
      :fileOption="fileOption"
      :fileList="imglist"
      :maxLength="maxLength"
      :httpRequest="httpRequest"
      ref="uploadimg"
    >
    </yh-upload-img>
    <yh-image-viewer
      v-if="showViewer"
      :on-close="
        () => {
          showViewer = false;
        }
      "
      :zIndex="9999"
      :initialIndex="initialIndex"
      :url-list="ViewerimgList"
    />
  </div>
</template>

<script>
import { delFile, currentFILEImg } from "api/modules/upload.js";
import {beforeUpoads} from '@/lib/currency'
export default {
  data() {
    return {
      showViewer: false,
      ViewerimgList: [],
      initialIndex: 0,
      fileOption: {
        id: "id",
        url: "filePath",
      },
    };
  },
  props: {
    id: {
      type: [Number, String],
      default: "",
    },
    type: {
      type: Number,
      require: true,
    },
    autoUpload: {
      type: Boolean,
      default: () => true,
    },
    imglist: {
      type: Array,
      default: () => [],
    },
    maxLength: {
      type: Number,
    },
  },
  computed: {},
  created() {
  },

  mounted() {},
  methods: {
    beforeUpload(file){
        let flag = beforeUpoads(file)
        if(!flag.success){
          this.$message({
            message: flag.msg,
            type: 'warning'
          });
        }
        console.log(flag)
        return flag.success
    },
    submit(id) {
      this.$refs.uploadimg.submit();
    },
    httpRequest(param) {
      return currentFILEImg(this.type, this.id, param);
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
    dele(item, i) {
      //   console.log(item);
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

      if (res.success) {
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
      this.uploadstop()
    },
    uploadstop(){
      // console.log(this.$refs.uploadimg.files)
      if(this.$refs.uploadimg.files.length===0){
        this.$emit("uploadstop");
      }
    },
    getFile(rawFile) {
      let fileList = this.imglist;
      let target, index;
      fileList.every((item, i) => {
        if (rawFile.id === item.id) {
          (target = item), (index = i);
        } else {
          target = null;
        }
        return !target;
      });

      if (target) {
        return {
          target,
          index,
        };
      } else {
        return null;
      }
    },
    deleteFile(file) {
      let data = this.getFile(file);
      this.dele(file, data.index);
     
    },
    imageViewer(file) {
      let data = this.getFile(file);
      this.preview(file, data.index);
    },
    onError(err, file, fileList) {
      this.uploadstop()
      this.$message({
        message: "未知异常，上传失败，请联系管理员！",
        type: "error",
      });
    },
    onExceed(){
      this.$message({
        message: '超出最大上传数量，请重新选择',
        type: 'warning'
      });
    }
  },
};
</script>
<style lang='scss' scoped>
</style>
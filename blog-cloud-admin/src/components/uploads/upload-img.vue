<!--
 * @Author: daidai
 * @Date: 2021-12-20 16:26:12
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-04 17:41:50
 * @FilePath: \web-pc\src\components\uploads\upload-img.vue
-->
<template>
  <div class="uplodimg_wrap">
    <ul class="img-list">
      <li v-for="(item, i) in imglist" :key="i">
        <img :src="item.filePath" alt="" />
        <div class="Mask flex-center">
          <i class="el-icon-delete" @click="dele(item, i)"></i>
          <i class="el-icon-zoom-in" @click="preview(item, i)"></i>
        </div>
      </li>
      <el-upload
        v-if="imglist.length< maxImg "
        class="uplodimg"
        :action="action"
        ref="uplodimgs"
        accept=".jpg,.jpeg,.png,.gif,.bmp,.pdf,.JPG,.JPEG,.PBG,.GIF,.BMP,.PDF"
        list-type="picture-card"
        :on-remove="handleRemove"
        :auto-upload="autoUpload"
        :headers="headers"
        :on-success="success"
        :on-error="error"
      >
        <i class="el-icon-plus"></i>
      </el-upload>
    </ul>
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
import { delFile } from "api/modules/upload.js";
import {getToken} from '@/lib/currency'

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
    imglist: {
      type: Array,
      default: () => [],
    },
    maxImg: {
      type: Number,
      default: () => 30,
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
  },
};
</script>
<style lang='scss' scoped>
.uplodimg {
  /deep/ .el-upload,
  /deep/ .el-upload-list__item {
    width: 100px;
    height: 100px;
  }
  /deep/ .el-upload {
    line-height: 102px;
  }
}
.img-list {
  display: flex;
  flex-wrap: wrap;
  li {
    width: 100px;
    height: 100px;
    overflow: hidden;
    background-color: #fff;
    border: 1px solid #c0ccda;
    border-radius: 6px;
    box-sizing: border-box;
    margin: 0 8px 8px 0;
    position: relative;
    display: inline-block;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    .Mask {
      position: absolute;
      width: 100%;
      height: 100%;
      left: 0;
      top: 0;
      z-index: 1;
      background: rgba($color: #000000, $alpha: 0.4);
      // display: none;
      align-items: center;
      opacity: 0;
      transition: opacity 0.4s;
      i {
        color: #fff;
        font-size: 16px;
        cursor: pointer;
        &:hover {
          color: $primary-color;;
        }
      }
      i + i {
        margin-left: 10px;
      }
    }
    &:hover {
      .Mask {
        opacity: 1;
      }
    }
  }
}
</style>
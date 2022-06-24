<!--
 * @Author: daidai
 * @Date: 2021-12-17 15:17:01
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-23 14:45:48
 * @FilePath: \web-pc\src\views\tzgl\terminal\terminal-add-or-update.vue
-->
<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="500px"
    :close-on-click-modal="false"
    class="role_add"
    top="190px"
    append-to-body
  >
    <el-form
      label-position="right"
      label-width="90px"
      :model="formData"
      :rules="rules"
      ref="user_add"
      size="small"
      v-loading="loading"
      element-loading-spinner="el-icon-loading"
    >
      <el-form-item label="终端ID：" prop="nameno">
        <el-input v-model="formData.nameno"></el-input>
      </el-form-item>
      <el-form-item label="相线：" prop="phase">
        <el-input v-model="formData.phase" @input="onPhaseChange"></el-input>
      </el-form-item>
      <!-- <el-form-item label="是否在线：" prop="isactive">
          <el-radio-group v-model="formData.isactive">
            <el-radio :label="0">离线</el-radio>
            <el-radio :label="1">在线</el-radio>
          </el-radio-group>
      </el-form-item> -->
      <div class="imgs" v-if="type == 'edit'">
        <label>照片:&nbsp;&nbsp;</label>
        <div>
         <UploadImgs
            :autoUpload="true"
            @success="success"
            @uploadstop="uploadstop"
            :id="row.id"
            :type="2"
            :imglist.sync="imglist"
            :maxLength="1"
            ref="uploadimg"
          />
        </div>
      </div>
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
import { currentSave, currentUpdate } from "api/modules";
import UploadImgs from "@/components/uploads/upload-imgs";

export default {
  components: { UploadImgs },

  data() {
    return {
      visible: false,
      title: "",
      loading: false,
      type: "add",
      formData: {
        nameno: "",
        phase: "",
      },
      rules: {
        nameno: [
          { required: true, message: "请输入终端ID！", trigger: "blur" },
        ],
        phase: [{ required: true, message: "请输入相线！", trigger: "blur" }],
      },
      isactiveOp: [
        {
          value: 0,
          label: "激活",
        },
        {
          value: 1,
          label: "不激活",
        },
      ],

      imglist: [],
      row:{}
    };
  },
  props: {
    id: {
      type: String,
      default: () => "",
    },
  },
  mounted() {},
  methods: {
    init(type, row) {
      this.visible = true;
      // console.log(row);
      this.type = type;
      if (type == "add") {
        this.title = "添加设备";
        for (let i in this.formData) {
          this.formData[i] = "";
        }
        this.formData.isactive = 0;
      } else {
        this.title = "编辑终端-" + row.nameno;
        console.log(row);
        this.row = { ...row };
        this.imglist = row.imgUrlList
        for (let i in this.formData) {
          this.formData[i] = row[i];
        }
      }
    },
    success(data) {
      console.log(data);
      this.imglist.push(data);
    },
    uploadstop(){
      
    },
    //相线控制字母大写
    onPhaseChange(  val){
      this.formData.phase = val.toUpperCase();
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
        param.gatewayid = this.id;
        this.loading = true;
        fun(3, param).then((data) => {
          this.loading = false;
          if (data.success) {
            this.$message.success(str + "成功");
            this.$emit("getdata");
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
    width: 90px;
    min-width: 90px;
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
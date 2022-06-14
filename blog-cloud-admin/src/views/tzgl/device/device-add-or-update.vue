<!--
 * @Author: daidai
 * @Date: 2021-12-17 15:17:01
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-23 14:45:40
 * @FilePath: \web-pc\src\views\tzgl\device\device-add-or-update.vue
-->
<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="700px"
    :close-on-click-modal="false"
    class="role_add"
    top="90px"
  >
    <el-form
      label-position="right"
      label-width="120px"
      :model="formData"
      :rules="rules"
      ref="user_add"
      size="small"
      v-loading="loading"
      element-loading-spinner="el-icon-loading"
          element-loading-text="拼命上传中"
         element-loading-background="rgba(255, 255, 255, 0.6)"
    >
      <div class="flex-sb">
        <el-form-item label="设备ID：" prop="gatewayno">
          <el-input v-model="formData.gatewayno"></el-input>
        </el-form-item>
        <el-form-item label="电话卡：" prop="simno">
          <el-input v-model="formData.simno" ></el-input>
        </el-form-item>
      </div>
      <div class="flex-sb">
        <el-form-item label="线路名称：" prop="lineid">
          <el-select
            v-model="formData.lineid"
            filterable
            placeholder="请选择线路"
            @change="linechange"
          >
            <el-option
              v-for="item in lineoptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="杆号名称：" prop="poleid">
          <el-select
            v-model="formData.poleid"
            filterable
            placeholder="请选择杆塔"
          >
            <el-option
              v-for="item in poleoption"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <div class="flex-sb">
        <el-form-item label="管理人员：" prop="userid">
          <el-select
            v-model="formData.userid"
            filterable
            placeholder="请选择人员"
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
        <el-form-item label="设备型号：" prop="model">
          <el-input v-model="formData.model"></el-input>
        </el-form-item>
      </div>
      <div class="flex-sb">
        <el-form-item label="经度：" prop="x">
          <el-input v-model="formData.x">
            <template slot="append">
              <i class="el-icon-location" @click="getlnglat"></i>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="纬度：" prop="y">
          <el-input v-model="formData.y">
            <template slot="append">
              <i class="el-icon-location" @click="getlnglat"></i>
            </template>
          </el-input>
        </el-form-item>
      </div>
      <div class="flex-sb">
        <el-form-item label="是否激活：" prop="isactive">
          <el-select v-model="formData.isactive" placeholder="请选择">
            <el-option
              v-for="item in isactiveOp"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否开启短信：" prop="ismessage">
          <el-select v-model="formData.ismessage" placeholder="请选择">
            <el-option
              v-for="item in ismessageeOp"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-form-item label="备注：" prop="remark">
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="请输入备注"
          v-model="formData.remark"
        >
        </el-input>
      </el-form-item>
      <div class="imgs">
        <label>照片:&nbsp;&nbsp;</label>
        <div>
          <UploadImgs
            :autoUpload="type == 'edit'"
            @success="success"
            @uploadstop="uploadstop"
            :id="id"
            :type="1"
            :imglist.sync="imglist"
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
    <DeviceLngLat ref="lnglat" @close="lnglat" />
  </el-dialog>
</template>
<script>

import {
  currentSave,
  currentUpdate,
  currentSelect,
  currentList
} from "api/modules";
import { userQueryUserList } from "api/modules/sys";
import DeviceLngLat from "./device-lnglat.vue";
import { uploadUrl } from "api/modules/upload";
import UploadImgs from "@/components/uploads/upload-imgs";

import { validatePhone, validateNum } from "@/constants/rules";
export default {
  components: { DeviceLngLat, UploadImgs },
  data() {
    return {
      visible: false,
      title: "",
      loading: false,
      type: "add",
      formData: {
        gatewayno: "",
        simno: "",
        userid: "",
        lineid: "",
        poleid: "",
        x: "",
        y: "",
        isactive: "",
        ismessage: "",
        model: "",
        remark: "", //备注
      },
      rules: {
        gatewayno: [
          { required: true, message: "请输入设备ID！", trigger: "blur" },
          { required: true, validator: validateNum, trigger: "change" },
        ],
        simno: [
          { required: true, message: "请输入电话卡号", trigger: "change" },
        ],
        userid: [{ required: true, message: "请选择人员！", trigger: "blur" }],
        lineid: [{ required: true, message: "请选择线路！", trigger: "blur" }],
        poleid: [{ required: true, message: "请选择杆塔！", trigger: "blur" }],
        x: [
          {
            required: true,
            message: "请输入或选择经纬度！",
            trigger: "change",
          },
        ],
        y: [
          {
            required: true,
            message: "请输入或选择经纬度！",
            trigger: "change",
          },
        ],
      },
      lineoptions: [],
      useroptions: [],
      poleoption: [],
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
      ismessageeOp: [
        {
          value: 0,
          label: "否",
        },
        {
          value: 1,
          label: "是",
        },
      ],
      imglist: [],
      row: null,
      id: "",
    };
  },
  mounted() {
    this.getOption();
  },
  methods: {
    init(type, row) {
      this.visible = true;
       this.loading = false;
       this.imglist= []
      // console.log(row);
      this.type = type;
      this.$nextTick(() => {
        this.$refs.user_add.resetFields();
        if (type == "add") {
          this.title = "添加设备";
          for (let i in this.formData) {
            this.formData[i] = "";
          }
          this.formData.isactive = 0;
          this.formData.ismessage = 0;
          if (this.lineoptions[0]) {
            this.formData.lineid = this.lineoptions[0].id;
            this.getPoleOption();
          }
        } else {
          this.title = "编辑设备-" + row.gatewayno;
          // console.log(row);
          this.row = { ...row };
          this.id = row.id;
          this.imglist = row.imgUrlList;
          for (let i in this.formData) {
            this.formData[i] = row[i];
          }
          if (row.poleid) {
            this.getPoleOption();
          }
        }
      });
    },
    success(data) {
      // console.log(data);
      this.imglist.push(data);
    },
    uploadstop(){
      if(this.type=='edit')return
      this.$message.success("新增成功");
      this.$parent.getdata();
        this.loading = false;
      this.visible = false;
  
    },
    lnglat(lnglat) {
      (this.formData.x = lnglat.lng), (this.formData.y = lnglat.lat);
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
        fun(1, param).then((data) => {
          
          // console.log(data.data);
          if (data.success) {
            if (this.type == "add") {
              this.id = data.data.tableId;
              this.$nextTick(() => {
                this.$refs.uploadimg.submit(data.data.tableId);
              });
            } else {
               this.loading = false;
              this.$message.success(str + "成功");
              this.$parent.getdata();
              this.visible = false;
            }
          } else {
              this.loading = false;
            this.$message.error(data.msg);
          }
        });
      });
    },
    getlnglat() {
      // console.log(this.row)
      if (this.type == "edit" && this.row.x && this.row.y) {
        this.$refs.lnglat.init(this.row.x, this.row.y);
      } else {
        this.$refs.lnglat.init();
      }
    },
    //线路变化
    linechange(val) {
      this.formData.poleid = "";
      this.getPoleOption();
    },
    //根据线路获取杆塔
    getPoleOption() {
      currentList(6,{
        lineId: this.formData.lineid,
      }).then((res) => {
        if (res.success) {
          //   console.log(res);
          this.poleoption = res.data;
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    //获取下拉框列表
    getOption() {
      //线路列表
      currentSelect(2).then((res) => {
        if (res.success) {
          this.lineoptions = res.data;
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
      //用户列表
      userQueryUserList().then((res) => {
        if (res.success) {
          this.useroptions = res.data;
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
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
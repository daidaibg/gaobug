<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
    :visible.sync="visible"
    v-dialogDrag
    width="500px"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
      size="small"
    >
      <el-form-item label="公司名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="所属公司" prop="parentid">
        <CompanyTree
          v-model="dataForm.parentid"
          @handleNodeClick="onCompanyTree"
          ref="companyTree"
        />
      </el-form-item>
      <el-form-item label="所属区域" prop="qy">
        <!-- <RegionTree
          :parentId="dataForm.regionid"
          @handleNodeClick="handleNodeClick"
          ref="regionTree"
        /> -->
        <RegionTreeCascader
          style="width: 100%"
          @handleNodeClick="handleNodeClick2"
          ref="regionTree"
          :isDelOne="true"
        />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取消</el-button>
      <el-button
        type="primary"
        @click="dataFormSubmit()"
        size="small"
        :loading="loading"
        >确定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import { companySave, companyUpdate } from "api/modules/sys";
import RegionTree from "@/components/trees/region-tree.vue";
import RegionTreeCascader from "@/components/trees/region-cascader.vue";
import CompanyTree from "@/components/trees/company-tree.vue";
export default {
  components: { RegionTree, RegionTreeCascader, CompanyTree },
  data() {
    return {
      visible: false,
      loading: false,
      dataForm: {
        name: "",
        cityid: "", //市
        countyid: "", //区
        provinceid: "", //省
        qy: "",
        parentid: "",
        parentName: "",
      },
      regionTypeSelect: this.$parent.regionTypeSelect,
      title: "",
      dataRule: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        qy: [
          { required: true, message: "所属区域不能为空", trigger: "change" },
        ],
        parentid: [
          { required: true, message: "所属公司不能为空", trigger: "change" },
        ],
      },
    };
  },
  methods: {
    init(type, row) {
      // console.log(type, row);
      this.visible = true;
      this.type = type;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (type == "add") {
          this.title = "添加 - 公司";
          let CompanyData = { ...this.$parent.CompanyData };
          // console.log(CompanyData);
          this.dataForm.parentid = CompanyData.id;
          this.dataForm.parentName = CompanyData.name;
           this.initRegion(CompanyData)
        } else {
          this.title = row.name + " - 编辑";
          this.row = { ...row };
          for (let i in this.dataForm) {
            this.dataForm[i] = row[i];
          }
          this.initRegion(row)
        }
      });
    },
    initRegion(data) {
      let arrs = [];
      if (data.provinceid) arrs.push(data.provinceid);
      if (data.cityid) arrs.push(data.cityid);
      if (data.countyid) arrs.push(data.countyid);
      this.$refs.regionTree.initChekeds(arrs);
      this.handleNodeClick2(arrs);
    },
    handleNodeClick(val) {
      this.dataForm.regionName = val.name;
      this.dataForm.regionid = val.id;
    },
    //行政区域选择 
    handleNodeClick2(val, node) {
      // console.log(val);
      (this.dataForm.provinceid = val[0] || ""), //省
        (this.dataForm.cityid = val[1] || ""), //市
        (this.dataForm.countyid = val[2] || ""); //区
      if (val.length > 0) {
        this.dataForm.qy = "true";
      } else {
        this.dataForm.qy = "";
      }
    },
    //点击公司树
    onCompanyTree(val) {
      this.dataForm.parentName = val.name;
      // this.dataForm.parentid = val.id;
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          let param = { ...this.dataForm };
          let fun = companySave;
          let str = "添加";
          if (this.type == "edit") {
            fun = companyUpdate;
            param.id = this.row.id;
            str = "编辑";
          }
          // console.log(param);
          this.loading = true;
          fun(param).then((data) => {
            this.loading = false;
            if (data.success) {
              this.$message.success(str + "成功");
              this.$parent.getAllData();
              this.$refs.companyTree.getdata()
              this.visible = false;
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
  },
};
</script>

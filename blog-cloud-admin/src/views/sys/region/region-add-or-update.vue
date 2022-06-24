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
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入名称"></el-input>
      </el-form-item>

      <el-form-item label="上级区域" prop="parentid">
        <!-- <RegionTree
          :parentId="dataForm.parentid"
          @handleNodeClick="handleNodeClick"
          ref="regionTree"
        /> -->
        <RegionTreeCascader
          style="width: 100%"
          @handleNodeClick="handleNodeClick2"
          ref="regionTree"
          :isDelOne="false"
          disabled
        />
      </el-form-item>
      <el-form-item label="行政级别" prop="lev">
        <el-select
          v-model="dataForm.lev"
          placeholder="请选择所属区域"
          disabled
          clearable
          style="width: 100%"
        >
          <el-option
            v-for="item in regionTypeSelect"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取消</el-button>
      <el-button
        type="primary"
        @click="dataFormSubmit()"
        size="small"
        :loadin="loading"
        >确定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import { regionSave, regionUpdate } from "api/modules/sys";
import RegionTree from "@/components/trees/region-tree.vue";
import RegionTreeCascader from "@/components/trees/region-cascader.vue";

export default {
  components: { RegionTree, RegionTreeCascader },
  data() {
    return {
      visible: false,
      loading: false,
      dataForm: {
        name: "",
        lev: "",
        parentid: "",
        parentName: "",
      },
      regionTypeSelect: this.$parent.regionTypeSelect,
      title: "",
      dataRule: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        lev: [{ required: true, message: "行政级别不能为空", trigger: "blur" }],
        parentid: [
          { required: true, message: "父节点不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    init(type, row) {
      console.log(type, row);
      this.visible = true;
      this.type = type;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (type == "add") {
          this.title = "添加 - 区域";
          this.dataForm.parentid = -1;
          this.dataForm.parentName = "中国";
          let id = "";
          console.log(this.$parent.regionData);
          if (this.$parent.regionData.lev < 3) {
            this.dataForm.lev = this.$parent.regionData.lev + 1;
            id = this.$parent.regionData.id;

            this.dataForm.parentid = id;
            this.dataForm.parentName = this.$parent.regionData.name;
          }
          this.$refs.regionTree.init(id);
        } else {
          this.title = row.name + " - 编辑";
          this.row = { ...row };
          this.$refs.regionTree.init(row.parentid);

          for (let i in this.dataForm) {
            this.dataForm[i] = row[i];
          }
        }
      });
    },
    handleNodeClick(val) {
      this.dataForm.parentName = val.name;
      this.dataForm.parentid = val.id;
    },
    handleNodeClick2(val, node) {
      // console.log(node)
      if (node) {
        this.dataForm.lev = node.data.lev + 1;
        this.dataForm.parentid = node.data.id;
        this.dataForm.parentName = node.data.name;
      }
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          let param = { ...this.dataForm };
          let fun = regionSave;
          let str = "添加";
          if (this.type == "edit") {
            fun = regionUpdate;
            param.id = this.row.id;
            str = "编辑";
          }
          console.log(param);
          this.loading = true;
          fun(param).then((data) => {
            this.loading = false;
            if (data.success) {
              this.$message.success(str + "成功");
              this.$parent.Echo();
              this.$refs.regionTree.getdata();
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

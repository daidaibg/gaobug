<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    
    :visible.sync="visible"
    width="600px"
    class="menu_add"
    :close-on-click-modal="false"
  >
    <el-form
      label-position="right"
      label-width="92px"
      :model="formData"
      :rules="rules"
      ref="add"
      size="small"
    >
      <el-form-item label="名称：" prop="name">
        <el-input v-model="formData.name" clearable></el-input>
      </el-form-item>
      <el-form-item label="类型：" prop="type">
        <el-select
          v-model="formData.type"
          placeholder=""
          clearable
          style="width: 100%"
        >
          <el-option
            v-for="item in menuTypeSelect"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="路径：" prop="url">
        <el-input v-model="formData.url" clearable></el-input>
      </el-form-item>
      <el-form-item label="授权：" prop="perms">
        <el-input v-model="formData.perms" clearable></el-input>
      </el-form-item>
      <el-form-item label="排序：" prop="orderNum">
        <el-input v-model="formData.orderNum" clearable></el-input>
      </el-form-item>

      <el-form-item label="父级菜单：" prop="parentName">
        <MenuTreeNew
          :parentId="formData.parentId"
          @handleNodeClick="handleNodeClick"
          
          style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="图标：" prop="icon">
        <IconSelect :val.sync="formData.icon"/>
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
import { menuSave, menuUpdate } from "api/modules/sys";
import { menuTypeSelect } from "@/config/sys-config";
import MenuTreeNew from "@/components/trees/menu-tree.vue";
import IconSelect from "@/components/icons/icon-select.vue";
export default {
  components: {
    MenuTreeNew,
    IconSelect,
  },
  data() {
    return {
      visible: false,
      title: "编辑",
      formData: {
        name: "",
        parentName: "",
        parentId: "",
        url: "",
        orderNum: "",
        type: "",
        perms: "",
        icon: "",
      },
      rules: {
        name: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
        type: [{ required: true, message: "请选择菜单类型", trigger: "blur" }],
        // url: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
        // types: [{ required: true, message: '请选择菜单类型', trigger: 'blur' }],
      },
      type: "edit",
      loading: false,
      menuTypeSelect: menuTypeSelect,
    };
  },

  created() {},
  mounted() {},
  methods: {
    init(type, row) {
      console.log(type, row);
      if (this.$refs.add) {
        this.$refs.add.resetFields();
      }
      this.visible = true;
      this.loading = false;
      this.type = type;
      if (type == "add") {
        this.title = "添加 - 菜单";
        for (let i in this.formData) {
          this.formData[i] = "";
        }

      } else {
        // console.log(row)
        this.title = row.name + "-编辑";
        this.row = {...row};
        for (let i in this.formData) {
          this.formData[i] = row[i];
        }
      }
    },
    handleNodeClick(val) {
      // console.log(val)
      this.formData.parentName = val.name;
      this.formData.parentId = val.menuId;
    },
    subItem() {
      this.$refs.add.validate((valid) => {
        if (!valid) {
          return;
        }
        let param = { ...this.formData };
        let fun = menuSave;
        let str = "添加";
        if (this.type == "edit") {
          fun = menuUpdate;
          param.menuId = this.row.menuId;
          str = "编辑";
        }
        if (param.parentName == "" || param.parentName == null) {
          param.parentId = 99999;
        }
        // console.log(param);
        this.loading = true;
        fun(param).then((data) => {
          this.loading = false;
          // console.log(data.data);
          if (data.success) {
            this.$message.success(str+"成功");
            this.$parent.getdata();
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

<style lang="scss">
.menu_add {
  .tubiao {
    .el-input__inner {
      z-index: 2;
      position: relative;
      background: transparent;
    }
    .el-input__suffix {
      position: absolute;
      left: 0px;
      top: 0;
      font-size: 22px;
      color: #000;
      z-index: 0;
    }
  }
}
</style>
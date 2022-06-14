<!--
 * @Author: daidai
 * @Date: 2021-12-16 09:56:32
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-21 14:30:03
 * @FilePath: \web-pc\src\views\tzgl\line\line-add.vue
-->

<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    :visible.sync="visible"
    width="500px"
    :close-on-click-modal="false"
    class="role_add"
  >
    <LineForm ref="form" @close="close" />
    <span class="dialog-footer" slot="footer">
      <el-button @click="visible = false" size="mini">取 消</el-button>
      <el-button type="primary" @click="subItem" size="mini" :loading="loading"
        >保 存</el-button
      >
    </span>
  </el-dialog>
</template>
<script>
import LineForm from "./line-form.vue";
export default {
  components: { LineForm },
  data() {
    return {
      visible: false,
      title: "",
      loading: false,
    };
  },
  methods: {
    init(type, row) {
      this.visible = true;
      this.title = "添加线路";
      this.$nextTick(() => {
        this.$refs.form.init(type, row);
      });
    },
    close() {
      this.$parent.getData();
      this.visible = false;
    },
    subItem() {
      this.$nextTick(() => {
        this.$refs.form.subItem();
      });
    },
  },
};
</script>

<!--
 * @Author: daidai
 * @Date: 2021-12-06 11:35:52
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-16 16:48:45
 * @FilePath: \web-pc\src\views\manage-home\manage-submenu.vue
-->
<template >
  <el-submenu :index="menuChidren.url" :key="menuChidren.url">
    <template slot="title">
      <i :class="menuChidren.icon"></i>
      <span slot="title">{{ menuChidren.name }}</span>
    </template>
    <template v-for="item in menuChidren.list">
      <template v-if="item.list && item.list.length > 0">
        <submenu :menuChidren="item" :key="item.url" />
      </template>
      <el-menu-item v-else :key="item.url" :index="item.url">{{ item.name }}</el-menu-item>
    </template>
  </el-submenu>
</template>
<script>
export default {
  name: "submenu",
  props: {
    menuChidren: {
      type:  Object,
      required: true,
    },
  },
  mounted() {
    // console.log(this.menuChidren);
  },
};
</script>
<style lang="scss" scoped>
.el-submenu {
}
</style>
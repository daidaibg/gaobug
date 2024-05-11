<template>
  <gb-header />
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component
        :is="Component"
        :key="$route.name"
        v-if="$route.meta.keepAlive"
      />
    </keep-alive>
    <component
      :is="Component"
      :key="$route.name"
      v-if="!$route.meta.keepAlive"
    />
  </router-view>
</template>
<script setup lang="ts">
import GbHeader from "@/components/header/gb-header.vue";
</script>
<style lang="scss" scoped>
.page-container_wrap {
  width: 100%;
  height: calc(100% - 80px);
  position: relative;
  flex: 1;
}

.page-container {
  padding: 0 0px;

  .el-main {
    height: 100%;
    padding-bottom: 100px;
    padding-top: 50px;
    box-sizing: border-box;
  }
}
</style>

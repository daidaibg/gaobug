<script setup lang='ts'>
import { ElMain } from "element-plus"
</script>

<template>
  <el-main class="layout-main">
    <router-view v-slot="{ Component }">
      <transition name="slide-right" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </el-main>
</template>

<style scoped lang='scss'>
.layout-main{
  padding:16px var(--main-space);
  position: relative;
  // height: ;
}
</style>
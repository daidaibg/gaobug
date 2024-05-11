<script setup lang="ts">
import { Logo } from "@/components/header/logo";
import {  useRoute } from "vue-router";
import GbHeader from "@/components/header/gb-header.vue";

const route = useRoute();
// console.log(route);
</script>

<template>
  <GbHeader :tabShow="false" v-if="route.meta.isShowHeader"><template #search>  </template> </GbHeader>
  <!-- <div class="tools-layout">
    <Logo class="tools-layout_logo" />
    <span class="tools-layout_title">{{route.meta.title||"搞bug工具"}}</span>
  </div> -->
  <router-view />
</template>

<style scoped lang="scss">
.tools-layout {
  position: fixed;
  left: 48px;
  top: 16px;
  background-color: var(--dd-component-card-background);
  backdrop-filter: blur(10px);
  border-radius: 30px;
  height: 40px;
  padding: 0 16px;
  display: flex;
  align-items: center;
  box-shadow:var(--yh-shadow-3) ;

  .tools-layout_logo {
    height: 100%;
  }
  .tools-layout_title{
    margin-left: 12px;
    font-size: 14px;
    color: var(--yh-text-color-brand);
  }
}
</style>

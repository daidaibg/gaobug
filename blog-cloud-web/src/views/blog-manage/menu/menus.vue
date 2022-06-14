<script setup lang='ts'>
import { manageMenuList } from "./menus"
import { useRoute, useRouter } from "vue-router";
import Menus from "@/components/menus"
const router = useRouter()
const gowrite = () => {
  router.push('/write/essay')
}
</script>

<template>

  <Menus :menus="manageMenuList">
    <template #header>
      <div class="flex justify-center items-center px-8 py-3">
        <yh-button theme="primary" size="medium" block shape="round" @click="gowrite">写文章</yh-button>

      </div>
    </template>

  </Menus>
</template>

<style scoped lang='scss'>
.blog_menu {
  width: 240px;
  height: 100%;
  overflow-y: auto;
  border-right: none;
  background: var(--yh-bg-color-container);
  border-radius: 4px;
  box-sizing: border-box;
  padding: 6px;

  :deep(.el-sub-menu__title) {
    color: var(--yh-text-color-primary);

  }

  :deep(.el-sub-menu) {
    &.is-active {
      >.el-sub-menu__title {
        color: var(--yh-brand-color);
      }
    }
  }

  .el-icon {
    font-size: 16px;
  }

  :deep(.el-sub-menu__title) {
    height: 44px;
  }

  :deep(.el-menu-item) {
    border-radius: 4px;
    height: 44px;
    color: var(--yh-text-color-primary);

    &.is-active {
      background-color: var(--yh-brand-color-focus);
      color: var(--yh-brand-color);

    }
  }
}
</style>
<script setup lang='ts'>
import { ElSubMenu, ElMenu, ElMenuItemGroup, ElMenuItem } from "element-plus"
import { ref, computed, PropType } from "vue"
import { useRoute, useRouter } from "vue-router";
import { MenuProps } from "./menus"
const route = useRoute();
const router = useRouter()
const defaultActive = computed(() => {
  return route.path
})
const props = defineProps({
  menus: {
    type: Array as PropType<MenuProps[]>,
    default: []
  }
})
</script>

<template>
  <div class="menu_wrap">
    <slot name="header"></slot>
    <el-menu class="blog_menu" :collapse="false" router :default-active="defaultActive">
      <template v-for="item in menus" :key="item.url">
        <el-sub-menu index="1" v-if="item.sub" :key="item.url">
          <template #title>
            <i :class="item.icon" class="el-icon"></i>
            <span>{{ item.name }}</span>
          </template>
          <el-menu-item :index="subItem.url" v-for="subItem in item.sub" :key="item.url">{{ subItem.name }}
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item v-else :index="item.url">
          <i :class="item.icon" class="el-icon"></i>
          <template #title>{{ item.name }}</template>
        </el-menu-item>
      </template>
    </el-menu>
  </div>

</template>

<style scoped lang='scss'>
.menu_wrap {
  width: 240px;
  height: 100%;
  overflow-y: auto;
  background: var(--yh-bg-color-container);
  border-radius: 4px;
  box-sizing: border-box;
  padding: 6px;
}
.blog_menu {
  border-right: none;
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
    margin-right: 5px;
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
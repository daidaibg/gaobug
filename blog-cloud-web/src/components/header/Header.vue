<!--
 * @Author: daidai
 * @Date: 2021-09-09 17:19:05
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-06-14 17:57:05
 * @FilePath: \yhht-ui\src\views\Header.vue
-->
<template>
  <header class="headers box-shadow-bootom" >
    <div class="header_inner">
      <div class="left">
        <logo></logo>
        <ul class="header_list flex items-center">
          <li v-for="(item, i) in headerList" :key="i">
            <a :href="item.path" :class="{ active: active == item.path }" @click.prevent="jump(item.path)">{{
                item.translation ? $t(item.name) : item.name
            }}</a>
          </li>
        </ul>
      </div>
      <div class="right">
        <div class="phoneTab" style="display: none;">
          <el-dropdown trigger="click" @command="command">
            <el-icon size="28px">
              <operation style="margin-right: 8px" />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>

              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
        <Theme></Theme>

        <Lang></Lang>
        <User></User>


        <!-- <GitHub /> -->
      </div>
    </div>
  </header>
</template>
<script lang="ts" setup>
import { Lang } from "./lang";
import { GitHub } from "./github";
import { Theme } from "./theme";
import { Logo } from "./logo";
import User from "./user";

import { useElementSize } from '@vueuse/core'
import {
  ElDropdown,
  ElDropdownMenu,
  ElDropdownItem,
  ElIcon,
} from "element-plus";
import { Operation } from "@element-plus/icons-vue";
import { reactive, ref, Ref, computed } from "vue";
import { useRoute, useRouter } from "vue-router";





const route = useRoute();
const router = useRouter()
const headerList = ref([
  {
    name: "header.home",
    translation: true,
    path: '/'
  },
  {
    name: "分类",
    path: '#'
  },
  {
    name: "标签",
    path: '#'
  },
  {
    name: "专题",
    path: '#'
  },
  {
    name: "留言",
    path: '#'
  },
])
const active = computed(() => {
  return route.path
})
const command = (type: string) => {
  console.log(type);
  // this.$router.push(type);
};
const jump = (path: string) => {
  router.push(path)
}
</script>

<style lang="scss" scoped>
.headers {
  width: 100%;
  box-sizing: border-box;
  padding: 0 24px;
  background: var(--yh-bg-color-container);
  box-shadow: var(--yh-shoadow-bootom);
  position: fixed;
  top: 0;
  width: 100%;
  height: 60px;
  z-index: 990;
  // right: 0;
  box-shadow: var(--yh-shadow-inset-bottom);
  .header_inner {
    max-width: 1440px;
    margin: auto;
    display: flex;
    justify-content: space-between;
    height: 100%;
    // border-bottom: 1px solid var(--header-bottom-color);

  }



  .left {
    display: flex;
    align-items: center;

  }

  .header_list {
    margin-left: 16px;
    height: 100%;

    li {
      text-align: center;
      flex-shrink: 0;
      display: flex;
      justify-content: center;
      height: 100%;
      font-size: 14px;
      padding: 0 16px;

      a {
        height: 100%;
        display: flex;
        align-items: center;
        color: var(--yh-text-color-secondary);
        position: relative;

        &::before {
          width: 100%;
          content: "";
          position: absolute;
          top: auto;
          right: 0;
          bottom: 0;
          left: 0;
          height: 2px;
          background-color: var(--yh-brand-color);
          display: none;
        }

        &:hover {
          color: var(--yh-text-color-primary);

          &::before {
            display: inline;
          }
        }

        &.active {
          color: var(--yh-brand-color);
          //  &::before {
          //   display: inline;
          // }
        }
      }

    }
  }

  .right {
    display: flex;
    align-items: center;

    .right_inner {
      display: flex;
      align-items: center;
      height: 100%;
    }

    .item {
      margin: 0 10px;
      height: 100%;
      border-bottom: solid 2px rgba(25, 137, 250, 0);

      a,
      .item-inner {
        height: 100%;
        color: var(--yh-brand-color-11);
        display: flex;
        align-items: center;
      }

      cursor: pointer;

      &:hover {

        a,
        .item-inner {
          color: var(--yh-brand-color-8);
        }
      }
    }

    .activeItem {
      border-color: var(--yh-brand-color-8);

      a {
        color: var(--yh-brand-color-8);
      }
    }
  }
}
</style>

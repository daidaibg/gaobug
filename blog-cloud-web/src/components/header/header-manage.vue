<!--
 * @Author: daidai
 * @Date: 2021-09-09 17:19:05
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-06-16 10:59:17
 * @FilePath: \yhht-ui\src\views\Header.vue
-->
<template>
  <header class="headers box-shadow-bootom headers-root">
    <div class="header_inner">
      <div class="left flex">
        <logo></logo>
        <div></div>
      </div>
      <div class="right">
        <theme></theme>
        <lang></lang>
        <!-- <git-hub ></git-hub> -->
        <user></user>
      </div>
    </div>
  </header>
</template>
<script lang="ts" setup>
import { Lang } from "./lang";
import { GitHub } from "./github";
import { Theme } from "./theme";
import User from "./user";


import { useRoute } from "vue-router";
import { Logo } from "./logo";

const route = useRoute();

</script>

<style lang="scss" scoped>

.headers {
  flex: 1;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  padding: 0 24px;
  background: var(--yh-bg-color-container);
  box-shadow: var(--yh-shoadow-bootom);
  position: fixed;
  top: 0;
  width: 100%;
  height: 60px;
  z-index: 990;
  // right: $header-right;
  box-shadow: var(--yh-shadow-inset-bottom);

  .header_inner {
    max-width: 1440px;
    margin: auto;
    display: flex;
    justify-content: space-between;
    height: 100%;
    // border-bottom: 1px solid var(--header-bottom-color);

  }

  .logo {
    display: flex;
    align-items: center;

    img {
      height: 40px;
      margin-left: 0px;
    }

    p {
      font-size: 24px;
      font-weight: 900;
      color: var(--yh-brand-color-8);
      margin-left: 12px;
      color: transparent;
      background-color: var(--yh-brand-color-8);
      text-shadow: var(--header-logo-shadow);
      -webkit-background-clip: text;
      font-family: STXinwei, FZShuTi, "微软雅黑", "黑体", "宋体";
      line-height: 1;
    }
  }

  .left {
    display: flex;
    align-items: center;

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

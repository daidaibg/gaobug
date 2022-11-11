<!--
 * @Author: daidai
 * @Date: 2021-09-09 17:19:05
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-11-11 09:41:03
 * @FilePath: \yhht-ui\src\views\Header.vue
-->
<template>
  <header class="headers box-shadow-bootom">
    <div class="header_inner">
      <div class="left">
        <logo :theme="themeStore.getTheme"></logo>
        <template v-if="tabShow">
          <div class="header_list_wrap">
            <p
              class="medium-screen-hide medium-active cursor-pointer"
              :mode-data="active"
              @click.stop="showHideminShow"
            >
              {{
                activeData.translation ? $t(activeData.name) : activeData.name
              }}
            </p>
            <transition name="el-fade-in">
              <div
                class="header-nav-small medium-screen-hide"
                v-show="minShow"
                ref="nav_target"
                :class="{ navHideClass: navHide }"
              >
                <header-nav
                  :HeaderList="headerList"
                  :active="active"
                  :jump="jump"
                >
                  <div class="medium-screen-hide">
                    <Theme></Theme>
                  </div>
                </header-nav>
              </div>
            </transition>
          </div>
          <div class="small-screen-hide header_list_wrap">
            <header-nav
              :HeaderList="headerList"
              :active="active"
              :jump="jump"
            ></header-nav>
          </div>
        </template>
      </div>
      <blog-search></blog-search>
      <div class="right">
        <div class="phoneTab" style="display: none">
          <el-dropdown trigger="click" @command="command">
            <el-icon size="28px">
              <operation style="margin-right: 8px" />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu> </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
        <div class="small-screen-hide">
          <Theme></Theme>
        </div>
        <Lang></Lang>
        <User></User>
        <!-- <GitHub /> -->
      </div>
    </div>
  </header>
</template>
<script lang="ts" setup>
import { Lang } from "./lang";
import { Theme } from "./theme";
import { Logo } from "./logo";
import User from "./user";
import HeaderNav from "./nav";
import BlogSearch from "./blog-search";
// import {
//   ElDropdown,
//   ElDropdownMenu,
//   ElDropdownItem,
//   ElIcon,
// } from "element-plus";
import { Operation } from "@element-plus/icons-vue";
import { reactive, ref, Ref, computed, nextTick } from "vue";
import { useRoute, useRouter } from "vue-router";
import { HeaderListType } from "./type";
import Props from "./props";
import { on, off } from "yhht-plus/utils";
import { userThemeStore } from "@/store";

const themeStore = userThemeStore();
const router = useRouter();

defineProps(Props);
const route = useRoute();
const nav_target = ref(null);
const activeData = ref<HeaderListType>({
  name: "",
  path: "",
});
const navHide = ref<boolean>(false);
const minShow = ref<boolean>(false);
const showHideminShow = async () => {
  if (navHide.value) {
    return;
  }
  if (minShow.value) {
    off(document, "click", hideMinshow);
  } else {
    on(document, "click", hideMinshow);
  }
  minShow.value = !minShow.value;
};
const hideMinshow = (e: Event) => {
  minShow.value = false;
  off(document, "click", hideMinshow);
};
const headerList = ref<HeaderListType[]>([
  {
    name: "header.home",
    translation: true,
    path: "/",
  },
  {
    name: "分类",
    path: "#",
  },
  {
    name: "标签",
    path: "#",
  },
  {
    name: "专题",
    path: "#",
  },
  {
    name: "header.BBS",
    translation: true,
    path: "/message/board",
  },
]);
const active = computed(() => {
  let path: string = route.path;
  //如果是详情页的话渲染首页
  if (path.indexOf("/article/details") != -1) {
    path = "/";
  }
  headerList.value.forEach((item: HeaderListType) => {
    try {
      if (item.path === path) {
        activeData.value = item;
        throw new Error("终止forEach");
      }
    } catch (error) {
      return;
    }
  });
  return path;
});

const command = (type: string) => {
  console.log(type);
  // this.$router.push(type);
};
const jump = (path: string) => {
  router.push(path);
};
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
    align-items: center;
    // border-bottom: 1px solid var(--header-bottom-color);
  }

  .left {
    display: flex;
    align-items: center;
    height: 100%;
  }

  .header_list_wrap {
    position: relative;
    height: 100%;

    .medium-active {
      height: 100%;
      color: var(--yh-brand-color);
      font-size: 14px;
      position: relative;
      padding: 0 16px;
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
          color: var(--yh-brand-color);
        }
      }
    }
  }
}

.navHideClass {
  transition: opacity 0.24s;
  opacity: 0;
}
.light .headers {
  // background-image: radial-gradient(transparent 1px,#ffffff 1px) !important;
  // background-size: 4px 4px !important;
  // backdrop-filter: saturate(50%) blur(4px) !important;
  backdrop-filter: blur(8px) !important;
  // background: transparent !important;
  background-color: rgba(255, 255, 255, .6);
}
@media screen and (max-width: 960px) {
  .headers {
    height: 50px;
    padding: 0 8px;

    :deep(.logo) {
      p {
        margin-left: 6px;
      }
    }

    .medium-active {
      display: flex;
      align-items: center;
      flex-shrink: 0;
    }

    .header-nav-small :deep(.header_list) {
      padding: 8px;
      position: absolute;
      flex-direction: column;
      background-color: var(--yh-bg-color-container);
      left: -30px;
      top: 90%;
      width: 90px;
      height: auto;
      box-shadow: var(--yh-shadow-1);
      border-radius: 6px;
      border: solid 1px var(--yh-border-level-1-color);

      li {
        padding: 4px 0px;

        a {
          padding: 2px 0;
          width: 100%;
        }
      }
    }

    :deep(.actionTheme) {
      height: 24px;
      border-radius: 3px;

      .actionTheme-tabs__block {
        border-radius: 2px;
      }

      .action_item {
        height: 20px;
      }
    }
  }
}

@media screen and (max-width: 475px) {
  .headers {
    .header-nav-small :deep(.header_list) {
      left: -40px;
    }
    .header_list_wrap .medium-active {
      display: flex;
      align-items: center;
      flex-shrink: 0;
      white-space: nowrap;
      padding: 8px;
    }
    :deep(.blog-search) {
      flex: 1;
    }
  }
}
</style>

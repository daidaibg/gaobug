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

      <div class="right">
        <blog-search
          :active="active"
          v-if="searchShow && !slots.search"
        ></blog-search>
        <slot name="search"></slot>
        <div class="small-screen-hide">
          <Theme> </Theme>
        </div>
        <Lang lang-class="small-screen-hide"> </Lang>
        <User></User>
        <MenuHamburger class="medium-screen-hide" />
        <!-- <GitHub /> -->
      </div>
    </div>
  </header>
  <div class="header_fixed"></div>
</template>
<script lang="ts" setup>
import { Lang } from "./lang";
import { Theme } from "./theme";
import { Logo } from "./logo";
import User from "./user";
import HeaderNav from "./nav";
import BlogSearch from "./blog-search";
import MenuHamburger from "./menu-hamburger/menu-hamburger.vue";
import { RouterEnum } from "@/enums";
import { Operation } from "@element-plus/icons-vue";
import { reactive, ref, Ref, computed, nextTick, useSlots } from "vue";
import { useRoute, useRouter } from "vue-router";
import { HeaderListType } from "./type";
import Props from "./props";
import { on, off } from "yhht-plus/lib/utils/index";
import { userThemeStore } from "@/store";
const themeStore = userThemeStore();
const router = useRouter();
const slots = useSlots();

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
    path: RouterEnum.Home,
  },
  {
    name: "专题",
    path: "#",
  },
  {
    name: "header.BBS",
    translation: true,
    path: RouterEnum.MessageBoard,
  },
  {
    name: "header.tool",
    translation: true,
    path: RouterEnum.Tool,
  },
]);
const active = computed(() => {
  let path: string = route.path;
  //如果是详情页的话渲染首页
  if (path.indexOf(RouterEnum.ArticleDetails) != -1) {
    path = "/";
  }
  try {
    headerList.value.forEach((item: HeaderListType) => {
      if (item.path === path) {
        activeData.value = item;
        throw new Error("终止forEach");
      }
    });
  } catch (error) {}
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
.header_fixed {
  width: 100%;
  height: var(--header-height);
}
.headers {
  width: 100%;
  box-sizing: border-box;
  padding: 0 24px;
  // background: var(--yh-bg-color-container);
  background-color: var(--dd-component-card-background);
  box-shadow: var(--yh-shoadow-bootom);
  position: fixed;
  top: 0;
  width: 100%;
  height: var(--header-height);
  z-index: 990;
  // right: 0;
  box-shadow: var(--yh-shadow-inset-bottom);
  backdrop-filter: blur(8px) !important;

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
    cursor: pointer;
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
  // background: transparent !important;
  background-color: rgba(255, 255, 255, 0.6);
}
@media screen and (max-width: 960px) {
  .headers {
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

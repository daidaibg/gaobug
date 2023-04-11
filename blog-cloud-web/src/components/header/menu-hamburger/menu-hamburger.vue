<script setup lang="ts">
import { ref,nextTick } from "vue";
import { Theme } from "../theme";
import { langList } from "@/i18n";
import { useLangStore } from "@/store";

const langStore = useLangStore();
const active = ref(false);

const onHamburger = () => {
  active.value = !active.value;
  if (active.value) {
    nextTick(() => {
      document.body.style.width = "";
    });
  }
};
</script>

<template>
  <div class="menu-hamburger" :class="{ active: active }" @click="onHamburger">
    <span class="hamburger-1"></span>
    <span class="hamburger-2"></span>
    <span class="hamburger-3"></span>
    <el-drawer
      v-model="active"
      :with-header="false"
      append-to-body
      size="320px"
      direction="ltr"
    >
      <div class="drawer-header">
        <div class="title">设置：</div>
        <div
          class="menu-hamburger"
          :class="{ active: active }"
          @click="onHamburger"
        >
          <span class="hamburger-1"></span>
          <span class="hamburger-2"></span>
          <span class="hamburger-3"></span>
        </div>
      </div>
      <div class="menu-hamburger-drawer">
        <el-divider content-position="left"> language </el-divider>
        <ul class="langlist">
          <li
            v-for="item in langList"
            :key="item.key"
            :class="{ activeclass: langStore.getLang == item.key }"
            @click="langStore.changeLang(item.key)"
          >
            {{ item.label }}
          </li>
        </ul>
        <el-divider content-position="left">Theme </el-divider>
        <div class="hamburger-theme">
          <span class="labels">Theme:</span>
          <Theme />
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<style scoped lang="scss">
.menu-hamburger {
  width: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  flex-shrink: 0;
  margin-left: 12px;
  font-size: 14px;
  > span {
    background-color: var(--yh-text-color-primary);
    border-radius: 10px;
    height: 2px;
    margin: 2px 0;
    transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
    &.hamburger-1 {
      width: 50%;
    }
    &.hamburger-2 {
      width: 100%;
    }
    &.hamburger-3 {
      width: 75%;
    }
  }
  &.active {
  }
  &.active .hamburger-1 {
    transform-origin: bottom;
    transform: rotate(45deg) translate(13px);
  }
  &.active .hamburger-2 {
    transform-origin: top;
    transform: rotate(-45deg);
  }
  &.active .hamburger-3 {
    transform-origin: bottom;
    width: 50%;
    transform: translate(2px, -10px) rotate(45deg);
  }
}
.hamburger-theme {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.menu-hamburger-drawer {
  font-size: 14px;
  .labels {
    color: var(--yh-text-color-secondary);
  }
  .langlist {
    li {
      line-height: 2;
      cursor: pointer;

      &.activeclass {
        color: var(--yh-brand-color);
      }
    }

    li:hover {
      color: var(--yh-brand-color);
    }
  }
}
.drawer-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  .title {
    font-size: 16px;
    color: var(--yh-text-color-primary);
    font-weight: 900;
  }
  .menu-hamburger {
  }
}
</style>

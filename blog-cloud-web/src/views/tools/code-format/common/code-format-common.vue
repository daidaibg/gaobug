<script setup lang="ts">
import { codeEditConfig } from "../code-format-config";
import { computed, ref } from "vue";
import { CommonProps } from "./common.type";
const props = defineProps(CommonProps);
const commonFocused = ref("");
const emits = defineEmits<{
  (event: "update:modelValue", val: String): void;
  (event: "clickItem", commonItem: any): void;
}>();
//点击事件
const onCommonClick = (commonItem: any) => {
  emits("clickItem", commonItem);
  hideCommon()
};
//隐藏
const hideCommon = () => {
  emits("update:modelValue", "");
};
const commonList = computed(() => {
  switch (props.modelValue) {
    case "theme":
      // console.log(props);
      commonFocused.value = props.modelValue;
      return [...codeEditConfig.theme.light, ...codeEditConfig.theme.dark];
    default:
      break;
  }
});
</script>

<template>
  <div class="code-format-common" v-if="modelValue">
    <ul class="common-list">
      <li
        class="common-list_item"
        v-for="item in commonList"
        :key="item.value"
        :class="{
          division: item.isDivision,
          focused: editorOption.theme == item.value,
        }"
        @click.stop="onCommonClick(item)"
      >
        <div class="common-list_inner">
          <div class="common-list_inner-label">
            {{ item.label }} 
          </div>
          <div class="common-list_inner_separator">
            {{ item.suffix }}
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
.code-format-common {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  background-color: var(--format-bg-clolor);
  color: var(--common-palete-color);
  box-shadow: var(--common-palete-box-shadow);
  width: 600px;
  z-index: 999;
  padding: 8px 6px;
  border-radius: 4px;
}
.common-list {
  font-family: Segoe WPC, Segoe UI, sans-serif;
}
.common-list_item {
  font-size: 13px;
  height: 22px;
  cursor: pointer;
  border-radius: 3px;
  color: var( --format-text-color-secondary);
  overflow: hidden;
  .common-list_inner {
    padding: 0 6px;

    display: flex;
    align-items: center;
    justify-content: space-between;
    .common-list_inner-label {
      flex: 1;
      text-indent: 5px;
    }
    .list_inner_separator {
      width: 52px;
    }
  }
  &.division {
    .common-list_inner {
      border-top-width: 1px;
      border-top-style: solid;
      border-top-color: var(--common-palete-border-color);
    }
  }
  &:hover {
    background-color: var(--common-palete-hover-color);
  }
  &.focused {
    color: var(--format-text-color-anti);
    background: var(--format-color-brand);
  }
}
</style>

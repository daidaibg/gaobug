<script setup lang="ts">
import { ref } from "vue";

const emits = defineEmits<{
  (e: "downFile"): void;
}>();

//设置栏  弹窗ref  ref
const menubarMenuRef = ref();

const downFile = () => {
  menubarMenuRef.value?.hide();
  emits("downFile");
};
</script>

<template>
  <el-popover
    placement="right"
    :width="200"
    :hide-after="0"
    :offset="0"
    trigger="hover"
    :show-arrow="false"
    popper-class="setting_action_setting"
    ref="menubarMenuRef">
    <template #reference>
      <div class="menubar-menu-button">
        <i class="dd-icon-mulu"></i>
      </div>
    </template>
    <ul class="activitybar_left_item_pop">
      <li class="pop_item" @click="downFile()">下载文件</li>
    </ul>
  </el-popover>
</template>

<style scoped lang="scss">
@import "../style/left-action.scss";
.menubar-menu-button {
    color: rgba(255, 255, 255, 0.4);
    text-align: center;
    height: 35px;
    cursor: pointer;

    i {
      line-height: 35px;
      font-size: 16px;
    }

    &:hover {
      color: var(--format-setting-text-active-color);
    }
  }
</style>

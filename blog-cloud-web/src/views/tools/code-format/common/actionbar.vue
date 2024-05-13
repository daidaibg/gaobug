<script setup lang="ts">
import { ref } from "vue";

const emits = defineEmits<{
  (e: "selectTheme"): void;
}>();

//设置栏  弹窗ref  ref
const settingRef = ref();

const onSelectTheme = () => {
  settingRef.value?.hide();
  emits("selectTheme");
};
</script>

<template>
  <ul class="setting_action">
    <li class="activitybar_left_item">
      <el-popover
        placement="right"
        :width="200"
        :hide-after="0"
        :offset="0"
        trigger="hover"
        :show-arrow="false"
        popper-class="setting_action_setting"
        ref="settingRef">
        <template #reference>
          <i class="dd-icon-shezhi setting_icon flex justify-center items-center"> </i>
        </template>
        <ul class="activitybar_left_item_pop">
          <li class="pop_item" @click="onSelectTheme()">颜色主题</li>
        </ul>
      </el-popover>
    </li>
  </ul>
</template>

<style scoped lang="scss">
@import "../style/left-action.scss";
</style>

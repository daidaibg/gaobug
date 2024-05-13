<script setup lang="ts">
import { ref, reactive } from "vue";
const settingConfig = reactive({
  menuActive: "file",
});
</script>

<template>
  <ul class="composite-bar">
    <li
      :class="{ menuActive: settingConfig.menuActive === 'file' }"
      class="activitybar_left_item flex justify-center items-center">
      <i class="dd-icon-file"></i>
    </li>
  </ul>
</template>

<style scoped lang="scss">
@import "../style/left-action.scss";
.composite-bar {
  margin-bottom: auto;
}
</style>

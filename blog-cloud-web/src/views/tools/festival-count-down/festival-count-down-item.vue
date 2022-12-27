<script setup lang="ts">
const props = defineProps({
  timestamp: {
    type: Object,
    default: null,
  },
});
</script>

<template>
  <div class="countDown">
    <div class="countDown_item">{{ timestamp.day }}</div>
    <span class="unit">天</span>
    <div class="countDown_item">{{ timestamp.hour }}</div>
    <span class="unit">时</span>
    <div class="countDown_item">{{ timestamp.min }}</div>
    <span class="unit">分</span>
    <div class="countDown_item">{{ timestamp.second }}</div>
    <span class="unit">秒</span>
  </div>
</template>

<style scoped lang="scss">
$item-height: 60px;
.countDown {
  display: flex;
}
.unit {
  line-height: $item-height;
  color: #212529;
}
.countDown_item {
  position: relative;
  width: 100px;
  height: $item-height;
  line-height: $item-height;
  // border: solid 1px #343a40;
  border: solid 1px #2789ff;
  border-radius: 12px;
  font-size: 52px;
  color: #fff;
  box-shadow: 0 0 6px rgba(39, 137, 255, 0.5);
  font-family: "DS-DIGI";
  background: #2789ff;
  margin: 0 16px;
  text-align: center;
}
</style>

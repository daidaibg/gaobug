<script setup lang="ts"></script>

<template>
  <div class="gb-loading-wrap">
    <div class="gb-loading">
      <span></span>
      <span></span>
      <span></span>
      <span></span>
      <span></span>
    </div>
  </div>
</template>

<style scoped lang="scss">
.gb-loading-wrap {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 2000;
  background-color: var(--yh-mask-disabled);
  margin: 0;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  transition: opacity var(--el-transition-duration);
  display: flex;
  align-items: center;
  justify-content: center;
}
// css
.gb-loading {
  width: 60px;
  height: 40px;
  margin:  auto;
  display: flex;
  justify-content: space-between;
}
.gb-loading span {
  display: inline-block;
  width: 8px;
  height: 100%;
  border-radius: 4px;
  background: var(--yh-brand-color);
  animation: load 1s ease infinite;
}
@keyframes load {
  0%,
  100% {
    height: 40px;
    background: var(--yh-brand-color);
  }
  50% {
    height: 70px;
    margin: -15px 0;
    background: var(--yh-brand-color-4);
  }
}
.gb-loading span:nth-child(2) {
  animation-delay: 0.2s;
}
.gb-loading span:nth-child(3) {
  animation-delay: 0.4s;
}
.gb-loading span:nth-child(4) {
  animation-delay: 0.6s;
}
.gb-loading span:nth-child(5) {
  animation-delay: 0.8s;
}
</style>

<!--
 * @Author: daidai
 * @Date: 2021-09-30 15:00:52
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-05-06 14:55:49
 * @FilePath: \yhht-ui\src\components\Code-wrap.vue
-->
<template>
  <div class="code-wrap" ref="codewrap" @mouseenter="mouseenter" @mouseleave="mouseleave">
    <div class="demo_wrap">
      <slot></slot>
    </div>
    <div class="code-inner-wrap" :style="{ height: `${codeH}px` }">
      <div class="code_wrap" ref="codeinner">
        <slot name="code">
          <code-light :text="codeText" :codeType="codeType"></code-light>
        </slot>
      </div>
    </div>
    <div class="inout" @click="setShow" ref="inout" :class="{ 'is-fixed': isfixed }" :style="style">
      <i :class="[
        show ? 'yh-icons-caret-top' : 'yh-icons-caret-bottom',
        { hovering: hovering },
      ]"></i>
      <transition name="text-slide">
        <span v-show="hovering"> {{ show ? "隐藏代码" : "显示代码" }}</span>
      </transition>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onBeforeUnmount, Ref, nextTick } from 'vue'
const show = ref(false)
const style = ref('')
const hovering: Ref<boolean> = ref(false)
const codeH = ref(0)
const isfixed = ref(false)
const codewrap: any = ref(null)
const codeinner: any = ref(null)
const scrollParent: any = ref(null)
const props = defineProps({
  codeText: {
    type: String,
    default: "",
  },
   codeType: {
    type: String,
    default: () => "javascript html xml"
  },
})

const clientHeight = computed(() => {
  get: () => {
    return document.documentElement.clientHeight;
  }
  set: () => { }
})

onBeforeUnmount(() => {
  removeScrollHandler();
})
const mouseenter = ()=>{
  hovering.value=true
}
const mouseleave = ()=>{
  hovering.value=false
}
const setShow = async () => {
  show.value = !show.value;
  document.removeEventListener("scroll", handleScrollx, true);
  if (show.value) {
    await nextTick()
    if (codeinner.value && codewrap.value) {
      codeH.value = codeinner.value.offsetHeight;
      style.value = `width:${codewrap.value.offsetWidth}px`;

      // console.log( codeH.value,style.value);
    }
    await nextTick()
    scrollParent.value = document.body;
    // console.log('body', scrollParent.value);
    scrollParent.value &&
      scrollParent.value.addEventListener("scroll", handleScrollx);
    handleScrollx();
  } else {
    codeH.value = 0;
    isfixed.value = false;
    removeScrollHandler();
  }
}
const removeScrollHandler = () => {
  scrollParent.value &&
    scrollParent.value.removeEventListener("scroll", handleScrollx);
}
const handleScrollx = () => {
  const { top, bottom, left } =
    codeinner.value.getBoundingClientRect();
  const clientHeight = document.documentElement.clientHeight;
  // console.log(top, bottom, left );
  isfixed.value = bottom > clientHeight && top + 44 <= clientHeight;
}
</script>
<style lang='scss' scoped>
.code-wrap {
  .code_wrap {
    border-bottom: none;
  }
}

.inout {
  border: 1px solid var(--yh-component-stroke);
  height: 44px;
  width: 100%;
  box-sizing: border-box;
  background-color: var(--yh-bg-color-container);
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
  text-align: center;
  margin-top: -1px;
  color: #d3dce6;
  cursor: pointer;
  position: relative;
  line-height: 44px;
  i {
    font-size: 16px;
    line-height: 44px;
    transition: 0.3s;

    &.hovering {
      transform: translateX(-40px);
    }
  }

  span {
    position: absolute;
    transform: translateX(-30px);
    font-size: 14px;
    line-height: 44px;
    transition: 0.14s;
    display: inline-block;
  }

  &:hover {
    color: var(--yh-text-color-brand);
    background-color: var(--yh-bg-color-container-hover);
  }

  .text-slide-enter-active,
  .text-slide-leave-to {
    opacity: 0;
    transform: translateX(10px);
  }
}

.is-fixed {
  position: fixed;
  bottom: 0;
}

.code-inner-wrap {
  //   background-color: #fafafa;
  //   border-top: solid 1px #eaeefb;
  overflow: hidden;
  height: 0;
  transition: height 0.3s;
}

.demo_wrap {
  border: solid 1px var(--yh-component-stroke);
  border-bottom: none;
  padding: 24px;
}
</style>
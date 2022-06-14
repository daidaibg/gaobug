<!--
 * @Author: daidai
 * @Date: 2022-02-09 15:00:59
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-09 17:44:23
 * @FilePath: \yhht-ui\src\components\Codes.vue
-->
<template>
  <div v-highlight :class="wrapClass">
    <pre><code  :class="codeType"><slot>{{ text }}</slot>  </code></pre>
  </div>
</template>

<script lang="ts" setup>
const props = defineProps({
  codeType: {
    type: String,
    default: () => "javascript html xml"
  },
  text: {
    type: String,
    default: () => ""
  },
  wrapClass: {
    type: String,
    default: () => ""
  },
})
</script>
<style lang='scss' scoped>
</style>
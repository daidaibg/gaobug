<!--
 * @Author: daidai
 * @Date: 2021-12-06 10:52:57
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-23 14:30:03
-->
<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script >
export default {
  data() {
    return {
      
    }
  },
  mounted() {
    document.documentElement.setAttribute("data-theme","light")
  },
}
</script>
<style lang="scss">

</style>

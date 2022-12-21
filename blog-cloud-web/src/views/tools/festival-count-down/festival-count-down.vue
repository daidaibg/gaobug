<script setup lang='ts'>
import {getPublic} from "@/api"

getPublic("/json/jjr.json").then(res=>{
    console.log(res);
})
</script>

<template>
  <div class=''>

  </div>
</template>

<style scoped lang='scss'>

</style>
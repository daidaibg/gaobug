<script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ElInput } from "element-plus";
import { ref } from "vue";
import {currentGET} from "@/api"
const searchVal = ref<string>("");

const search =()=>{
    console.log(searchVal.value);
    // currentGET("blogSearch",)
}
</script>

<template>
  <div class="blog-search">
    <el-input
      v-model="searchVal"
      class="w-50 m-2"
      placeholder="探索搞bug"
      @keyup.enter.native="search"
      :suffix-icon="Search"
    />
  </div>
</template>

<style scoped lang="scss">


</style>

<script setup lang='ts'>
import {ElContainer} from "element-plus"
import Aside from "./aside"
import Header from "./header"
import Main from "./main"


</script>

<template>
  <el-container class="layout-container">
       <Aside></Aside>
        <el-container class="content-wrapper">
            <Header ></Header>
            <Main ></Main>
        </el-container>
    </el-container>
</template>

<style scoped lang='scss'>
.layout-container {
    height: 100%;
    width: 100%;
}
.content-wrapper {
    flex-direction: column;
    width: 100%;
    height: 100%;
}
</style>
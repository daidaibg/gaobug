<template>
    <el-header class="layout-header">
        <!-- <component :is="config.layout.layoutMode + 'NavBar'"></component> -->
        <div class="nav-bar">
            <nav-tab></nav-tab>
            <nav-menu></nav-menu>
        </div>
    </el-header>
</template>
<script setup lang="ts">

import NavTab from "../nav-tabs"
import NavMenu from "../nav-menu"

</script>


<style scoped lang="scss">
.layout-header {
    height: auto;
    padding: 0;
    display: flex;
    height: 50px;
    margin: var(--main-space) var(--main-space) 0 var(--main-space);

    // background-color: red;
    .nav-bar {
        display: flex;
        height: 50px;
        width: 100%;
    }
}
</style>

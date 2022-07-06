<script setup lang='ts'>
import {ElAside} from "element-plus";
import Logo from "../logo"
import Menus from "../menu"
import {useMenuNavStore} from "@/store"
const menuNavStore  =useMenuNavStore()
</script>

<template>
    <el-aside class="layout-aside-Default" :class="{'aside-collapse':menuNavStore.isCollapse}">
        <Logo></Logo>
        <Menus ></Menus>
    </el-aside>
</template>

<style scoped lang='scss'>
.layout-aside-Default {
    background: var(--yh-bg-color-container);
    margin: var(--main-space) 0 var(--main-space) var(--main-space);
    height: calc(100vh - 32px);
    box-shadow: var(--el-box-shadow-light);
    border-radius: var(--el-border-radius-base);
    overflow: hidden;
    transition: width .3s ease;
    width:260px;
    &.aside-collapse{
        width: 64px;
        :deep(.fold){
            margin: 0 auto;
        }
    }
}
</style>
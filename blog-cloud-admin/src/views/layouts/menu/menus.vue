<script setup lang='ts'>
import { computed, nextTick, onMounted, reactive, ref, } from 'vue'
import {ElScrollbar,ElMenu} from "element-plus"
import MenuTree from "./menu-tree.vue"
import {useMenuNavStore} from "@/store"
import { useRoute,useRouter, onBeforeRouteUpdate, RouteLocationNormalizedLoaded } from 'vue-router'
const router = useRouter()
const route = useRoute()
const menuNavStore  =useMenuNavStore()
menuNavStore.getMenus()
const state = reactive({
    defaultActive: '',
})
// 激活当前路由的菜单
const currentRouteActive = (currentRoute: RouteLocationNormalizedLoaded) => {
    state.defaultActive = currentRoute.path
}
onMounted(() => {
    currentRouteActive(route)
})
onBeforeRouteUpdate((to) => {
    // console.log(to);
    currentRouteActive(to)
})
</script>

<template>
   <el-scrollbar ref="verticalMenusRef" class="vertical-menus-scrollbar">
        <el-menu
            class="layouts-menu-vertical"
            :collapse-transition="false"
            :unique-opened="false"
            :default-active="state.defaultActive"
            :collapse="menuNavStore.isCollapse"
        >
            <MenuTree :menus="menuNavStore.menusViewRoutes" />
        </el-menu>
    </el-scrollbar>
</template>

<style scoped lang='scss'>
.vertical-menus-scrollbar {
    height: calc(100% - 50px );
}
.layouts-menu-vertical {
    border: 0;
}
</style>
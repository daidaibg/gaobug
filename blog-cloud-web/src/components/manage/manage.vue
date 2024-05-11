<script setup lang='ts'>
import GbHeader from "@/components/header/gb-header.vue"
</script>

<template>
    <GbHeader :tabShow="false"></GbHeader>
    <div class="blog-manage gaobug ">
        <div class="menu_wrap">
            <slot></slot>
        </div>
        <div class="content_wrap">
            <router-view />
        </div>
    </div>
</template>
<style scoped lang='scss'>
$menu-width:240px;
$menu-width-margin:256px;
.menu_wrap{ 
    width: $menu-width;
    position: fixed;
    top: $header-top;
    bottom: 16px;
}
.content_wrap{ 
    margin: 0 0 0 $menu-width-margin;
    border-radius: 4px;
    width:calc(100% - $menu-width-margin);
    box-sizing: border-box;
    padding:0 16px 16px;
    min-height: $content-height;
     background: var(--yh-bg-color-container);
}
</style>
<script setup lang='ts'>
import Lang from "@/components/header/lang"
import User from "@/components/header/user"
import Theme from "@/components/header/theme"

</script>

<template>
  <div class='nav-menus Default'>
        <Lang></Lang>
        <User></User>

        <Theme></Theme>
  </div>
</template>

<style scoped lang='scss'>
.nav-menus.Default {
    border-radius: 6px;
    box-shadow: var(--el-box-shadow-light);

}
.nav-menus {
    padding: 0 8px;
    display: flex;
    align-items: center;
    height: 100%;
    margin-left: auto;
    background-color:var(--yh-bg-color-container) ;
    overflow: hidden;

}
</style>
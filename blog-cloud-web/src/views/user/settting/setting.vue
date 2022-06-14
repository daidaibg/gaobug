<script setup lang='ts'>
import Manage from "@/components/manage"
import Menus from "@/components/menus"
import {settingUserMenuList} from "./index"
</script>
<template>
  <Manage>
    <Menus :menus="settingUserMenuList"></Menus>
  </Manage>
</template>
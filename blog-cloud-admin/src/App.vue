<script setup lang="ts">
import {ElConfigProvider} from "element-plus"
import { useLangStore } from '@/store'
import { computed } from "@vue/reactivity";
const langStore = useLangStore()
// console.log(langStore);
const locale:any =computed(()=>{
   return langStore
})
</script>

<template>
   <el-config-provider :locale="locale">
        <router-view></router-view>
    </el-config-provider>
</template>


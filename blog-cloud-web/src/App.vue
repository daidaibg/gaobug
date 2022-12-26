<script setup lang="ts">
import {computed} from "vue"
import ModelLogin from "./views/login/model-login.vue";
//@ts-ignore
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
//@ts-ignore
import en from 'element-plus/dist/locale/en.mjs'
import I18n from "@/components/I18n";
import { useLangStore } from '@/store'
import {LangEnum} from "@/enums"
const langStore = useLangStore()
// import { onMounted, ref } from "vue"
// const modelLogin:any =ref(null)
// onMounted(()=>{
//   console.log(modelLogin.value.flag);
// })
const locale = computed(() => (langStore.lang === LangEnum.ZH ? zhCn : en))
</script>

<template>
  <el-config-provider :locale="locale">
    <router-view />
    <model-login ref="modelLogin"></model-login>
    <I18n />
  </el-config-provider>
</template>

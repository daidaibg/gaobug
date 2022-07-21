<script setup lang="ts">
import ModelLogin from "./views/login/model-login.vue"
import I18n from "@/components/I18n"
// import { onMounted, ref } from "vue"
// const modelLogin:any =ref(null)
// onMounted(()=>{
//   console.log(modelLogin.value.flag);
// })
</script>

<template>
   <router-view />
   <model-login ref="modelLogin"></model-login>
   <I18n/>
</template>


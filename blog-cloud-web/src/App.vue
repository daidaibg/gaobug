<script setup lang="ts">
import ModelLogin from "./views/login/model-login.vue"
// import { onMounted, ref } from "vue"
// const modelLogin:any =ref(null)
// onMounted(()=>{
//   console.log(modelLogin.value.flag);
// })
</script>

<template>
   <router-view />
   <model-login ref="modelLogin"></model-login>
</template>


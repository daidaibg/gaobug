<script setup lang='ts'>
import { useUserStore } from '@/store'
import { computed, reactive, ref } from "vue"
import LoginForm from "./login-form"
const userStore = useUserStore()

userStore.getUserInfo()

defineExpose({
})

</script>

<template>
    <div class='model-login flex justify-center items-center ' v-if="userStore.getModelLoginShow">
        <login-form isModel></login-form>
    </div>
</template>

<style scoped lang='scss'>
.model-login {
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background-color: var(--yh-mask-active);
    z-index: 999;

}
</style>
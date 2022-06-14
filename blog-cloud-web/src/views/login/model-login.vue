<script setup lang='ts'>
import { computed, reactive, ref } from "vue"
import { useStore } from "vuex"
import LoginForm from "./login-form"
const store = useStore()
store.dispatch("userStore/getUserInfo")
const modelLoginShow = computed(() => {
    return store.state.userStore.modelLoginShow
})

defineExpose({
})

</script>

<template>
    <div class='model-login flex justify-center items-center ' v-if="modelLoginShow">
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
    z-index: 9999;

}
</style>
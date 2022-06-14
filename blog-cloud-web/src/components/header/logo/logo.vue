<script setup lang='ts'>
import {useRouter} from "vue-router"
const router = useRouter()
const goHome=()=>{
    router.push("/")
}
</script>

<template>
    <div class="logo cursor-pointer" @click="goHome">
          <!-- <img src="../assets/img/logo.png" alt="" /> -->
        <p>搞bug</p>
    </div>
</template>

<style scoped lang='scss'>
  .logo {
    display: flex;
    align-items: center;
    img {
      height: 40px;
      margin-left: 0px;
    }

    p {
      font-size: 24px;
      font-weight: 900;
      color: var(--yh-brand-color-8);
      margin-left: 12px;
      color: transparent;
      background-color: var(--yh-brand-color-8);
      text-shadow: var(--header-logo-shadow);
      -webkit-background-clip: text;
      font-family: STXinwei, FZShuTi, "微软雅黑", "黑体", "宋体";
      line-height: 1;
    }
  }
</style>
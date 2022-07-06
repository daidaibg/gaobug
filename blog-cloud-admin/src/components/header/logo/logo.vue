<script setup lang='ts'>
import {useRouter} from "vue-router"
import Props from "./props"
import { ThemeEnum } from '@/enums'

defineProps(Props)

const router = useRouter()
const goHome=()=>{
    router.push("/")
}
</script>

<template>
    <div class="logo cursor-pointer headers-root" @click="goHome">
          <!-- <img src="../assets/img/logo.png" alt="" /> -->
        <img src="@/assets/img/logo/logon-dark.png" alt="搞bug" v-if="theme==ThemeEnum.DARK">
        <img src="@/assets/img/logo/logo.png" alt="搞bug" v-else>
    </div>
</template>

<style scoped lang='scss'>
  .logo {
    display: flex;
    align-items: center;
      flex-shrink: 0;
    img {
      height: 26px;
      width: 70px;
      margin-left: 0px;
    }

    // p {
    //   font-size: 24px;
    //   font-weight: 900;
    //    width: 70px;
    //   flex-shrink: 0;
    //   color: var(--yh-brand-color);
    //   margin-left: 12px;
    //   color: transparent;
    //   background-color: var(--yh-brand-color);
    //   text-shadow: var(--header-logo-shadow);
    //   -webkit-background-clip: text;
    //   font-family: STXinwei, FZShuTi, "微软雅黑", "黑体", "宋体";
    //   line-height: 1;
    // }
  }
</style>
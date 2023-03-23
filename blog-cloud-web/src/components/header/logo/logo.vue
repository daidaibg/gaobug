<script setup lang="ts">
import { useRouter } from "vue-router";
import Props from "./props";
import { LogoSvg } from "@/components/logo/index";

const props = defineProps(Props);
const router = useRouter();

const goHome = () => {
  if (props.target == "") {
    router.push("/");
  } else {
    const { href } = router.resolve({
      name: "/",
      query: {},
    });
    window.open(href, props.target);
  }
};
</script>

<template>
  <div class="logo cursor-pointer headers-root" @click="goHome">
    <LogoSvg />
  </div>
</template>

<style scoped lang="scss">
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
  //   font-size: 48px;
  //   font-weight: 900;
  //    width: 140px;
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

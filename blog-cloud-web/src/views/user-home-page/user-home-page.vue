<script setup lang="ts">
import { Theme } from "@/components/header/theme/index";
</script>

<template>
  <div class="user-home-page overflow-x-hidden">
    <div class="flex justify-between items-center h-12 md:h-20">
      <div></div>
      <Theme />
    </div>
    <!-- 欢迎语 简介 -->
    <div class="user-welcome-box">
      <div
        class="user-welcome-content-bg_name absolute font-black text-4xl md:text-6xl"
      >
        {{ "搞bug" }}
      </div>

      <div class="user-welcome md:flex max-w-screen-lg m-auto relative">
        <div class="user-welcome-content px-4 w-auto md:w-7/12">
          <h3
            class="user-welcome-content_title uppercase font-black text-2xl pt-4 md:text-5xl"
          >
            Hell0
          </h3>
          <h1
            class="user-welcome-content_name font-black text-3xl mb-4 md:text-7xl md:mb-7"
          >
            I AM {{ "搞bug" }}
          </h1>
          <h5
            class="user-welcome-content_summary font-black text-xl md:text-2xl"
          >
            {{ "anim occaecat in magna pariatur" }}
          </h5>
          <div class="welcome-action flex items-center mt-4 md:mt-8">
            <button class="user-button primary">关注</button>
          </div>
        </div>
        <div class="user-welcome-right w-5/12 px-4">
          <img
            src="@/assets/img/user-home-page/home-right.png"
            alt=""
            class="xl:max-w-none"
          />
        </div>
      </div>
    </div>
    <!-- 介绍 -->
    <div class="uers-home-introduce max-w-screen-lg m-auto flex">
      <div class="introduce_left w-5/12 min-hidden">
        <img src="@/assets/img/user-home-page/about-us.png" alt="介绍" />
      </div>
      <div class="introduce_right">
        <h2 class="introduce-title">简单介绍下关于我自己</h2>
        <pre class="introduce-text">
像阳光一样的人，像阳光一样的事，像阳光一样的爱，像阳光一样的慈悲，世界上遍地都是。

梦想似乎是遥远的，但是只要通过自己的努力，就能一步步的拉近之间的距离，当距离越来越短时，它就不再是梦，而成为了触手可及的想法，无论如何，不要停下走向梦想的脚步。
          </pre
        >
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "./public.scss";
$box-m-b:88px;
.user-home-page {
  min-height: 100vh;
  line-height: 26px;
  font-size: 16px;
  font-family: "Roboto", sans-serif;
  font-weight: 400;
  // background: url("@/assets/img/user-home-page/body-bg.png") no-repeat center;
  // background-size: 100% auto;
  // background-position: top;
  background-color: var(--yh-bg-color-container);
}
.user-welcome-box {
  font-family: "Rubik", sans-serif;
  background: url("@/assets/img/user-home-page/home-banner.png") no-repeat;
  background-position: center;
  background-size: cover;
  margin-bottom: $box-m-b;
  .user-welcome-content-bg_name {
    color: var(--yh-text-color-disabled);
    top: 17%;
    z-index: 0;
    opacity: 0.2;
  }
}
.user-welcome {
  color: var(--yh-text-color-primary);
  height: 100%;
  .user-welcome-content {
    z-index: 1;
    position: relative;
  }
  .user-welcome-content_title {
    margin-bottom: 20px;
    position: relative;

    &::after {
      content: "";
      width: 200px;
      height: 2px;
      position: absolute;
      top: 65%;
      left: 100px;
      background: var(--yh-text-color-primary);
    }
  }
  .user-welcome-right {
    position: absolute;
    right: 0;
    top: 32px;
    z-index: 0;
  }
}


//介绍
.uers-home-introduce {
  margin-bottom: $box-m-b;
  .introduce_left {
  }
  .introduce_right {
    padding:0 16px;
  }
  .introduce-title {
    font-size: 25px;
    font-weight: 900;
    line-height: 1.2;
    margin-bottom: 32px;
  }
  .introduce-text {
    font-size: 15px;
    line-height: 26px;
    white-space: pre-wrap;
    color: var(--yh-text-color-secondary);
  }
}
@media screen and (min-width: 768px) {
  .user-welcome-box {
    margin-bottom: 200px;
    .user-welcome-content-bg_name {
      top: 22%;
    }
  }
  .user-welcome {
    .user-welcome-content {
      display: flex;
      flex-direction: column;
      justify-content: center;
    }
    .user-welcome-content_title {
      margin-bottom: 24px;
      &::after {
        left: 176px;
        width: 410px;
      }
    }
    .user-welcome-right {
      position: relative;
      padding-top: 48px;
    }
  }
  //介绍
  .uers-home-introduce {
    .introduce_left {
    }
    .introduce_right {
      margin-left: 8%;
    }
    .introduce-title {
        font-size: 36px;
    }
  }
}
@media screen and (min-width: 1280px) {
  .user-welcome-box {
    height: 600px;
    .user-welcome-content-bg_name {
      top: 32%;
    }
  }
  .user-welcome {
    .user-welcome-right {
      padding-top: 0;
    }
  }
}
</style>

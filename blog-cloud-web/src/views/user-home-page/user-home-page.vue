<script setup lang="ts">
import { Theme } from "@/components/header/theme/index";
</script>

<template>
  <div class="user-home-page overflow-x-hidden">
    <div class="flex justify-between items-center h-12 md:h-20">
      <div></div>
      <Theme />
    </div>
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
  </div>
</template>

<style scoped lang="scss">
.user-button {
  width: 60px;
  height: 32px;
  border-radius: 28px;
  transition: all 0.2s cubic-bezier(0.215, 0.61, 0.355, 1);
  font-size: 15px;
  &.primary {
    background-image: linear-gradient(
      to right,
      var(--yh-brand-color-5),
      var(--yh-brand-color)
    );
    box-shadow: inset 10px 0px 10px var(--yh-brand-color),
      inset -10px 0px 10px var(--yh-brand-color-5);
    color: var(--yh-text-color-anti);
    &:hover {
      background-image: linear-gradient(
        to right,
        var(--yh-brand-color),
        var(--yh-brand-color-5)
      );
      box-shadow: inset 10px 0px 10px var(--yh-brand-color-5),
        inset -10px 0px 10px var(--yh-brand-color);
    }
  }
}
.user-home-page {
  min-height: 100vh;
  line-height: 26px;
  font-size: 16px;
  font-family: "Roboto", sans-serif;
  font-weight: 400;
  background: url("@/assets/img/user-home-page/body-bg.png") no-repeat center;
  background-size: 100% auto;
  background-position: top;
}
.user-welcome-box {
  font-family: "Rubik", sans-serif;
  background: url("@/assets/img/user-home-page/home-banner.png") no-repeat;
  background-position:top center;
  background-size: cover;
  height: 400px;
  margin-bottom: 200px;
  .user-welcome-content-bg_name {
    color: var(--yh-text-color-disabled);
    top: 17%;
    z-index: -1;
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

@media screen and (min-width: 768px) {
  .user-button {
    width: 100px;
    height: 48px;
    font-size: 18px;
  }
  .user-welcome-box {
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

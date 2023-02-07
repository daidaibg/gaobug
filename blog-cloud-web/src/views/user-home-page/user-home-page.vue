<script setup lang="ts">
import { ref, reactive } from "vue";
import { BlogData } from "./type";
import { Theme } from "@/components/header/theme/index";
import { getUserBlogList } from "@/api/modules/manage";
import { ElMessage } from "element-plus";
import shuffle from "lodash/shuffle";
import { useBlogAction } from "@/hook/modules/use-blog-action";
import { getTimeInterval } from "@/utils/time";
import LogoSvg from "@/components/logo/logo-svg.vue"
const { blogLike } = useBlogAction();

const blogData = reactive<BlogData>({
  list: [],
  current: 1,
  size: 12,
  total: 0,
  tabActive: 1,
});

const selectBlogTab = (tabActive: number) => {
  blogData.tabActive = tabActive;
  blogData.list = shuffle(blogData.list);
};

//获取自己博客列表
const getBlog = () => {
  blogData.list = [];
  let param = {
    current: blogData.current,
    size: blogData.size,
    publish: 1,
  };
  getUserBlogList(param).then((res: any) => {
    // console.log(res);
    if (res.code == 200) {
      blogData.list = res.data.records;
      blogData.total = res.data.total;
    } else ElMessage.error(res.msg);
  });
};

getBlog();
// https://preview.colorlib.com/theme/satner/#
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
        <h2 class="introduce-title title_2">简单介绍下关于我自己</h2>
        <pre class="introduce-text">
像阳光一样的人，像阳光一样的事，像阳光一样的爱，像阳光一样的慈悲，世界上遍地都是。

梦想似乎是遥远的，但是只要通过自己的努力，就能一步步的拉近之间的距离，当距离越来越短时，它就不再是梦，而成为了触手可及的想法，无论如何，不要停下走向梦想的脚步。
          </pre
        >
      </div>
    </div>
    <!-- 别人的评价 -->
    <div class="others-comments max-w-screen-lg m-auto">
      <h2 class="others-comments-title title_2">别人评价</h2>
      <p class="others-comments-text">
        Is give may shall likeness made yielding spirit a itself togeth created
        after sea is in beast beginning signs open god you're gathering ithe
      </p>
    </div>
    <!-- 发表文章 -->
    <div class="user-article max-w-screen-lg m-auto">
      <h2 class="user-article-title title_2">个人文章</h2>
      <ul class="user-article-tab flex">
        <li
          class="user-article-tab_item"
          :class="{ tabActive: blogData.tabActive == 1 }"
          @click="selectBlogTab(1)"
        >
          全部
        </li>
        <li
          class="user-article-tab_item"
          :class="{ tabActive: blogData.tabActive == 2 }"
          @click="selectBlogTab(2)"
        >
          最受欢迎的
        </li>
        <li
          class="user-article-tab_item"
          :class="{ tabActive: blogData.tabActive == 3 }"
          @click="selectBlogTab(3)"
        >
          最新的
        </li>
      </ul>
      <div class="user-article-list">
        <transition-group name="list-complete">
          <template v-for="item in blogData.list" :key="item">
            <div class="list-complete-item user-article-list-item">
              <div class="article-list-content">
                <div class="article-list-filter "><LogoSvg width="66%" height="auto" class="flex justify-center items-center" style="height:100%"/></div>
                <img
                  class="article-list-img"
                  :src="item.coverUrl"
                  alt=""
                  v-if="item.coverUrl"
                />
                <span class="float-right article-list-time">{{
                  getTimeInterval(item.createTime)
                }}</span>
                <p class="article-list-summary truncate w-full">
                  {{ item.summary }}
                </p>
              </div>
              <h5 class="article-list-title" :title="item.title">
                {{ item.title }}
              </h5>
              <p class="article-list-tag">
                {{ item.tag }}
                <i
                  :class="
                    item.isLike ? 'dd-icon-guanzhu liked' : 'dd-icon-guanzhu1'
                  "
                  class="a float-right mr-2 cursor-pointer like"
                ></i>
              </p>
            </div>
          </template>
        </transition-group>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "./public.scss";

$box-m-b: 88px;
$mr-b-768: 200px;

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

.title_2 {
  font-size: 25px;
  font-weight: 900;
  line-height: 1.2;
  margin-bottom: 32px;
}

//介绍
.uers-home-introduce {
  margin-bottom: $box-m-b;

  .introduce_left {
  }

  .introduce_right {
    padding: 0 16px;
  }

  .introduce-title {
  }

  .introduce-text {
    font-size: 15px;
    line-height: 26px;
    white-space: pre-wrap;
    color: var(--yh-text-color-secondary);
  }
}

//别人评价
.others-comments {
  margin-bottom: $box-m-b;
  padding: 0 12px;

  .others-comments-title {
    text-align: center;
    margin-bottom: 12px;
  }

  .others-comments-text {
    text-indent: 12px;
    word-break: break-all;
    word-wrap: break-word;
    color: var(--yh-text-color-secondary);
    font-size: 15px;
  }
}

//文章
.user-article {
  margin-bottom: $box-m-b;
  padding: 16px;

  .user-article-title {
  }

  .user-article-tab {
    margin-right: 8px;
    margin-bottom: 32px;

    .user-article-tab_item {
      margin-right: 50px;
      cursor: pointer;
      transition: color 0.2s linear;

      &:hover {
        color: var(--yh-brand-color);
      }

      &.tabActive {
        color: var(--yh-brand-color);
      }
    }
  }

  .user-article-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  .user-article-list-item {
    cursor: pointer;
    padding: 0 12px;
    width: 30%;
    margin-bottom: 48px;
    position: relative;
    .article-list-content {
      margin-bottom: 8px;
      position: relative;
      z-index: 0;
      overflow: hidden;
      aspect-ratio: 3 / 2;
      border-radius: 8px;
    }
    .article-list-filter {
      position: absolute;
      width: 100%;
      height: 100%;
      left: 0;
      z-index: 0;
      // backdrop-filter: blur(10px);
      background-color: var(--dd-scrollbar-color);
      filter: blur(7px);
      opacity: 0.2;
      border-radius: 8px;
    }
    .article-list-img {
      position: relative;
      z-index: 1;
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    .article-list-time {
      position: absolute;
      right: 0;
      top: 0;
      z-index: 2;
      font-size: 12px;
      color: var(--yh-text-color-placeholder);
      padding: 2px 4px;
    }
    .article-list-summary {
      position: absolute;
      bottom: 0;
      line-height: 2.4;
      text-indent: 4px;
      color: var(--yh-text-color-placeholder);
      font-size: 14px;
      z-index: 2;
      background-color: var(--yh-text-color-disabled);
      color: var(--yh-bg-color-container-active);
    }
    .article-list-title {
      font-size: 16px;
      font-weight: 500;
      color: var(--yh-text-color-primary);
    }

    .article-list-tag {
      font-size: 14px;
      color: var(--yh-text-color-secondary);
    }

    .like {
      &.liked,
      &:hover {
        color: var(--yh-brand-color);
      }
    }
  }
}

@media screen and (min-width: 768px) {
  .user-welcome-box {
    margin-bottom: $mr-b-768;

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
    margin-bottom: $mr-b-768;

    .introduce_left {
    }

    .introduce_right {
      margin-left: 8%;
    }

    .introduce-title {
      font-size: 36px;
    }
  }

  .others-comments {
    margin-bottom: $mr-b-768;
    padding: 0 32px;
  }

  .user-article {
    margin-bottom: $mr-b-768;
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

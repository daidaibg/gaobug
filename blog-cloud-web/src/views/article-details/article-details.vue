<script setup lang="ts">
import { ref, Ref, nextTick } from "vue";
import { useRoute, useRouter } from "vue-router";
import { currentGET } from "@/api";
import { ElMessage } from "element-plus";
import { HeadList } from "md-editor-v3";
import MdView from "@/components/md-view/md-view.vue";
import "md-editor-v3/lib/preview.css";
import { useTitle } from "@vueuse/core";
import { mdEditorConfig, generateId } from "@/config";
import { userThemeStore, useUserStore } from "@/store";
import Backtop from "@/components/backtop";
import Actions from "./actions";
import Comment from "./comment";
import { PreviewThemeType, BlogDetailsType, CodeTheme } from "./type";
import { useMetaContent } from "@/hook";
import { RouterEnum } from "@/enums/router-enums";
import { windowScrollTo } from "@/utils/scroll";
import UtilVar from "@/config/UtilVar";

const themeStore = userThemeStore();
const userStore = useUserStore();
const title = useTitle();
const route = useRoute();
const router = useRouter();

const mdText = ref<string>(""); //内容
const blogDetails = ref<BlogDetailsType>({}); //详情
const catalogList = ref<HeadList[]>([]); //目录
const { setMetaTagContent } = useMetaContent();
let moundFlag: boolean = false;

//目录
const onGetCatalog = (list: HeadList[]) => {
  catalogList.value = list;
  if (!moundFlag) {
    let timer = setTimeout(() => {
      anchorHandle();
      clearTimeout(timer);
    }, 150);
    moundFlag = true;
  }
};

//点赞和取消点赞成功
const like = (res: any) => {
  getDetail();
};

//获取详情
const getDetail = () => {
  currentGET("indexBlogDetail", {}, route.params.id).then((res: any) => {
    // console.log("getDetail",res);
    if (res.code == 200) {
      blogDetails.value = res.data;
      mdText.value = res.data.content;
      title.value = res.data.title; // change current title
      setMetaTagContent("description", res.data.summary == "" ? res.data.title : res.data.summary);
      setMetaTagContent("keywords", res.data.title);
    } else {
      ElMessage.error(res.msg);
    }
  });
};

//处理锚点
const anchorHandle = async () => {
  await nextTick();
  if (route.hash) {
    console.log(route.hash);
    const str = `[id="${route.hash.slice(1).replace(/\s/g, "%20")}"]`;
    windowScrollTo(str, 74);
  }
};

let id: BlogDetailsType["id"] = route.params.id;
if (id) {
  blogDetails.value.id = id;
  getDetail();
  mdEditorConfig();
} else {
  router.push(RouterEnum.Home);
}

//编辑文章
const goEditArticle = () => {
  router.push({
    path: RouterEnum.WriteEdit,
    query: { articleId: blogDetails.value.id },
  });
};
</script>

<template>
  <div class="gaobug details">
    <div class="details_inner">
      <div class="container-bg box-border px-6 py-3 conetnt">
        <div class="details_title" id="1">
          {{ blogDetails.title }}
        </div>
        <div class="user_info mt-3 flex items-center">
          <div class="user_info_avatar mr-2">
            <img src="../../assets/img/avatar.png" alt="" />
          </div>
          <div class="article_little">
            <div class="flex mb-0.5 article_little_inner">
              <span class="name"> {{ blogDetails.authorName }}</span>
              <span class="mx-2 details_right">·</span>
              <span class=""> {{ blogDetails.createTime }} </span>
              <span class="mx-2">·</span>
              阅读{{ 0 }}
            </div>
            <div class="flex">
              <span>分类：{{ blogDetails.categoryName || "-" }}</span>
              <span class="ml-2">标签：{{ blogDetails.tag }}</span>
            </div>
          </div>
          <div class="attention" v-if="!blogDetails.isAuthor">
            <yh-button
              theme="primary"
              size="medium"
              variant="outline"
              v-if="userStore.getUserData.id != blogDetails.author"
              >关注</yh-button
            >
            <yh-button theme="primary" size="medium" variant="outline" v-else @click="goEditArticle()">编辑</yh-button>
          </div>
        </div>
        <div class="cover-summy flex mt-4">
          <img :src="blogDetails.coverUrl" alt="" class="cover" v-if="blogDetails.coverUrl" />
          <p class="summy break-all">{{ blogDetails.summary }}</p>
        </div>
        <md-view
          editor-id="edit2preview"
          showCodeRowNumber
          class="mt-8"
          :text="mdText"
          @GetCatalog="onGetCatalog"
          :mdHeadingId="generateId">
        </md-view>
      </div>

      <div class="catalog blog-cloud_info flex-shrink-0 small-screen-hide">
        <!-- <el-affix :offset="81" target="body">
            </el-affix> -->
        <div class="silder_inner">
          <div class="author_info container-bg mb-4 box-border px-3 py-2">
            <div class="author_header flex items-center">
              <img src="../../assets/img/avatar.png" :alt="blogDetails.authorName" class="author_img" />
              <div class="user_name truncate flex-1">
                {{ blogDetails.authorName }}
              </div>
            </div>
            <ul class="user_num_list flex justify-around mt-3">
              <li class="item">
                <p class="num">0</p>
                <p class="description">文章</p>
              </li>
              <li class="item">
                <p class="num">0</p>
                <p class="description">粉丝</p>
              </li>
              <li class="item">
                <p class="num">0</p>
                <p class="description">获赞</p>
              </li>
            </ul>
          </div>
          <div
            class="catalog_wrap container-bg box-border px-3 pb-2 box-shadow-0 flex flex-col"
            v-show="catalogList.length > 0">
            <header class="py-2 logs-header">目录</header>
            <div class="catalog_list overflow-y-auto mt-1">
              <yh-anchor class=" " :targetOffset="80">
                <yh-anchor-item
                  :href="`#${generateId(item.text, 1, i + 1)}`"
                  :title="item.text"
                  v-for="(item, i) in catalogList"
                  :key="i"
                  :class="'catalog_list_' + item.level">
                  <!-- {{ `#${item.text}_${i + 1}`}} -->
                </yh-anchor-item>
              </yh-anchor>
            </div>
          </div>
        </div>
      </div>
      <comment :article-id="blogDetails.id" :avatarUrl="userStore.getUserData.avatar" @like="like" />
    </div>
    <actions
      :article-id="blogDetails.id"
      :collectCount="blogDetails.collectCount"
      :likeNum="blogDetails.clickCount"
      :commentNum="blogDetails.openComment"></actions>
  </div>

  <backtop> </backtop>
</template>

<style scoped lang="scss">
@import "@/assets/css/edit-md/edit-md.scss";
.details {
  position: relative;

  .details_inner {
    width: calc(100% - $right-margin-width);
  }

  .conetnt {
    min-height: $content-height;
    border-radius: $border-radius;

    .details_title {
      font-size: 28.8px;
      word-wrap: break-word;
      color: var(--yh-text-color-primary);
      font-weight: 600;
      margin: 0;
      word-break: break-all;
    }

    .user_info {
      color: var(--yh-text-color-placeholder);
      font-size: 14px;
      position: relative;

      .user_info_avatar {
        flex-shrink: 0;

        img {
          width: 40px;
          height: 40px;
          border-radius: 50%;
        }
      }

      .attention {
        position: absolute;
        right: 0;
      }
    }

    //封面与简介
    .cover-summy {
      .cover {
        width: 240px;
        height: 160px;
        object-fit: cover;
        border-radius: 10px;
      }

      .summy {
        margin-left: 16px;
        color: var(--yh-text-color-secondary);
      }
    }
  }

  .catalog {
    width: $right-width;
    position: absolute;
    right: 16px;
    top: 80px;
    height: auto;

    .silder_inner {
      width: $right-width;
      position: fixed;
      top: 80px;
    }

    .catalog_wrap {
      border-radius: $border-radius;
      max-height: 450px;

      .logs-header {
        border-bottom: 1px solid var(--yh-border-level-1-color);
        color: var(--yh-text-color-primary);
      }

      .catalog_list {
        width: calc(100% + 6px);
        .yh-anchor {
          width: 212px;
        }
        .yh-anchor__item {
          box-sizing: border-box;
          font-size: 14px;
          padding: 0;
          border-radius: $border-radius;
          cursor: pointer;

          &:hover {
            background-color: var(--yh-bg-color-container-hover);
          }
        }
        :deep(.yh-anchor__item-link) {
          padding: 6px 6px 6px 6px;
          width: 100%;
        }
        .catalog_list {
          &_2 {
            text-indent: 14px;
          }

          &_3,
          &_4,
          &_5,
          &_6,
          &_7,
          &_8 {
            text-indent: 28px;
          }
        }

        .catalogActive {
          color: var(--yh-text-color-brand);
        }
      }
    }

    //作者信息
    .author_info {
      border-radius: $border-radius;

      .author_header {
        width: 100%;
        line-height: 1;

        .author_img {
          width: 40px;
          height: 40px;
          border-radius: 50%;
          margin-right: 6px;
        }

        .user_name {
          color: var(--yh-text-color-secondary);
          line-height: 1.4;

          span {
            font-size: 16px;
          }
        }
      }

      .user_num_list {
        .item {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;

          .num {
            color: var(--yh-text-color-secondary);
          }

          .description {
            color: var(--yh-text-color-placeholder);
            font-size: 14px;
          }
        }
      }
    }
  }

  @include editmdVar;
}

@media screen and (min-width: 960px) and (max-width: 1320px) {
  .details {
    margin-left: 70px;
  }
}

@media screen and (max-width: 960px) {
  .details {
    margin: auto;
    padding-bottom: 60px;

    .details_inner {
      width: 100%;

      .conetnt {
        .details_title {
          font-size: 22px;
        }

        :deep(.yh-button) {
          height: 28px;
          line-height: 26px;
          padding: 0 12px;
        }
      }
    }

    // 封面与简介
    .cover-summy {
      flex-direction: column;
      .cover {
        max-width: 480px;
        max-height: 320px;
        width: 100% !important;
        height: auto !important;
      }
    }

    //文章作者处内容
    .article_little {
      font-size: 12px;

      .article_little_inner {
        flex-wrap: wrap;

        .name {
          color: var(--yh-text-color-secondary);
          width: 100%;
          font-size: 14px;
          word-break: break-all;
        }
      }
    }

    //操作栏
    :deep(.detail-actions) {
      width: 100%;
      top: inherit;
      bottom: 0;
      left: 0;
      margin-left: auto;
      display: flex;
      border-top: 1px solid var(--yh-border-level-1-color);
      box-shadow: var(--yh-shadow-1);

      .action_item {
        width: 33.3333%;
        border-radius: 0;
        margin-bottom: 0;
        box-shadow: none;
        border-right: 1px solid var(--yh-border-level-1-color);

        // height: 40px;
        &::after {
          position: relative;
          display: flex;
          left: 4px;
          background-color: inherit;
          color: var(--yh-text-color-placeholder);
          font-size: 14px;
        }

        &:last-child {
          border-right: none;
        }
      }
    }
  }
}
</style>

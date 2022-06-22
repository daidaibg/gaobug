<script setup lang='ts'>
import { ref, Ref } from "vue"
import { useRoute } from "vue-router"
import { currentGETPath } from "@/api"
import { ElMessage, ElAffix } from 'element-plus'
import MdEditor from "md-editor-v3"
import { HeadList } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { useTitle } from '@vueuse/core'
import { mdEditorConfig } from "@/config"
import { userThemeStore } from '@/store'
import Backtop from "@/components/backtop"
import Actions from "./actions"
import Comment from "./comment"
const themeStore = userThemeStore()
const title = useTitle()
const route = useRoute()
const previewTheme = ref("github")
// 'default' | 'github' | 'vuepress' | 'mk-cute' | 'smart-blue' | 'cyanosis'
const mdText = ref('')//内容
const blogDetails: any = ref({})//详情 
const catalogList: any = ref([])  //目录
const onGetCatalog = (list: HeadList[]) => {
    // console.log(list);
    catalogList.value = list
};
//获取详情
const getDetail = () => {
    currentGETPath('indexBlogDetail', route.params.id).then(res => {
        // console.log(res);
        if (res.code == 200) {
            blogDetails.value = res.data
            mdText.value = res.data.content
            title.value = res.data.title // change current title
        } else {
            ElMessage.error(res.msg)
        }
    })
}
if (route.params.id) {
    blogDetails.id = route.params.id
}
getDetail()
mdEditorConfig(MdEditor)
</script>

<template>
    <div class='gaobug   details  '>
        <div class="details_inner">
            <div class="container-bg  box-border px-6 py-3 conetnt">
                <div class="details_title" id="1">
                    {{ blogDetails.title }}
                </div>
                <div class="user_info mt-3 flex items-center">
                    <div class="user_info_avatar mr-2">
                        <img src="../../assets/img/avatar.png" alt="">
                    </div>
                    <div>
                        <div class="flex mb-0.5">
                            <span> {{ blogDetails.authorName }}</span>
                            <span class="mx-2">·</span>
                            <span class=""> {{ blogDetails.createTime }}
                            </span>
                            <span class="mx-2">·</span>
                            阅读{{ 0 }}
                        </div>
                        <div class="flex">
                            <span>分类：{{ blogDetails.categoryName || "-" }}</span>
                            <span class="ml-2">标签：{{ blogDetails.tag }}</span>
                        </div>
                    </div>
                    <div class="attention" v-if="!blogDetails.isAuthor">
                        <yh-button theme="primary" size="medium" variant="outline">关注</yh-button>
                    </div>
                </div>
                <div class="cover-summy flex  mt-4">
                    <img :src="blogDetails.coverUrl" alt="" class="cover" v-if="blogDetails.coverUrl">
                    <p class="summy break-all">{{ blogDetails.summary }}</p>
                </div>
                <md-editor :theme="themeStore.getTheme" :preview-theme="previewTheme" editor-id="edit2preview"
                    showCodeRowNumber class="previewmd mt-8" preview-only v-model="mdText" @GetCatalog="onGetCatalog">
                </md-editor>

            </div>

            <div class="catalog blog-cloud_info flex-shrink-0  ">
                <!-- <el-affix :offset="81" target="body">
            </el-affix> -->
                <div class="silder_inner">


                    <div class="author_info container-bg mb-4 box-border px-3 py-2">
                        <div class="author_header flex items-center">
                            <img src="../../assets/img/avatar.png" :alt="blogDetails.authorName" class="author_img">
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
                    <div class="catalog_wrap container-bg box-border px-3 pb-2 box-shadow-0"
                        v-show="catalogList.length > 0">
                        <header class="py-2 logs-header">
                            目录
                        </header>
                        <yh-anchor class="catalog_list mt-1" :targetOffset="80">
                            <yh-anchor-item :href="`#gaobug-heade-${i}`" :title="item.text"
                                v-for="(item, i) in catalogList" :key="i" :class="'catalog_list_' + item.level">
                            </yh-anchor-item>
                        </yh-anchor>
                    </div>
                </div>
            </div>
            <comment :article-id="blogDetails.id" :avatarUrl="'//www.gaobug.com/img/avatar/avatar.png'"/>
        </div>
        <actions :article-id="blogDetails.id" :collectCount="blogDetails.collectCount" :likeNum="blogDetails.clickCount"
            :commentNum="blogDetails.openComment"></actions>

    </div>


    <backtop>
    </backtop>
</template>

<style scoped lang='scss'>
.details {
    position: relative;
    .details_inner{
          width: calc(100% - $right-margin-width);
    }
    .conetnt {
        min-height: $content-height;
        border-radius: $border-radius;

        .details_title {
            font-size: 1.8em;
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
                width: 100%;

                .yh-anchor__item {
                    box-sizing: border-box;
                    padding: 6px 6px;
                    font-size: 14px;
                    border-radius: $border-radius;
                    cursor: pointer;

                    &:hover {
                        background-color: var(--yh-bg-color-container-hover);
                    }
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

    :deep(.md-dark) {
        // background-color: var(--yh-bg-color-container);
        --md-color: var(--yh-text-color-secondary);
        --md-hover-color: var(--yh-text-color-anti);

        --md-bk-color: var(--yh-bg-color-container);
        --md-bk-color-outstand: var(--yh-bg-color-secondarycontainer-hover);
        --md-bk-hover-color: var(--yh-bg-color-secondarycontainer-active);
        --md-border-color: var(--yh-border-level-1-color);
        --md-border-hover-color: var(--yh-border-level-3-color);
        --md-border-active-color: var(--yh-border-level-3-color);
        --md-modal-mask: var(--yh-mask-active);
        --md-scrollbar-bg-color: var(--yh-scrollbar-color);
        --md-scrollbar-thumb-color: var(--yh-scrollbar-color);
        --md-scrollbar-thumb-hover-color: var(--yh-scrollbar-color);
        --md-scrollbar-thumb-avtive-color: var(--yh-scrollbar-color);
    }

    :deep(.md-content) {
        h1 {
            font-size: 1.7em;
        }

        .title_md {
            // margin-top: -70px;
            // padding-top: 80px;
            // box-sizing: border-box;
        }

    }
}
</style>
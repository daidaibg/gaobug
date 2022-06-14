<script setup lang='ts'>
import { ref, Ref } from "vue"
import { useStore } from "vuex"
import { useRoute } from "vue-router"
import { currentGETPath } from "@/api"
import { ElMessage, ElAffix } from 'element-plus'
import MdEditor from "md-editor-v3"
import { HeadList } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import { useTitle } from '@vueuse/core'
import {mdEditorConfig} from "@/config"
const title = useTitle()
const route = useRoute()
const store = useStore()
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

getDetail()
mdEditorConfig(MdEditor)
</script>

<template>
    <div class='gaobug flex justify-between items-start details  '>
        <div class="container-bg  box-border px-6 py-3 conetnt">
            <div class="details_title" id="1">
                {{ blogDetails.title }}
            </div>
            <div class="user_info my-3 flex items-center">
                <div class="user_info_avatar mr-2">
                    <img src="../../assets/img/avatar.png" alt="">
                </div>
                <div>
                    <div class="flex mb-0.5">
                        <span> {{ blogDetails.author }}</span>
                        <span class="ml-2"> {{ blogDetails.updateTime }}
                        </span>
                        <span class="mx-2">·</span>
                        阅读{{ 0 }}
                    </div>
                    <div class="flex">
                        <span>分类：{{ blogDetails.category || "-" }}</span>
                        <span class="ml-2">标签：{{ blogDetails.tag }}</span>
                    </div>
                </div>
            </div>
            <md-editor :theme="store.state.themeStore.theme" :preview-theme="previewTheme" editor-id="edit2preview"
                showCodeRowNumber class="previewmd mt-6" preview-only v-model="mdText" @GetCatalog="onGetCatalog">
            </md-editor>
        </div>

        <div class="catalog blog-cloud_info flex-shrink-0  ">
            <el-affix :offset="81" target="body">
                <div class="catalog_wrap container-bg box-border px-3 pb-2 box-shadow-0">
                    <header class="py-2 headers">
                        目录
                    </header>
                    <yh-anchor container="body" class="catalog_list mt-1" :targetOffset="80">
                        <yh-anchor-item :href="`#gaobug-heade-${i}`" :title="item.text" v-for="(item, i) in catalogList"
                            :key="i" :class="'catalog_list_' + item.level"></yh-anchor-item>
                    </yh-anchor>
                </div>
            </el-affix>
        </div>
    </div>
</template>

<style scoped lang='scss'>
.details {
    .conetnt {
        min-height: $content-height;
        border-radius: $border-radius;
        width: calc( 100% - $right-margin-width);
        .details_title {
            font-size: 1.8em;
            word-wrap: break-word;
            color: var(--yh-text-color-primary);
            font-weight: 600;
            margin: 0;
            word-break: break-all;

        }

        .user_info {
            color: var(--yh-text-color-secondary);
            font-size: 14px;
            position: relative;

            .user_info_avatar {
                img {
                    width: 40px;
                    height: 40px;
                    border-radius: 50%;
                }
            }
        }
    }

    .catalog {
        width: $right-width;

        .catalog_wrap {
            border-radius: $border-radius;
            max-height: 450px;

            .headers {
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
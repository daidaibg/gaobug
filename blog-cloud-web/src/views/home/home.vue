<script setup lang='ts'>

import HomeUser from "./home-user.vue"
import { reactive, ref, toRefs, } from "vue"
import { currentGETPath } from "@/api"
import { ElMessage, ElImage, ElBacktop ,ElTooltip} from "element-plus"
import { HomeBlogState } from "./home-types"
import { typelist } from "./home-config"
import { useInfiniteScroll } from '@vueuse/core'
import { getScrollContainer } from "yhht-plus/utils/index"
import { TypeList } from "./home-types"
import { useRouter } from "vue-router"
const router = useRouter()
const state: HomeBlogState = reactive({
    blogList: [],
    blogPage: {
        current: 1, size: 15
    },
    loading: false,
})
const active = ref("")
active.value = typelist[0].type

const onToggleType = (item: TypeList) => {
    if (active.value === item.type) return;
    state.blogPage.current = 1
    active.value = item.type
    getBlogList()
}
const jumpDetail = (item: any) => {
    router.push({ path: "/article/details/" + item.id })
}
const getBlogList = () => {
    state.loading = true
    currentGETPath('home1', active.value, { ...state.blogPage }).then(res => {
        // console.log('blogList',res);
        if (res.code == 200) {
            let blogList = res.data.records
            if (blogList.length < state.blogPage.size) {
                if (state.blogPage.current == 1) {
                    state.blogList = []
                }
                blogList.forEach((item: object) => {
                    state.blogList.push(item)
                });
                state.loading = "end"
            } else if (state.blogPage.current === 1) {
                state.blogList = blogList
                state.loading = false
            } else {
                blogList.forEach((item: object) => {
                    state.blogList.push(item)
                });
                state.loading = false
            }
        } else {
            state.loading = false
            ElMessage.error(res.msg)
        }
    })
}
useInfiniteScroll(getScrollContainer('body'), (e) => {
    if (state.loading || state.loading === "end") return
    state.blogPage.current++
    getBlogList()
}, { distance: 20 })

getBlogList()
</script>

<template>
    <div class='gaobug flex justify-between items-start'>
        <div class="blog-cloud_content flex-1 box-shadow-0 container-bg">
            <div class="list-header ">
                <ul class="list-header_wrap flex">
                    <li class="list-header_item" v-for="item in typelist" :key="item.type" @click="onToggleType(item)"
                        :class="{ active: active == item.type }">
                        {{ $t(item.name) }}
                    </li>
                </ul>
            </div>
            <ul class="blog-list">
                <li v-for="item in state.blogList" :key="item.oid" class="blog-list_item cursor-pointer"
                    @click="jumpDetail(item)">
                    <header class="blog-list_header flex items-center">
                        <span class="name">{{ item.authorName }}</span>
                        <span class="time">{{ item.publishTime }}</span>
                        <span class="sortName">{{ item.categoryName || '-' }}</span>
                    </header>
                    <div class="flex blog-list_body justify-between items-center">
                        <div class="info-box">
                            <h2 class="info-box_title">
                                {{ item.title }}
                            </h2>
                            <div class="info-box_content">
                                {{ item.summary }}
                            </div>
                            <div class="info-box_action flex items-center">
                                <div class=" info-box_action-item">
                                    <i class="dd-icon-liulan icon"></i>
                                    <span>{{ item.clickCount }}</span>
                                </div>
                                <div class="info-box_action-item hovers">
                                    <i class="dd-icon-dianzan icon"></i>
                                    <span>{{ item.collectCount }}</span>
                                </div>
                                <div class="info-box_action-item hovers">
                                    <i class="dd-icon-pinglun icon"></i>
                                    <span>{{ item.comment || 0 }}</span>
                                </div>
                            </div>
                        </div>
                        <div class="thumb overflow-hidden" v-if="item.coverUrl">
                            <el-image style="width: 100%; height: 100%" :src="item.coverUrl" fit="cover" />
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="blog-cloud_info flex-shrink-0 ml-4 ">
            <home-user></home-user>
        </div>
    </div>
    <el-backtop :right="100" :bottom="100" target="body">
        <svg data-v-0b82b7b1="" width="16" height="16" viewBox="0 0 16 16" fill="none"
            xmlns="http://www.w3.org/2000/svg" class="">
            <path data-v-0b82b7b1="" fill-rule="evenodd" clip-rule="evenodd"
                d="M2.75 1C2.33579 1 2 1.33579 2 1.75C2 2.16421 2.33579 2.5 2.75 2.5H13.25C13.6642 2.5 14 2.16421 14 1.75C14 1.33579 13.6642 1 13.25 1H2.75ZM7.24407 3.87287C7.64284 3.41241 8.35716 3.41241 8.75593 3.87287L13.0622 8.84535C13.6231 9.49299 13.163 10.5 12.3063 10.5H10V14C10 14.5523 9.55228 15 9 15H7C6.44772 15 6 14.5523 6 14V10.5H3.69371C2.83696 10.5 2.37691 9.49299 2.93778 8.84535L7.24407 3.87287Z"
                fill="#8A919F"></path>
        </svg>
    </el-backtop>

</template>

<style scoped lang='scss'>
$border-radius: 4px;
$border-color: var(--yh-border-level-1-color);
$border-secondarycontainer-color: var(--yh-bg-color-secondarycontainer-hover);
$color-placeholder: var(--yh-text-color-placeholder);
$color-brand: var(--yh-brand-color);
$color-brand-hover: var(--yh-brand-color-hover);


$color-content: var(--dd-color-content);
$color-secondary: var(--yh-text-color-secondary);
$color-primary: var(--yh-text-color-primary);
$font-gray-1: var(--dd-font-gray-1);

.gaobug {
    .list-header {
        padding: 16px 16px;
        border-bottom: 1px solid $border-color;
        font-size: 14px;

        &_item {
            padding: 0 10px;
            border-right: 1px solid $border-secondarycontainer-color;
            line-height: 1;
            color: $color-placeholder;
            cursor: pointer;

            &:hover {
                color: $color-brand;
            }

            &.active {
                color: $color-brand;
            }

            &:last-child {
                border-right: none;
            }
        }
    }

    &__content {
        border-radius: $border-radius;
        height: auto;
    }

    .blog-cloud_info {
        width: $right-width;

    }
}

.blog-list {
    &_item {
        box-sizing: border-box;
        padding: 10px 20px 12px;

        border-bottom: 1px solid $border-color;

        &:hover {
            background-color: var(--yh-bg-color-container-hover);
        }
    }

    &_header {
        height: 22px;
        font-size: 14px;

        span {
            padding: 0 6px;
            border-right: 1px solid $border-color;
            line-height: 1;

            &:last-child {
                border-right: none;
            }
        }

        .name {
            color: $font-gray-1;
            padding-left: 0;
        }

        .time {
            color: $color-content;

        }

        .sortName {
            color: $color-content;

        }
    }

    &_body {
        .info-box {
            flex: 1;

            &_action {
                font-size: 12px;
                margin: 10px 0;

                &-item {
                    display: flex;
                    align-items: center;
                    margin-right: 20px;
                    line-height: 1;

                    .icon {
                        font-size: 14px;
                        margin-right: 4px;
                        color: $color-placeholder;
                        font-weight: 900;
                    }

                    span {
                        color: $color-secondary;

                    }

                    &:hover {

                        .icon,
                        span {
                            color: $color-brand;
                        }

                    }
                }
            }

            &_title {
                color: $color-primary;
                line-height: 1.6;
                font-weight: 900;
                margin: 8px 0;
            }

            &_content {
                color: $color-content;
                margin-bottom: 8px;
                font-size: 14px;
            }
        }

        .thumb {
            flex: 0 0 auto;
            width: 120px;
            height: 80px;
            margin: -20px 10px 0 24px;

            border-radius: $border-radius;
        }
    }
}
</style>
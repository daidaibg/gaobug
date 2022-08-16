<script setup lang='ts'>

import HomeUser from "./home-user.vue"
import { reactive, ref, toRefs, } from "vue"
import { currentGETPath } from "@/api"
import { ElMessage, ElImage, ElBacktop, ElTooltip } from "element-plus"
import { HomeBlogState } from "./home-types"
import { typelist } from "./home-config"
import { useInfiniteScroll } from '@vueuse/core'
import { getScrollContainer } from "yhht-plus/utils/index"
import { TypeList } from "./home-types"
import { useRouter } from "vue-router"
import {useBlogAction} from "@/hook/modules/use-blog-action"
import Backtop from "@/components/backtop"
const router = useRouter()
const state = reactive<HomeBlogState>({
    blogList: [],
    blogPage: {
        current: 1, size: 15
    },
    loading: false,
})
const active = ref("")
const {blogLike} =useBlogAction()

active.value = typelist[0].type
// 点赞
const onLike = (item: any) => {

    console.log(item);
    blogLike({
        "targetId": item.id,
        "targetType": 1,
        "likeFlag": 1
    },{
        success:(res:any)=>{
        }
    })
}

//切换首页类型
const onToggleType = (item: TypeList) => {
    if (active.value === item.type) return;
    state.blogPage.current = 1
    active.value = item.type
    getBlogList()
}
// 跳转详情
const jumpDetail = (item: any) => {
    router.push({ path: "/article/details/" + item.id })
}
// 获取博客列表
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
// 无限滚动
useInfiniteScroll(getScrollContainer('body'), (e) => {
    if (state.loading || state.loading === "end") return
    state.blogPage.current++
    getBlogList()
}, { distance: 20 })
// 页面初始化触发方法
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
                <li v-for="item in state.blogList" :key="item.oid" class="blog-list_item cursor-pointer box-variable"
                    @click="jumpDetail(item)">
                    <header class="blog-list_header flex items-center">
                        <span class="name">{{ item.authorName }}</span>
                        <span class="time">{{ item.publishTime }}</span>
                        <span class="sortName">{{ item.categoryName || '-' }}</span>
                    </header>
                    <h2 class="info-box_title">
                        {{ item.title }}
                    </h2>
                    <div class="flex blog-list_body justify-between ">

                        <div class="info-box">

                            <div class="info-box_content">
                                {{ item.summary }}
                            </div>
                            <div class="info-box_action flex items-center">
                                <div class=" info-box_action-item">
                                    <i class="dd-icon-liulan icon"></i>
                                    <span>{{ item.clickCount }}</span>
                                </div>
                                <div class="info-box_action-item hovers" @click.stop="onLike(item)">
                                    <i class="dd-icon-dianzan icon"></i>
                                    <span>{{ item.likeCount }}</span>
                                </div>
                                <div class="info-box_action-item hovers">
                                    <i class="dd-icon-pinglun icon"></i>
                                    <span>{{ item.commentCount }}</span>
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
        <div class="blog-cloud_info flex-shrink-0 ml-4 small-screen-hide">
            <home-user></home-user>
        </div>
    </div>
    <backtop>
    </backtop>

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
            position: relative;

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
    .blog-list_item {
        box-sizing: border-box;
        padding: 10px 20px 12px;
        // border-bottom: 1px solid $border-color;
        position: relative;

        &:hover {
            // background-color: var(--yh-bg-color-container-hover);
            background-color: var(--gb-container-hover);
        }

        &::after {
            content: " ";
            position: absolute;
            width: calc(100% - 40px);
            height: 1px;
            background: $border-color;
            bottom: 0;
            left: 20px;
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

    .info-box_title {
        color: $color-primary;
        line-height: 1;
        font-weight: 900;
        margin: 8px 0;
    }

    &_body {


        .info-box {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: space-between;

            &_action {
                font-size: 12px;
                // margin: 10px 0;

                margin-top: 10px;

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


            &_content {
                flex: 1;
                flex-shrink: 0;
                color: $color-content;
                // margin-bottom: 8px;
                font-size: 14px;
            }
        }

        .thumb {
            flex: 0 0 auto;
            width: 120px;
            height: 80px;
            margin: 0px 10px 0 16px;
            border-radius: $border-radius;
        }
    }
}

@media screen and (max-width:960px) {
    .info-box {
        .info-box_content {
            max-height: 80px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
        }
    }
}
</style>
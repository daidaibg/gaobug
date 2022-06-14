<script setup lang='ts'>
import HomeCarousel from "./home-carousel.vue"
import { ElAffix } from "element-plus"
import { useRouter } from "vue-router"
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex"
import {todayTime} from "@/utils"
import { computed } from "vue";
const store = useStore()

const router = useRouter()
const goRouter = (path: string) => {
    router.push(path)
}
const userData: any = computed(()=>{
    return store.state.userStore.userData
})
const userDataList = [
    {
        name: "home.article",
        key: 'myArticle',
        icon: "dd-icon-wenzhang",
        url: '/blogs/manage/article',

    },
    {
        name: "home.collect",
        key: 'myCollection',
        icon: "dd-icon-shoucang",
        url: ''
    },
    {
        name: "home.attention",
        key: 'myAttention',
        icon: "dd-icon-guanzhu",
        url: ''
    },
]

const goPage = (item: any) => {
    if (item.url && item.url !== "") {
        goRouter(item.url)
    }
}
const openModelLogin = ()=>{
    store.commit("userStore/onModelLogin")
}


</script>

<template>
    <el-affix :offset="81" target="body">
        <template v-if="store.state.userStore.isLogin">
            <div class='user-info container-bg box-border box-shadow-0'>
                <div class="user-info_header flex items-center justify-between">
                    <div class="flex items-center">
                        <i class="yh-icons-user-solid user_info_icon"></i>
                        <span class="username ml-2">{{userData.nickName}}</span>
                        <span class="grade ml-2" style="font-size:12px ;"> ✨</span> 
                        <!-- ⭐✨-->
                    </div>
                    <div class="cursor-pointer yh-brand-color-hover go-release" @click="goRouter('/write/essay')">
                        去发布
                        <i class="yh-icons-edit"></i>
                    </div>
                </div>
                <home-carousel></home-carousel>
            </div>
            <!-- 用户我的 -->
            <div class="user_data container-bg mt-2.5 box-shadow-0">
                <div class="user_data_item flex justify-between items-center cursor-pointer"
                    v-for="item in userDataList" :key="item.key" @click="goPage(item)">
                    <div class="user_data_name flex  items-center">
                        <i class="user_data_icon" :class="item.icon"></i>
                        <span class="name ml-1">{{ $t(item.name) }}</span>
                    </div>
                    <div class="num flex items-center justify-center">{{  userData[item.key] | 0 }}</div>
                </div>
            </div>
        </template>
        <template v-else>
            <div class="container-bg box-shadow-0 nologin user-info">
                 <div class="user-info_header flex items-center justify-between">
                    <div class="flex items-center">
                        <i class="yh-icons-message-solid user_info_icon"></i>
                        <span class="username ml-2">{{todayTime()}}</span>
                    </div>
                    <div class="cursor-pointer yh-brand-color-hover go-release" @click="openModelLogin">
                        发布文章
                        <i class="yh-icons-edit"></i>
                    </div>
                </div>
                <div class="tips mb-4 mt-2">
                    <strong>搞bug</strong>
                    祝您欢乐生活每一天！
                </div>
            </div>
        </template>
    </el-affix>
</template>

<style scoped lang='scss'>
$border-radius: 4px;

.user-info {
    padding: 0 16px 8px 16px;
    border-radius: $border-radius;

    &_header {
        height: 52px;
        line-height: 1;
        font-size: 14px;

        .user_info_icon {
            font-size: 18px;
            color: var(--yh-text-color-secondary);
        }

        .username {
            color: var(--yh-text-color-secondary);
        }

        .grade {
            color: var(--yh-brand-color);
            font-size: 16px;
        }
    }

    .go-release {}
}

.user_data {
    width: 100%;
    box-sizing: border-box;
    padding: 4px 0px;
    font-size: 14px;
    line-height: 1;
    border-radius: $border-radius;

    .user_data_item {
        padding: 8px 16px;
        box-sizing: border-box;

        .user_data_name {
            color: var(--yh-text-color-secondary);
        }

        .num {
            min-width: 24px;
            height: 24px;
            background-color: var(--yh-bg-color-secondarycontainer);
            border-radius: 4px;
            color: var(--yh-text-color-secondary);
        }

        &:hover {
            background-color: var(--yh-bg-color-secondarycontainer-hover);

            .num {
                background-color: var(--yh-bg-color-container);
            }
        }
    }
}
.nologin{ 
    .tips{ 
        font-size: 14px;
        color: var( --yh-text-color-primary);
        text-align: center;
    }
}
</style>
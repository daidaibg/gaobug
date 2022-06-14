<script setup lang='ts'>
import { ElPopover } from 'element-plus'
import { useStore } from "vuex"
import { useRouter } from "vue-router"
const router = useRouter()
const store = useStore()
const layout = () => {
   store.commit("userStore/userOffline")
    router.push("/login")
}
const userSetting = (path: string) => {
    router.push(path)
}

</script>

<template>
    <div class='header_user'>
        <el-popover width="120px" trigger="hover" popper-class="user_pop" v-if="store.state.userStore.isLogin">
            <template #reference> <img :src="store.state.userStore.userData.avatar" alt=""
                    class="user_img" /></template>
            <ul class="user_list">
                <li>
                    <i class="dd-icon-home icon"></i>
                    <span class="titles">我的主页</span>
                </li>
                <li @click="userSetting(`/blogs/manage/article`)">
                    <i class="dd-icon-wenzhang icon" ></i>
                    <span class="titles">文章管理</span>
                </li>
                <li @click="userSetting(`/user/setting/profile`)">
                    <i class="dd-icon-wode-wode icon "></i>
                    <span class="titles">个人信息</span>
                </li>
                <li @click="layout">
                    <i class="dd-icon-tuichu icon"></i>
                    <span class="titles">退出登录</span>

                </li>
            </ul>
        </el-popover>
        <yh-button theme="primary" size="medium" v-else variant="outline" @click="layout">登 陆</yh-button>
    </div>
</template>

<style scoped lang='scss'>
.header_user {
    flex-shrink: 0;
    .user_img {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        cursor: pointer;
    }

}

.user_list {
    li {
        line-height: 1.8;
        cursor: pointer;
        padding: 2px 0;
        margin-bottom: 4px;
        // color: var(--yh-text-color-secondary);

        .titles {
            margin-left: 6px;
        }

        .icon {
            font-size: 14px;
            // color: var(--yh-text-color-placeholder);
        }

        &.activeclass {
            color: var(--yh-brand-color-8);

            .icon {
                color: var(--yh-brand-color-8);
            }

        }

        &:last-child {
            margin-bottom: 0;
        }

    }

    li:hover {
        color: var(--yh-brand-color-8);

        .icon {
            color: var(--yh-brand-color-8);
        }
    }
}
</style>
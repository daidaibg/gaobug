<script setup lang='ts'>
import { useUserStore } from '@/store'
import { ElPopover } from 'element-plus'
import { useRouter } from "vue-router"
const router = useRouter()
const userStore = useUserStore()
const layout = () => {
   userStore.userOffline()
    router.push("/login")
}
const userSetting = (path: string) => {
    router.push(path)
}

</script>

<template>
    <div class='header_user'>
        <el-popover width="120px" trigger="hover" popper-class="user_pop" >
            <template #reference> <img src="https://www.gaobug.com/img/avatar/avatar.png" alt=""
                    class="user_img" /></template>
            <ul class="user_list">
                <li @click="layout">
                    <i class="yh-icons-message-solid icon"></i>
                    <span class="titles">退出登录</span>

                </li>
            </ul>
        </el-popover>
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
            color: var(--yh-brand-color);

            .icon {
                color: var(--yh-brand-color);
            }

        }

        &:last-child {
            margin-bottom: 0;
        }

    }

    li:hover {
        color: var(--yh-brand-color);

        .icon {
            color: var(--yh-brand-color);
        }
    }
}
</style>
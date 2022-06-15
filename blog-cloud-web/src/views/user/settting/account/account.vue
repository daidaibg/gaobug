<script setup lang='ts'>
import { useUserStore } from '@/store'

import { reactive } from "vue"
const userStore = useUserStore()

const getUserDataKey = (key: string) => {
    return userStore.getUserData[key]
}
const formData = reactive({
    email: getUserDataKey('email'),
    phone: getUserDataKey('phone'),
    password: getUserDataKey('password'),

})

</script>

<template>
    <div class='user_setting_profile_wrap'>
        <h2 class="titles">账号设置</h2>
        <ul class="form-wrap">
            <li class="form_item" style="align-items: center;">
                <label class="labels"> 密码</label>
                <span class="aciton">修改</span>
            </li>
            <li class="form_item" style="align-items: center;">
                <div class="form_inner_con">
                    <label class="labels"> 邮箱</label>
                    <span class="text">{{ formData.email }}</span>
                </div>
                <span class="aciton" v-if="formData.email">修改</span>
                <span class="aciton" v-else>绑定</span>

            </li>
            <li class="form_item" style="align-items: center;">
                <div class="form_inner_con">
                    <label class="labels"> 手机号</label>
                    <span class="text">{{ formData.phone }}</span>
                </div>
                <span class="aciton" v-if="formData.phone">修改</span>
                <span class="aciton" v-else>绑定</span>

            </li>
        </ul>
    </div>
</template>

<style scoped lang='scss'>
.user_setting_profile_wrap {
    .titles {
        height: 48px;
        line-height: 48px;
        font-size: 18px;
        font-weight: 600;
        color: var(--yh-text-color-primary);
        padding-left: 16px;
        border-bottom: 1px solid var(--yh-border-level-1-color);
    }

    .form-wrap {
        padding: 0 20px;
        font-size: 14px;
    }

    .form_item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 18px;
        border-bottom: 1px solid var(--yh-border-level-1-color);

        .aciton {
            cursor: pointer;
            color: var(--yh-brand-color);
            &:hover{
                color: var(--yh-brand-color-hover);
            }
            &:active{
                color: var(--yh-brand-color-active);
            }
        }

        .labels {
            color: var(--yh-text-color-primary);
                width: 50px;
                flex-shrink: 0;
        }
        .form_inner_con{
            display: flex;
        }
        .text {
            color: var(--yh-text-color-secondary);
            margin-left: 12px;
        }

    }

}
</style>
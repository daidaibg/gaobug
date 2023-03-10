<script setup lang='ts'>

import { computed, reactive, ref } from "vue"
import { useUserStore } from '@/store'

import type { FormInstance } from 'element-plus'
import { ElForm, ElFormItem, ElInput, ElMessage } from 'element-plus'
import { useRouter } from "vue-router"
import { postLogin } from "@/api/modules/home"
import { setLocalStorage ,validatePhone} from "@/utils"
import { StorageEnum } from "@/enums"
const router = useRouter()
const userStore = useUserStore()

const formRef = ref<FormInstance>()//密码登录表单
const formRefPhone = ref<FormInstance>();//手机号登录表单
const formLoading = ref(false)
const titleActive = ref(2);//默认手机登录 1 手机 2 免密
const loginForm = reactive({
    account: '',
    password: '',
    phone: '',
    code: "",
})
const state = reactive({
    getCode: false, //是否正在获取验证码
    countdown: "获取验证码"

})
const props = defineProps({
    isModel: {
        type: Boolean,
        default: false
    }
})
const rules = reactive({
    account: [{
        required: true,
        message: '请输入手机号/邮箱/账号！',
        trigger: 'blur',
    }],
    password: [{
        required: true,
        message: '请输入密码',
        trigger: 'blur',
    }],
    phone: [{
        required: true,
        message: '请输入手机号！',
        trigger: 'blur',
    }],
    code: [{
        required: true,
        message: '请输入验证码',
        trigger: 'blur',
    }],
})
const close = () => {
    userStore.offModelLogin()
}
const register = () => {
    router.push("register")
    close()
}
//获取验证码
async function GetCode() {
    if (state.getCode) return;
    state.getCode = true;
    state.countdown = "发送中...";
    // await getCaptchaCode({
    //   email: state.loginForm.email,
    // }).then((res) => {
    //   if (res.success) {
    //     state.sendeSuccess();
    //   } else {
    //     state.countdown = "重新获取";
    //     state.$message.error(res.msg);
    //     state.getCode = false;
    //     if (res.msg.indexOf("绑定") != -1) {
    //       state.isBindUser = false;
    //     }
    //   }
    // });
    sendeSuccess()
}
//倒计时
function sendeSuccess() {
    ElMessage({
        message: "发送成功",
        type: "success",
    });

    let s = 60;
    state.countdown = s + "s";
    let interval = setInterval(() => {
        s--;
        state.countdown = s + "s";
        if (s < 0) {
            state.countdown = "重新获取";
            state.getCode = false;
            clearInterval(interval);
        }
    }, 1000);
}
//账号登录
const submit = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            formLoading.value = true
            postLogin({ account: loginForm.account, password: loginForm.password, "loginType": "1" }).then(async (res:any) => {
                if (res.code === 200) {
                    setLocalStorage(StorageEnum.GB_TOKEN_STORE, res.data.authToken)
                    userStore.getUserInfo()
                    // 如果是弹窗形式展示的就需要关闭弹窗
                    if (props.isModel) {
                        
                        close()
                    } else {
                        await router.push('/')
                    }
                } else {
                    ElMessage.error({
                        message: res.msg,
                        customClass: 'message_top'
                    })
                }
                formLoading.value = false

            })
        } else {
            return false
        }
    })
}
// 手机号登陆注册
const submitPhone = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {

        } else {
            return false
        }
    })
}
</script>

<template>
    <div class="login_form p-6">
        <header class="login_header flex items-center ">
            <span class="login_type_title " :class="{ login_type_title_active: titleActive == 1 }"
                @click="titleActive = 1">
                免密登录
            </span>
            <span class="login_type_title ml-4" :class="{ login_type_title_active: titleActive == 2 }"
                @click="titleActive = 2">
                密码登录
            </span>
            <i class="yh-icons-close cursor-pointer" @click="close"></i>
        </header>
        <template v-if="titleActive == 1">
            <el-form :model="loginForm" label-width="0px" class="mt-6" size="large" ref="formRefPhone" :rules="rules">
                <el-form-item label="" prop="phone">
                    <el-input v-model="loginForm.phone" placeholder="手机号" />
                </el-form-item>
                <el-form-item label="" prop="code" class="captcha">
                    <el-input v-model="loginForm.code" type="code" autocomplete="off" placeholder="验证码">
                        <template #append>
                            <div class="captcha_inner" @click="GetCode">
                                {{ state.countdown }}
                            </div>
                        </template>
                    </el-input>
                </el-form-item>
            </el-form>
            <yh-button theme="primary" block size="large" @click="submitPhone(formRefPhone)" :loading="formLoading">
                登录/注册</yh-button>
            <div class="action flex justify-between mt-1.5">
                <span class="warning">短信已欠费，暂无法免密登录/注册</span>
            </div>
        </template>
        <template v-else-if="titleActive == 2">
            <el-form :model="loginForm" label-width="0px" class="mt-6" size="large" ref="formRef" :rules="rules">
                <el-form-item label="" prop="account">
                    <el-input v-model="loginForm.account" placeholder="手机号/邮箱/账号" />
                </el-form-item>
                <el-form-item label="" prop="password">
                    <el-input v-model="loginForm.password" type="password" autocomplete="off" placeholder="请输入密码" />
                </el-form-item>
            </el-form>
            <yh-button theme="primary" block size="large" @click="submit(formRef)" :loading="formLoading">登 录
            </yh-button>
            <div class="action flex justify-between mt-1.5">
                <span class="register" @click="register">去注册</span>
            </div>
        </template>

    </div>
</template>

<style scoped lang='scss'>
.login_form {
    width: 380px;
    background-color: var(--yh-bg-color-container);
    border-radius: 10px;
    box-shadow: var(--yh-shadow-1);

    .captcha {
        :deep(.el-input-group__append) {
            padding: 0;
        }

        .captcha_inner {
            width: 106px;
            text-align: center;
            cursor: pointer;
            padding: 0 16px;
        }
    }
}

.login_header {
    height: 30px;
    position: relative;

    .login_type_title {
        cursor: pointer;
        font-size: 16px;
        font-weight: 900;
        color: var(--yh-text-color-placeholder);
        position: relative;

        &::before {
            content: " ";
            display: none;
            width: 24px;
            height: 2px;
            background: var(--yh-text-color-primary);
            position: absolute;
            bottom: -8px;
            left: 50%;
            transform: translateX(-50%);
        }
    }

    .login_type_title_active {
        color: var(--yh-text-color-primary);

        &::before {
            display: block;
        }
    }

    .yh-icons-close {
        position: absolute;
        right: 0;
        font-size: 16px;
        top: 8px;
        color: var(--yh-text-color-secondary);

    }
}

.action {
    font-size: 14px;

    .register {

        color: var(--yh-brand-color);
        cursor: pointer;
    }

    .warning {
        color: var(--yh-warning-color);
    }
}
</style>
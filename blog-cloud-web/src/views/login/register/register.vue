<!--
 * @Author: daidai
 * @Date: 2021-12-13 14:58:20
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-01-04 15:14:29
 * @FilePath: \web-pc\src\views\Login\Retrieve.vue
-->
<template>
  <div class="retrieve">
    <div class="flex-center flex justify-center items-center flex-col">
      <el-steps :space="400" :active="state.active" finish-status="success" process-status="finish" align-center>
        <el-step title="验证邮箱" :icon="Message">
        </el-step>
        <el-step title="填写密码" :icon="Lock">
        </el-step>
        <!-- <el-step title="成功" icon="el-icon-circle-check"></el-step> -->
      </el-steps>
      <div class="form_wrap">


        <el-form class="retrieve-form" :model="state.loginForm" :rules="state.rules" ref="formRef" size="large">
          <el-form-item prop="email">
            <el-input v-model="state.loginForm.email" :prefix-icon="Message" placeholder="请输入邮箱">
            </el-input>
            
          </el-form-item>
          <el-form-item class="captcha" prop="captcha">
            <div class="captcha_inner flex justify-between items-center">
              <el-input v-model="state.loginForm.captcha" placeholder="验证码" maxlength="6"></el-input>
              <p @click="GetCode">{{ state.countdown }}</p>
            </div>
          </el-form-item>
          <el-form-item prop="pass" v-if="state.active == 1">
            <el-input type="password" v-model="state.loginForm.pass" autocomplete="off" placeholder="请设置6-20位密码"
              :prefix-icon="Lock"></el-input>
          </el-form-item>
          <el-form-item prop="checkPass" v-if="state.active == 1">
            <el-input type="password" v-model="state.loginForm.checkPass" autocomplete="off" placeholder="请再次确认您的密码"
              :prefix-icon="Lock"></el-input>
          </el-form-item>
          <!-- @input="$refs.loginForm.validateField('checkPass')"  -->
        </el-form>
        <div class="tips flex justify-between">
          <yh-button size="large" style="width: 30%" @click="back()" variant="outline">返回
          </yh-button>
          <yh-button size="large" theme="primary" :loading="state.loading" class="flex-1 ml-4"
            @click="nextStep(formRef)" v-if="state.active == 0">下一步</yh-button>
          <yh-button size="large" theme="primary" :loading="state.loading" class="flex-1 ml-4" @click="save(formRef)"
            v-if="state.active == 1">保存</yh-button>
        </div>
      </div>
    </div>

  </div>
</template>

<script lang="ts" setup>
import { currentPOST ,currentGET} from "@/api";
import { reactive, ref } from "vue"
import { ElMessage, ElStep, ElSteps, ElForm, ElFormItem, ElInput } from "element-plus"
import { useRouter } from "vue-router"
import { Message, Lock } from '@element-plus/icons-vue'
import { validateEmailReg, validatePassword } from "@/utils"
const formRef: any = ref()
const router = useRouter()
var validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请再次输入密码"));
  } else if (value !== state.loginForm.pass) {
    callback(new Error("两次输入密码不一致!"));
  } else {
    callback();
  }
};
const state = reactive({
  active: 0,
  loading: false,
  loginForm: {
    email: "",
    pass: "",
    checkPass: "",
    captcha: "",
  },
  rules: {
    pass: [
      { required: true, message: "请输入密码", trigger: "blur" },
      { validator: validatePassword, trigger: "change" }],
    checkPass: [{ validator: validatePass2, trigger: "change" }],
    email: [
      { required: true, message: "请输入邮箱", trigger: "blur" },
      { validator: validateEmailReg, trigger: "change" },
    ],
    captcha: [{ required: true, message: "请输入验证码", trigger: "blur" }],
  },
  getCode: false,
  countdown: "获取验证码",
})

const back = () => {
  router.push("/login")
}
//下一步
const nextStep = (formEl: any) => {
  if (!formEl) return
  formEl.validate((valid: boolean) => {
    if (valid) {
      state.active = 1;
    } else {
      return false
    }

  });
}
// 保存
function save(formEl: any) {
  formEl.validate((valid: boolean) => {
    if (valid) {
      state.loading = true;
      currentPOST("registerEmail", {
        email: state.loginForm.email,
        password: state.loginForm.pass,
        code: state.loginForm.captcha,
      }).then((res) => {
        state.loading = false;
        if (res.code == 200) {
          ElMessage.success("注册成功");
          router.push("/login");
        } else {
          ElMessage.error(res.msg);
        }
      });
    } else {

      return false
    }

  });
}
//获取验证码
async function GetCode() {
  if (state.getCode) return;
  state.getCode = true;
  state.countdown = "发送中...";
  await currentGET("captchaEmail",{
    email: state.loginForm.email,
  }).then((res:any) => {
    if (res.code===200) {
      sendeSuccess();
    } else {
      state.countdown = "重新获取";
      ElMessage.error(res.msg);
      state.getCode = false;
      if (res.msg.indexOf("绑定") != -1) {
      }
    }
  });
}
function sendeSuccess() {
  ElMessage({
    message: "发送成功",
    type: "success",
  });

  let s = 30;
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

</script>
<style lang='scss' scoped>
.retrieve {
  // height: calc(100vh - $header-height);
  height: 100vh;
  background-color: var(--yh-bg-color-container);
  
  >.flex-center {
    height: 100%;
  }

  .el-steps {
    width: 800px;
    margin-top: -25vh;

    :deep(.el-step__icon) {
      background: var(--yh-bg-color-container);
    }
  }

  .form_wrap {
    width: 340px;
    margin-top: 100px;

    .retrieve-form {}
  }



  .captcha_inner {
    width: 100%;

    :deep(.el-input) {
      width: 230px;
    }

    p {
      margin-left: 18px;
      flex: 1;
      cursor: pointer;
      border: solid 1px var(--yh-brand-color);
      border-radius: 6px;
      height: 30px;
      color: var(--yh-brand-color);
      line-height: 28px;
      text-align: center;

      &:hover {
        background: var(--yh-brand-color-light);
      }
    }
  }
}
</style>
<!--
 * @Author: daidai
 * @Date: 2021-12-13 14:58:20
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-23 14:45:27
 * @FilePath: \web-pc\src\views\Login\Retrieve.vue
-->
<template>
  <div class="retrieve">
    <header class="headers">
      <a class="lseft" href="#/">
        <h1>{{ sysName }}</h1>
      </a>
    </header>
    <div class="flex-center">
      <el-steps
        :space="400"
        :active="active"
        finish-status="success"
        process-status="finish"
        align-center
      >
        <el-step title="验证邮箱" icon="el-icon-message"></el-step>
        <el-step title="修改密码" icon="el-icon-edit"></el-step>
        <!-- <el-step title="成功" icon="el-icon-circle-check"></el-step> -->
      </el-steps>
      <el-form
        class="retrieve-form"
        :model="loginForm"
        status-icon
        :rules="rules"
        ref="loginForm"
        @submit.native.prevent
      >
        <el-form-item prop="email">
          <el-input
            v-model="loginForm.email"
            prefix-icon="el-icon-message"
            placeholder="请输入邮箱"
            @blur="isBindUser=true"
          ></el-input>
        </el-form-item>
        <el-form-item class="captcha" prop="captcha">
          <div class="captcha_inner flex-sb">
            <el-input
              v-model="loginForm.captcha"
              placeholder="验证码"
              maxlength="6"
            ></el-input>
            <p @click="GetCode">{{ countdown }}</p>
          </div>
        </el-form-item>
        <el-form-item prop="pass" v-if="active == 1">
          <el-input
            type="password"
            v-model="loginForm.pass"
            autocomplete="off"
            placeholder="请设置6-20位新密码"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="checkPass" v-if="active == 1">
          <el-input
            type="password"
            v-model="loginForm.checkPass"
            autocomplete="off"
            placeholder="请再次确认您的密码"
            @input="$refs.loginForm.validateField('checkPass')"
            prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <div class="tips flex-sb">
            <el-button
            size="medium"
            :loading="loading"
            native-type="submit"
            style="width: 30%"
            @click="back"
            >返回</el-button
          >
          <el-button
            size="medium"
            type="primary"
            :loading="loading"
            native-type="submit"
            style="width: 100%"
            @click="nextStep"
            v-if="active == 0"
            >下一步</el-button
          >
          <el-button
            size="medium"
            type="primary"
            :loading="loading"
            native-type="submit"
            style="width: 100%"
            @click="save"
            v-if="active == 1"
            >保存</el-button
          >
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { sysName } from "@/constants";
import { validateEmailReg } from "@/constants/rules";
import { getCaptchaCode, UpdatePassword } from "api/modules/sys";
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.loginForm.checkPass !== "") {
          this.$refs.loginForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.loginForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    var vaildemail = (rule, value, callback) => {
        // console.log(this.isBindUser)
      if (!this.isBindUser) {
          callback(new Error("该邮箱未绑定用户!"));
      } else {
        callback();
      }
    };
    return {
      active: 0,
      loading: false,
      loginForm: {
        email: "",
        pass: "",
        checkPass: "",
        captcha: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "change" }],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: validateEmailReg, trigger: "change" },
          { validator: vaildemail, trigger: "blur" },
        ],
        captcha: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
      getCode: false,
      sysName: sysName,
      countdown: "获取验证码",
      isBindUser: true,
    };
  },
  created() {},

  mounted() {},
  methods: {
    back(){
      this.$router.push("/login")
    },
    nextStep() {
      this.$refs["loginForm"].validate((valid) => {
        if (!valid) {
          return;
        }
        this.active = 1;
      });
    },
    save() {
      this.$refs["loginForm"].validate((valid) => {
        if (!valid) {
          return;
        }
        this.loading = true;
        UpdatePassword({
          code: this.loginForm.captcha,
          email: this.loginForm.email,
          password: this.loginForm.pass,
          repeatPassword: this.loginForm.checkPass,
        }).then((res) => {
          this.loading = false;
          if (res.success) {
            this.$message.success("修改成功");
            this.$router.push("/login");
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },
    //获取验证码
    async GetCode() {
      if (this.getCode) return;
      this.getCode = true;
      this.isBindUser = true;
      this.countdown = "发送中...";
      await getCaptchaCode({
        email: this.loginForm.email,
      }).then((res) => {
        if (res.success) {
          this.sendeSuccess();
        } else {
          this.countdown = "重新获取";
          this.$message.error(res.msg);
          this.getCode = false;
          if (res.msg.indexOf("绑定") != -1) {
            this.isBindUser = false;
          }
        }
      });
       this.$refs.loginForm.validateField("email");
    },
    sendeSuccess() {
      this.$message({
        message: "发送成功",
        type: "success",
      });

      let s = 60;
      this.countdown = s + "s";
      let interval = setInterval(() => {
        s--;
        this.countdown = s + "s";
        if (s < 0) {
          this.countdown = "重新获取";
          this.getCode = false;
          clearInterval(interval);
        }
      }, 1000);
    },
  },
};
</script>
<style lang='scss' scoped>
.retrieve {
  height: 100%;
  > .flex-center {
    flex-direction: column;
    height: $index-content-height;
  }
  .el-steps {
    width: 800px;
    margin-top: -25vh;
  }
  .retrieve-form {
    width: 340px;
    margin-top: 100px;
  }
  .captcha_inner {
    /deep/ .el-input {
      width: 230px;
    }
    p {
      margin-left: 18px;
      width: 120px;
      cursor: pointer;
      border: solid 1px rgba($color: $primary-color, $alpha: 0.5);
      border-radius: 6px;
      height: 26px;
      color: $primary-color;;
      line-height: 26px;
      text-align: center;
      &:hover {
        background: rgba($color: $primary-color, $alpha: 0.1);
      }
    }
  }
}
.headers {
  background: $primary-color;;
  height: $index-height;
  display: flex;
  align-items: center;
  .lseft {
    cursor: pointer;
  }
  h1 {
    font-size: 22px;
    font-weight: 900;
    color: rgb(255, 255, 255);
    letter-spacing: 2px;
    text-indent: 40px;
  }
}
</style>
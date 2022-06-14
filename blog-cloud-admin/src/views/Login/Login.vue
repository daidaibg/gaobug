<template>
  <div class="login-container flex-center">
    <div id="birds" ref="vantaRef"></div>
    <div class="login-inner">
      <div class="right flex-center">
        <el-form
          class="login-form"
          :model="loginForm"
          status-icon
          :rules="rules"
          ref="loginForm"
          @submit.native.prevent
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              prefix-icon="el-icon-user"
              maxlength="11"
              placeholder="请输入账号"
              autocomplete="on"
              type="text"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              type="password"
              v-model="loginForm.password"
              prefix-icon="el-icon-lock"
              placeholder="请输入密码"
            >
            </el-input>
          </el-form-item>
          <div class="Retrieve">
            <el-link type="primary" href="#/retrieve">忘记密码？</el-link>
          </div>

          <div class="tips">
            <el-button
              size="medium"
              type="primary"
              :loading="loading"
              native-type="submit"
              @click="doLogin"
              >登录
            </el-button>
          </div>
        </el-form>
      </div>
      <LoginSlider
        v-show="codeShow"
        ref="loginSliderRef"
        :randomStr="randomStr"
        @parentHandleSubmit="handleSubmit"
        @close="closecode"
      ></LoginSlider>
    </div>
  </div>
</template>


<script>
import { Login } from "api/login.js";
import { BASE_PREFIX, sysName } from "@/constants";
import LoginSlider from "@/components/login-slider/login-slider.vue";
import { getUUID } from "@/lib/currency";
import * as THREE from 'three';
import BIRDS from "vanta/src/vanta.birds";
export default {
  components: { LoginSlider },
  name: "login",
  data() {
    const validatePassword = (rule, value, callback) => {
      const phoneReg = /^1[3456789]\d{9}$/;
      phoneReg.test(value) ? callback() : callback(new Error("密码格式不正确"));
    };
    return {
      loginForm: {
        username: "",
        password: "",
      },
      LoginSlidershow: true,
      rules: {
        username: [
          { required: true, message: "账号不能为空", trigger: "change" },
          // { validator: validatePhone, trigger: 'blur' }
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          // { validator: validatePassword, trigger: 'blur' }
        ],
      },
      loading: false,
      logoImgUrl: "logoImgUrl",
      sysName: sysName,
      captcha: null,
      randomStr: getUUID(),
      codeShow: false,
    };
  },
  computed: {},
  created() {},
  mounted() {
    this.vantaEffect = BIRDS({
      el: this.$refs.vantaRef,
      mouseControls: true,
      touchControls: true,
      gyroControls: false,
      minHeight: 200.0,
      minWidth: 200.0,
      scale: 1.0,
      scaleMobile: 1.0,
      THREE:THREE
    });
    // VANTA.BIRDS({
    //   el: "#birds",

    // });
  },

  beforeDestroy() {
    if (this.vantaEffect) {
      this.vantaEffect.destroy();
    }
  },
  methods: {
    handleSubmit(code) {
      this.captcha = code;
      this.submit();
    },
    closecode() {
      this.codeShow = false;
    },
    submit() {
      this.loading = true;
      Login({
        username: this.loginForm.username,
        password: this.loginForm.password,
        captcha: this.captcha,
        id: this.randomStr,
      }).then((res) => {
        console.log("login", res);
        // this.$refs.loginSliderRef.refreshImg();
        this.captcha = null;
        if (res.code == 200) {
          this.$refs.loginSliderRef.onSuccess();
          localStorage.setItem("token", res.data.token);
          this.$router
            .push("/")
            .then((routerRes) => {
              this.loading = false;
            })
            .catch((routerErr) => {
              console.log("登录跳转失败", routerErr);
              this.$message.error("内部异常，跳转失败！");
              this.loading = false;
            });
        } else if (res.code == 400) {
          //登陆失败
          this.closecode();
          this.$refs.loginSliderRef.onFail();
        } else if (res.code == 410) {
          //是验证码过期 不关闭验证框要用户请求新的验证码
          this.$refs.loginSliderRef.onFail();
        } else if (res.code == 406) {
          // 验证失败
          this.$refs.loginSliderRef.onFail(false);
        } else {
          this.closecode();
        }
        //不成功时都要提示错误与 关闭loading
        if (res.code !== 200) {
          this.loading = false;
          this.$message.error(res.msg);
        }
      });
    },
    doLogin() {
      this.$refs.loginForm.validate((success, options) => {
        if (success) {
          this.codeShow = true;
          this.$refs.loginSliderRef.init();
        }
      });
    },
    goRegister() {},
  },
};
</script>
<style lang="scss" scoped>
$form-width: 360px;
#birds {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 0;
}
// 表单
.login-form {
  margin: 0 auto;
  width: $form-width;

  .tips {
    text-align: right;

    // padding: 0 60px;
    /deep/ .el-button {
      background: linear-gradient(to bottom, #3373e8, #5695f4);
      height: 50px;
      border-radius: 25px;
      font-size: 20px;
      letter-spacing: 10px;
      border: none;

      .el-icon-loading {
        width: 20px;
        height: 20px;
      }
    }
  }

  .el-form-item {
    // padding: 0 60px;
    margin-bottom: 28px;

    /deep/ .el-form-item__content {
      .el-input {
        .el-input__prefix,
        .el-input__suffix {
          font-size: 18px;
          vertical-align: middle;
        }

        .el-input__prefix {
          width: 40px;
          text-align: center;
        }

        input {
          border-color: transparent;
          background: #f6f7fb;
          height: 50px;
          border-radius: 25px;
          font-size: 16px;
          padding: 0 30px 0 40px;
        }
      }

      .el-form-item__error {
        font-size: 15px;
        line-height: 22px;
        text-indent: 30px;
      }
    }
  }

  button {
    width: 100%;
  }

  .captcha {
    .captcha_inner {
      height: 50px;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    /deep/ .el-form-item__content {
      .el-input {
        width: 65%;
      }

      img {
        width: 100px;
        height: 50px;
        border-radius: 4px;
        cursor: pointer;
      }
    }
  }
}

.left,
.right {
  flex: 1;
  height: 100%;
  // font-family: Source Han Sans CN;
}

.left {
  h1 {
    font-size: 36px;
    font-weight: bold;
    color: #ffffff;
    line-height: 36px;
    width: 100%;
    margin-bottom: 20px;
    text-indent: 47px;
  }
}

.right {
  box-sizing: border-box;
  padding: 0 8px;
}
.login-inner {
  position: relative;
  z-index: 99;
}
@media screen and (max-width: 768px) {
  .login-container {
    background: radial-gradient(
      260px 260px ellipse at 25% 40%,
      #afcaff,
      #467cee
    );
    height: 100%;

    .login-inner {
      margin-top: -120px;
    }

    .left {
      h1 {
        font-size: 22px;
        margin-bottom: 10px;
      }
    }

    .Retrieve {
      margin-bottom: 6px;

      /deep/ .el-link.el-link--primary {
        color: rgba($color: #ffffff, $alpha: 1);
      }
    }
  }
}

@media screen and (min-width: 768px) {
  .login-container {
    // background:  linear-gradient(180deg, #6170FE, #3C52EB);
    min-height: 100%;
    width: 100%;
    // background: url("../../assets/img/login/background.png") no-repeat;
    background-size: 100% 100%;
    justify-content: flex-end;

    .login-inner {
      margin-right: 300px;
      width: 430px;
      // height: 400px;
      height: 320px;
      padding: 16px 0;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      //毛玻璃1 s
      background-color: rgba(180, 180, 180, 0.1);
      font-family: sans-serif;
      text-align: right;
      line-height: 1;
      backdrop-filter: blur(20px); //调他的大小
      //毛玻璃1 e

      .left {
        background: radial-gradient(
          260px 260px ellipse at 25% 40%,
          #afcaff,
          #467cee
        );

        border-radius: 10px 10px 10px 10px;
        min-width: 400px;
      }

      .right {
        // background: rgba(137, 143, 143, 0.1);
        flex-direction: column;
        border-radius: 10px 10px 10px 10px;
      }
    }

    .Retrieve {
      margin-bottom: 18px;

      /deep/ .el-link.el-link--primary {
        color: #e5e5e5;
        font-weight: 100;
      }
    }
  }
}
</style>

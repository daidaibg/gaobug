<script setup lang='ts'>
import LoginForm from "./login-form"
</script>

<template>
  <div class='login '>
    <div class="login_wrap">
      <div></div>
      <login-form></login-form>
    </div>

  </div>
</template>

<style scoped lang='scss'>
.login {
  width: 100%;
  height: 100vh;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: linear-gradient(45deg, rgba(242, 185, 178,1), rgba(20, 145, 168,1), rgba(147, 181, 207,1), rgba(85, 187, 138,1), rgba(226, 216, 73,1));
  background-size: 400%;
  animation: bganimation 15s infinite;

  .login_wrap {
    margin-top: -100px;
    width: 1200px;
    display: flex;
    justify-content: space-between;
  }
}

@keyframes bganimation {
  0% {
    background-position: 0% 50%;
  }

  50% {
    background-position: 100% 50%;
  }

  100% {
    background-position: 0% 50%;
  }
}
</style>
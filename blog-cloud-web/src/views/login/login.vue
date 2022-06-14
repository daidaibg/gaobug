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
  .login_wrap {
    margin-top: -100px;
    width:  1200px;
    display: flex;
    justify-content: space-between;
  }
}
</style>
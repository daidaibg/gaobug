<script setup lang="ts">
import LoginForm from "./login-form";
</script>

<template>
  <div class="login">
    <ul class="bubble-bgwall">
      <li>love</li>
      <li>搞</li>
      <li>love</li>
      <li>bug</li>
      <li>Love</li>
      <li>Love</li>
      <li>爱搞bug</li>
      <li>Love</li>
      <li>搞</li>
      <li>搞bug</li>
    </ul>
    <div class="login_wrap">
      <div></div>
      <login-form></login-form>
    </div>
  </div>
</template>

<style scoped lang="scss">
.login {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  // background-image: linear-gradient(270deg, #af8fca, #7095f3);
      //  rgba(242, 185, 178, 1),
    // rgba(20, 145, 168, 1),
    // rgba(147, 181, 207, 1),
    // rgba(85, 187, 138, 1),
    // rgba(226, 216, 73, 1)
  background-image: linear-gradient(
    45deg,
    var(--yh-error-color-4),
    var(--yh-brand-color-4),
    var(--yh-success-color-4),
    var(--yh-warning-color-4)
  );
  background-size: 400%;
  animation: bganimation 15s infinite;
  
  .login_wrap {
    margin-top: -100px;
    width: 1200px;
    display: flex;
    justify-content: space-between;
    position: relative;
    z-index: 2;
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

.bubble-bgwall {
  overflow: hidden;
  position: absolute;
  margin: 0 auto;
  left: 0;
  top: 0;
  height: 100%;
  width: 100%;
  z-index: 1;
  li {
    display: flex;
    position: absolute;
    bottom: -200px;
    justify-content: center;
    align-items: center;
    border-radius: 10px;
    width: 50px;
    height: 50px;
    background-color: rgba(#fff, 0.35);
    color: var(--yh-text-color-anti);
    animation: bubble 15s infinite;
    &:nth-child(1) {
      left: 10%;
    }
    &:nth-child(2) {
      left: 20%;
      width: 90px;
      height: 90px;
      animation-duration: 7s;
      animation-delay: 2s;
    }
    &:nth-child(3) {
      left: 25%;
      animation-delay: 4s;
    }
    &:nth-child(4) {
      left: 40%;
      width: 60px;
      height: 60px;
      background-color: rgba(#fff, 0.3);
      animation-duration: 8s;
    }
    &:nth-child(5) {
      left: 70%;
    }
    &:nth-child(6) {
      left: 80%;
      width: 120px;
      height: 120px;
      background-color: rgba(#fff, 0.2);
      animation-delay: 3s;
    }
    &:nth-child(7) {
      left: 32%;
      width: 160px;
      height: 160px;
      animation-delay: 2s;
    }
    &:nth-child(8) {
      left: 55%;
      width: 40px;
      height: 40px;
      font-size: 12px;
      animation-duration: 15s;
      animation-delay: 4s;
    }
    &:nth-child(9) {
      left: 25%;
      width: 40px;
      height: 40px;
      background-color: rgba(#fff, 0.3);
      font-size: 12px;
      animation-duration: 12s;
      animation-delay: 2s;
    }
    &:nth-child(10) {
      left: 85%;
      width: 160px;
      height: 160px;
      animation-delay: 5s;
    }
  }
}
@keyframes bubble {
  0% {
    opacity: 0.5;
    transform: translateY(0) rotate(45deg);
  }
  25% {
    opacity: 0.75;
    transform: translateY(-400px) rotate(90deg);
  }
  50% {
    opacity: 1;
    transform: translateY(-600px) rotate(135deg);
  }
  100% {
    opacity: 0;
    transform: translateY(-1000px) rotate(180deg);
  }
}
</style>

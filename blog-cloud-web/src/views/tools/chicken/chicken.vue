<template>
    <!-- https://code.juejin.cn/pen/7194618804104593466 -->
  <div class="chicken">
    <div class="ball">
      <div class="count" v-if="pass === 2">
        倒计时:{{ time }} 运球次数:{{ ballArray.length - 1 }}
      </div>
      <div class="time">练习时长:{{ kunCount }}</div>
      <div v-if="gameState" class="casketball-img">
        <img
          src="./qiu.png"
          v-for="(item, index) in ballArray.length - 1"
          :key="index"
          alt=""
        />
      </div>
    </div>
    <div
      v-show="!mentwo && !menthree"
      :class="{ men: true, jumpjump: jumpstate }"
      :style="`left:${left}px`"
    >
      <div :class="{ head: true, bodybaskit: play }">
        <img
          src="./qiu.png"
          alt=""
          :class="{
            baskitball: true,
            baskitmove: baskitstate,
            baskitleft: baskitgame && pass === 2,
            baskitright: !baskitgame && pass === 2,
          }"
        />
        <div :class="{ faceright: !face }">答</div>
        <div :class="`hear hear1 ${play ? 'hear1shake' : ''}`"></div>
        <div :class="`hear hear2 ${play ? 'hear2shake' : ''}`"></div>
        <div class="body">
          <div class="kuku"></div>
          <div
            :class="{
              hand: true,
              left: true,
              moveleft: movestate,
              zindx: !face,
              handshake1: play,
            }"
          ></div>
          <div
            :class="{
              hand: true,
              right: true,
              moveright: movestate,
              zindx: face,
              handshake2: play,
            }"
          ></div>
          <div
            :class="{
              leg: true,
              left: true,
              moveleft: movestate,
              hear1shake: play,
            }"
          ></div>
          <div
            :class="{
              leg: true,
              right: true,
              moveright: movestate,
              hear2shake: play,
            }"
          ></div>
        </div>
      </div>
    </div>
    <div v-show="mentwo" class="men" style="left: 700px">
      <div :class="['head', legState ? 'headMove' : '']">
        <img
          src="./qiu.png"
          alt=""
          :class="['baskitball', legState ? 'baskit2' : '']"
        />
        <div :class="['faceright', legState ? 'facemove' : '']">答</div>
        <div class="hear hear1"></div>
        <div class="hear hear2"></div>
        <div class="body">
          <div class="kuku kuku2">
            <div class="kubox">
              <div :class="['beidai1', legState ? 'beidai1move' : '']">
                <div class="bb bb1"></div>
                <div class="bb bb2"></div>
              </div>
              <div :class="['beidai2', legState ? 'beidai2move' : '']">
                <div class="bb bb1"></div>
                <div class="bb bb2"></div>
                <div class="bb bb3"></div>
              </div>
            </div>
          </div>
          <div :class="['hand', 'left', 'hand1', legState ? 'hand3move' : '']">
            <div :class="['hand2', legState ? 'hand4move' : '']"></div>
          </div>
          <div :class="['hand', 'right', 'hand1', legState ? 'hand1move' : '']">
            <div :class="['hand2', legState ? 'hand2move' : '']"></div>
          </div>
          <div :class="['leg', 'left', 'leg1', legState ? 'leg1move' : '']">
            <div :class="['leg2', legState ? 'leg2move' : '']"></div>
          </div>
          <div :class="['leg', 'leg1', 'right', legState ? 'leg3move' : '']">
            <div :class="['leg2', legState ? 'leg4move' : '']"></div>
          </div>
        </div>
      </div>
    </div>
    <div v-show="menthree" class="men" style="left: 700px">
      <div
        :class="[
          'head',
          'headThree',
          shakeYState ? 'headShake' : '',
          shakeXState ? 'headMove1' : '',
        ]"
      >
        <div :class="['faceright', 'faceThree']">答</div>
        <div class="hear hear1"></div>
        <div class="hear hear2"></div>
        <div :class="['body', shakeXState ? 'bodyShake' : '']">
          <div class="kuku kuku2">
            <div class="kubox">
              <div :class="['beidai2', 'beidaiThree']">
                <div class="bb bb1"></div>
                <div class="bb bb2"></div>
                <div class="bb bb3"></div>
              </div>
            </div>
          </div>
          <div
            :class="[
              'hand',
              'left',
              'hand1',
              'handThree3',
              shakeXState ? 'handThreeShakeLeft' : '',
            ]"
          >
            <div
              :class="[
                'hand2',
                'handThree4',
                shakeXState ? 'handThreeShakeBottom1' : '',
              ]"
            ></div>
          </div>
          <div
            :class="[
              'hand',
              'right',
              'hand1',
              'handThree1',
              shakeXState ? 'handThreeShakeRight' : '',
            ]"
          >
            <div
              :class="[
                'hand2',
                'handThree2',
                shakeXState ? 'handThreeShakeBottom2' : '',
              ]"
            ></div>
          </div>
          <div
            :class="[
              'leg',
              'left',
              'leg1',
              shakeYState ? 'shakeYtop' : 'legThree1',
            ]"
          >
            <div
              :class="['leg2', 'legThree2', shakeYState ? 'shakeLeg' : '']"
            ></div>
          </div>
          <div
            :class="[
              'leg',
              'leg1',
              'right',
              shakeYState ? 'shakeYdown' : 'legThree3',
            ]"
          >
            <div
              :class="['leg2', 'legThree4', shakeYState ? 'shakeLeg' : '']"
            ></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessageBox } from "element-plus";
const movestate = ref(false); //是否移动
const left = ref(100); //位置
const jumpstate = ref(false); //跳跃状态
const face = ref(false); //面向
const time = ref(10); //计时器
const baskitstate = ref(true); //运球状态
const baskitgame = ref(false); //运球动画状态
const ballArray = reactive<any>([1]); //运球数据
const play = ref(false); //运球身体部分
const kunCount = ref("零年"); //总分数
const level = ref(1); //运球难度
const pass = ref(1); //关卡
const mentwo = ref(false); //第二个小人
const menthree = ref(false); //第三个小人
const shakeXState = ref(false); //X抖动状态
const shakeYState = ref(false); //Y抖动状态
const gameState = ref(false); //游戏状态
const legState = ref(false); //第三关动画
let count: any = ""; //定时器
console.log(1);

onMounted(() => {
  ElMessageBox.alert("先通过awd或者←↑→来熟悉下kunkun吧", "提示", {
    showClose: false,
  }).then(() => {
    gameState.value = true;
    window.addEventListener("keydown", keyclick);
  });
});
//移动监听
function keyclick(e: any) {
  switch (e.key) {
    case "w":
      jump();
      break;
    case "ArrowUp":
      jump();
      break;
    case "a":
      if (!movestate.value) moveleft();
      break;
    case "ArrowLeft":
      if (!movestate.value) moveleft();
      break;
    case "d":
      if (!movestate.value) moveright();
      break;
    case "ArrowRight":
      if (!movestate.value) moveright();
      break;
    default:
  }
}
//左移
function moveleft() {
  face.value = true;
  movestate.value = true;
  if (left.value >= 50 && left.value != 700) {
    left.value = left.value - 100;
  } else {
    removeClick();
  }
  setTimeout(() => {
    movestate.value = false;
  }, 500);
}
//右移
function moveright() {
  face.value = false;
  movestate.value = true;
  if (left.value < 700) {
    left.value = left.value + 100;
  } else {
    removeClick();
  }
  setTimeout(() => {
    movestate.value = false;
  }, 500);
}
//跳跃
function jump() {
  console.log("跳");
  if (!jumpstate.value) {
    jumpstate.value = true;
    setTimeout(() => {
      jumpstate.value = false;
    }, 500);
  }
}
//第一关结束第二关开始
function removeClick() {
  kunCount.value = "半年";
  window.removeEventListener("keydown", keyclick);
  baskitstate.value = false;
  pass.value = 2;
  ElMessageBox.confirm(`坤坤运球挑战，完成10秒左右运球`, "篮球练习", {
    showClose: false,
    confirmButtonText: "简单模式(50次)",
    cancelButtonText: "困难模式(100次)",
  })
    .then(() => {
      level.value = 1;
      start();
    })
    .catch(() => {
      level.value = 2;
      start();
    });
}
//第二关开始
function start() {
  window.addEventListener("keydown", playball);
  count = setInterval(() => {
    time.value--;
    if (time.value === 0) {
      clearInterval(count);
      if (ballArray.length - 1 > level.value * 50) {
        victory();
      } else {
        baskitgame.value
          ? fail(0, "坤坤未完成练习目标,再来一次吧")
          : fail(1, "坤坤未完成目标,再来一次吧");
      }
    }
  }, 1000);
}
//监听运球
function playball(e: any) {
  switch (e.key) {
    case "a":
      ballleft();
      break;
    case "ArrowLeft":
      ballleft();
      break;
    case "d":
      ballright();
      break;
    case "ArrowRight":
      ballright();
      break;
    default:
  }
}
//球左移动
function ballleft() {
  if (ballArray.at(-1) === 1) {
    shake();
    baskitgame.value = true;
    ballArray.push(0);
  } else {
    fail(0);
  }
}
//球右移动
function ballright() {
  if (ballArray.at(-1) === 0) {
    shake();
    baskitgame.value = false;
    ballArray.push(1);
  } else {
    fail(1);
  }
}
//运球动作
function shake() {
  play.value = true;
  setTimeout(() => {
    play.value = false;
  }, 100);
}
//运球失败
function fail(a: any, text = "篮球掉了,重新开始吧") {
  clearInterval(count);
  window.removeEventListener("keydown", playball);
  ElMessageBox.alert(text, "提示", { showClose: false }).then(() => {
    ballArray.length = 0;
    ballArray.push(a);
    time.value = 10;
    start();
  });
}

//运球成功
function victory() {
  clearInterval(count);
  window.removeEventListener("keydown", playball);
  let max: any = localStorage.getItem("maxkun");
  if (ballArray.length - 1 > max) {
    localStorage.setItem("maxkun", (ballArray.length - 1) as any);
  }
  ElMessageBox.alert(
    `挑战成功,练习时长+1！(当前最好成绩为${localStorage.getItem("maxkun")}个)`,
    "提示",
    { confirmButtonText: "继续挑战", showClose: false }
  ).then(() => {
    resetPosition();
    let timeinterval = setInterval(() => {
      ballArray.pop();
      if (ballArray.length === 1) {
        clearInterval(timeinterval);
      }
    }, 50);
    turn();
  });
}
//重置位置
function resetPosition() {
  left.value = 700;
  face.value = false;
  kunCount.value = "一年半";
  baskitstate.value = true;
  pass.value = 3;
  baskitgame.value = false;
}
//转身动画
function turn() {
  setTimeout(() => {
    baskitstate.value = false;
    mentwo.value = true;
    legState.value = true;
    setTimeout(() => {
      mentwo.value = false;
      legState.value = false;
      menthree.value = true;
      startThree();
    }, 3000);
  }, 2000);
}
//第三关开始
function startThree() {
  ElMessageBox.alert("真爱粉检测,完成五次抖肩", "唱跳练习", {
    confirmButtonText: "开始挑战",
    showClose: false,
  }).then(() => {
    ballArray[0] = 1;
    window.addEventListener("keydown", kunshake);
  });
}
//监听抖动
function kunshake(e: any) {
  switch (e.key) {
    case "ArrowUp":
      shakeTop();
      break;
    case "w":
      shakeTop();
      break;
    case "ArrowDown":
      shakeDown();
      if (ballArray.length === 21) {
        endgame();
      }
      break;
    case "s":
      shakeDown();
      if (ballArray.length === 21) {
        endgame();
      }
      break;
    case "ArrowLeft":
      shakeLeft();
      break;
    case "a":
      shakeLeft();
      break;
    case "ArrowRight":
      shakeRight();
      break;
    case "d":
      shakeRight();
      break;
    default:
      break;
  }
}
//上抖
function shakeTop() {
  shakeYState.value = true;
  if (ballArray.at(-1) === 2 && shakeXState.value === true) {
    ballArray.push(3);
  } else {
    ballArray.length = 1;
  }
}
//下抖
function shakeDown() {
  shakeYState.value = false;
  if (ballArray.at(-1) === 4 && shakeXState.value === false) {
    ballArray.push(1);
  } else {
    ballArray.length = 1;
  }
}
//左抖动
function shakeLeft() {
  shakeXState.value = false;
  if (ballArray.at(-1) === 3 && shakeYState.value === true) {
    ballArray.push(4);
  } else {
    ballArray.length = 1;
  }
}
//右抖动
function shakeRight() {
  shakeXState.value = true;
  if (ballArray.at(-1) === 1 && shakeYState.value === false) {
    ballArray.push(2);
  } else {
    ballArray.length = 1;
  }
}
function autoplay() {
  let index = 0;
  count = setInterval(() => {
    switch (index) {
      case 0:
        shakeRight();
        break;
      case 1:
        shakeTop();
        break;
      case 2:
        shakeLeft();
        break;
      case 3:
        shakeDown();
        index = -1;
        break;
      default:
        break;
    }
    index++;
  }, 100);
}
//结束游戏
function endgame() {
  kunCount.value = "两年半";
  gameState.value = false;
  window.removeEventListener("keydown", kunshake);
  ElMessageBox.alert("小黑子漏出鸡脚了吧！music~", "只因你太棒！", {
    confirmButtonText: "鸡你太美~",
    showClose: false,
  }).then(() => {
    ballArray.length = 1;
    autoplay();
  });
}
</script>

<style lang="scss" scoped>
@media screen and (max-height: 800px) {
  .chicken {
    /* transform: scale(0.8); */
  }
}
@media screen and (max-width: 1500px) {
  .chicken {
    transform: scale(0.8);
  }
}
.chicken {
  display: flex;
  width: 1500px;
  height: 700px;
  background-image: url("https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/00b660e0954f40b5b4db00090ab1e14c~tplv-k3u1fbpfcp-watermark.image?");
  background-size: 1500px 700px;
  background-repeat: no-repeat;
  position: relative;
}

.ball {
  font-size: 20px;

  img {
    width: 50px;
  }
}

.casketball-img{
    display: flex;
    flex-wrap: wrap;
}
@keyframes moveleft {
  0% {
    transform: rotate(30deg);
  }

  50% {
    transform: rotate(-30deg);
  }

  100% {
    transform: rotate(30deg);
  }
}

@keyframes moveright {
  0% {
    transform: rotate(-30deg);
  }

  50% {
    transform: rotate(30deg);
  }

  100% {
    transform: rotate(-30deg);
  }
}

@keyframes jumpjump {
  0% {
    top: 325px;
  }

  50% {
    top: 150px;
  }

  100% {
    top: 325px;
  }
}

@keyframes baskit {
  from {
    bottom: -120px;
  }

  to {
    bottom: -200px;
  }
}

@keyframes baskitleft {
  0% {
    left: 60px;
    bottom: -120px;
  }

  50% {
    left: 20px;
    bottom: -200px;
  }

  100% {
    left: -30px;
    bottom: -120px;
  }
}

@keyframes baskitright {
  0% {
    left: -30px;
    bottom: -120px;
  }

  50% {
    left: 20px;
    bottom: -200px;
  }

  100% {
    left: 60px;
    bottom: -120px;
  }
}

.baskitball {
  position: absolute;
  left: 60px;
  bottom: -120px;
  z-index: 99;
}

.baskitmove {
  animation: baskit 0.2s ease-in-out infinite alternate;
}

.baskitleft {
  animation: baskitleft 0.1s ease-in-out;
  animation-fill-mode: forwards;
}

.baskitright {
  animation: baskitright 0.1s ease-in-out;
  animation-fill-mode: forwards;
}

.moveleft {
  animation: moveleft 0.5s linear;
}

.moveright {
  animation: moveright 0.5s linear;
}

.jumpjump {
  animation: jumpjump 0.5s ease-in-out;
}

.faceright {
  user-select: none;
  transform: rotateY(180deg);
  z-index: 9999;
}

.men {
  position: absolute;
  top: 325px;
  left: 120px;
  transition: all 0.5s linear;

  .bodybaskit {
    animation: bodybaskit 0.1s ease-in-out;
  }

  @keyframes bodybaskit {
    0% {
      transform: translateY(0px);
    }

    50% {
      transform: translateY(40px);
    }

    100% {
      transform: translateY(0px);
    }
  }

  .head {
    border-radius: 50%;
    background-color: #000;
    width: 100px;
    height: 100px;
    position: relative;
    text-align: center;
    line-height: 115px;
    color: #fff;
    font-size: 80px;
    font-family: cursive;

    .hear {
      width: 75px;
      height: 30px;

      position: absolute;
      top: -20px;
    }

    .hear1 {
      left: -20px;
      transform: rotate(-35deg);
      transform-origin: 75px 30px;
      z-index: 2;
      background-image: linear-gradient(to right, lightgrey, grey);
    }

    @keyframes hear1shake {
      0% {
        transform: rotate(-35deg);
      }

      50% {
        transform: rotate(0deg);
      }

      100% {
        transform: rotate(-35deg);
      }
    }

    .hear1shake {
      animation: hear1shake 0.1s ease-in-out;
    }

    .hear2 {
      right: -20px;
      transform: rotate(35deg);
      transform-origin: 0 30px;
      background-image: linear-gradient(to right, grey, lightgrey);
    }

    @keyframes hear2shake {
      0% {
        transform: rotate(35deg);
      }

      50% {
        transform: rotate(0deg);
      }

      100% {
        transform: rotate(35deg);
      }
    }

    .hear2shake {
      animation: hear2shake 0.1s ease-in-out;
    }

    .body {
      position: absolute;
      top: 100%;
      left: 50%;
      width: 20px;
      height: 100px;
      background-color: #000;
      transform: translate(-50%, -5%);

      .kuku {
        background-color: darkgray;
        width: 20px;
        height: 50px;
        position: absolute;
        bottom: 0;
        left: 0;
        z-index: 23;

        &::before {
          content: "";
          width: 4px;
          height: 50px;
          position: absolute;
          top: -50px;
          left: 2px;
          z-index: 98;
          background-color: darkgray;
        }

        &::after {
          content: "";
          width: 4px;
          height: 50px;
          position: absolute;
          top: -50px;
          right: 2px;
          z-index: 98;
          background-color: darkgray;
        }
      }

      .hand {
        width: 20px;
        height: 100px;
        background-color: #000;
        position: absolute;
        top: -4px;
        left: 0;
        transform-origin: top;
      }

      .handshake1 {
        animation: handshake1 0.1s ease-in-out;
      }

      .handshake2 {
        animation: handshake2 0.1s ease-in-out;
      }

      @keyframes handshake1 {
        0% {
          transform: rotate(30deg);
        }

        50% {
          transform: rotate(80deg);
        }

        100% {
          transform: rotate(30deg);
        }
      }

      @keyframes handshake2 {
        0% {
          transform: rotate(-30deg);
        }

        50% {
          transform: rotate(-80deg);
        }

        100% {
          transform: rotate(-30deg);
        }
      }

      .leg {
        width: 20px;
        height: 100px;
        background: darkgray;
        position: absolute;
        bottom: -95px;
        left: 0;
        transform-origin: top;
      }

      .left {
        transform: rotate(30deg);
      }

      .right {
        transform: rotate(-30deg);
      }

      .zindx {
        z-index: 99;
      }
    }
  }
}
.hand1 {
  height: 60px !important;
  .hand2 {
    width: 20px;
    height: 50px;
    position: absolute;
    bottom: -40px;
    background-color: #000;
    transform-origin: 10px 0px;
  }
}
.headMove {
  animation: headMove 3s linear;
  animation-fill-mode: forwards;
}
@keyframes headMove {
  83% {
    top: 0;
  }
  100% {
    top: 20px;
  }
}
.headMove1 {
  left: 15px;
}
.kuku2 {
  position: relative;
  .kubox {
    overflow: hidden;
    position: relative;
    width: 20px;
    height: 50px;
    top: -50px;
  }
  &::after {
    content: "";
    display: none;
  }
  &::before {
    content: "";
    display: none;
  }
  .bb {
    background-color: darkgray;
    position: absolute;
  }
  .beidai1 {
    position: absolute;
    // top: -50px;
    .bb1 {
      width: 4px;
      height: 50px;
      left: 2px;
    }
    .bb2 {
      width: 4px;
      height: 50px;
      left: 14px;
    }
  }
  .beidai1move {
    animation: beidai1 3s linear;
    animation-fill-mode: forwards;
  }
  .beidai2move {
    animation-delay: 2s;
    animation: beidai2 3s linear;
    animation-fill-mode: forwards;
  }
  @keyframes beidai1 {
    0% {
      left: 0;
    }
    83% {
      left: 0;
    }
    100% {
      left: 20px;
    }
  }
  @keyframes beidai2 {
    0% {
      left: -40px;
    }
    85% {
      left: -40px;
    }
    100% {
      left: 0;
    }
  }
  .beidai2 {
    position: absolute;
    // top: -50px;
    .bb1 {
      width: 4px;
      height: 25px;
      left: 4px;
      transform: rotate(-25deg);
    }
    .bb2 {
      width: 4px;
      height: 35px;
      left: 8px;
      top: 20px;
    }
    .bb3 {
      width: 4px;
      height: 25px;
      left: 12px;
      transform: rotate(25deg);
    }
  }
}
.leg1 {
  height: 60px !important;
  bottom: -55px !important;
  .leg2 {
    width: 20px;
    height: 50px;
    position: absolute;
    bottom: -40px;
    background-color: darkgray;
    transform-origin: 10px 5px;
  }
}
.leg1move {
  animation: leg1 3s linear;
  animation-fill-mode: forwards;
}
.leg2move {
  animation: leg2 3s linear;
  animation-fill-mode: forwards;
}
.leg3move {
  animation: leg3 3s linear;
  animation-fill-mode: forwards;
}
.leg4move {
  animation: leg4 3s linear;
  animation-fill-mode: forwards;
}
@keyframes leg1 {
  83% {
    transform: rotate(30deg);
  }
  100% {
    transform: rotate(55deg);
  }
}
@keyframes leg2 {
  83% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(-40deg);
  }
}
@keyframes leg3 {
  83% {
    transform: rotate(-30deg);
  }
  100% {
    transform: rotate(-55deg);
  }
}
@keyframes leg4 {
  83% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(40deg);
  }
}
@keyframes hand2 {
  0% {
    transform: rotate(0deg);
  }
  16% {
    transform: rotate(-90deg);
  }
  83% {
    transform: rotate(-90deg);
  }
  100% {
    transform: rotate(90deg);
  }
}
.hand2move {
  animation: hand2 3s linear;
  animation-fill-mode: forwards;
}
.hand1move {
  animation: hand1 3s linear;
  animation-fill-mode: forwards;
}
@keyframes hand1 {
  0% {
    transform: rotate(-30deg);
  }
  16% {
    transform: rotate(-50deg);
  }
  83% {
    transform: rotate(-50deg);
  }
  100% {
    transform: rotate(-40deg);
  }
}
@keyframes hand3 {
  83% {
    transform: rotate(30deg);
  }
  100% {
    transform: rotate(50deg);
  }
}
@keyframes hand4 {
  83% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(-100deg);
  }
}
.hand3move {
  animation: hand3 3s linear;
  animation-fill-mode: forwards;
}
.hand4move {
  animation: hand4 3s linear;
  animation-fill-mode: forwards;
}
@keyframes baskit2 {
  0% {
    left: 70px;
    bottom: -120px;
  }
  16% {
    left: 95px;
    bottom: -10px;
    transform: rotate(0deg);
  }
  66% {
    left: 95px;
    bottom: -10px;
    transform: rotate(1440deg);
  }
  100% {
    left: 750px;
    bottom: 480px;
    transform: rotate(3600deg);
    visibility: hidden;
  }
}
.baskit2 {
  animation: baskit2 3s ease-in-out;
  animation-fill-mode: forwards;
}
.facemove {
  transform-origin: center center 40px;
  animation: facemove 3s linear;
  animation-fill-mode: forwards;
}
@keyframes facemove {
  0% {
    transform: rotateY(180deg);
  }
  16% {
    transform: rotateY(200deg);
  }
  83% {
    transform: rotateY(200deg);
  }
  100% {
    transform: rotateY(270deg);
  }
}
.headThree {
  top: 20px !important;
}
.faceThree {
  transform: rotateY(270deg) !important;
}
.beidaiThree {
  left: 0 !important;
}
.handThree1 {
  transform: rotate(-40deg) !important;
}
.handThree2 {
  transform: rotate(90deg) !important;
}
.handThree3 {
  transform: rotate(50deg) !important;
}
.handThree4 {
  transform: rotate(-100deg) !important;
}
.legThree1 {
  transform: rotate(55deg) !important;
}
.legThree2 {
  transform: rotate(-40deg) !important;
}
.legThree3 {
  transform: rotate(-55deg) !important;
}
.legThree4 {
  transform: rotate(40deg) !important;
}
.shakeLegLeft {
  transform: rotate(30deg) !important;
}
.shakeLegRight {
  transform: rotate(-30deg) !important;
}
.shakeLeg {
  transform: rotate(0deg) !important;
}
.headShake {
  top: 0px !important;
}
.bodyShake {
  transform: translate(-75%, -5%) skew(-8deg, 0deg) !important;
}

.handThreeShakeLeft {
  transform: rotate(40deg) !important;
}
.handThreeShakeRight {
  transform: rotate(-55deg) !important;
}
.handThreeShakeBottom1 {
  transform: rotate(-110deg) !important;
}
.handThreeShakeBottom2 {
  transform: rotate(110deg) !important;
}
</style>

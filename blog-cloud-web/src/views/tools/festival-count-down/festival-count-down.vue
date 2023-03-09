<script setup lang="ts">
import { getPublic } from "@/api";
import { reactive, onBeforeUnmount } from "vue";
import CountDown from "./festival-count-down-item.vue";
const state = reactive<any>({
  jjrList: [],
  currentYear: null,
});
let jjrDate: any = [];
let timer: any = null;
onBeforeUnmount(() => {
  clearInterval(timer);
});

const getjjr = () => {
  const date = new Date();
  let current_time = date.getTime();
  state.currentYear = date.getFullYear(); //获取完整的年份(4位)
  let newJjrDate = [];
  for (let i = 0; i < jjrDate.length; i++) {
    let tgt_date = new Date(jjrDate[i].date + " 00:00:00");
    let target_time = tgt_date.getTime();
    let result_time: any = target_time - current_time;
    if (result_time != "" && result_time > 0) {
      newJjrDate.push({
        ...jjrDate[i],
        timestamp: getInfo(result_time),
      });
    }
  }
  state.jjrList = newJjrDate;
};
const getInfo = (times: any) => {
  let day = Math.floor(times / 1000 / 60 / 60 / 24);
  let hour = Math.floor((times / 1000 / 60 / 60) % 24);
  let min = Math.floor((times / 1000 / 60) % 60);
  let second = Math.floor((times / 1000) % 60);
  let obj = {
    day: day < 10 ? "0" + day : day,
    hour: hour < 10 ? "0" + hour : hour,
    min: min < 10 ? "0" + min : min,
    second: second < 10 ? "0" + second : second,
  };
  return obj;
};
const xbdjs = () => {
  getjjr();
  timer = setInterval(() => {
    // console.time("aaa");

    getjjr();
    // console.timeEnd("aaa");
  }, 1000 - 0.12); //减去代码运行时间
};
const getData = () => {
  getPublic("/json/jjr.json").then((res) => {
    console.log(res);
    jjrDate = res;
    xbdjs();
  });
};

getData();
</script>

<template>
  <div class="festival-count-down">
    <ul class="jjrlist">
      <li v-for="(item, index) in state.jjrList" :key="index" class="jjrlistli">
        <p class="jjr_title">2023年{{ item.name }}倒计时</p>
        <p class="jjy_date">{{ item.date }}</p>
        <CountDown :timestamp="item.timestamp" />
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
.festival-count-down {
  width: 100%;
  box-sizing: border-box;
  padding: 16px 10px;
  .jjrlistli {
    display: flex;
    height: 60px;
    line-height: 60px;
    width: 90%;
    background-color: rgba(39, 137, 255, 0.1);
    margin: 0 auto;
    height: 240px;
    border-radius: 16px;
    margin-bottom: 32px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    .jjr_title {
      text-align: center;
      font-size: 32px;
      color: #212529;
      // color: #2789FF;
    }
    .jjy_date {
      color: #555;
      font-size: 24px;
    }
  }

  .gooffwork {
    width: 100%;
    font-size: 40px;
    justify-content: center;
  }
}
</style>

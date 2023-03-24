<template>
  <div class="work-calendar">
    <!-- <div>下一月</div>
    <div>上一月</div> -->
    <div class="calendar-box">
      <div class="calendar-wrap" v-if="swiperShow">
        <swiper
          :effect="'coverflow'"
          :grabCursor="true"
          :centeredSlides="true"
          :initialSlide="27"
          :slidesPerView="'auto'"
          :preventInteractionOnTransition="true"
          :coverflowEffect="{
            rotate: 50,
            stretch: 0,
            depth: 100,
            modifier: 1,
            slideShadows: true,
          }"
          :loop="false"
          :pagination="false"
          :modules="modules"
          @activeIndexChange="activeIndexChange"
          @swiper="setControlledSwiper"
          class="mySwiper"
        >
          <SwiperSlide
            v-for="item in calendarList"
            :key="item.id"
            class="swiper-slide-li"
          >
            <Calendar
              class="calendar-li"
              :year="item.year"
              :month="item.month"
              :today="todayYearMonthDay"
              :jjrDates="newJjrDate"
              :ruleDate="ruleDate"
              v-if="item"
            />
            <div v-else></div>
          </SwiperSlide>
        </swiper>
      </div>
    </div>

    <div class="add-rule">
      <i class="yh-icons-circle-plus" @click="addRule"></i>
    </div>
    <el-drawer
      v-model="drawerVisible"
      direction="rtl"
      :with-header="false"
      size="300px"
      :before-close="draweHandleClose"
    >
      <h3 class="rule-title">请设定规则</h3>
      <el-form :model="ruleDate" label-width="90px" size="default">
        <el-form-item label="最近值班：">
          <el-date-picker
            v-model="ruleDate.nearDate"
            type="date"
            placeholder="请选择最近日期"
          />
        </el-form-item>
        <el-form-item label="排班数量：">
          <el-select v-model="ruleDate.dateNum" placeholder="排班数量">
            <el-option :label="item" :value="item" v-for="item in 66" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="draweHandleClose">取消</el-button>
          <el-button type="primary" @click="onSubmit">确认</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>
</template>

<script lang="ts" setup>
import { reactive, toRaw, ref, nextTick } from "vue";
import Calendar from "./calendar.vue";
import { guid } from "@/utils/current";
import { Swiper, SwiperSlide } from "swiper/vue";
// import required modules
import { EffectCoverflow } from "swiper";
import "swiper/css";
import "swiper/css/effect-coverflow";
import "swiper/css/pagination";
import dayjs from "dayjs";
import { ElMessage } from "element-plus";
import jjrDate from "./jjr.json";

let newJjrDate: any = {};
jjrDate.forEach((item) => {
  let formattedDate = item.date_cn
    .replace("年", "-")
    .replace("月", "-")
    .replace("日", "");
  const dateTime = new Date(formattedDate).getTime() + "";
  // console.log(dateStr, formattedDate, item.date, dateTime, new Date(item.date));
  newJjrDate[dateTime] = {
    ...item,
    dateTime,
    name:
      item.holiday_cn.length > 2
        ? item.holiday_cn.replace("中国", "")
        : item.holiday_cn,
  };
});

// console.log(jjrDate, newJjrDate);

const modules = [EffectCoverflow];
const calendarList = ref<any>([]);
const swiperRef = ref();
const todayDate = new Date();
const todayDateDay = todayDate.getDate();
const drawerVisible = ref(false);
const ruleDate = reactive({
  dateNum: "",
  nearDate: "",
  nearDateFormat: "",
  nearDateTime: "",
});
const swiperShow = ref(false);

//今天年月日
const todayData = getDateYearMonthDay(todayDate);
const todayYearMonthDay = {
  ...todayData,
  day: todayDate.getDate(),
  date: `${todayData.year}-${
    todayData.month < 10 ? "0" + todayData.month : todayData.month
  }-${todayDateDay < 10 ? "0" + todayDateDay : todayDateDay}`,
};

function getDateYearMonthDay(date: Date) {
  return {
    year: date.getFullYear(),
    month: date.getMonth() + 1,
    id: guid(),
  };
}
const setControlledSwiper = (a: any) => {
  // console.log(a);
  swiperRef.value = a;
};


const activeIndexChange = (a: any) => {
  // console.log(a, a.swipeDirection);
  if (!a.swipeDirection) return;
  if (a.swipeDirection == "next") {
    if (a.activeIndex >= calendarList.value.length - 3) {
      calendarList.value.push(
        getNextOrPrevDate(
          calendarList.value[calendarList.value.length - 1],
          "next"
        )
      );
    }
  } else {
    // console.log(  calendarList.value[a.activeIndex], calendarList.value[a.activeIndex-1],calendarList.value[a.activeIndex-2],);
    if (!calendarList.value[a.activeIndex - 2]) {
      calendarList.value[a.activeIndex - 2] = getNextOrPrevDate(
        calendarList.value[a.activeIndex - 1],
        "prev"
      );
      calendarList.value[a.activeIndex - 3] = getNextOrPrevDate(
        calendarList.value[a.activeIndex - 2],
        "prev"
      );
      calendarList.value[a.activeIndex - 4] = getNextOrPrevDate(
        calendarList.value[a.activeIndex - 3],
        "prev"
      );
    }
    // nextTick(() => {
    //   //
    //   swiperRef.value.slideReset()
    //   swiperRef.value.update();
    //   swiperRef.value?.updateSlides();
    // });
  }
  // console.log(swiperRef.value);
};

const addRule = () => {
  drawerVisible.value = true;
};

const onSubmit = () => {
  if (!ruleDate.nearDate) {
    ElMessage.error("请选择日期");
    return;
  }
  if (!ruleDate.dateNum) {
    ElMessage.error("请输入天数");
    return;
  }
  ruleDate.nearDateFormat = dayjs(ruleDate.nearDate).format("YYYY-MM-DD");
  ruleDate.nearDateTime = new Date(ruleDate.nearDate).getTime() + "";
  console.log(toRaw(ruleDate));
  localStorage.setItem("paibanDate", JSON.stringify(ruleDate));
  swiperShow.value = false;
  nextTick(() => {
    init();
    
  });

  ElMessage.success("添加成功");
  draweHandleClose();
};

const draweHandleClose = () => {
  drawerVisible.value = false;
};

const getNextOrPrevDate = (item: any, type: string = "next") => {
  const month = item.month - 1;
  if (type == "next") {
    // 获取当前月份的下一个月
    return getDateYearMonthDay(new Date(item.year, month + 1, 1));
  }
  // 获取当前月份的上一个月
  return getDateYearMonthDay(new Date(item.year, month - 1, 1));
};

const init = () => {
  const prev1 = getNextOrPrevDate(todayYearMonthDay, "prev");
  const prev2 = getNextOrPrevDate(prev1, "prev");
  const prev3 = getNextOrPrevDate(prev2, "prev");
  // const prev4 = getNextOrPrevDate(prev2, "prev3");
  // const prev5 = getNextOrPrevDate(prev2, "prev4");
  // const prev6 = getNextOrPrevDate(prev2, "prev5");
  // const prev7 = getNextOrPrevDate(prev2, "prev6");

  const next1 = getNextOrPrevDate(todayYearMonthDay, "next");
  const next2 = getNextOrPrevDate(next1, "next");
  const next3 = getNextOrPrevDate(next2, "next");

  calendarList.value = [
    ...new Array(24).fill(false),
    prev3,
    prev2,
    prev1, //上个月
    todayYearMonthDay, //当前月
    next1, //下个月
    next2,
    next3,
  ];
  swiperShow.value = true;

};

let paibanDate:any=localStorage.getItem("paibanDate")
if(paibanDate){
  paibanDate=JSON.parse(paibanDate);
  ruleDate.nearDateFormat=paibanDate.nearDateFormat;
  ruleDate.dateNum=paibanDate.dateNum;
  ruleDate.nearDateTime=paibanDate.nearDateTime;
  ruleDate.nearDate=paibanDate.nearDate;
}
init();
</script>

<style lang="scss" scoped>
.work-calendar {
  width: 100%;
  height: 300px;
  padding: 16px 4px;
  max-width: 600px;
  margin: 0 auto;
}
.calendar-box {
  width: 100%;
  position: relative;
}
.calendar-wrap {
  display: flex;
  width: auto;
  // overflow: hidden;
  .calendar-li {
    width: 100%;
    flex-shrink: 0;
  }
  :deep(.swiper-slide) {
    .swiper-slide-shadow-left,
    .swiper-slide-shadow-right {
      border-radius: 8px;
    }
  }
}
.add-rule {
  position: fixed;
  right: 20px;
  bottom: 50px;
  font-size: 60px;
  color: var(--yh-brand-color);
}
.rule-title {
  margin-bottom: 24px;
  text-align: center;
  font-size: 18px;
  font-weight: 900;
}
</style>

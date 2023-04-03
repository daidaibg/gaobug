<template>
  <div class="calendar">
    <div class="month">{{ monthName }} {{ year }}</div>
    <div class="days-header">
      <div class="day-name" v-for="(day, index) in weekDays" :key="index">
        {{ day }}
      </div>
    </div>
    <div class="days">
      <div
        class="dayli"
        v-for="(day, index) in days"
        :key="index"
        :class="day.classNames"
      >
        {{ day.number }}
        <div class="pbr" v-if="day.dutyFalg">值</div>
        <div class="tiaoxiu" v-else-if="day.isOvertimeDay">调</div>
        <template v-if="day.isHoliDay">
          <div class="jjr">休</div>
          <div class="jjr-name" v-if="day.holiDay.holiday_today == 1">
            {{ day.holiDay.holiday_cn }}
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, watch, toRaw } from "vue";
import dayjs from "dayjs";

const props = defineProps({
  year: {
    type: Number,
    required: true,
  },
  month: {
    type: Number,
    required: true,
  },
  today: {
    type: Object,
    required: true,
  },
  jjrDates: {
    type: Object,
    default: () => {},
  },
  ruleDate: {
    type: Object,
    default: () => {},
  },
});

const weekDays = ["一", "二", "三", "四", "五", "六", "日"];
const monthNames = [
  "一月",
  "二月",
  "三月",
  "四月",
  "五月",
  "六月",
  "七月",
  "八月",
  "九月",
  "十月",
  "十一月",
  "十二月",
];

const days = ref<any>([]);

const monthName = computed(() => {
  return monthNames[props.month - 1];
});

function dayClass(date: string): any {
  if (!date) return;

  const todayTime = new Date(props.today.date).getTime() + "";
  // const todayTime = new Date("2023-04-01").getTime() + "";

  const currentDayTime = new Date(date).getTime() + "";
  //日期数据
  let rqData = {
    classNames: "", //class
    holiDay: false, //是否是节假日或则调休
    dutyFalg: false, // 是否是值班日期
    isHoliDay: false, //是否是节假日
    isOvertimeDay: false, //是否是调休
  };
  //最近值班日期 与 排班周期
  const { nearDateFormat: ruleDate, dateNum: ruleNum } = props.ruleDate;
  //是否是节假日
  const holiday = props.jjrDates[currentDayTime];

  //workday 1调休 2节假日
  if (holiday && holiday.workday == 1) {
    rqData.isOvertimeDay = true;
  } else if (holiday && holiday.workday == 2) {
    rqData.isHoliDay = true;
  }

  //如果有值班日期和排班周期
  if (ruleDate != "" && ruleNum != "") {
    const currentDate = dayjs(date);

    // if ("2023-04-05" == date) {
    //   console.log(date, rqData,holiday);
    // }
    //周六周日、节假日中的值班日期
    if (
      ([6, 0].includes(currentDate.day()) && !holiday) ||
      (holiday && rqData.isHoliDay)
    ) {
      const diffInDays = dayjs(ruleDate).diff(currentDate, "day");
      //并且是排班周期的倍数
      if (diffInDays % ruleNum === 0) {
        rqData.dutyFalg = true;
      }
    }
  }

  //今天之前的日期 今天 今天之后的日期
  if (props.today.date < todayTime) {
    rqData.classNames = "prevDay";
  } else if (currentDayTime == todayTime) {
    rqData.classNames = "currentyDay";
  } else {
    rqData.classNames = "nextDay";
  }

  //值班日期
  if (rqData.dutyFalg) {
    rqData.classNames = rqData.classNames + " dutyDay";
  }
  //调休
  if (rqData.isOvertimeDay) {
    rqData.classNames = rqData.classNames + " overtimeDay";
  }
  //节假日
  if (rqData.isHoliDay) {
    rqData.holiDay = holiday;
    rqData.classNames = rqData.classNames + " holiDay";
  }

  return rqData;
}

const initMonthDay = () => {
  // 获取当前月份的天数
  const daysInMonth = new Date(props.year, props.month, 0).getDate();
  // 获取当前月份第一天是星期几（0代表星期天，1代表星期一，以此类推）
  let firstDayOfMonth = new Date(props.year, props.month - 1, 1).getDay();
  if (firstDayOfMonth == 0) {
    firstDayOfMonth = 7;
  }
  firstDayOfMonth--;

  // console.log(daysInMonth,props);
  const daysArray = new Array(daysInMonth).fill(0).map((_, index) => {
    const newIndex = index + 1;
    const date = `${props.year}-${
      props.month < 10 ? "0" + props.month : props.month
    }-${newIndex < 10 ? "0" + newIndex : newIndex}`;

    const rqData = dayClass(date);
    return {
      number: newIndex,
      date: date,
      ...rqData,
    };
  });

  const offsetDays = new Array(firstDayOfMonth).fill({});

  days.value = [...offsetDays, ...daysArray];
  // console.log(toRaw(days.value));
};

initMonthDay();
</script>

<style lang="scss">
.calendar {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.month {
  font-weight: bold;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

$grid-gap-px: 4px;
.days-header {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-gap: $grid-gap-px;
  width: 100%;
  margin-bottom: 0.5rem;
  .day-name {
    text-align: center;
    color: var(--yh-text-color-secondary);
  }
}

.days {
  display: grid;
  grid-gap: $grid-gap-px;
  grid-template-columns: repeat(7, 1fr);
  width: 100%;
}

.dayli {
  text-align: center;
  border-radius: 4px;
  margin-bottom: 4px;
  padding: 8px 0;
  position: relative;
  //今天之前
  &.prevDay {
    color: var(--yh-text-color-secondary);
  }

  //节假日
  &.holiDay {
    background-color: var(--yh-success-color-2);
    color: var(--yh-success-color);
  }
  &:nth-child(7n),
  &:nth-child(7n-1) {
    color: var(--yh-success-color);
  }
  //值班日
  &.dutyDay {
    // background-color: var(--yh-error-color-2);
    // color: var(--yh-error-color);

    background-color: var(--yh-warning-color-2);
    color: var(--yh-warning-color);
  }
  //调休日
  &.overtimeDay {
    background-color: var(--yh-error-color-2);
    color: var(--yh-error-color);
  }
  //当天
  &.currentyDay {
    // background-color: var(--yh-brand-color);
    // color: var(--yh-text-color-anti);

    background-color: var(--yh-brand-color-4);

    color: var(--yh-brand-color);
    &::before {
      content: "";
      position: absolute;
      width: 100%;
      height: 100%;
      left: 0;
      top: 0;
      background: url("../../../assets/img/tool/calendar-bg.jpg") no-repeat;
      background-size: 100% auto;
      mix-blend-mode: multiply;
    }
    .pbr {
      // color: var(--yh-error-color-4);
      color: var(--yh-warning-color);
    }
  }

  .jjr {
    position: absolute;
    left: 2px;
    top: 2px;
    font-size: 10px;
    color: var(--yh-success-color-4);
    line-height: 1;
  }
  .jjr-name {
    position: absolute;
    bottom: -1px;
    left: 0;
    width: 100%;
    text-align: center;
    line-height: 1;
    font-size: 16px;
    color: var(--yh-text-color-primary);
    transform: scale(0.625);
  }
  .pbr,
  .tiaoxiu {
    position: absolute;
    right: 2px;
    top: 2px;
    font-size: 10px;
    // color: var(--yh-error-color);
    line-height: 1;
  }
  .tiaoxiu {
    color: var(--yh-error-color);
  }
  .pbr {
    color: var(--yh-warning-color);
  }
}
</style>

<script setup lang="ts">
import CountUp from "@/components/count-up";

import { ref } from "vue";

const homeInfo = ref<any>({
  userNum: 921,
  wzNum: 1998,
  browseNum: 28931,
  onUserNum: 520,
});
const infoOverViews: any = [
  {
    title: "用户总数",
    icon: "gb-icon-s_yonghuliebiao",
    class: "user-num",
    per: "+14%",
    valueId: "userNum",
  },
  {
    title: "文章总数",
    icon: "gb-icon-fabuwenzhang",
    class: "wz-num",
    per: "+28%",
    valueId: "wzNum",
  },
  {
    title: "文章浏览量",
    icon: "gb-icon-statistics-full",
    class: "browse-num",
    per: "+60%",
    valueId: "browseNum",
  },
  {
    title: "在线人数",
    icon: "gb-icon-zaixianyonghu",
    class: "on-user-num",
    per: "+20%",
    valueId: "onUserNum",
  },
];
</script>

<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col
        :sm="12"
        :lg="6"
        v-for="item in infoOverViews"
        :key="item.valueId"
      >
        <div class="small-panel suspension" :class="item.class">
          <div class="small-panel-title">
            {{ item.title }}
          </div>
          <div class="small-panel-content">
            <div class="content-left">
              <i class="icon" :class="item.icon"></i>
              <count-up
                :start-val="0"
                :end-val="homeInfo[item.valueId]"
              ></count-up>
            </div>
            <div class="content-right">{{ item.per }}</div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.small-panel {
  background-color: var(--yh-bg-color-container);
  border-radius: var(--border-radius);
  transition: all 0.3s ease;
  padding: 24px;
  margin-bottom: 20px;
  // box-shadow: var(--yh-shadow-2);
  .small-panel-title {
    color: var(--yh-text-color-placeholder);
    font-size: 15px;
  }
  .small-panel-content {
    display: flex;
    align-items: flex-end;
    margin-top: 20px;
    color: var(--yh-text-color-primary);
    .content-left {
      font-size: 24px;
      display: flex;
      align-items: center;
      .icon {
        margin-right: 10px;
        font-size: 20px;
      }
      .gb-icon-s_yonghuliebiao {
        color: rgb(133, 149, 244);
      }
      .gb-icon-zaixianyonghu{
         color: rgb(173, 133, 244)
      }
      .gb-icon-fabuwenzhang{
        color: rgb(116, 168, 181);
      }
      .gb-icon-statistics-full{
        color: rgb(244, 133, 149);
      }
      span {
        display: inline-block;
        font-size: 28px;
      }
    }
    .content-right {
      font-size: 18px;
      margin-left: auto;
    }
  }
}
.suspension:hover {
  transform: translateY(-4px) scale(1.02);
  box-shadow: var(--yh-shadow-3);
  z-index: 999;
  border-radius: var(--border-radius);
}
</style>

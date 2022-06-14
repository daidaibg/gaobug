<!--
 * @Author: daidai
 * @Date: 2021-12-28 14:07:42
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-17 17:53:34
 * @FilePath: \web-pc\src\views\statistics\surroundings-statistics\surrounddings-echart.vue
-->
<template>
  <div class="charts_item" ref="charts"></div>
</template>

<script>
import { HjHistory, ZdHistoryChartOp } from "./echarts-option";
export default {
  data() {
    return {
      chart: null,
      index: null,
    };
  },
  created() {},

  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.chart = echarts.init(this.$refs.charts);
    },
    setOption(xData, yData,name,Company) {
        // console.log(xData, yData,name,Company)
      HjHistory(this.chart, xData, yData,name,Company);
    },
    setZdOption(xData, yData,name, Company,index,) {
        // console.log(xData, yData,name, Company,index,Company)
      if (index==undefined) {
        index = this.index;
      } else {
        this.index = index;
      }

      ZdHistoryChartOp(this.chart, xData, yData,name, index,Company);
    },
    resize() {
      this.chart && this.chart.resize();
    },
  },
};
</script>
<style lang='scss' scoped>
.charts_item {
  width: 100%;
  height: 100%;
}
</style>
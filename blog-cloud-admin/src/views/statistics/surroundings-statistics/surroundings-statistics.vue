<!--
 * @Author: daidai
 * @Date: 2021-12-27 11:01:02
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-30 17:10:53
 * @FilePath: \web-pc\src\views\statistics\surroundings-statistics\surroundings-statistics.vue
-->
<template>
  <div class="contents hjsjfx">
    <div class="search-form">
      <div class="search-form-item">
        <label>网关ID:</label>
        <el-input
          size="small"
          v-model="dataForm.gatewayno"
          clearable
          placeholder=""
        ></el-input>
      </div>
      <div class="search-form-item time">
        <label>时间选择:</label>
        <el-date-picker
          @change="timeschange"
          size="small"
          value-format="yyyy-MM-dd HH:mm:ss"
          v-model="times"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          :picker-options="pickerOptions"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </div>

      <div class="action flex-center">
        <el-button
          size="mini"
          @click="search"
          clearable
          :loading="loading"
          type="primary"
          >查询</el-button
        >
        <el-button size="mini" @click="reset"> 重置</el-button>
      </div>
    </div>
    <div class="chart-wrap">
      <div class="hjcsqx" v-loading="loading == 'loading'">
        <h2 class="left_h2">{{ wgTypeData.ID }}环境参数曲线(网关)</h2>
        <div class="type_item_wrap flex justify-end">
          <div
            v-for="item in typeList"
            :key="item.value"
            class="type_item"
            :class="{ typeActive: item.value == wgTypeData.type }"
            @click="switchWgType(item)"
          >
            {{ item.label }}
          </div>
        </div>
        <el-empty
          :description="wgTypeData.loadingText"
          v-if="wgTypeData.loadingType == 'Nodata'"
        ></el-empty>
        <Charts ref="wgchart" class="chart_item" v-else />
      </div>

      <!-- 终端 -->
      <ul class="zd-chart-wrap flex flex-wrap justify-between">
        <li v-for="(item, key) in zdList" :key="key">
          <h2 class="left_h2">{{ item.terminalno }}(终端)</h2>
          <div class="type_item_wrap flex flex-center">
            <div
              v-for="inneritem in zdtypeList"
              :key="inneritem.value"
              class="type_item"
              :class="{ typeActive: item.type == inneritem.value }"
              @click="switchType(key, inneritem, item)"
            >
              {{ inneritem.label }}
            </div>
          </div>
          <el-empty
            :description="item.loadingText"
            v-if="item.loadingType == 'Nodata'"
          ></el-empty>
          <Charts :ref="'zdChart' + key" class="chart_item" />
        </li>
        <li class="kong" v-if="JSON.stringify(zdList) != '{}'"></li>
      </ul>
    </div>
    <yh-table-pag
      :data="tableData"
      @getData="getData"
      :total="total"
      style="width: 100%"
      class="my_table"
      :row-class-name="rowClassName"
      ref="myTable"
      :loading="loading"
      loading-background="rgba(255, 255, 255, 0.4)"
      size="small"
      max-height="500"
      :currentPage="pageData.current"
    >
      <el-table-column prop="number" label="序号" width="56" align="center">
        <template slot-scope="scope">
          <div>
            {{ scope.$index + 1 + (pageData.current - 1) * pageData.size }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gatewayno" label="网关ID" align="center">
      </el-table-column>
      <el-table-column
        prop="poleName"
        label="温度"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="companyName"
        label="太阳能板电压"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="realName"
        label="电池电压"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="createtime"
        label="创建时间"
        align="center"
      ></el-table-column>
    </yh-table-pag>

    <yh-table-pag
      :data="tableData"
      @getData="getData"
      :total="total"
      style="width: 100%"
      class="my_table"
      :row-class-name="rowClassName"
      ref="myTable"
      :loading="loading"
      loading-background="rgba(255, 255, 255, 0.4)"
      size="small"
      max-height="500"
      :currentPage="pageData.current"
    >
      <el-table-column prop="number" label="序号" width="56" align="center">
        <template slot-scope="scope">
          <div>
            {{ scope.$index + 1 + (pageData.current - 1) * pageData.size }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gatewayno" label="终端ID" align="center">
      </el-table-column>
      <el-table-column prop="gatewayno" label="相线" align="center">
      </el-table-column>
      <el-table-column
        prop="poleName"
        label="温度"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="companyName"
        label="太阳能板电压"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="realName"
        label="电池电压"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="createtime"
        label="创建时间"
        align="center"
      ></el-table-column>
    </yh-table-pag>
    <div style="height: 5px"></div>
  </div>
</template>

<script>
import { shortcuts } from "@/lib/currency";
import Charts from "./surrounddings-echart.vue";
import DDmoment from "@/lib/dd-moment";
import {
  GetyGatewayCurveDataList,
  QueryTerminalCurveDataList,
} from "api/modules/sjzx";
export default {
  components: { Charts },
  data() {
    return {
      dataForm: {
        gatewayno: "",
        startTime: DDmoment()
          .subtract(1, "weeks")
          .format("yyyy-MM-dd HH:mm:ss"),
        endTime: DDmoment().format("yyyy-MM-dd HH:mm:ss"),
      },
      pickerOptions: {
        shortcuts: shortcuts,
        disabledDate: function (time) {
          return (
            time.getTime() > Date.now() ||
            time.getTime() < DDmoment().subtract(1, "months").getTime()
          );
        },
      },
      loading: false,
      total: 0,
      tableData: [],
      pageData: {
        current: 1,
        size: 10,
      },
      times: [
        DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
        DDmoment().format("yyyy-MM-dd HH:mm:ss"),
      ],
      wgTypeData: {
        type: 1,
        Company: "℃",
        ID: "",
        loadingType: "Nodata",
        loadingText: "暂无数据",
      },
      zdList: {
        // 9001: {
        //   type: 1,
        // },
        // 9002: { type: 1, Company: "℃" },
        // 9003: { type: 1, Company: "℃" },
        // 9004: { type: 1, Company: "℃" },
        // 9005: { type: 1, Company: "℃" },
        // 9006: { type: 1, Company: "℃" },
        // 9007: { type: 1, Company: "℃" },
      },
      typeList: [
        {
          value: 1,
          label: "温度",
          Company: "℃",
        },
        {
          value: "2",
          label: "太阳能板电压",
          Company: "V",
        },
        {
          value: "3",
          label: "电池电压",
          Company: "V",
        },
      ],
      zdtypeList: [
        {
          value: 4,
          label: "温度",
          Company: "℃",
        },
        {
          value: 5,
          label: "太阳能板电压",
          Company: "V",
        },
        {
          value: 6,
          label: "电池电压",
          Company: "V",
        },
      ],
    };
  },
  created() {},

  mounted() {
    this.init();
    window.addEventListener("resize", this.resize);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resize);
    console.log("%c清除监听图表变化 ", "color: #67C23A;");
  },
  methods: {
    getData() {},
    //切换网关 类型
    switchWgType(typeData) {
      //   console.log(typeData);
      this.wgTypeData.type = typeData.value;
      this.wgTypeData.Company = typeData.Company;
      this.getWgData();
    },
    //切换终端 类型
    switchType(zdId, typeData, zdData) {
      // console.log(zdId,typeData)
      this.zdList[zdId].type = typeData.value;
      this.zdList[zdId].Company = typeData.Company;
      this.getZdData(typeData.value, zdId);
    },

    init() {
      //1111222
      this.getWgData();
      this.getZdData(this.zdtypeList[0].value);
    },
    //获取网关数据
    getWgData(callback) {
		  if (!this.times) {
		  this.$message({
			  message: '请选择时间范围',
			  type: 'warning'
		  });
		  return
      }
      this.wgTypeData.ID = this.dataForm.gatewayno;
      this.wgTypeData.loadingType = "loading";
      GetyGatewayCurveDataList({
        ...this.dataForm,
        itemCode: this.wgTypeData.type,
      }).then((res) => {
        // console.log(res);
        if (res.success) {
          if (res.data.length > 0) {
            this.wgTypeData.loadingType = "true";
            let data = this.handleWgData(res.data);
            this.$nextTick(() => {
              this.$refs.wgchart.setOption(
                data.xData,
                data.yData,
                this.wgTypeData.ID,
                this.wgTypeData.Company
              );
            });
          } else {
            this.wgTypeData.loadingType = "Nodata";
            this.wgTypeData.loadingText = "暂无数据！";
          }
        } else {
          this.wgTypeData.loadingType = "Nodata";
          this.wgTypeData.loadingText = res.msg;
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    //获取终端信息
    getZdData(itemCode, zdID = "") {
      if (!this.times) {
		  this.$message({
			  message: '请选择时间范围',
			  type: 'warning'
		  });
		  return
      }
      QueryTerminalCurveDataList({
        ...this.dataForm,
        deviceId: zdID,
        itemCode: itemCode,
      }).then((res) => {
        console.log(res);
        if (res.success) {
          if (zdID != "") {
            let data = res.data[0];
            if (data.dataList.length > 0) {
              this.zdList[zdID].loadingType = "true";
              let xyData = this.handleWgData(data.dataList);
              this.$nextTick(() => {
                this.$refs["zdChart" + zdID] &&
                  this.$refs["zdChart" + zdID][0].setZdOption(
                    xyData.xData,
                    xyData.yData,
                    this.zdList[zdID].terminalno,
                    this.zdList[zdID].Company
                  );
              });
            } else {
              this.zdList[zdID].loadingType = "Nodata";
            }
          } else {
            let arr = res.data;
            if (arr.length == 0) {
              return;
            }
            let typeData = this.typeList[0];
            let Obj = {};
            for (let i = 0; i < arr.length; i++) {
              const element = arr[i];
              Obj[element.deviceId] = {
                type: itemCode,
                Company: typeData.Company,
                loadingType: "Nodata",
                terminalno: element.terminalno,
                loadingText: "暂无数据！",
              };
            }
            this.zdList = Obj;
            for (let i = 0; i < arr.length; i++) {
              const element = arr[i];
              if (element.dataList.length > 0) {
                this.zdList[element.deviceId].loadingType = "true";
                let xyData = this.handleWgData(element.dataList);
                this.$nextTick(() => {
                  this.$refs["zdChart" + element.deviceId] &&
                    this.$refs["zdChart" + element.deviceId][0].setZdOption(
                      xyData.xData,
                      xyData.yData,
                      element.terminalno,
                      this.typeList[0].Company,
                      i
                    );
                });
              }
            }
          }
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    // 处理网关数据
    handleWgData(list) {
      let xData = [];
      let yData = [];
      for (let i = 0; i < list.length; i++) {
        const element = list[i];
        xData.push(element.uploadtime);
        yData.push(element.datavalue);
      }
      return {
        xData,
        yData,
      };
    },

    timeschange(val) {
      if (val) {
        this.dataForm.startTime = val[0];
        this.dataForm.endTime = val[1];
      } else {
        this.dataForm.startTime = "";
        this.dataForm.endTime = "";
      }
    },
    resize() {
      this.$refs.wgchart && this.$refs.wgchart.resize();
      //   for (const key in this.zdList) {
      //     if (Object.hasOwnProperty.call(this.zdList, key)) {
      //       let ref = "zdChart" + key;
      //       this.$refs[ref][0].resize();
      //     }
      //   }
    },
    randomArr() {
      let arr = [];
      for (let index = 0; index < 20; index++) {
        arr.push(Math.floor(Math.random() * 500 + index * 50));
      }
      return arr;
    },
    search() {
      this.init();
    },
    reset() {
      this.times = [
        DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
        DDmoment().format("yyyy-MM-dd HH:mm:ss"),
      ];
      this.init();
    },
  },
};
</script>
<style lang='scss' scoped>
$shodow-current: 0px 2px 8px 0px rgba(45, 45, 46, 0.1);
$margin-bottom: 15px;
.hjsjfx {
  border-radius: 8px;
  &-top {
    height: 100%;
  }
}
.search-form {
  margin-bottom: $margin-bottom;
}
.chart-wrap {
  flex: 1;
}
.hjcsqx {
  background: #fff;
  margin-bottom: $margin-bottom;
  box-shadow: $shodow-current;
  border-radius: 8px;
  height: 32vh;
  min-height: 340px;
  position: relative;
  .type_item_wrap {
    position: absolute;
    top: 8px;
    width: 100%;
    right: 30px;
    height: 20px;
    line-height: 20px;
  }
}
.left_h2 {
  position: absolute;
  left: 0;
  top: 0;
}

.zd-chart-wrap {
  li {
    .left_h2 {
      text-align: center;
      width: 100%;
    }
    position: relative;
    background: #fff;
    min-height: 290px;
    margin-bottom: $margin-bottom;
    box-shadow: $shodow-current;
    border-radius: 8px;
    width: 32.6%;
    height: 30vh;

    .type_item_wrap {
      position: absolute;
      top: 36px;
      width: 100%;
      left: 0;
      height: 20px;
    }
    &.kong {
      background: transparent;
      box-shadow: none;
    }
  }
}

.type_item_wrap {
  .type_item {
    font-size: 14px;
    font-weight: 400;
    color: #333232;
    line-height: 9px;
    margin: 0 4px;
    height: 100%;
    line-height: 20px;
    padding: 0px 8px;
    cursor: pointer;
    z-index: 1;
  }
  .typeActive {
    background: #1890ff;
    box-shadow: 0px 4px 6px 0px rgba(97, 142, 255, 0.82);
    border-radius: 14px;
    color: #fff;
  }
}
.my_table {
  box-shadow: $shodow-current;
  margin-bottom: $margin-bottom;
}
</style>

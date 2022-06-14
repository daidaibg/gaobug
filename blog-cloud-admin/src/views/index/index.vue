<template>
  <div class="flex position-re contents rdx-font-14" ref="index_ref">
    <div id="container" class="map-size" @click="searchHide"></div>
    <!-- 		<div class="position-ab map-explain">
			未标注经纬度的设备不在该地图显示!
		</div> -->
    <div class="position-ab flex" style="top: 20px; left: 20px">
      <el-input
        placeholder="请输入网关ID(未标注经纬度的设备不在该地图显示!)"
        v-model="val.searchKey"
        size="mini"
        @keyup.enter.native="getSearch"
      ></el-input>
      <div class="search-button" @click="getSearch">搜索</div>
    </div>
    <div
      class="position-ab flex flex-direction bg-white"
      v-if="show.searchShow"
      style="top: 62px; left: 20px"
    >
      <div
        class="flex search align-center line-bottom"
        v-for="(searchS, index) in getData.searchList.list"
        :key="index"
        @click="onAddress(index)"
      >
        <img
          :src="searchS.imgUrlList[0].filePath"
          v-if="searchS.imgUrlList.lenght > 0"
          class="rdx-radius-4"
          style="width: 40px; height: 40px"
        />
        <img
          src="../../assets/img/no_head.png"
          else
          class="rdx-radius-4"
          style="width: 40px; height: 40px"
        />
        <div class="flex rdx-flex-1 justify-between rdx-p-l-10 align-center">
          <div class="flex flex-direction">
            <span class="font-strong rdx-font-14">{{ searchS.poleName }}</span>
            <span class="rdx-font-14 rdx-line-height-26">{{
              searchS.gatewayno
            }}</span>
          </div>
          <div
            class="yh-icons-finished rdx-font-18"
            :class="searchS.onlinestate == 1 ? 'color-green' : 'color-9'"
          ></div>
        </div>
      </div>
      <div class="flex justify-center rdx-p-tb-4">
        <el-pagination
          :page-size="val.size"
          :pager-count="5"
          layout="total,prev, pager, next"
          :total="getData.searchList.totalCount"
          @current-change="changeCurrent"
        >
        </el-pagination>
      </div>
    </div>
    <div class="position-ab" style="right: 10px; top: 20px">
      <div class="flex flex-direction rdx-p-15 right-box">
        <div class="flex title_header">
          <div class="right-tigs"></div>
          <span class="rdx-font-15 rdx-p-l-4 font-strong">设备及告警看板</span>
          <i
            class="el-icon-d-arrow-right"
            @click="showHideSbgj()"
            :style="{
              transform: show.showSbgj ? 'rotate(-90deg)' : 'rotate(90deg)',
            }"
          ></i>
        </div>
        <div :class="{ sbkbHide: !show.showSbgj }" class="sbkbShow">
          <div class="right-item flex-direction rdx-m-t-10 position-re">
            <div class="flex flex-direction">
              <span class="rdx-font-14 font-strong">网关设备数</span>
            </div>
            <div class="flex align-center rdx-p-t-12">
              <span class="rdx-font-22" style="color: #3a8cfd">{{
                getData.gatewayData.gatewayTotalNum ||0
              }}</span>
              <span class="rdx-font-16 rdx-p-l-10">台</span>
            </div>
            <div class="flex align-center rdx-p-t-10">
              <span class="rdx-font-12 rdx-p-r-6">在线网关数</span>
              <span class="right-item-number rdx-font-14 rdx-p-lr-8"
                >{{ getData.gatewayData.gatewayOnlineNum||0 }}台</span
              >
            </div>
            <div class="flex align-center rdx-p-t-10">
              <span class="rdx-font-12 rdx-p-r-6">离线网关数</span>
              <span class="right-item-number rdx-font-14 rdx-p-lr-8"
                >{{ getData.gatewayData.gatewayOfflineNum ||0}}台</span
              >
            </div>
            <div
              ref="gateway"
              class="position-ab"
              style="width: 150px; height: 160px; right: -5px; top: 0px"
            ></div>
            <div
              class="position-ab flex flex-direction align-center"
              style="z-index: 10; right: 41px; top: 65px"
            >
              <span
                style="font-weight: bold; font-size: 16px"
                v-if="
                  getData.gatewayData.gatewayOnlineNum &&
                  getData.gatewayData.gatewayTotalNum
                "
                >{{
                  (
                    (getData.gatewayData.gatewayOnlineNum /
                      getData.gatewayData.gatewayTotalNum) *
                    100
                  ).toFixed(2)
                }}%</span
              >
              <span style="font-weight: bold; font-size: 16px" v-else>0%</span>
              <span class="rdx-line-height-20 color-remark">在线率</span>
            </div>
          </div>

          <div class="right-item2 flex-direction rdx-m-t-10 position-re">
            <div class="flex flex-direction">
              <span class="rdx-font-14 font-strong">终端设备数</span>
            </div>
            <div class="flex align-center rdx-p-t-12">
              <span class="rdx-font-22" style="color: #53dca2">{{
                getData.gatewayData.terminalTotalNum||0
              }}</span>
              <span class="rdx-font-16 rdx-p-l-10">台</span>
            </div>
            <div class="flex align-center rdx-p-t-10">
              <span class="rdx-font-12 rdx-p-r-6">在线终端数</span>
              <span class="right-item-number2 rdx-font-14 rdx-p-lr-8"
                >{{ getData.gatewayData.terminalOnlineNum ||0}}台</span
              >
            </div>
            <div class="flex align-center rdx-p-t-10">
              <span class="rdx-font-12 rdx-p-r-6">离线终端数</span>
              <span class="right-item-number2 rdx-font-14 rdx-p-lr-8"
                >{{ getData.gatewayData.terminalOfflineNum ||0}}台</span
              >
            </div>
            <div
              ref="device"
              class="position-ab"
              style="width: 150px; height: 160px; right: -5px; top: 0px"
            ></div>
            <div
              class="position-ab flex flex-direction align-center"
              style="z-index: 10; right: 41px; top: 65px"
            >
              <span
                style="font-weight: bold; font-size: 16px"
                v-if="
                  getData.gatewayData.terminalOnlineNum &&
                  getData.gatewayData.terminalTotalNum
                "
                >{{
                  (
                    (getData.gatewayData.terminalOnlineNum /
                      getData.gatewayData.terminalTotalNum) *
                    100
                  ).toFixed(2)
                }}%</span
              >
              <span style="font-weight: bold; font-size: 16px" v-else>0%</span>
              <span class="rdx-line-height-20 color-remark">在线率</span>
            </div>
          </div>
          <div class="right-item3 flex justify-between align-center rdx-m-t-10">
            <span class="font-strong" style="line-height: 22px">告警总数</span>
            <div class="flex align-end">
              <span class="rdx-font-22" style="color: #f81223">{{
                getData.gatewayData.alarmNum||0
              }}</span>
              <span class="rdx-font-14 rdx-p-l-10">台</span>
            </div>
            <span class="font-strong" style="line-height: 22px">今日告警</span>
            <div class="flex align-end">
              <span class="rdx-font-22" style="color: #f81223">{{
                getData.gatewayData.todayAlarmNum || 0
              }}</span>
              <span class="rdx-font-14 rdx-p-l-10">台</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 底部告警列表 -->
    <div
      class="position-ab device-warn-hide flex"
      @click="
        () => {
          show.bottomList = true;
        }
      "
    >
      <i class="el-icon-s-operation"></i>
      设备及告警列表
    </div>
    <div
      class="position-ab device-warn-list"
      :class="{ bottomshow: show.bottomList }"
    >
      <div class="flex flex-direction rdx-p-t-10 rdx-m-t-10 bottom-box">
        <div class="flex rdx-p-lr-15 rdx-m-b-6 device-warn-list_title">
          <div class="right-tigs"></div>
          <span class="rdx-font-15 rdx-p-l-4 font-strong">设备及告警列表</span>
          <i
            class="el-icon-close"
            @click="
              () => {
                show.bottomList = false;
              }
            "
          ></i>
        </div>
        <yh-table-pag
          :data="getData.alertList.list"
          style="width: 100%"
          layout="total, prev, pager, next"
          @getData="getAleat"
          :total="getData.alertList.totalCount"
          size="mini"
          :loading="loading"
          class="minitable"
          height="160"
        >
          <el-table-column
            align="center"
            prop="gatewayno"
            label="网关ID"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="terminalno"
            label="终端ID"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="lineName"
            label="线路"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="poleName"
            label="杆号"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="phase"
            label="相线"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="alertname"
            label="报警类型"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="alertdetail"
            label="报警内容"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="createtime"
            label="报警时间"
          ></el-table-column>

          <!-- 					<el-table-column align="center" label="处理状态">
						<template slot-scope="{ row }">
							{{row.processstate == 0 ? '未处理' : '已处理'}}
						</template>
					</el-table-column> -->
        </yh-table-pag>
      </div>
    </div>

    <!-- 设备详情 -->
    <!-- 		<div class="position-fixed"></div> -->
    <div
      class="flex flex-direction home-item-show"
      ref="homeShow"
      v-if="show.detailShow"
    >
      <div
        class="
          home-item-show-title
          flex
          justify-between
          align-center
          rdx-p-lr-20
        "
        @mousedown="mousedown"
      >
        <div class="flex align-center">
          <i class="yh-icons-document color-primary rdx-font-20"></i>
          <span class="rdx-p-l-6 font-strong">设备详情</span>
        </div>
        <i
          class="yh-icons-close color-remark button-pointer"
          @click="onClose"
        ></i>
      </div>
      <div class="flex flex-direction home-item-box " style="padding:20px 10px 20px 20px">
        <div class="flex">
          <div
            class="
              flex flex-direction
              position-re
              align-center
              rdx-m-r-60
              button-pointer
            "
            @click="onTab('1')"
          >
            <span :class="val.showIndex == 1 ? 'color-primary' : ''"
              >网关设备详情</span
            >
            <span
              class="border-bottom rdx-m-t-10"
              v-show="val.showIndex == 1"
            ></span>
          </div>
          <div
            class="flex flex-direction position-re align-center button-pointer"
            @click="onTab('2')"
          >
            <span :class="val.showIndex == 2 ? 'color-primary' : ''"
              >终端设备详情</span
            >
            <span
              class="border-bottom rdx-m-t-10"
              v-show="val.showIndex == 2"
            ></span>
          </div>
        </div>
        <div
          class="flex flex-direction"
          style="height: 560px"
          v-if="val.showIndex == 1"
        >
          <div class="flex line-bottom rdx-p-b-16 rdx-m-t-20">
            <div class="flex align-center">
              <div class="right-tigs"></div>
              <span class="rdx-p-l-6">网关台账</span>
            </div>
          </div>
          <div class="flex flex-wrap rdx-p-l-10">
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">网关ID</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.gatewayno
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">线路名称</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.lineName
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">电话卡号</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.simno
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">创建时间</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.uploadtime
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">杆号</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.poleName
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">在线状态 </span>
              <span class="color-remark">{{
                getData.gatewayDataShow.onlinestate == 0 ? "离线" : "在线"
              }}</span>
            </div>
          </div>
          <div class="flex line-bottom rdx-p-b-16 rdx-m-t-30">
            <div class="flex align-center">
              <div class="right-tigs"></div>
              <span class="rdx-p-l-6">最近告警信息</span>
            </div>
          </div>
          <div class="flex flex-wrap rdx-p-l-10">
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">告警类型</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.alertData == null
                  ? "--"
                  : getData.gatewayDataShow.alertData.alertname
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">告警内容</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.alertData == null
                  ? "--"
                  : getData.gatewayDataShow.alertData.alertdetail
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">是否短信通知</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.alertData == null
                  ? "--"
                  : getData.gatewayDataShow.alertData.ismessage == 0
                  ? "否"
                  : "是"
              }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-20">
              <span class="rdx-p-b-10">告警时间</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.alertData == null
                  ? "--"
                  : getData.gatewayDataShow.alertData.createtime
              }}</span>
            </div>
          </div>
          <div class="flex line-bottom rdx-p-b-16 rdx-m-t-30">
            <div class="flex align-center">
              <div class="right-tigs"></div>
              <span class="rdx-p-l-6">实时数据</span>
               <span class="color-remark" style="font-size:12px">&nbsp;&nbsp;{{getData.gatewayDataShow.uploadtime}}</span>
            </div>
          </div>
          <div class="flex flex-wrap rdx-p-l-10">
        
            <div
              class="home-w30 flex flex-direction rdx-p-t-20"
              v-for="(realS, index2) in getData.realData"
              :key="index2"
            >
              <span class="rdx-p-b-10">{{ realS.itemname }}</span>
              <span class="color-remark">{{
                getData.gatewayDataShow.data && getData.gatewayDataShow.data[realS.itemcode] |montionFilter
              }}</span>
            </div>
          </div>
        </div>
        <div
          class="flex flex-direction"
          style="overflow-y: auto; height: 560px"
          v-if="val.showIndex == 2"
        >
          <div
            class="flex flex-wrap line-bottom rdx-p-b-20"
            v-for="(terminalS, index4) in getData.terminalData"
            :key="index4"
          >
            <div class="home-w30 flex flex-direction rdx-p-t-10">
              <span class="rdx-p-b-4">终端ID</span>
              <span class="color-remark">{{ terminalS.terminalno }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-10">
              <span class="rdx-p-b-4">相线</span>
              <span class="color-remark">{{ terminalS.phase }}</span>
            </div>
            <div class="home-w30 flex flex-direction rdx-p-t-10">
              <span class="rdx-p-b-4">终端通信状态</span>
              <span class="color-remark">{{
                terminalS.onlinestate == 0 ? "离线" : "在线"
              }}</span>
            </div>
            <div
              class="home-w30 flex flex-direction rdx-p-t-10"
              v-for="(realS, index3) in getData.zdjcsjData"
              :key="index3"
            >
              <span class="rdx-p-b-4">{{ realS.itemname }}</span>
              <span class="color-remark">{{
                terminalS.data && terminalS.data[realS.itemcode] |montionFilter
              }}</span>
            </div>
            <div
              class="home-w30 flex flex-direction rdx-p-t-10"
       
            >
              <span class="rdx-p-b-4">上传时间</span>
              <span class="color-remark" style="font-size:12px">{{
                terminalS.uploadtime || "--"
              }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Drag } from "yhht-ui/src/util/current.js";
import {
  searchApi,
  gatewayApi,
  gatewayShowApi,
  realShowApi,
  mapMarkersApi,
  alertdataApi,
  terminalShowApi,
} from "api/modules/home";
export default {
  data() {
    return {
      map: null,
      markerlayer: null,
      infoWindow: null,
      total: 0,
      loading: false,
      getData: {
        LabelsData: [], //地图标记点
        searchList: "", //搜索结果
        gatewayData: "", //看板
        gatewayDataShow: "", //详情--网关
        realData: [], //详情--实时数据 温度 湿度...
        zdjcsjData:[],//终端的 实时数据。。。
        terminalData: [], //详情--终端
        alertList: "", //设备及告警列表
        markerList: [], //marker点列表
      },
      val: {
        searchKey: "",
        address: [], //地图地址
        deviceId: "", //设备id
        page: 1, //分页页数
        size: 7, //分页条数
        showIndex: "1", //详情tab 1网关设备详情 2终端设备详情
      },
      show: {
        searchShow: false, //弹出搜索框
        detailShow: false, //详情弹窗
        showSbgj: true, //设备告警看板
        bottomList: false, //设备及告警列表
      },
    };
  },
  mounted() {
    this.gatewayCharts = echarts.init(this.$refs.gateway);
    this.deviceCharts = echarts.init(this.$refs.device);
    this.getGatewayData();
    // this.getMarker()
    this.getAleat();
    this.getInit();
    this.getMarker();
  },
  methods: {
    // 收缩设备告警看板
    showHideSbgj() {
      this.show.showSbgj = !this.show.showSbgj;
    },
    //网关 终端详情
    onTab(index) {
      this.val.showIndex = index;
    },
    //设备及告警列表
    getAleat() {
      this.loading = false;
      alertdataApi({
        size: this.val.size,
        current: this.val.aleatPage,
      }).then((res) => {
        if (res.success) {
          this.getData.alertList = res.data;
        } else {
          this.$message(res.msg);
        }
      });
    },
    //关闭详情
    onClose() {
      this.show.detailShow = false;
    },
    //控制移动
    mousedown(e) {
      return Drag(e, this.$refs.homeShow);
    },
    //隐藏
    searchHide() {
      this.show.searchShow = false;
    },
    //点击搜索结果返回地址
    onAddress(index) {
      let data = this.getData.searchList.list[index];
      this.val.address = [data.x, data.y];
      this.show.searchShow = false;
      this.val.deviceId = data.id; //传id用于判断地图缩放
      //构建信息窗体中显示的内容
      var info = [];
      info.push(
        "<p class='font-strong rdx-line-height-28 rdx-font-14'>所属公司:" +
          data.companyName +
          "</p>"
      );
      info.push(
        "<p class='rdx-line-height-28 rdx-font-14'>线路名称:" +
          data.poleName +
          "</p>"
      );
      info.push(
        "<p class='rdx-line-height-28 rdx-font-14'>网关ID:" +
          data.gatewayno +
          "</p>"
      );

      this.infoWindow = new AMap.InfoWindow({
        content: info.join(""),
      });
      this.infoWindow.open(this.map, this.val.address);
      this.map.setZoomAndCenter(15, this.val.address);
    },
    //获取搜索数据
    getSearch() {
      searchApi({
        size: this.val.size,
        current: this.val.page,
        gatewayno: this.val.searchKey,
        homeSearch: 1, //判断是home页面搜索,传回过滤掉没有经纬度的list
      }).then((res) => {
        this.show.searchShow = true;
        if (res.success) {
          this.getData.searchList = res.data;
        } else {
          this.$message(res.msg);
        }
      });
    },
    //分页
    changeCurrent(e) {
      this.val.page = e;
      this.getSearch();
    },
    //获取数据
    getMarker() {
      var iconNo = {
        type: "image",
        image: require("../../assets/img/home_no.png"),
        size: [27, 37],
        anchor: "center",
      };
      var iconOk = {
        type: "image",
        image: require("../../assets/img/home_yes.png"),
        size: [27, 37],
        anchor: "center",
      };
      mapMarkersApi({}).then((res) => {
        if (res.success) {
          this.getData.markerList = res.data;
          this.getData.markerList.forEach((item, index) => {
            if (item.x != null && item.y != null) {
              let icon = iconNo;
              if (item.onlinestate == 1) {
                icon = iconOk;
              }
              this.getData.LabelsData.push({
                poleName: item.poleName,
                deviceId: item.id,
                zooms: [4, 20],
                zIndex: index,
                position: [item.x, item.y],
                icon: icon,
              });
            }
          });
          this.setMarker();
          //   this.val.address = this.getData.LabelsData[0].position; //地图初始范围
        } else {
          this.$message(res.msg);
        }
      });
    },
    //渲染marker点
    setMarker() {
      //   console.log(this.getData.LabelsData);
      var markers = [];
      // 初始化 labelMarker
      for (var i = 0; i < this.getData.LabelsData.length; i++) {
        var curData = this.getData.LabelsData[i];
        curData.extData = {
          index: i,
        };
        var labelMarker = new AMap.LabelMarker(curData);
        markers.push(labelMarker);
        var my = this;
        labelMarker.on("click", function (e) {
          my.$nextTick(() => {
            my.val.showIndex = 1;
            my.val.deviceId = e.target._opts.deviceId;
            my.getGatewayShow();
          });
        });
      }
      // 将 marker 添加到图层
      this.markerlayer.add(markers);
      //自动缩放地图到合适的视野级别
      this.map.setFitView(null, false, [100, 150, 10, 10]);
    },
    //初始化地图
    getInit() {
      this.map = new AMap.Map("container", {
        // zoom: 15.8,
        // center: this.val.address,
        showIndoorMap: false,
        viewMode: "3D",
      });
      this.markerlayer = new AMap.LabelsLayer({
        zIndex: 1000,
        // collision: false,
      });
      // 图层添加到地图
      this.map.add(this.markerlayer);
    },
    //获取详情
    getGatewayShow() {
      this.show.detailShow = true;
      this.$nextTick(() => {
        this.$refs.homeShow.style.top =
          this.$refs.index_ref.offsetTop + 10 + "px";
        this.$refs.homeShow.style.left =
          this.$refs.index_ref.offsetLeft + 10 + "px";
      });
      gatewayShowApi({
        deviceId: this.val.deviceId,
      }).then((res) => {
        if (res.success) {
          this.getData.gatewayDataShow = res.data;
        } else {
          this.$message(res.msg);
        }
      });
      realShowApi({
        deviceType: 1, //1：网关，2：终端
      }).then((res) => {
        if (res.success) {
          console.log("type", this.getData.realData);
          this.getData.realData = res.data;
        } else {
          this.$message(res.msg);
        }
      });

      realShowApi({
        deviceType: 2, //1：网关，2：终端
      }).then((res) => {
        if (res.success) {
          this.getData.zdjcsjData = res.data;
        } else {
          this.$message(res.msg);
        }
      });
      terminalShowApi({
        deviceId: this.val.deviceId,
      }).then((res) => {
        if (res.success) {
          this.getData.terminalData = res.data;
          console.log("zhong", this.getData.terminalData);
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    //获取右边图表数据
    getGatewayData() {
      gatewayApi({}).then((res) => {
        if (res.success) {
          console.log(res)
          this.getData.gatewayData = res.data;
          this.getGateway();
          this.getDevice();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    //网关初始图表
    getGateway() {
      var option = {
        color: ["#3A8CFD", "#FFFFFF"],
        tooltip: {
          trigger: "item",
          backgroundColor: "rgba(0,0,0,0.7)",
          textStyle: {
            color: "#ffffff",
          },
        },
        series: [
          {
            name: "在线率",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
            },
            data: [
              {
                value: this.getData.gatewayData.gatewayOnlineNum,
                name: "在线网关",
              },
              {
                value: this.getData.gatewayData.gatewayOfflineNum,
                name: "离线网关",
              },
            ],
          },
        ],
      };
      this.$nextTick(() => {
        this.gatewayCharts.clear();
        this.gatewayCharts.setOption(option);
      });
    },
    //终端设备数初始图表
    getDevice() {
      var option = {
        color: ["#53DCA2", "#FFFFFF"],
        tooltip: {
          trigger: "item",
          backgroundColor: "rgba(0,0,0,0.7)",
          textStyle: {
            color: "#ffffff",
          },
        },
        series: [
          {
            name: "在线率",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
            },
            data: [
              {
                value: this.getData.gatewayData.terminalOnlineNum,
                name: "在线终端数",
              },
              {
                value: this.getData.gatewayData.terminalOfflineNum,
                name: "离线终端数",
              },
            ],
          },
        ],
      };
      this.$nextTick(() => {
        this.deviceCharts.clear();
        this.deviceCharts.setOption(option);
      });
    },
  },
};
</script>
<style lang='scss' scoped>
@import "./index.scss";
</style>

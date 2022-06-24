<!--
 * @Author: daidai
 * @Date: 2021-12-20 10:38:26
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-16 16:15:05
 * @FilePath: \web-pc\src\views\tzgl\device\device-lnglat.vue
-->
<template>
  <el-dialog
    v-dialogDrag
    :title="title"
    append-to-body
    :visible.sync="visible"
    width="880px"
    :close-on-click-modal="false"
    class="lnglats"
  >
    <div class="inputs">
      <el-input
        v-model="keyword"
        placeholder=""
        @input="search"
        size="mini"
        @focus="tipsshow = true"
      ></el-input>
      <ul v-show="tipsshow">
        <li
          @click="onitem(item)"
          v-for="(item, i) in tips"
          :key="i"
          :title="item.name + '(' + item.district + ')'"
        >
          <i>{{ i + 1 }}、</i>
          {{ item.name }}
          <span>({{ item.district }})</span>
        </li>
      </ul>
    </div>
    <div class="latlngtext">
      经度：<span>{{ lanlat.lng }}</span
      >&nbsp; 纬度：<span>{{ lanlat.lat }}</span>
    </div>
    <div id="map" ref="map"></div>
    <span class="dialog-footer">
      <el-button @click="visible = false" size="mini">取 消</el-button>
      <el-button type="primary" @click="subItem" size="mini" :loading="loading"
        >保 存</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      title: "获取坐标",
      visible: false,
      loading: false,
      map: null,
      lanlat: {},
      autoComplete: null,
      keyword: "",
      tips: [],
      tipsshow: false,
    };
  },
  created() {},

  mounted() {},
  beforeDestroy() {
    this.destroyMap();
  },
  methods: {
    init(x, y) {
      this.visible = true;
      //   console.log(this.lanlat);
      this.$nextTick(() => {
        // console.log(this.$refs);
        let center = null;
        if (x && y) {
          center = [x, y];
        }
        console.log(center);
        if (!this.map) {
          // console.log("开图")
          this.map = new AMap.Map("map", {
            viewMode: "3D", // 默认使用 2D 模式，如果希望使用带有俯仰角的 3D 模式，请设置 viewMode: '3D',
            zoom: 11, //初始化地图层级
            center: center, //初始化地图中心点
          });
          this.mapEvent();

          var options = {
            enableHighAccuracy: true, //是否使用高精度定位，默认:true
            timeout: 10000, //超过10秒后停止定位，默认：5s
            position: "LB", //定位按钮的停靠位置
            buttonOffset: new AMap.Pixel(10, 20), //定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            zoomToAccuracy: true, //定位成功后是否自动调整地图视野到定位点
          };

          this.PlaceSearch();
          AMap.plugin(["AMap.Geolocation"], () => {
            var geolocation = new AMap.Geolocation(options);
            this.map.addControl(geolocation);
            geolocation.getCurrentPosition((status, result) => {
              //   console.log(status, result);
              if (x && y) {
                this.onitem({
                  location: {
                    lng: x,
                    lat: y,
                  },
                });
              }
              if (status == "complete") {
                this.Geolocationpostion = result.position;
                if (x && y) {
                } else {
                  this.lanlat = result.position;
                  this.setMarker();
                }
                document
                  .querySelector(".amap-geolocation-marker")
                  .addEventListener("click", this.addclickGeolocation);
              } else {
                // onError(result);
                this.$message({
                  message:
                    "定位失败,失败原因排查信息:" +
                    result.message +
                    "</br>浏览器返回信息：" +
                    result.originMessage,
                  type: "error",
                });
              }
            });
          });
        }
      });
    },
    onitem(item) {
      console.log(item);
      this.tipsshow = false;
      this.lanlat = {
        lat: item.location.lat,
        lng: item.location.lng,
      };
      this.setMarker();
      this.map.setCenter([item.location.lng, item.location.lat]); //设置地图中心点
      //   this.map.setZoom(18); //设置地图层级
    },
    // 搜索提示
    search() {
      if (this.keyword == "") {
        this.tips = [];
        return;
      }
      this.autoComplete.search(this.keyword, (status, result) => {
        // console.log(status, result);
        if (status == "complete") {
          this.tips = result.tips;
        } else if (status == "no_data") {
          this.tips = [];
        } else {
          this.$message.error("未知异常，查询失败！");
        }
      });
    },
    PlaceSearch() {
      let _this = this;
      var autoOptions = {
        city: "全国",
      };
      AMap.plugin(["AMap.PlaceSearch", "AMap.AutoComplete"], () => {
        _this.autoComplete = new AMap.AutoComplete(autoOptions);
        // console.log(_this.autoComplete);
      });
    },
    //监听点击定位点
    addclickGeolocation() {
      this.lanlat = this.Geolocationpostion;
      this.setMarker();
    },
    destroyMap() {
      this.map && this.map.destroy();
      console.error("地图已销毁");
    },

    mapEvent() {
      this.map.on("click", (e) => {
        this.lanlat = {
          lat: e.lnglat.getLat(),
          lng: e.lnglat.getLng(),
        };
        this.setMarker();
        // console.log(this.lanlat);
      });
    },
    setMarker() {
      if (!this.marker) {
        let size = new AMap.Size(20, 26);
        this.marker = new AMap.Marker({
          zIndex: 99,
          icon: new AMap.Icon({
            image:
              "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
            size: size,
            imageSize: size,
          }),
          position: [this.lanlat.lng, this.lanlat.lat],
          offset: new AMap.Pixel(-10, -23),
        });

        this.marker.setMap(this.map);
      } else {
        this.marker.setPosition(
          new AMap.LngLat(this.lanlat.lng, this.lanlat.lat)
        );
      }
    },
    subItem() {
      this.visible = false;
      this.$emit("close", this.lanlat);
    },
  },
};
</script>
<style lang='scss' scoped>
.lnglats {
  #map {
    width: 100%;
    height: 460px;
    /deep/ .amap-geolocation {
      left: 15px !important;
    }
  }
  .inputs {
    position: absolute;
    z-index: 10;
    width: 240px;
    left: 20px;
    top: 15px;
    ul {
      background: #fff;
      max-height: 360px;
      overflow-y: auto;
      padding: 0px 10px;
      box-sizing: border-box;
      li {
        width: 100%;
        font-size: 12px;
        line-height: 16px;
        padding: 6px 0px;
        box-sizing: border-box;
        cursor: pointer;
        @include text-overflow(2);
        border-bottom: 1px dashed rgb(180, 180, 180);
        span {
          color: rgb(163, 163, 163);
        }
        i {
          color: rgba($color: $primary-color, $alpha: 0.7);
        }
        &:hover {
          background: $primary-color;
        }
      }
    }
  }
  .latlngtext {
    position: absolute;
    right: 20px;
    z-index: 10;
    top: 15px;
    padding: 6px 10px;
    border-radius: 6px;
    background: #fff;
    box-shadow: $primary-shadow;
    color: rgb(117, 117, 117);
    span {
      color: rgb(46, 46, 46);
    }
  }
  /deep/ .el-dialog {
    .el-dialog__body {
      padding: 8px;
      position: relative;
    }
    .dialog-footer {
      position: absolute;
      bottom: 24px;
      right: 32px;
    }
  }
}
</style>
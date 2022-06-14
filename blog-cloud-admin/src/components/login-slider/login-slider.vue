<template>
  <div class="captcha-wrap flex-center">
    <!-- <div class="captcha_wrap"></div> -->
    <div
      class="captcha"
      ref="captcha"
      style="position: relative; width: 360px"
      element-loading-background="rgba(250, 250, 250, 0.2)"
      v-loading="loading"
    >
      <div class="sliderPanel" ref="sliderPanel">
        <img
          :src="formCustom.bigImage"
          alt=""
          class="bigImages"
          ref="bingimgref"
        />
        <div class="action">
          <span class="el-icon-refresh-right" @click="refreshImg"></span>
          <span class="el-icon-close" @click="close"> </span>
        </div>

        <img :src="formCustom.smallImage" alt="" class="block" ref="block" />
      </div>
      <!-- 滑块 -->
      <div
        class="sliderContainer"
        :class="{
          sliderContainer_active: isSliderContaineactive,
          sliderContainer_fail: sliderContainerType == 'fail',
          sliderContainer_success: sliderContainerType == 'success',
        }"
      >
        <div class="sliderMask" ref="sliderMask">
          <div class="slider" ref="slider" @mousedown="sliderMousedown">
            <span
              class="sliderIcon el-icon-close flex align-center justify-center"
              v-if="sliderContainerType == 'fail'"
            ></span>
            <span
              v-else-if="sliderContainerType == 'success'"
              class="sliderIcon el-icon-check flex align-center justify-center"
            ></span>
            <span
              v-else
              class="sliderIcon el-icon-right flex align-center justify-center"
            ></span>
          </div>
        </div>
        <span class="sliderText">{{ loading ? "加载中" : text }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import "./login-slider.scss";
import { captcha } from "api/login";

export default {
  name: "LoginSlider",
  props: {
    randomStr: { type: String },
  },
  data() {
    return {
      loading: true,
      show: false,
      formCustom: {
        bigImage: require("../../assets/img/code/code.png"),
        smallImage: "",
        yHeight: 0,
        trail: [],
      },
      text: "向右滑动滑块填充拼图",
      DOM: {
        block: "",
        sliderMask: "",
        slider: "",
      },
      isSliderContaineactive: false,
      sliderContainerType: "",
    };
  },
  mounted() {
    this.initDOM();
    // this.initCodeImg();
  },
  beforeDestroy() {
    this.timer && clearTimeout(this.timer);
  },
  methods: {
    init() {
      this.initCodeImg();
    },
    close() {
      this.$emit("close");
    },
    //获取验证码
    initCodeImg() {
      return new Promise((resolve, reject) => {
        this.loading = true;
        captcha(this.randomStr).then((res) => {
          this.sliderContainerType = null;
          this.loading = false;
          if (res.code === 200) {
            this.formCustom = res.data;
            this.initImg();
            if (!this.isBindEvents) {
              this.$nextTick(() => {
                this.isBindEvents = true;
              });
            }
          }else{
            this.close()
            this.$message.error(res.msg)
          }
          this.reset();
          resolve();
        });
      });
    },
    onVerify(code) {
      /** 传给父组件 请求登录验证 **/
      this.$emit("parentHandleSubmit", code);
      this.loading =true
    },
    async onSuccess() {
      this.sliderContainerType = "success";
      this.loading =false
      this.timer = setTimeout(() => {
        clearTimeout(this.timer);
        this.$emit("close");
        this.reset();
      }, 200);
    },
    /**
     * @description: 验证失败
     * @param {isReset} boobean 是否重置
     */
    onFail(isReset = true) {
      this.sliderContainerType = "fail";
       this.loading =false
      if (isReset) {
        this.initCodeImg();
      } else {
        this.timer = setTimeout(() => {
          clearTimeout(this.timer);
          this.reset();
        }, 400);
      }
    },

    //初始化 需要用到的dom
    initDOM() {
      this.DOM.block = this.$refs.block; // 滑块
      this.DOM.sliderMask = this.$refs.sliderMask;
      this.DOM.slider = this.$refs.slider;
    },
    //初始化 img 位置
    initImg() {
      this.DOM.block.style = "top:" + this.formCustom.yHeight + "px";
    },
    //重置图片
    refreshImg() {
      this.initCodeImg()
    },
    // 鼠标按下事件
    sliderMousedown(e) {
      let that = this;
      if (this.loading) {
        return;
      }
      let originX = e.x,
        // originY = e.y,
        formCustomW = 360,
        moveDom = that.DOM.slider,
        blockDom = that.DOM.block,
        sliderMaskDom = that.DOM.sliderMask,
        blockLeft;
      document.onmousemove = (e) => {
        const moveX = e.x - originX;
        // const moveY = e.y - originY;
        if (moveX < 0 || moveX + 38 >= formCustomW) return false;
        moveDom.style.left = moveX + "px";
        blockLeft = ((formCustomW - 40 - 16) / (formCustomW - 40)) * moveX;
        blockDom.style.left = blockLeft + "px";
        that.isSliderContaineactive = true;
        sliderMaskDom.style.width = moveX + "px";
        return false;
      };
      document.onmouseup = (e) => {
        document.onmousemove = null;
        document.onmouseup = null;
        if (e.x == originX) return false;
        that.isSliderContaineactive = false;
        that.onVerify(parseInt(blockLeft));
      };
    },
    //重置位置和状态
    reset() {
      this.sliderContainerType = null;
      this.DOM.slider.style.left = 0; //滑块
      this.DOM.block.style.left = 0; //小图片
      this.DOM.sliderMask.style.width = 0; //遮罩
    },
  },
};
</script>


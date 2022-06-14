<!--
 * @Author: daidai
 * @Date: 2021-12-07 18:24:07
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-08 10:09:50
 * @FilePath: \web-pc\src\components\icons\icon-select.vue
-->
<template>
  <el-select
    v-model="value"
    placeholder="请选择"
    popper-class="icons-select"
    clearable
    @change="change"
    class="icons-select-input"
    :class="value"
  >
    <el-option v-for="item in icons" :key="item" :label="item" :value="item">
      <!-- <span style="float: left">{{ item }}</span> -->
      <i :class="item"></i>
    </el-option>
  </el-select>
</template>

<script>
export default {
  data() {
    return {
      icons: ["blq-icon-shezhi01", "blq-icon-shezhi02"],
      value: "",
    };
  },
  props: {
    placeholder: {
      type: String,
      default: () => "请选择",
    },
    val: {
      type: String,
      default: () => "",
    },
  },
  created() {
    this.value= this.val
  },
  watch: {
    val(newval, oldval) {
      console.log(newval);
      this.value= newval
    },
  },
  mounted() {},
  methods: {
    change(val) {
      //   console.log(val);
      this.$emit("update:val", val);
      //   this.$emit("change", val);
    },
  },
};
</script>
<style lang='scss' scoped>
.icons-select-input {
  &::before {
    position: absolute;
    left: 16px;
    top: 1px;
    //减去input 的padding与border
    width: calc(100% - 47px);
    height: calc(100% - 2px);
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: left;
    vertical-align: middle;
    text-indent: 20px;
    z-index: 2000;
  }
}
</style>
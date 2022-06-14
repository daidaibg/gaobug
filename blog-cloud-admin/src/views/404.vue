<!--
 * @Author: daidai
 * @Date: 2021-12-06 15:12:11
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-28 11:43:22
 * @FilePath: \web-pc\src\views\404.vue
-->
<template>
  <div class="error-page">
    <div class="error-code">4<span>0</span>4</div>
    <div class="error-desc">啊哦~ 你所访问的页面不存在</div>
    <div class="error-handle">
      <router-link to="/">
        <el-button type="primary" size="large">返回首页</el-button>
      </router-link>
      <el-button class="error-btn" type="primary" size="large" @click="goBack"
        >返回上一页</el-button
      >
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: [],
    };
  },
  mounted() {
    // console.log(this.$refs["cascaderAddr"].currentLabels);
    // this.value=[ 'shejiyuanze', 'layout', 'data']
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },

  },
};
</script>


<style scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background: #f3f3f3;
  box-sizing: border-box;
}
.error-code {
  line-height: 1;
  font-size: 250px;
  font-weight: bolder;
  color: #2d8cf0;
}
.error-code span {
  color: #00a854;
}
.error-desc {
  font-size: 30px;
  color: #777;
}
.error-handle {
  margin-top: 30px;
  padding-bottom: 200px;
}
.error-btn {
  margin-left: 100px;
}
</style>

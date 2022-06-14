<!--
 * @Author: daidai
 * @Date: 2021-12-08 11:32:46
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-08 11:54:19
 * @FilePath: \web-pc\src\components\current-public\form-Skeleton.vue
-->
<template>
  <el-skeleton :loading="loading" animated>
    <template slot="template">
        <template v-for="item in row">
          <div :key="item" style="" class="item-wrap">
            <el-skeleton-item
              variant="h1"
              style="margin-right: 16px; width: 14%"
            />
            <el-skeleton-item variant="h1" style="flex: 1" />
          </div>
        </template>
        
    </template>
  </el-skeleton>
</template>

<script>
export default {
  data() {
    return {  };
  },
  props: {
    row: {
      type: Number,
      default: () => 3,
    },
    loading:{
        type: Boolean,
      default: () => true,
    }
  },
  created() {},

  mounted() {},
  methods: {},
};
</script>
<style lang='scss' scoped>
.item-wrap {
  display: flex;
  align-items: center;
  justify-items: space-between;
  margin-bottom: 16px;
  .el-skeleton__h1{
      height: 28px;
  }
}
</style>
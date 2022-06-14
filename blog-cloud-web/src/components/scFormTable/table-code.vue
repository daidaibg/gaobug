<!--
 * @Author: daidai
 * @Date: 2022-02-11 10:00:54
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-05-19 17:16:04
 * @FilePath: \yhht-ui\src\components\scFormTable\table-code.vue
-->
<template>
    <table class="tables">
      <tr>
        <th v-for="item in theads[type]" :key="item.val" :style="{ minWidth: item.minWidth }">{{ item.name }}</th>
      </tr>
      <tr v-for="(item, i) in data" :key="i">
        <td v-for="keyItem in theads[type]" :key="keyItem.val">{{ item[keyItem.val] }}</td>
      </tr>
    </table>
</template>

<script lang="ts" setup>
import { ref,Ref,PropType  } from 'vue'
export interface ColumnProps {
 [key:string]:string
}
const props = defineProps({
  data: {
    type: Array as PropType<ColumnProps[]>,
    default: () => [],
  },
  type: {
    type: [String, Number],
    default: () => "param",
  },
})
const theads:any = ref({
  param: [
    { name: "参数", val: "key" },
    { name: "说明", val: "explain", minWidth: '100px' },
    { name: "类型", val: "type" },
    { name: "默认值", val: "def" },
    { name: "可选值", val: "opt" },
  ],
  events: [
    { name: "事件名称", val: "method" },
    { name: "参数", val: "parms" },
    { name: "说明", val: "explain" },
  ],
  funs: [
    { name: "方法名", val: "name" },
    { name: "说明", val: "explain" },
  ]
})

</script>
<style lang='scss' scoped>
.tables {
  border-collapse: collapse;
  width: 100%;
  // background-color: #fff;
  background-color: var(--yh-bg-color-container);
  font-size: 14px;
  margin-bottom: 45px;
  line-height: 1.5em;
  text-align: left;
  margin-top: 22px;

}

.tables th,
.tables td {
  border-bottom: 1px solid var(--yh-component-border);
  padding: 15px;
  max-width: 250px;
  // color: #606266;
  color: var(--yh-text-color-secondary);
}

.tables th {
  color: #909399;
}
</style>
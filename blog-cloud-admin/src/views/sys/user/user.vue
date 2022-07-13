<script setup lang="ts">
import Loading from "@/components/loading";
import {ElTable,ElTableColumn} from "element-plus"
interface User {
  id: number
  date: string
  name: string
  hasChildren?: boolean
  children?: User[]
}
const tableData: User[] = [
  {
    id: 1,
    date: '2016-05-02',
    name: 'wangxiaohu',
  },
  {
    id: 2,
    date: '2016-05-04',
    name: 'wangxiaohu',
  },
  {
    id: 3,
    date: '2016-05-01',
    name: 'wangxiaohu',
    children: [
      {
        id: 31,
        date: '2016-05-01',
        name: 'wangxiaohu',
      },
      {
        id: 32,
        date: '2016-05-01',
        name: 'wangxiaohu',
      },
    ],
  },
  {
    id: 4,
    date: '2016-05-03',
    name: 'wangxiaohu',
  },
]
</script>

<template>
  <!-- <Loading></Loading> -->
  <div class="gb-content">
      <el-table
    :data="tableData"
    stripe 
    style="width: 100%; margin-bottom: 20px"
    row-key="id"
    :border="true"
    default-expand-all
  >
    <el-table-column prop="date" label="date"   align="left" header-align="center" pr/>
    <el-table-column prop="name" label="Name"  align="center"/>
  </el-table>
  </div>


</template>

<style scoped lang="scss">
// css
</style>

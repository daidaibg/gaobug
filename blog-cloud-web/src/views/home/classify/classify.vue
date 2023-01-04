<script setup lang="ts">
import {  currentGET } from "@/api";
import { reactive, ref, toRefs } from "vue";
import { ElMessage } from "element-plus";
import type {ClassifyListType} from "../home-types"
const classifyList = ref<ClassifyListType[]>([]); //分类列表


const props = withDefaults(defineProps<{
  classifyListActive:string|number
}>(), {
  classifyListActive:"",
});
const emits = defineEmits<{
  (event: "on-classify", item: ClassifyListType): void;
}>();
const onClassify = (item: any) => {
  emits("on-classify", item);
};

//获取分类列表
const getCategory = () => {
  currentGET("category", { size: 20 }).then((res:any) => {
    // console.log("分类", res);
    if (res.code == 200) {
      classifyList.value = [
        { id: "", categoryName: "全部" },
        ...res.data.records,
      ];
    } else {
      ElMessage.error(res.msg);
    }
  });
};
const init=()=>{
  getCategory();
}
init()
</script>

<template>
  <div class="mod-classify container-bg">
    <ul class="classify-content flex">
      <li
        v-for="item in classifyList"
        :key="item.id"
        class="classify-item cursor-pointer"
        :class="{ 'is-active': classifyListActive == item.id }"
        @click="onClassify(item)"
      >
        {{ item.categoryName }}
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
// 分类
.mod-classify {
  box-shadow: 0 1px 2px 0 rgb(0 0 0 / 5%);
  width: 100%;
  position: fixed;
  top: var(--header-height);
  z-index: 1;

}
.light .mod-classify {
  backdrop-filter: blur(8px) !important;
  background-color: rgba(255, 255, 255, 0.6);
}
.classify-content {
  max-width: $max-width;
  margin: auto;
  overflow-x: auto;
}
.classify-item {
  padding: 8px 12px;
  font-size: 14px;
  color: var(--yh-text-color-secondary);
  flex-shrink: 0;
  &.is-active,
  &:hover {
    color: var(--yh-brand-color);
  }
}
</style>

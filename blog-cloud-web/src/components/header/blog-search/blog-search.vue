<script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ElInput } from "element-plus";
import { ref } from "vue";
import { useHeaderStore } from "@/store";
import { RouterEnum } from "@/enums";
import { useRouter, useRoute } from "vue-router";
import { addRouterParam } from "@/utils/current";
const router = useRouter();
const route = useRoute();
const searchVal = ref<string>((route as any).query.s || "");
const { setKeywords } = useHeaderStore();

interface Props {
  active: string;
}
const props = withDefaults(defineProps<Props>(), {
  active: RouterEnum.Home,
});
// import { storeToRefs } from "pinia";
/**
 * @description: 回车与点击搜索框触发
 * @return {*}
 */
const search = () => {
  setKeywords(searchVal.value);
  if (props.active !== RouterEnum.Home) {
    router.push({
      path: "/",
      query: {
        s: searchVal.value,
      },
    });
  } else {
    router.push({
      query: addRouterParam(route.query, "s", searchVal.value),
    });
  }
};
const onInput = () => {};
//初始话的时候赋值
searchVal.value = (route as any).query.s || "";
setKeywords(searchVal.value);
</script>

<template>
  <div class="blog-search">
    <el-input
      v-model="searchVal"
      class="w-50 m-2"
      placeholder="探索搞bug"
      @keyup.enter.native="search"
      @input="onInput"
      clearable
    >
      <template #suffix>
        <div class="searc-suffix" @click="search">
          <Search style="width: 16; height: 16" />
        </div>
      </template>
    </el-input>
  </div>
</template>

<style scoped lang="scss">
.searc-suffix {
  cursor: pointer;
  &:hover {
    color: var(--yh-brand-color);
  }
}
</style>

<script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ElInput } from "element-plus";
import { ref } from "vue";
import { useHeaderStore } from "@/store";
import { RouterEnum } from "@/enums";
import { useRouter } from "vue-router";
const searchVal = ref<string>("");
const router = useRouter();

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
  if(!searchVal.value)return
  setKeywords(searchVal.value);
  if (props.active !== RouterEnum.Home) {
    router.push("/");
  }
};
const onInput = () => {};
</script>

<template>
  <div class="blog-search">
    <el-input
      v-model="searchVal"
      class="w-50 m-2"
      placeholder="探索搞bug"
      @keyup.enter.native="search"
      @input="onInput"
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

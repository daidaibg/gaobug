<script setup lang="ts">
import { Search } from "@element-plus/icons-vue";
import { ref, nextTick } from "vue";
import { useHeaderStore } from "@/store";
import { RouterEnum } from "@/enums";
import { useRouter, useRoute } from "vue-router";
import { addRouterParam } from "@/utils/current";

const router = useRouter();
const route = useRoute();
const searchVal = ref<string>((route as any).query.s || "");
const miniSearchVal = ref<string>(searchVal.value);
const miniSearchInput = ref<HTMLInputElement>();
const searchDislogShow = ref<boolean>(false);
const { setKeywords } = useHeaderStore();

interface Props {
  active: string;
}
const props = withDefaults(defineProps<Props>(), {
  active: RouterEnum.Home,
});

//搜索类型
enum SearchType {
  Mini = "mini",
}

//关闭小屏幕搜索框
const closeMiniSearch = () => {};

//取消小屏幕搜索
const cancelSearch = () => {
  miniSearchVal.value = "";
  search(SearchType.Mini);
};

//弹窗打开完毕
const miniOpened = () => {
  miniSearchInput.value?.focus();
};

//打开迷你弹窗
const showMiniSearch = () => {
  searchDislogShow.value = true;
  nextTick(() => {
    document.body.style.width = "";
  });
};

//隐藏迷你弹窗
const hideMiniSearch = () => {
  searchDislogShow.value = false;
  miniSearchInput.value?.blur();
};

/**
 * @description: 回车与点击搜索框触发
 */
const search = (type?: SearchType) => {
  let val = searchVal.value;
  if (type === SearchType.Mini) {
    val = miniSearchVal.value;
    hideMiniSearch();
  }
  setKeywords(val);
  if (props.active !== RouterEnum.Home) {
    router.replace({
      query: {
        s: val,
      },
    });
  } else {
    router.push({
      query: addRouterParam(route.query, "s", val),
    });
  }
};
const onInput = () => {};

setKeywords(searchVal.value);
</script>

<template>
  <div class="blog-search">
    <el-input
      v-model="searchVal"
      class="mini-screen-hide blog-search-input"
      placeholder="探索搞bug"
      @keyup.enter.native="search"
      @input="onInput"
      clearable
      @clear="search"
    >
      <template #suffix>
        <div class="searc-suffix" @click="search()">
          <Search style="width: 16; height: 16" />
        </div>
      </template>
    </el-input>
    <div class="mini-search-icon-box" @click="showMiniSearch()">
      <span class="mini-search-text">{{ miniSearchVal }}</span>
      <Search
        style="width: 20; height: 20"
        class="mini-screen-show mini-search-icon"
      />
    </div>

    <el-drawer
      v-model="searchDislogShow"
      :with-header="false"
      append-to-body
      @opened="miniOpened"
      @close="closeMiniSearch()"
      size="70px"
      :close-on-click-modal="false"
      direction="ttb"
    >
      <div class="mini-search-box">
        <form
          @submit.prevent="search(SearchType.Mini)"
          class="mini-search-input"
          action="javascript:return true;"
        >
          <input
            type="search"
            placeholder="探索搞bug"
            class="search-input-inner"
            v-model="miniSearchVal"
            ref="miniSearchInput"
          />
          <yh-button
            @click="search(SearchType.Mini)"
            theme="primary"
            class="search-btn"
            type="button"
            >搜素</yh-button
          >
          <yh-button @click="cancelSearch()">取消</yh-button>
        </form>
        <div class="mini-search-content"></div>
      </div>
    </el-drawer>
  </div>
</template>

<style scoped lang="scss">
.blog-search {
  margin: 0 8px;
}
.searc-suffix {
  cursor: pointer;
  &:hover {
    color: var(--yh-brand-color);
  }
}
.mini-search-icon-box {
  display: flex;
  align-items: center;
  .mini-search-text {
    margin-right: 8px;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 140px;
  }
  .mini-search-icon {
    margin-right: 16px;
    cursor: pointer;
  }
  &:hover {
    cursor: pointer;
    .mini-search-icon {
      color: var(--yh-brand-color);
    }
    .mini-search-text {
      color: var(--yh-brand-color);
    }
  }
}

.mini-search-input {
  display: flex;
  align-items: center;

  .search-input-inner {
    border: solid 1px var(--yh-border-level-2-color);
    transition: var(--el-transition-border);
    border-radius: 4px;
    margin-right: 12px;
    outline-style: none;
    font-size: 14px;
    padding: 0px 8px;
    height: 28px;
    color: var(--yh-text-color-primary);
    flex: 1;
    &:hover,
    &:focus {
      border-color: var(--yh-brand-color);
    }
  }
  input::-webkit-search-decoration,
  input::-webkit-search-cancel-button {
    display: none;
  }
  .search-btn {
    margin-right: 12px;
  }
}
</style>

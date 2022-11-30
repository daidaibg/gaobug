<script setup lang="ts">
import { ref } from "vue";
import monacoEditor from "@/components/monaco-editor";
import  type {Options,Theme} from "@/components/monaco-editor";
import { Logo } from "@/components/header/logo";
const value = ref('{"test":1}');
const language = ref("json");
const theme = ref<Theme>("vs");
const options = ref<any>({
  minimap: {
    enabled: true,//显示小地图
  },
});
const languageOptions = [
  {
    value: "json",
  },
  {
    value: "javascript",
  },
  {
    value: "html",
  },
];
const themeOptions = [
  {
    value: "vs",
  },
  {
    value: "hc-black",
  },
  {
    value: "vs-dark",
  },
  {
    value:"hc-light",
  }
];
const hightChange = ref<any>(false);
const editorMounted = (editor: any) => {
  console.log("editor实例加载完成", editor);
};
</script>

<template>
  <div class="json_format">
    <div class="json_format_nav">
      <div class="logo_wrap">
        <Logo class="tools-layout_logo" />
        <span>格式化工具</span>
      </div>
      <div class="form_item">
        <label for="">语言:</label>
        <el-select
          v-model="language"
          class="language_select"
          placeholder="Select"
          style="width: 90px"
          size="small"
        >
          <el-option
            v-for="item in languageOptions"
            :key="item.value"
            :label="item.value"
            :value="item.value"
          />
        </el-select>
      </div>
      <div class="form_item">
        <label for="">皮肤:</label>
        <el-select
          v-model="theme"
          class="language_select"
          placeholder="Select"
          style="width: 90px"
          size="small"
        >
          <el-option
            v-for="item in themeOptions"
            :key="item.value"
            :label="item.value"
            :value="item.value"
          />
        </el-select>
      </div>
    </div>
    <monacoEditor
      v-model="value"
      :language="language"
      :hight-change="hightChange"
      :options="options"
      :theme="theme"
      :read-only="false"
      @editor-mounted="editorMounted"
      class="json_format_editor"
    />
  </div>
</template>

<style scoped lang="scss">
.json_format {
  width: 100%;
  height: 100vh;
  //   padding-top: 70px;
  position: relative;
  box-sizing: border-box;
  font-size: 14px;
  // padding-top: var(--header-height);
  // height: 100vh;
  display: flex;
  .json_format_editor {
    flex: 1;
  }
  .json_format_nav {
    width: 210px;
    flex-shrink: 0;
    box-shadow: var(var(--yh-shadow-inset-left));
    box-sizing: border-box;
    padding: 0 8px;
  }
}
.logo_wrap {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  span {
    color: var(--yh-text-color-brand);
    margin-left: 12px;
    font-weight: 900;
  }
}
.form_item {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
  label {
    margin-right: 6px;
  }
}
</style>

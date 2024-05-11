<script setup lang="ts">
import "md-editor-v3/lib/style.css";

import { ref, computed, reactive, toRefs, PropType } from "vue";
import { toolbars } from "./config";
import { MdEditor } from "md-editor-v3";
import MdEmoji from "@/components/md-edits/md-emoji/md-emoji.vue";
import ReadExtension from "@/components/md-edits/read/read.vue";
import MarkExtension from "@/components/md-edits/mark-extension/index.vue";
import { mdEditorConfig, generateId } from "@/config";
import { userThemeStore } from "@/store";
import { onUploadImg } from "@/utils/upload";

import type { ExposeParam, InsertContentGenerator } from "md-editor-v3";

const themeStore = userThemeStore();

const editorId = "editor-preview";
// const editorId = "md-editor-v3";
const editorRef = ref<ExposeParam>();

const props = defineProps({
  previewTheme: {
    type: String as PropType<any>,
  },
});

const insert = (generator: InsertContentGenerator) => {
  editorRef.value?.insert(generator);
};

// 初始化
const init = () => {
  mdEditorConfig();
};

init();
</script>

<template>
  <md-editor :toolbars="toolbars" class="editor_wrap" showCodeRowNumber :previewTheme="props.previewTheme" :auto-detect-code="true"
    :theme="themeStore.getTheme" ref="editorRef" :editor-id="editorId" @uploadImg="onUploadImg" :mdHeadingId="generateId"
    v-bind="$attrs">
    <template #defToolbars>
      <!-- <MarkExtension :editor-id="editorId" @on-change="onChangeMark" />
            <MdEmoji :editor-id="editorId" @onChange="onEmojiChange" /> -->
      <MarkExtension :onInsert="insert" />
      <MdEmoji :onInsert="insert" />
      <ReadExtension :previewTheme="props.previewTheme" :theme="themeStore.getTheme" />
      <!-- :md-text="content" -->
    </template>
  </md-editor>

</template>

<style scoped lang="scss">


@import "@/assets/css/edit-md/edit-md.scss";
.editor_wrap {
  width: 100%;
  height: 100%;
  @include editmdVar("write");
}

</style>

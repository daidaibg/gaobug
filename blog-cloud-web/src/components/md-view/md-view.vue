<script setup lang="ts">
import { type PropType } from "vue";
import { MdPreview } from "md-editor-v3";
import { mdEditorConfig, generateId } from "@/config/modules/md-editor";
import { userThemeStore, useUserStore } from "@/store";

import "md-editor-v3/lib/preview.css";

const id = "edit2preview";


const themeStore = userThemeStore();
const userStore = useUserStore();
const previewTheme = "github"
const codeTheme = "atom"

const props = defineProps({
  text: {
    type: String as PropType<string>,
    default: "",
  },
});

mdEditorConfig();
</script>

<template>
  <MdPreview
    :editorId="id"
    :modelValue="props.text"
    v-bind="$attrs"
    :mdHeadingId="generateId"
    :theme="themeStore.getTheme"
    class="previewmd"
    :previewTheme="previewTheme"
    :codeTheme="codeTheme"
    />
</template>

<style lang="scss">
#md-preview-preview {
  table {
    width: 100%;
    display: table !important;
  }
}
</style>

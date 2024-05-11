<template>
  <ModalToolbar
    showAdjust
    :visible="state.visible"
    :is-fullscreen="state.modalFullscreen"
    title="语法帮助"
    modal-title="语法帮助"
    width="870px"
    height="600px"
    @onClick="state.visible = true"
    @onClose="state.visible = false"
    @onAdjust="state.modalFullscreen = !state.modalFullscreen"
  >
    <div
      style="height: 100%; padding: 20px; overflow: auto"
      v-if="state.visible"
    >
      <MdPreview 
        :theme="theme"
        :preview-theme="props.previewTheme"
        editor-id="edit2preview"
        :modelValue="state.mdText"
        :mdHeadingId="generateId"
      />
    </div>
    <template #trigger>
      <svg class="md-editor-icon" aria-hidden="true">
        <use xlink:href="#md-editor-icon-read"></use>
      </svg>
    </template>
  </ModalToolbar>
</template>

<script lang="ts" setup>
import { reactive, PropType } from "vue";
import {MdPreview ,ModalToolbar} from "md-editor-v3";
import { mdGrammar } from "@/api/blog/blog";
import { ElMessage } from "element-plus";
import {generateId} from "@/config/modules/md-editor"

export interface ColumnProps {
  theme: any;
}
const props = defineProps({
  previewTheme: String,
  theme: {
    type: String as PropType<any>,
    default: "",
  },
});

// console.log(props);
const state = reactive({
  visible: false,
  modalFullscreen: false,
  mdText: "",
});

mdGrammar().then((res) => {
  if (res.code == 200) {
    state.mdText = res.data.content;
  } else {
    ElMessage.error(res.msg);
  }
});

</script>
<script lang="ts">
export default {
  name: 'ReadExtension'
};
</script>

<template>
    <modal-toolbar :visible="state.visible" :is-fullscreen="state.modalFullscreen" show-adjust title="语法帮助"
        modal-title="语法帮助" width="870px" height="600px" @onClick="state.visible = true" @onClose="state.visible = false"
        @onAdjust="state.modalFullscreen = !state.modalFullscreen">
        <div style="height: 100%; padding: 20px; overflow: auto "  v-if="state.visible">
            <md-editor :theme="theme" :preview-theme="previewTheme" editor-id="edit2preview"   preview-only
            :modelValue="state.mdText" />
        </div>
        <template #trigger>
            <svg class="md-editor-icon" aria-hidden="true">
                <use xlink:href="#icon-read"></use>
            </svg>
        </template>
    </modal-toolbar>
</template>

<script lang="ts" setup>
import { reactive, PropType } from 'vue';
import MdEditor from "md-editor-v3"
import {mdGrammar} from "@/api/blog/blog"
import {ElMessage} from "element-plus"

export interface ColumnProps {
    theme: any
}
const { ModalToolbar } = MdEditor;
const props = defineProps({
    previewTheme: String,
    theme: {
        type: String as PropType<any>,
        default: ""
    }
});

// console.log(props);
const state = reactive({
    visible: false,
    modalFullscreen: false,
    mdText:''
});

mdGrammar().then(res=>{
    if (res.code == 200) {
      state.mdText = res.data.content;
    } else {
      ElMessage.error(res.msg);
    }
})

</script>

<script lang="ts">
export default {
    name: 'ReadExtension'
};
</script>

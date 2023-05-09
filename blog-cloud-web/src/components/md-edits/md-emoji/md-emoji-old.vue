<script lang="ts" setup>
import { reactive } from "vue";
import { emojis } from "./md-emoji";
import MdEditor from "md-editor-v3";

const { DropdownToolbar } = MdEditor;
const props = defineProps({
  editorId: String,
});
const emit = defineEmits(["onChange"]);

const state = reactive({
  visible: false,
});

const emojiHandler = (emoji: string) => {
  // 获取输入框
  const textarea = document.querySelector(
    `#${props.editorId}-textarea`
  ) as HTMLTextAreaElement;
  // 获取选中的内容
  const selection = window.getSelection()?.toString();
  // 获取鼠标位置
  const endPoint = textarea.selectionStart;

  // 根据鼠标位置分割旧文本
  // 前半部分
  const prefixStr = textarea.value.substring(0, endPoint);
  // 后半部分
  const suffixStr = textarea.value.substring(
    endPoint + (selection?.length || 0)
  );

  emit("onChange", `${prefixStr}${emoji}${suffixStr}`);

  setTimeout(() => {
    textarea.setSelectionRange(endPoint, endPoint + 1);
    textarea.focus();
  }, 0);
};

const onChange = (visible: boolean) => {
  state.visible = visible;
};
</script>

<script lang="ts">
export default {
  name: "EmojiExtension",
};
</script>
<template>
  <dropdown-toolbar title="emoji" :visible="state.visible" @onChange="onChange">
    <template #overlay>
      <div class="emoji-container">
        <ol class="emojis">
          <li
            v-for="(emoji, index) of emojis"
            :key="`emoji-${index}`"
            @click="emojiHandler(emoji)"
            v-text="emoji"
          ></li>
        </ol>
      </div>
    </template>
    <template #trigger>
      <svg class="md-editor-icon" aria-hidden="true">
        <use xlink:href="#icon-emoji"></use>
      </svg>
    </template>
  </dropdown-toolbar>
</template>
<style scoped lang="scss">
.tips-text {
  color: #777;
}

.emoji-container {
  border-radius: 3px;
  border: 1px solid #e6e6e6;
}

.emojis {
  position: relative;
  width: 363px;
  margin: 10px;
  padding: 0;
  background-color: #fff;

  li {
    cursor: pointer;
    float: left;
    border: 1px solid #e8e8e8;
    height: 24px;
    width: 28px;
    overflow: hidden;
    margin: -1px 0 0 -1px;
    padding: 4px 2px;
    text-align: center;
    list-style: none;
    z-index: 11;
    box-sizing: initial;
    &:hover {
      position: relative;
      border: 1px solid #63a35c;
      z-index: 12;
    }
  }

  &::after {
    content: "";
    clear: left;
    display: block;
  }

  * {
    user-select: none;
  }
}
.md-dark {
  .emoji-container {
    border-color: var(--md-border-color);
  }
  .emojis {
    background-color: inherit;

    li {
      border-color: var(--md-border-color);

      &:hover {
        border: 1px solid #497744;
      }
    }
  }
}
</style>

<template>
    <DropdownToolbar title="emoji" :visible="state.visible" @onChange="onChange">
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
          <use xlink:href="#md-editor-icon-emoji"></use>
        </svg>
      </template>
    </DropdownToolbar>
  </template>
  
  <script lang="ts" setup>
  import { reactive } from 'vue';
  import {DropdownToolbar} from "md-editor-v3";
  import type { PropType } from 'vue';
  import type { InsertContentGenerator } from 'md-editor-v3';
  import { emojis } from './md-emoji';


  const props = defineProps({
    onInsert: {
      type: Function as PropType<(generator: InsertContentGenerator) => void>,
      default: () => () => null
    }
  });
  
  const state = reactive({
    visible: false
  });
  
  const emojiHandler = (emoji: string) => {
    const generator: InsertContentGenerator = () => {
      return {
        targetValue: emoji,
        select: true,
        deviationStart: 0,
        deviationEnd: 0
      };
    };
  
    props.onInsert(generator);
  };
  
  const onChange = (visible: boolean) => {
    state.visible = visible;
  };
  </script>
  
  <script lang="ts">
  export default {
    name: 'EmojiExtension'
  };
  </script>
  
  <style scoped lang="scss">
.tips-text {
  color: #777;
}

.emoji-container {
  border-radius: 3px;
  border: 1px solid var(--md-border-color);
}

.emojis {
  position: relative;
  width: 363px;
  margin: 10px;
  padding: 0;
  background-color: var(--md-bk-color);

  li {
    cursor: pointer;
    float: left;
    border: 1px solid var(--md-border-color);
    height: 24px;
    width: 28px;
    overflow: hidden;
    margin: -1px 0 0 -1px;
    padding: 4px 2px;
    text-align: center;
    list-style: none;
    z-index: 11;
    box-sizing: initial;
    color: var(--yh-text-color-primary);
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

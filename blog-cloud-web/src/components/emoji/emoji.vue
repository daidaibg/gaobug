<script setup lang="ts">
import { ref } from "vue";
import { emojiList, emojiObj } from "./emoji";
import { EmojiListType } from "./type";
import { onClickOutside } from "@vueuse/core";
const emoji_target = ref(null);
const emojiShow = ref<boolean>(false);
// 点击弹窗外边关闭 弹窗
onClickOutside(emoji_target, (event) => {
  emojiShow.value = false;
});
const emits = defineEmits<{
  (event: "on-emoji", e: Event, emojiItem: EmojiListType): void;
  (event: "emoji-hide", e: Event): void;
}>();
/**
 * @description: 点击表情
 * @param {*} e
 * @param {*} emojiItem
 * @return {*}
 */
const onEmoji = (e: Event, emojiItem: EmojiListType): void => {
  // console.log(emojiItem,commentEdit);
  emits("on-emoji", e, emojiItem);
};
/**
 * @description: 隐藏emoji列表窗口
 * @return {*}
 */
const hideEmojiList = () => {
  emojiShow.value = false;
};

defineExpose({
  hideEmojiList,
});
</script>

<template>
  <div class="emoji_door" ref="emoji_target">
    <div class="flex items-center" @click="emojiShow = !emojiShow">
      <svg
        data-v-9fe533ba=""
        width="16"
        height="16"
        viewBox="0 0 16 16"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
        class="icon"
      >
        <path
          data-v-9fe533ba=""
          fill-rule="evenodd"
          clip-rule="evenodd"
          d="M8.00002 0.666504C12.0501 0.666504 15.3334 3.94975 15.3334 7.99984C15.3334 12.0499 12.0501 15.3332 8.00002 15.3332C3.94993 15.3332 0.666687 12.0499 0.666687 7.99984C0.666687 3.94975 3.94993 0.666504 8.00002 0.666504ZM8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2ZM10.6667 5.66667V7.66667H9.33333V5.66667H10.6667ZM6.66667 5.66667V7.66667H5.33333V5.66667H6.66667ZM10.0767 9.33333H11.0495C11.1804 9.33333 11.2866 9.43951 11.2866 9.57048C11.2866 9.60754 11.2779 9.64409 11.2612 9.67718L11.244 9.71053C10.6294 10.8739 9.40726 11.6667 7.99998 11.6667C6.61523 11.6667 5.40977 10.8991 4.7859 9.76612L4.73786 9.67593C4.67845 9.56052 4.72385 9.4188 4.83926 9.35939C4.87253 9.34226 4.90941 9.33333 4.94683 9.33333H5.92347C6.02396 9.33332 6.11908 9.37865 6.18238 9.4567C6.26207 9.55496 6.32833 9.62955 6.38117 9.68046C6.80074 10.0847 7.37133 10.3333 7.99998 10.3333C8.63289 10.3333 9.20694 10.0814 9.62728 9.67224C9.67791 9.62296 9.74135 9.55121 9.8176 9.45698C9.88089 9.37877 9.97611 9.33333 10.0767 9.33333Z"
        ></path>
      </svg>
      <span class="name">表情</span>
    </div>
    <transition name="el-fade-in-linear">
      <div class="emoji_wrap_list" v-show="emojiShow">
        <ul class="emoji_wrap flex flex-wrap">
          <li
            class="emoji_item flex justify-center"
            v-for="(item, i) in emojiList"
            @click.stop="(e) => onEmoji(e, item)"
            :title="item.name"
            :key="i"
          >
            <img
              :src="item.path"
              :alt="item.name"
              class="emoji_img cursor-pointer"
            />
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<style scoped lang="scss">
.emoji_door {
  user-select: none;
  position: relative;
  cursor: pointer;
  color: var(--yh-text-color-secondary);
  .icon {
    fill: var(--yh-text-color-secondary);
  }

  .name {
    line-height: 22px;
    margin-left: 4px;
  }
}
.emoji_wrap_list {
  position: absolute;
  top: 28px;
  width: 386px;
  background: var(--yh-bg-color-container);
  min-width: 150px;
  border-radius: 8px;
  border: 1px solid var(--yh-border-level-1-color);
  padding: 12px;
  box-shadow: var(--el-box-shadow-light);
  word-break: break-all;

  &::after {
    content: " ";
    width: 12px;
    height: 12px;
    background-color: inherit;
    position: absolute;
    left: 24px;
    top: -7px;
    transform: rotate(135deg);
    border-left: 1px solid var(--yh-border-level-1-color);
    border-bottom: 1px solid var(--yh-border-level-1-color);
  }
}
.emoji_wrap {
  max-height: 270px;
  overflow-y: auto;
  position: relative;

  .emoji_item {
    width: 44px;
    height: 40px;

    .emoji_img {
      width: 28px;
      transition: transform .3s;
      height: 28px;
    }
    &:hover .emoji_img {
      transform: translateZ(0) scale(1.28);
    }
  }
}
</style>

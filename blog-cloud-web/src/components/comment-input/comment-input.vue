<script setup lang="ts">
import Emoji from "@/components/emoji";
import { ElInput } from "element-plus";
import type { EmojiListType } from "@/components/emoji/type";
import { ref, nextTick } from "vue";

const commentEdit = ref();//input dom
const emojiTarget = ref(); //emoji dom
const inputVal = ref<string>("");
const props = defineProps({
  autosize: {
    type:Object,
    default:()=>{
    return  { minRows: 3, maxRows: 6 }
    }
  },
  placeholder:{
    type:String,
    default:"请输入评论"
  },
  maxlength:{
    type:Number,
    default:150,
  },
  showWordLimit:{
    type:Boolean,
    default:true,
  },
});
const emits = defineEmits<{
     (event: "comment",  CommentVal: string): void;
     (event: "blur",  E: Event): void;
}>()

/**
 * @description: 点击评论
 * @param {*} e
 * @return {*}
 */
const comment =(e:Event)=>{
    emits("comment",inputVal.value)
    inputVal.value=""
}
const onEmoji = async (e: Event, emojiItem: EmojiListType) => {
  // console.log(emojiItem,commentEdit);
  let str = `[${emojiItem.name}]`;
  let oText = commentEdit.value.textarea;
  let index = getCursortPosition(commentEdit.value.textarea);
//   console.dir(commentEdit.value.textarea);
  await nextTick();
  insert(oText, str, index);
//   emojiTarget.value.hideEmojiList();
};

const inputblur = (e:Event) => {
  // console.log("失去焦点");
  emits("blur",e)
  // commentEdit.value.focus();
};
// 插入表情
const insert = async (oText: any, varstr: any, index: any) => {
  var text = inputVal.value;
  let startStr = text.substring(0, index);
  let endStr = text.substring(index, text.length);
  inputVal.value = startStr + varstr + endStr;
  await nextTick();
  setCaretPosition(oText, index + varstr.length);
};
// 设置光标位置
const setCaretPosition = async (ctrl: any, pos: any) => {
  if (ctrl.setSelectionRange) {
    commentEdit.value.focus();
    ctrl.setSelectionRange(pos, pos);
  } else if (ctrl.createTextRange) {
    var range = ctrl.createTextRange();
    range.collapse(true);
    range.moveEnd("character", pos);
    range.moveStart("character", pos);
    range.select();
  }
};
// 获取光标位置
const getCursortPosition = (obj: any) => {
  var cursorIndex = 0;
  let doc: any = document;
  if (doc.selection) {
    obj.focus();
    var range = doc.selection.createRange();
    range.moveStart("character", -obj.value.length);
    cursorIndex = range.text.length;
  } else if (obj.selectionStart || obj.selectionStart == 0) {
    cursorIndex = obj.selectionStart;
  }
  return cursorIndex;
};
</script>

<template>
  <div class="input_inner">
    <el-input
      v-model="inputVal"
      :autosize="autosize"
      type="textarea"
      :placeholder="placeholder"
      ref="commentEdit"
      @blur="inputblur"
      :show-word-limit="showWordLimit"
      :maxlength="maxlength"
      v-bind="$attrs"
    />
    <!-- <div contenteditable="true" ref="commentEdit" placeholder="请输入评论"
                        class="commitedit el-textarea__inner"></div> -->
    <div class="comment-action flex justify-between">
      <div class="left">
        <emoji @onEmoji="onEmoji" ref="emojiTarget"></emoji>
      </div>
      <div class="right">
        <yh-button theme="primary" size="medium" @click="comment">评论</yh-button>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.input_inner {
  flex: 1;

  width: 100%;
  .comment-action {
    margin-top: 16px;
  }

  .left {
  }
}
</style>

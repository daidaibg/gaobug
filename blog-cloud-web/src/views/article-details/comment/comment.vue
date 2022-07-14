<script setup lang="ts">
import { ref, nextTick, Comment } from "vue";
import Props from "./props";
import {  ElAvatar } from "element-plus";
import {emojiObj } from "@/components/emoji/emoji";
import CommentInput from "@/components/comment-input"
// console.log(emojiList);
const props = defineProps(Props);
// const text=`我是假的，现在还不能评论[看]<p class="a" id='a'>我是p标签</p>`
interface CommentListType{
    id?:number,
    content:string
}
const commentList = ref<CommentListType[]>([
    {
        id:1,
        content:'我是假的，现在还不能评论[看]'
    },
    {
        id:1,
        content:`我是假的，现在还不能评论[看]<p class="a" id='a'>我是p标签</p>`
    },

])
/**
 * @description: 点击评论按钮事件
 * @param {*} CommentVal
 */
const onComment= (CommentVal:string)=>{
    console.log(CommentVal);
    commentList.value.push({
        content:CommentVal,
    })
}
const handleStr = (str: string) => {
  // console.log(str);
  if (!str) {
    return "";
  } else {
    str = str
      .replace(/&/g, "&amp;")
      .replace(/\</g, "&lt;")
      .replace(/\>/g, "&gt;")
      .replace(/\"/g, "&quot;")
      .replace(/'/g, "&apos;");
    let pattern1 = /\[[\u4e00-\u9fa5]+\]/g;
    let pattern2 = /\[[\u4e00-\u9fa5]+\]/;
    let content = str.match(pattern1);
    // console.log(pattern1,content);
    let newStr = str;
    if (content) {
      for (let i = 0; i < content.length; i++) {
        if (Object.prototype.hasOwnProperty.call(emojiObj, content[i])) {
          let imoj = `<img src="${emojiObj[content[i]]}" class="emoji"/>`;
          newStr = newStr.replace(pattern2, imoj);
        }
      }
    }
    // console.log(newStr);
    return newStr;
  }
};
</script>

<template>
  <div id="comment" class="container-bg mt-16 rounded-6 comments">
    <div class="header-title">评论</div>
    <div class="input_wrap flex">
      <el-avatar :size="40" :src="avatarUrl" class="avatar" />
      <comment-input @comment="onComment"></comment-input>
    </div>
    <div class="header-title flex items-center">
      <div class="flex items-center">
        热门评论
        <svg
          data-v-5fe91717=""
          width="16"
          height="16"
          viewBox="0 0 16 16"
          xmlns="http://www.w3.org/2000/svg"
          class=""
        >
          <path
            data-v-5fe91717=""
            d="M12.8 5.2C13.9532 6.46 14.8 8.2 14.8 10C14.7039 12.8937 12.6843 15.1706 9.97973 15.8159C10.359 12.3442 7.77588 9.35406 7.77588 9.35406C7.77588 9.35406 7.99512 13.7064 6.79514 15.8104C4.03715 15.1428 2 12.7806 2 9.8C2 7.776 2.9336 5.9728 4.4 4.8C5.8608 3.7056 6.8 1.9656 6.8 0C9.684 0.4368 11.894 2.9264 11.894 5.932C11.894 6.5012 11.746 7.0652 11.6 7.6C12.1264 6.9024 12.6184 6.0876 12.8 5.2Z"
          ></path>
        </svg>
      </div>
    </div>
    <div class="comment_item flex" v-for="(item, i) in commentList" :key="i">
      <el-image
        :src="'//www.gaobug.com/img/avatar/avatar.png'"
        lazy
        class="comment_avatar"
        fit="cover"
      />
      <div class="comment_box">
        <div class="comment_main">
          <div class="user-box">
            <span class="name"> {{ "test用户" }}</span>
            <span class="time"> {{ "刚刚" }}</span>
          </div>
          <p v-html="handleStr(item.content)" class="discuss_item"></p>
          <div class="info-box_action flex items-center">
            <div class="info-box_action-item hovers">
              <i class="dd-icon-dianzan icon"></i>
              <span>{{ 10 }}</span>
            </div>
            <div class="info-box_action-item hovers">
              <i class="dd-icon-pinglun icon"></i>
              <span>{{ 0 }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="height: 400px"></div>
  </div>
</template>

<style scoped lang="scss">
.comments {
  font-size: 14px;
  padding: 16px 24px;

  .header-title {
    font-size: 18px;
    font-weight: 900;
    margin: 16px 0;
    color: var(--yh-text-color-primary);

    svg {
      margin-left: 4px;
      fill: var(--yh-error-color);
    }
  }

  .input_wrap {
    .avatar {
      flex-shrink: 0;
      margin-right: 16px;
    }

    .commitedit {
      min-height: 58px;
      height: auto;
      word-break: break-all;
    }
  }
}

//评论内容
.discuss_item {
  margin-top: 8px;
  color: var(--yh-text-color-secondary);

  img {
    display: inline;
    width: 20px;
    height: 20px;
    margin: 0 2px;
    vertical-align: sub;
  }
}

.comment_item {
  padding: 16px 0;
  .comment_avatar {
    width: 40px;
    height: 40px;
  }

  .comment_box {
    flex: 1;
    margin-left: 16px;
  }
}

//用户名称 时间
.user-box {
  display: flex;

  .name {
    color: var(--yh-text-color-primary);
  }

  .time {
    margin-left: auto;
    color: var(--yh-text-color-placeholder);
  }
}

// 评论action
.info-box_action {
  font-size: 12px;
  margin: 10px 0;

  &-item {
    display: flex;
    align-items: center;
    margin-right: 12px;
    line-height: 1;
    cursor: pointer;

    .icon {
      font-size: 14px;
      margin-right: 4px;
      color: var(--yh-text-color-placeholder);
      font-weight: 900;
    }

    span {
      color: var(--yh-text-color-placeholder);
    }

    &:hover {
      .icon,
      span {
        color: var(--yh-brand-color);
      }
    }
  }
}
</style>

<script setup lang="ts">
import { ref, computed, nextTick } from "vue";
import { chatInit, createChar } from "@/utils/chat/chat";
import { useElementBounding } from "@vueuse/core";
import { scrollTo } from "yhht-plus/lib/utils/index";
import { ElMessage } from "element-plus";

const input = ref("");
const chatInput = ref("");
const chatKeyInitFlag = ref(false);
const chatList = ref<any>([]);
const sendLoading = ref(false);
const chatIptBox = ref();

const { height: chatIptBoxHeight } = useElementBounding(chatIptBox);

const submitKey = () => {
  chatKeyInitFlag.value = chatInit(input.value);
  localStorage.setItem("key", input.value);
};

const listStyle = computed(() => {
  const newStyle: any = {};
  if (chatIptBoxHeight) {
    newStyle.paddingBottom = chatIptBoxHeight.value + "px";
  }
  return newStyle;
});

const sendChart = () => {
  if (!chatKeyInitFlag.value) return;
  if (sendLoading.value) {
    return;
  }
  sendLoading.value = true;
  chatList.value.push({
    role: "user",
    content: chatInput.value,
  });
  localstorge();
  const newchatList = chatList.value.slice(-2);
  createChar(newchatList).then((res) => {
    sendLoading.value = false;
    if (res.code == 200) {
    
      console.log(res);
      let content = res!.data.message.content;
      while (content.charAt(0) === "\n") {
        content = content.substring(1);
      }
      chatList.value.push({
        role: "assistant",
        content: content,
      });
      localstorge();
    } else if (res.code == 401) {
      ElMessage.error(res.msg);
      chatKeyInitFlag.value = false;
    } else {
      ElMessage.error(res.msg);
    }
  });
  chatInput.value = "";
};

const localstorge = async () => {
  localStorage.setItem("chatList", JSON.stringify(chatList.value));
  await nextTick();
  scrollTo(document.body.scrollHeight, {});
};

chatList.value = JSON.parse(localStorage.getItem("chatList") || "[]");

const key = localStorage.getItem("key");
if (key) {
  input.value = key;
  submitKey();
}
</script>

<template>
  <div class="chat-page">
    <div class="input-key" v-if="!chatKeyInitFlag">
      <div class="input-key-inner">
        <h2 class="ipt-key-title">请输入key</h2>
        <p class="description">
          你需要合法上网后后才可以访问到openAi接口，此页面只是个人想使用api开发。
        </p>
        <el-input v-model="input" placeholder="请输入key" class="ipt-key-ipt" clearable/>
        <yh-button class="ipt-key-sub" theme="primary" @click="submitKey"
          >确 定</yh-button
        >
      </div>
    </div>
    <div class="chat-list" :style="listStyle">
      <div
        v-for="(item, i) in chatList"
        :class="'chat-item-' + item.role"
        class="chat-item"
      >
        <div class="chat-item-content">
          <span class="chat-item-text">
            {{ item.content }}
            <!-- <div v-html="item.content"></div> -->
          </span>
        </div>
      </div>
    </div>
    <div class="setting">
      <el-tooltip content="重新设置key" >
        <i class="yh-icons-refresh" @click="chatKeyInitFlag=false"></i>
      </el-tooltip>
    </div>
    <div class="char-ipt-box" ref="chatIptBox">
      <el-input
        v-model="chatInput"
        placeholder="请输入提问内容"
        :autosize="{ minRows: 2, maxRows: 4 }"
        type="textarea"
        class="ipt-char-ipt"
        @keyup.enter="chatInput = ''"
        @keydown.enter="sendChart"
        ref="chatIptTarget"
      />
      <yh-button
        class="ipt-key-sub"
        theme="primary"
        @click="sendChart"
        :loading="sendLoading"
        >确 定</yh-button
      >
    </div>
  </div>
</template>

<style scoped lang="scss">
.chat-page {
}
.input-key {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--yh-mask-active);
  z-index: 999;
  .input-key-inner {
    margin-top: -20%;
    max-width: 400px;
    border-radius: 3px;
    background-color: var(--yh-bg-color-container);

    padding: 16px;
  }
  .ipt-key-title {
    text-align: center;
    font-size: 16px;
    font-weight: 900;
  }
  .ipt-key-ipt {
    margin: 16px 0;
  }
  .ipt-key-sub {
    margin-left: 50%;
    transform: translateX(-50%);
  }
  .description {
    font-size: 12px;
    text-indent: 12px;
    color: var(--yh-text-color-secondary);
  }
}
.setting{
    position: fixed;
    top: 60px;
    right: 0px;
    z-index: 999;
    cursor: pointer;
    background-color: var(--yh-bg-color-container);
    border-radius:3px ;
    padding: 2px 6px;
    i{
        &:hover{
            color: var(--yh-brand-color);
        }
    }
}
.char-ipt-box {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  display: flex;
  background-color: var(--yh-bg-color-container);
  align-items: center;
  padding: 16px;

  .ipt-char-ipt {
    margin: 0 16px;
  }
}

.chat-list {
  width: 100%;
  padding: 16px;

  .chat-item {
    margin-bottom: 8px;
    display: flex;
    .chat-item-content {
      max-width: 60%;
      padding: 8px 16px;
      border-radius: 4px;
    }
    .chat-item-text {
      font-size: 15px;
      word-break: break-all;
      white-space: pre-wrap;
    }
    &-user {
      justify-content: flex-end;
      .chat-item-content {
        background-color: var(--yh-brand-color);
        color: var(--yh-text-color-anti);
      }
    }
    &-assistant {
      .chat-item-content {
        background-color: var(--yh-bg-color-container);
      }
    }
  }
}
</style>

<script setup lang='ts'>
import { ref, nextTick } from "vue";
import Props from "./props"
import { ElInput, ElAvatar } from "element-plus"
import { emojiList, EmojiListType, emojiObj } from "./emoji"
import { onClickOutside } from '@vueuse/core'
// console.log(emojiList);
const emoji_target = ref(null)
const commentEdit = ref()
const props = defineProps(Props)
const inputVal = ref<string>('')
const emojiShow = ref<boolean>(false)
// const text=`我是假的，现在还不能评论[看]<p class="a" id='a'>我是p标签</p>`
const text=`我是假的，现在还不能评论[看]`

// 点击弹窗外边关闭 弹窗
onClickOutside(emoji_target, (event) => {
    emojiShow.value = false
})
const onEmoji = async (e: Event, emojiItem: EmojiListType) => {
    // console.log(emojiItem,commentEdit);
    // let Img = `<img src="${emojiItem.path}"  class="emoji"  >`;
    let str = `[${emojiItem.name}]`
    let oText = commentEdit.value.textarea
    let index = getCursortPosition(commentEdit.value.textarea)
    console.dir(commentEdit.value.textarea);
    await nextTick()
    insert(oText, str, index)
}
const inputblur = () => {
    // console.log("失去焦点");
    // commentEdit.value.focus();
}
// 插入表情
const insert = async (oText: any, varstr: any, index: any) => {
    var text = inputVal.value
    let startStr = text.substring(0, index)
    let endStr = text.substring(index, text.length)
    inputVal.value = startStr + varstr + endStr
    await nextTick()
    setCaretPosition(oText, index + varstr.length)
}
// 设置光标位置
const setCaretPosition = async (ctrl: any, pos: any) => {
    if (ctrl.setSelectionRange) {
        commentEdit.value.focus();
        ctrl.setSelectionRange(pos, pos);
        // let timer = setTimeout(() => {
        //     console.log("focus");
        //     commentEdit.value.focus();
        //     clearTimeout(timer)
        // }, 1000);
    }
    else if (ctrl.createTextRange) {
        var range = ctrl.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }

}
// 获取光标位置
const getCursortPosition = (obj: any) => {
    var cursorIndex = 0
    let doc: any = document
    if (doc.selection) {
        obj.focus()
        var range = doc.selection.createRange()
        range.moveStart('character', -obj.value.length)
        cursorIndex = range.text.length
    } else if (obj.selectionStart || obj.selectionStart == 0) {
        cursorIndex = obj.selectionStart
    }
    return cursorIndex
}

const handleStr = (str: string) => {
    // console.log(str);
    if (!str) {
        return ""
    } else {
        str = str.replace(/&/g, "&amp;").replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/\"/g, "&quot;").replace(/'/g, "&apos;");
        let pattern1 = /\[[\u4e00-\u9fa5]+\]/g;
        let pattern2 = /\[[\u4e00-\u9fa5]+\]/;
        // str = str.replace(new RegExp("&", 'g'), "&amp;").replace(new RegExp("\<", 'g'), "&lt;").replace(new RegExp("\>", 'g'), "&gt;")
        // .replace(new RegExp("\"", 'g'), "&quot;").replace(new RegExp("'", 'g'), "&apos;");
        // // console.log(str);

        // let pattern1 = new RegExp("\\[[\\u4e00-\\u9fa5]+\\]", 'g');
        // let pattern2 = new RegExp("\\[[\\u4e00-\\u9fa5]+\\]");
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

}
</script>

<template>
    <div id="comment" class="container-bg mt-16  rounded-6  comments">
        <div class="header-title">
            评论
        </div>
        <div class="input_wrap flex">
            <el-avatar :size="40" :src="avatarUrl" class="avatar" />
            <div class="input_inner">
                <el-input v-model="inputVal" :autosize="{ minRows: 2, maxRows: 6 }" type="textarea" placeholder="请输入评论"
                    ref="commentEdit" @blur="inputblur" />
                <!-- <div contenteditable="true" ref="commentEdit" placeholder="请输入评论"
                        class="commitedit el-textarea__inner"></div> -->
                <div class="comment-action flex justify-between">
                    <div class="left">
                        <!-- <el-popover :width="386" trigger="click" :teleported="false" placement="bottom-start">
                                <template #reference>
                                    <div class="emoji_door flex items-center">
                                        <svg data-v-9fe533ba="" width="16" height="16" viewBox="0 0 16 16" fill="none"
                                            xmlns="http://www.w3.org/2000/svg" class="icon">
                                            <path data-v-9fe533ba="" fill-rule="evenodd" clip-rule="evenodd"
                                                d="M8.00002 0.666504C12.0501 0.666504 15.3334 3.94975 15.3334 7.99984C15.3334 12.0499 12.0501 15.3332 8.00002 15.3332C3.94993 15.3332 0.666687 12.0499 0.666687 7.99984C0.666687 3.94975 3.94993 0.666504 8.00002 0.666504ZM8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2ZM10.6667 5.66667V7.66667H9.33333V5.66667H10.6667ZM6.66667 5.66667V7.66667H5.33333V5.66667H6.66667ZM10.0767 9.33333H11.0495C11.1804 9.33333 11.2866 9.43951 11.2866 9.57048C11.2866 9.60754 11.2779 9.64409 11.2612 9.67718L11.244 9.71053C10.6294 10.8739 9.40726 11.6667 7.99998 11.6667C6.61523 11.6667 5.40977 10.8991 4.7859 9.76612L4.73786 9.67593C4.67845 9.56052 4.72385 9.4188 4.83926 9.35939C4.87253 9.34226 4.90941 9.33333 4.94683 9.33333H5.92347C6.02396 9.33332 6.11908 9.37865 6.18238 9.4567C6.26207 9.55496 6.32833 9.62955 6.38117 9.68046C6.80074 10.0847 7.37133 10.3333 7.99998 10.3333C8.63289 10.3333 9.20694 10.0814 9.62728 9.67224C9.67791 9.62296 9.74135 9.55121 9.8176 9.45698C9.88089 9.37877 9.97611 9.33333 10.0767 9.33333Z">
                                            </path>
                                        </svg>
                                        <span class="name">表情</span>
                                    </div>

                                </template>
                                <ul class="emoji_wrap flex flex-wrap ">
                                    <li class="emoji_item flex justify-center" v-for="(item, i) in emojiList" @click.stop="(e) => onEmoji(e, item)"
                                        :title="item.name" :key="i"> <img :src="item.path" :alt="item.name"
                                            class="emoji_img cursor-pointer " >
                                    </li>
                                </ul>
                            </el-popover> -->
                        <div class="emoji_door ">
                            <div class="flex items-center" @click="emojiShow = !emojiShow">
                                <svg data-v-9fe533ba="" width="16" height="16" viewBox="0 0 16 16" fill="none"
                                    xmlns="http://www.w3.org/2000/svg" class="icon">
                                    <path data-v-9fe533ba="" fill-rule="evenodd" clip-rule="evenodd"
                                        d="M8.00002 0.666504C12.0501 0.666504 15.3334 3.94975 15.3334 7.99984C15.3334 12.0499 12.0501 15.3332 8.00002 15.3332C3.94993 15.3332 0.666687 12.0499 0.666687 7.99984C0.666687 3.94975 3.94993 0.666504 8.00002 0.666504ZM8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2ZM10.6667 5.66667V7.66667H9.33333V5.66667H10.6667ZM6.66667 5.66667V7.66667H5.33333V5.66667H6.66667ZM10.0767 9.33333H11.0495C11.1804 9.33333 11.2866 9.43951 11.2866 9.57048C11.2866 9.60754 11.2779 9.64409 11.2612 9.67718L11.244 9.71053C10.6294 10.8739 9.40726 11.6667 7.99998 11.6667C6.61523 11.6667 5.40977 10.8991 4.7859 9.76612L4.73786 9.67593C4.67845 9.56052 4.72385 9.4188 4.83926 9.35939C4.87253 9.34226 4.90941 9.33333 4.94683 9.33333H5.92347C6.02396 9.33332 6.11908 9.37865 6.18238 9.4567C6.26207 9.55496 6.32833 9.62955 6.38117 9.68046C6.80074 10.0847 7.37133 10.3333 7.99998 10.3333C8.63289 10.3333 9.20694 10.0814 9.62728 9.67224C9.67791 9.62296 9.74135 9.55121 9.8176 9.45698C9.88089 9.37877 9.97611 9.33333 10.0767 9.33333Z">
                                    </path>
                                </svg>
                                <span class="name">表情</span>
                            </div>

                            <div class="emoji_wrap_list" v-show="emojiShow" ref="emoji_target">
                                <ul class="emoji_wrap flex flex-wrap ">
                                    <li class="emoji_item flex justify-center" v-for="(item, i) in emojiList"
                                        @click.stop="(e) => onEmoji(e, item)" :title="item.name" :key="i"> <img
                                            :src="item.path" :alt="item.name" class="emoji_img cursor-pointer ">
                                    </li>
                                </ul>
                            </div>

                        </div>
                    </div>
                    <div class="right ">
                        <yh-button theme="primary" size="medium">评论</yh-button>

                    </div>
                </div>
            </div>

        </div>
        <div class="header-title flex items-center">
            <div class="flex items-center">
                热门评论
                <svg data-v-5fe91717="" width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg"
                    class="">
                    <path data-v-5fe91717=""
                        d="M12.8 5.2C13.9532 6.46 14.8 8.2 14.8 10C14.7039 12.8937 12.6843 15.1706 9.97973 15.8159C10.359 12.3442 7.77588 9.35406 7.77588 9.35406C7.77588 9.35406 7.99512 13.7064 6.79514 15.8104C4.03715 15.1428 2 12.7806 2 9.8C2 7.776 2.9336 5.9728 4.4 4.8C5.8608 3.7056 6.8 1.9656 6.8 0C9.684 0.4368 11.894 2.9264 11.894 5.932C11.894 6.5012 11.746 7.0652 11.6 7.6C12.1264 6.9024 12.6184 6.0876 12.8 5.2Z">
                    </path>
                </svg>

            </div>

        </div>
        <div class="comment_item flex">
            <el-image :src="'//www.gaobug.com/img/avatar/avatar.png'" lazy class="comment_avatar" fit="cover" />
            <div class="comment_box">
                <div class="comment_main">
                    <div class="user-box">
                        <span class="name"> {{ 'test用户' }}</span>
                        <span class="time"> {{ '刚刚' }}</span>
                    </div>
                    <p v-html="handleStr(text)" class="discuss_item"></p>
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
        <div style="height:400px"></div>
    </div>
</template>

<style scoped lang='scss'>
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

        .input_inner {
            flex: 1;

            .comment-action {
                margin-top: 16px;
            }

            .left {}

            .emoji_door {
                position: relative;
                cursor: pointer;
                color: var(--yh-text-color-secondary);

                .icon {
                    fill: var(--yh-text-color-secondary);
                }

                .name {
                    line-height: 1;
                    margin-left: 4px;
                }
            }
        }

        .commitedit {
            min-height: 58px;
            height: auto;
            word-break: break-all;
        }
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
            height: 28px;
        }
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
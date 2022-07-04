<script setup lang='ts'>
import Props from "./props"
import {TARGET_CONTAINER} from "../type"
import { scrollTo,getScrollContainer,getScroll } from "yhht-plus/utils"
const props = defineProps(Props)
const goComment =async (): Promise<void> => {
    const comment:TARGET_CONTAINER =  getAnchorTarget("comment")
    if (!comment) return;
    const scrollContainer = window
    const scrollTop = getScroll(scrollContainer);
    const offsetTop = getOffsetTop(comment, scrollContainer);
    const top = scrollTop + offsetTop - 74;
    await scrollTo(top, {});
    // console.log(comment,scrollContainer,scrollTop,offsetTop,top);
}
/**
    * 获取锚点对应的target元素
    * @param {string} id
    */
const getAnchorTarget = (id: string): HTMLElement | undefined => {
    const anchor = document.getElementById(id);
    if (!anchor) {
        return;
    }
    return anchor;
};
 function getOffsetTop(target: HTMLElement, container: TARGET_CONTAINER): number {
  const { top } = target.getBoundingClientRect();
  if (container === window) {
    // 减去document的边框
    return top - document.documentElement.clientTop;
  }
  return top - (container as HTMLElement).getBoundingClientRect().top;
}
</script>

<template>
    <div class='detail-actions detail-root'>
        <div :badge="likeNum" class=" action_item">
            <i class="dd-icon-dianzan_kuai icon"></i>
        </div>
        <div :badge="commentNum" class=" action_item" @click="goComment">
            <i class="dd-icon-pinglun1 icon"></i>
        </div>
        <div :badge="collectCount" class=" action_item">
            <i class="dd-icon-shoucang1 icon"></i>
        </div>
     
    </div>
</template>

<style scoped lang='scss'>
.detail-actions {
    position: fixed;
    top: 140px;
    margin-left: -70px;

    .action_item {
        position: relative;
        margin-bottom: 20px;
        width: 48px;
        height: 48px;
        background-color: var(--yh-bg-color-container);
        border-radius: 50%;
        // box-shadow: var(--yh-shadow-1);
        box-shadow: $primary-shadow;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;

        .icon {
            font-size: 20px;
            color: var(--yh-text-color-placeholder);
        }

        &::after {
            content: attr(badge);
            position: absolute;
            top: 0;
            left: 75%;
            height: 17px;
            line-height: 17px;
            padding: 0 5px;
            border-radius: 9px;
            font-size: 11px;
            text-align: center;
            white-space: nowrap;
            background-color: var(--bages-bg);
            color: var(--bages-color);
        }

        &:hover {
            background: var(--yh-bg-color-container-hover);

            .icon {
                color: var(--yh-text-color-secondary);
            }
        }
    }
}
</style>
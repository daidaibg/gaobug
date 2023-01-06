<script setup lang="ts">
import Props from "./props";
import { useBlogAction } from "@/hook/modules/use-blog-action";
import { articleDetailsConfig } from "@/config/article";
import { windowScrollTo } from "@/utils/scroll";
import { useRouter } from "vue-router";

const { blogLike } = useBlogAction();
const props = defineProps(Props);
const router = useRouter();

const emits = defineEmits<{
  (e: "like", res: any): void;
}>();

//点赞
const onLike = () => {
  blogLike(
    {
      targetId: props.articleId,
      targetType: 1,
      likeFlag: 1,
    },
    {
      success: (res: any) => {
        // console.log(res);
        emits("like", res);
      },
    }
  );
};

const goComment = async (): Promise<void> => {
  const id = "#" + articleDetailsConfig.commentAnchor;
  router.replace({ hash: id });
  windowScrollTo(id, 74);
  // console.log(comment,scrollContainer,scrollTop,offsetTop,top);
};




</script>

<template>
  <div class="detail-actions detail-root">
    <div class="action_item" @click="onLike">
      <i class="dd-icon-dianzan_kuai icon"></i>
      <span class="badge" v-show="likeNum != 0">{{ likeNum }}</span>
    </div>
    <div class="action_item" @click="goComment">
      <i class="dd-icon-pinglun1 icon"></i>
      <span class="badge" v-show="commentNum != 0">{{ commentNum }}</span>
    </div>
    <div class="action_item">
      <i class="dd-icon-shoucang1 icon"></i>
      <span class="badge" v-show="collectCount != 0">{{ collectCount }}</span>
    </div>
  </div>
</template>

<style scoped lang="scss">
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

    .badge {
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

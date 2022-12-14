<script setup lang="ts">
import { RouterEnum } from "@/enums";
import { useRouter } from "vue-router";
const router = useRouter();
const toolList = [
  {
    title: "常用工具",
    id: 1,
    list: [
      {
        title: "格式化工具",
        id: "11",
        type: "inner",
        img: "https://www.gaobug.com/blog-cloud-tool/img/json_format.png",
        url: RouterEnum.JsonFormat,
      },
      {
        title: "表格读取",
        id: "12",
        type: "inner",
        img: "https://www.gaobug.com/blog-cloud-tool/img/xlsx.png",
        url: RouterEnum.ReadXlsx,
      },
    ],
  },
  {
    title: "娱乐工具",
    id: 2,
    list: [
      {
        title: "弹跳爱心",
        id: 21,
        type: "out",
        img: "https://www.gaobug.com/blog-cloud-tool/img/love.png",
        url: "https://www.gaobug.com/blog-cloud-tool/love.html",
      },
    ],
  },
];

const aHrefFormat = (toolItem: any) => {
  console.log(toolItem);
  if (toolItem.type == "out") {
    return toolItem.url;
  }
  const href = router.resolve({ path: toolItem.url });
  return href.href;
};
</script>

<template>
  <div class="gb-tool comments gaobug">
    <div class="tool_inner">
      <div v-for="item in toolList" :key="item.id" class="tool_wrap">
        <h1 class="nav_title">{{ item.title }}</h1>
        <ul class="tool_item_wrap">
          <li
            class="tool_item"
            v-for="toolItem in item.list"
            :key="toolItem.id"
          >
            <a
              :href="aHrefFormat(toolItem)"
              target="_blank"
              rel="noopener noreferrer"
              class=""
            >
              <div class="tool_item_img_wrap">
                <img
                  :src="toolItem.img"
                  :alt="toolItem.title"
                  class="tool_item_img"
                />
                <img
                  :src="toolItem.img"
                  :alt="toolItem.title"
                  class="tool_item_img back"
                />
              </div>
              <span class="tool_item_title">{{ toolItem.title }}</span>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.gb-tool {
}
.tool_inner {
  padding: 16px;
  box-sizing: border-box;
  background-color: var(--dd-component-card-background);
  backdrop-filter: blur(10px);
  border-radius: 16px;
}
.tool_wrap {
  width: 100%;

  .nav_title {
    color: var(--yh-text-color-primary);
    font-weight: 900;
    line-height: 1.6;
    margin-bottom: 8px;
    margin-top: 8px;
  }
}
.tool_item_wrap {
  display: flex;
  flex-wrap: wrap;
  box-sizing: border-box;
  padding: 5px;
  .tool_item {
    width: 96px;
    height: 96px;
    border-radius: 11px;

    a {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 100%;
    }
    .tool_item_img_wrap {
      position: relative;
      perspective: 100px;
      background-color: var(--yh-bg-color-container);
      box-shadow: var(--yh-shadow-3);
      width: 56px;
      height: 56px;
      border-radius: 16px;
      border: transparent 1px solid;
      margin-bottom: 12px;
    }
    .tool_item_img {
      width: 46px;
      height: 46px;
      transform-style: preserve-3d;
      transition: transform 1.2s cubic-bezier(0.66,-0.47,0.33,1.5);
      backface-visibility: hidden;
      position: relative;
      left: 5px;
      top: 5px;
      transform: rotateY(0deg);
      &.back {
        position: absolute;
        transform: rotateY(180deg);
      }
    }
    .tool_item_title {
      color: var(--yh-text-color-primary);
      font-size: 14px;
      line-height: 1;
    }

    &:hover {
      background: var(--yh-bg-color-container-hover);
      .tool_item_img_wrap {
        border-color: var(--yh-text-color-link);
        /* 隐藏旋转div元素的背面 */
        // backface-visibility: hidden;
      }
      .tool_item_img {
        transform: rotateY(180deg);
        &.back {
          transform: rotateY(360deg);
        }
      }
      .tool_item_title {
        color: var(--yh-text-color-link);
      }
    }
  }
}
</style>

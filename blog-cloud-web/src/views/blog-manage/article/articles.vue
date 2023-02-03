<script setup lang="ts">
import { ref, reactive } from "vue";
import {
  ElTabs,
  ElTabPane,
  ElMessage,
  ElPagination,
  ElDropdown,
  ElDropdownItem,
  ElMessageBox,
  MessageParamsWithType,
} from "element-plus";
import type { TabsPaneContext } from "element-plus";
import { articlesType, articlesListType } from "./articles-type";
import { useRouter } from "vue-router";
import { getUserBlogList, deleteBlog } from "@/api/modules/manage";

const router = useRouter();
const blogData = reactive<articlesType>({
  current: 1,
  size: 15,
  total: 0,
  list: [],
  publish: "1",
});
//获取自己博客列表
const getBlog = () => {
  blogData.list = [];
  let param = {
    current: blogData.current,
    size: blogData.size,
    publish: blogData.publish,
  };
  getUserBlogList(param).then((res: any) => {
    // console.log(res);
    if (res.code == 200) {
      blogData.list = res.data.records;
      blogData.total = res.data.total;
    } else ElMessage.error(res.msg);
  });
};
const currentChange = (current: number) => {
  console.log(current);
  blogData.current = current;
  getBlog();
};
const tabClick = (tab: TabsPaneContext, event: Event) => {
  blogData.publish = tab.paneName;
  getBlog();
};
const jump = (path: string, item: articlesListType) => {
  router.push({ path: path + "/" + item.id });
};
const jumpEdit = (path: string, item: articlesListType) => {
  const href = router.resolve({ path: path, query: { articleId: item.id } });
  window.open(href.href, "_blank");
  // router.push({ path: path, query: { articleId: item.id } })
};
//点击按钮
const command = (commandName: string | undefined, item: any) => {
  // console.log(commandName, item);
  ElMessageBox.confirm("确定要删除该文章吗?", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
    customClass: "message-box-default",
    // center: true,
    confirmButtonClass: "button-no-active",
  })
    .then(() => {
      deleteBlog(item.id).then(
        (res: { code: number; msg: MessageParamsWithType }) => {
          if (res.code === 200) {
            getBlog();
            ElMessage.success("删除成功");
          } else {
            ElMessage.error(res.msg);
          }
        }
      );
    })
    .catch(() => {
      //   取消删除
    });
};
getBlog();
</script>

<template>
  <div class="articles-header flex items-center">
    <el-tabs
      v-model="blogData.publish"
      class="articles-tabs"
      @tab-click="tabClick"
    >
      <el-tab-pane label="文章" name="1">
        <ul class="articles_list">
          <li
            v-for="item in blogData.list"
            :key="item.id"
            class="articles_list_item"
          >
            <h2 class="title">
              <!-- @click="jump('/article/details', item)" -->
              <router-link
                tag="详情"
                target="_blank"
                :to="'/article/details/' + item.id"
              >
                {{ item.title }}</router-link
              >
            </h2>
            <div class="flex justify-between">
              <div class="info-box_action flex items-center">
                <div class="info-box_action-item">
                  <span>{{ item.updateTime }}</span>
                </div>
                <div class="info-box_action-item">
                  <i class="dd-icon-liulan icon"></i>
                  <span>{{ item.clickCount }}</span>
                </div>
                <div class="info-box_action-item">
                  <i class="dd-icon-dianzan icon"></i>
                  <span>{{ item.collectCount }}</span>
                </div>
                <div class="info-box_action-item">
                  <i class="dd-icon-pinglun icon"></i>
                  <span>{{ item.comment || 0 }}</span>
                </div>
              </div>
              <div class="actions flex">
                <div class="mr-2 item" @click="jumpEdit('/write/essay', item)">
                  编辑
                </div>
                <div class="mr-2 item" @click="jump('/article/details', item)">
                  浏览
                </div>
                <i class="not-italic item">
                  <el-dropdown
                    @command="(commands: string | undefined) => command(commands, item)"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48">
                      <path fill="none" d="M0 0h48v48H0z"></path>
                      <path
                        d="M35 21h6v6h-6v-6zm-22 0v6H7v-6h6zm14 0v6h-6v-6h6z"
                      ></path>
                    </svg>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item command="delete"
                          >删除</el-dropdown-item
                        >
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </i>
              </div>
            </div>
          </li>
        </ul>
        <div class="flex items-center justify-center mt-4">
          <el-pagination
            background
            layout="prev, pager, next"
            :page-size="blogData.size"
            :total="blogData.total"
            @current-change="currentChange"
            hide-on-single-page
          />
        </div>
      </el-tab-pane>
      <el-tab-pane label="草稿箱" name="0">
        <ul class="articles_list">
          <li
            v-for="item in blogData.list"
            :key="item.id"
            class="articles_list_item"
          >
            <h2 class="title">
              <router-link
                tag="详情"
                target="_blank"
                :to="'/write/essay?articleId=' + item.id"
              >
                {{ item.title }}</router-link
              >
            </h2>
            <div class="flex justify-between">
              <div class="info-box_action flex items-center">
                <div class="info-box_action-item">
                  <span>{{ item.updateTime }}</span>
                </div>
                <div class="info-box_action-item">
                  <i class="dd-icon-liulan icon"></i>
                  <span>{{ item.clickCount }}</span>
                </div>
                <div class="info-box_action-item">
                  <i class="dd-icon-dianzan icon"></i>
                  <span>{{ item.collectCount }}</span>
                </div>
                <div class="info-box_action-item">
                  <i class="dd-icon-pinglun icon"></i>
                  <span>{{ item.comment || 0 }}</span>
                </div>
              </div>
              <div class="actions flex">
                <div class="mr-2 item" @click="jumpEdit('/write/essay', item)">
                  编辑
                </div>
                <!-- <div class="mr-2 item" @click="jump('/article/details', item)"> 浏览</div> -->
                <i class="not-italic item">
                  <el-dropdown
                    @command="(commands: string | undefined) => command(commands, item)"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48">
                      <path fill="none" d="M0 0h48v48H0z"></path>
                      <path
                        d="M35 21h6v6h-6v-6zm-22 0v6H7v-6h6zm14 0v6h-6v-6h6z"
                      ></path>
                    </svg>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item command="delete"
                          >删除</el-dropdown-item
                        >
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </i>
              </div>
            </div>
          </li>
        </ul>
        <div class="flex items-center justify-center mt-4">
          <el-pagination
            background
            layout="prev, pager, next"
            :page-size="blogData.size"
            :total="blogData.total"
            @current-change="currentChange"
            hide-on-single-page
          />
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped lang="scss">
$border-color: var(--yh-border-level-1-color);
$color-placeholder: var(--yh-text-color-placeholder);
$color-brand: var(--yh-brand-color);
$color-secondary: var(--yh-text-color-secondary);
$color-primary: var(--yh-text-color-primary);

.articles-header {
  :deep(.el-tabs) {
    // height: 48px;
    width: 100%;

    .el-tabs__header {
      margin: 0;
      height: 48px;
    }

    .el-tabs__nav-wrap,
    .el-tabs__nav-scrol,
    .el-tabs__nav,
    .el-tabs__item {
      height: 48px;
      line-height: 48px;
    }
  }

  .articles_list {
    width: 100%;
  }
}

.articles_list {
  .articles_list_item {
    padding: 4px 0;
    box-sizing: border-box;
    margin: 0 20px;
    border-bottom: 1px solid $border-color;

    .info-box_action {
      font-size: 12px;
      margin: 10px 0;

      .info-box_action-item {
        display: flex;
        align-items: center;
        margin-right: 20px;
        line-height: 1;

        .icon {
          font-size: 14px;
          margin-right: 4px;
          color: $color-placeholder;
          font-weight: 900;
        }

        span {
          color: $color-secondary;
        }
      }
    }

    .title {
      color: $color-primary;
      line-height: 1.6;
      margin: 8px 0;
      cursor: pointer;

      a {
        display: block;
      }

      &:hover {
        color: var(--yh-brand-color-hover);
      }
    }

    .actions {
      font-size: 14px;
      cursor: pointer;

      .not-italic {
        width: 24px;
        height: 24px;
        border-radius: 4px;

        svg {
          width: 100%;
          height: 100%;
          fill: currentColor;
          pointer-events: none;
        }

        &:hover {
          background-color: var(--yh-bg-color-container-hover);
        }
      }

      .item {
        color: var(--yh-text-color-primary);

        &:hover {
          color: var(--yh-brand-color-hover);
        }
      }
    }
  }
}
</style>

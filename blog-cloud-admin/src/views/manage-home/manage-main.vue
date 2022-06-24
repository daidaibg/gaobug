<!--
 * @Author: daidai
 * @Date: 2021-12-06 11:06:15
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-23 15:08:49
-->
<template>
  <el-container class="home">
    <el-header><Headers /></el-header>
    <el-container>
      <el-aside :width="widths">
        <el-menu
          :default-active="onRoutes"
          :router="true"
          :collapse="collapse"
          :unique-opened="true"
          :collapse-transition="true"
          ref="menus"
          class="web-scroll-beautify"
        >
          <template v-for="item in menuList">
            <template v-if="item.list">
              <el-submenu :index="item.url" :key="item.url">
                <template slot="title">
                  <!-- <i :class="item.icon"></i> -->
                  <div class="iconimg" v-if="item.url">
                    <img :src="gemenimg(item.url)" alt="" />
                  </div>

                  <span slot="title">{{ item.name }}</span>
                </template>
                <template v-for="subItem in item.list">
                  <el-submenu
                    v-if="subItem.list"
                    :index="subItem.url"
                    :key="subItem.id"
                  >
                    <template slot="title">{{ subItem.name }}</template>
                    <el-menu-item
                      v-for="(threeItem, i) in subItem.list"
                      :key="i"
                      :index="threeItem.url"
                      :route="{
                        path: threeItem.url,
                        name: threeItem.name,
                      }"
                      >{{ threeItem.name }}</el-menu-item
                    >
                  </el-submenu>
                  <el-menu-item
                    v-else
                    :key="subItem.url"
                    :index="subItem.url"
                    :route="{
                      path: subItem.url,
                      meta: {
                        name: 111,
                      },
                    }"
                    >{{ subItem.name }}</el-menu-item
                  >
                </template>
              </el-submenu>
            </template>
            <template v-else>
              <el-menu-item :index="item.url" :key="item.url">
                <!-- <i :class="item.icon"></i> -->
                <div class="iconimg" v-if="item.url">
                  <img :src="gemenimg(item.url)" alt="" />
                </div>
                <span slot="title">{{ item.name }}</span>
              </el-menu-item>
            </template>
          </template>
          <!-- <template v-for="item in menuList">
            <template v-if="item.list && item.list.length > 0">
              <SubMenu :menuChidren="item" :key="item.url" />
            </template>
            <template v-else>
              <el-menu-item :index="item.url" :key="item.url">
                <i :class="item.icon"></i>
                <span slot="title">{{ item.name }}</span>
              </el-menu-item>
            </template>
          </template> -->
        </el-menu>
      </el-aside>
      <el-main class="el-main-content flex flex-direction">
        <div
          class="tags-wrap justify-between flex"
          ref="tagswidth"
          v-show="tagsData.show"
        >
          <ul class="tags flex align-center">
            <li
              v-for="(item, i) in tagsList"
              :key="item.path"
              class=""
              :class="{ actiovTag: onRoutes == item.path }"
            >
              <!-- :style="{ width: tagwidtsData[item.name.length] }" -->

              <span @click="selectTags(item)">{{ item.name }}</span>
              <i class="el-icon-close" @click="removeTags(item, i)"></i>
            </li>
          </ul>
          <div class="tags-close-box">
            <el-dropdown @command="handleTags">
              <el-button size="mini" class="tag_but">
                标签选项
                <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu size="small" slot="dropdown" class="tag-memou">
                <el-dropdown-item command="other">关闭其他</el-dropdown-item>
                <el-dropdown-item command="all">关闭所有</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
        <router-view />
        <!-- <transition name="el-fade-in-linear"> </transition
      > -->
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Headers from "./manage-header.vue";
import SubMenu from "./manage-submenu.vue";
const tagwidtsData = {
  1: "22px",
  2: "44px",
  3: "66px",
  4: "88px",
  5: "100px",
  6: "112px",
  7: "124px",
  8: "136px",
  9: "148px",
  10: "160px",
  11: "172px",
  12: "184px",
};
export default {
  components: { Headers, SubMenu },
  data() {
    return {
      tagsList: [],
      tagsData: {
        show: true,
        currentData: null,
      },
      // tagwidtsData: tagwidtsData,
    };
  },
  computed: {
    widths() {
      if (this.menuList.length == 0) {
        if (this.collapse) {
          return "65px";
        } else {
          return "200px";
        }
      } else {
        return "auto";
      }
    },
    onRoutes() {
      return this.$route.path;
    },
    collapse() {
      return this.$store.state.collapsed;
    },
    menuList: {
      get() {
        return this.$store.state.menus.menuList;
      },
      set(val) {
        // this.$store.commit("menus/updateMenuList", val);
      },
    },
  },
  watch: {
    $route(newValue, oldValue) {
      this.handleRouter(newValue);
    },
  },
  created() {},

  mounted() {
    this.handleRouter(this.$route);
  },
  methods: {
    handleTags(command) {
      command === "other" ? this.closeOther() : this.closeAll();
    },
    removeTags(itemTags, i) {
      this.tagsList.splice(i, 1);
      const item = this.tagsList[i] ? this.tagsList[i] : this.tagsList[i - 1];
      if (item) {
        itemTags.path === this.$route.fullPath && this.$router.push(item.path);
      } else {
        this.$router.push("/");
      }
    },
    selectTags(itemTags) {
      this.$router.push({
        path: itemTags.path,
      });
    },
    handleRouter(route) {
      if (route.path == "/home") {
        this.tagsData.show = false;
      } else {
        this.tagsData.show = true;
        this.setTags(route);
      }
    },
    // 关闭全部标签
    closeAll() {
      this.tagsList = [];
      this.tagsData.show = false;
      this.$router.push("/");
    },
    // 关闭其他标签
    closeOther() {
      const curItem = [];
      this.tagsList.map((item) => {
        // console.log(item)
        if (item.path === this.$route.fullPath) {
          curItem.push(item);
        }
      });
      this.tagsList = curItem;
    },
    // 设置标签
    setTags(route) {
      const isExist = this.tagsList.some((item) => {
        return item.path === route.fullPath;
      });
      if (!isExist) {
        let num = Math.floor(this.$refs.tagswidth.clientWidth / 130);
        let lengths = this.tagsList.length;
        if (lengths >= num) {
          // this.tagsList.pop();
          this.tagsList.splice(num - 1, lengths - (num - 2));
        }
        this.tagsData.currentData = null;
        this.getMenuData(this.menuList, route.path);
        this.tagsList.push({
          title: route.meta.title,
          path: route.fullPath,
          name: this.tagsData.currentData
            ? this.tagsData.currentData.name
            : route.meta.title,
        });
      }
    },
    gemenimg(type) {
      type = type.replace(/[\/]/g, "");
      let url = "";
      try {
        url = require("@/assets/img/menus/" + type + ".png");
      } catch (error) {
        url = require("@/assets/img/menus/xtgl.png");
      }
      return url;
    },
    getMenuData(list, url) {
      for (let i = 0; i < list.length; i++) {
        const item = list[i];
        if (item.url == url) {
          this.tagsData.currentData = item; // 结果赋值
        } else {
          if (item.list) {
            this.getMenuData(item.list, url);
          }
        }
      }
    },
  },
};
</script>
<style lang='scss' scoped>
.home {
  width: 100%;
  height: 100%;
  min-width: 1000px;
  > .el-header {
    height: $index-height !important;
    @include background_color("primary_color");
    padding: 0 20px 0 0;
  }
  > .el-container {
    overflow: hidden;
    .el-aside {
      [class^="blq-icon-"] {
        font-size: 14px;
        margin-right: 5px;
        width: 24px;
        text-align: center;
        @include font_color("primary_color");
      }
      /deep/ .el-submenu.is-active {
        > .el-submenu__title {
          span {
            @include font_color("primary_color");
          }
          .el-submenu__icon-arrow {
            @include font_color("primary_color");
            // font-weight: 900;
          }
        }
        .el-menu-item {
          background: #f0f7ff;
        }
      }
      // 展开 :not(.el-menu--collapse)
      .el-menu {
        height: 100%;
        overflow-x: hidden;
        overflow-y: auto;
        // &::-webkit-scrollbar {
        //   display: none; /* Chrome Safari */
        // }

        .iconimg {
          display: inline-block;
          margin-right: 12px;
          // vertical-align: middle;
          img {
            width: 18px;
          }
        }
        .el-menu-item {
          &.is-active {
            // background: linear-gradient(to right, #4AA8FF, #8CC7FF);
            background: linear-gradient(to right, #4da8fd, #99ceff);
            color: #ffffff;
            position: relative;
            &::before {
              content: "";
              display: block;
              position: absolute;
              width: 8px;
              height: 100%;
              background: #108cff;
              left: 0;
              top: 0;
            }
          }
        }
      }
      .el-menu:not(.el-menu--collapse) {
        .el-menu-item {
          min-width: 220px;
        }
      }
      //缩起来
      /deep/ .el-menu--collapse {
        .is-active {
          .el-submenu__title,
          .el-tooltip {
            background: linear-gradient(to right, #99cdfd, #c7dff7);
          }
          &.el-menu-item {
            &::before {
              background: transparent;
            }
          }
        }
      }
    }
  }
  .el-main-content {
    background: $content-background;
    // background: #f3f5fa;
    padding: 0px;
    overflow-y: hidden;
  }
}
.tags-wrap {
  height: $index-tags-height;
  min-height: $index-tags-height;
  line-height: $index-tags-height;
  background: #fff;
  top: 60px;
  z-index: 10;
  width: 100%;
  margin-bottom: $content-padding;
  .tags-close-box {
    margin-right: 10px;
    .el-dropdown {
      display: flex;
      align-items: center;
      height: 100%;
    }
  }
}
.tags {
  box-sizing: border-box;
  padding-left: 10px;
  li {
    font-size: 13px;
    cursor: pointer;
    padding: 0 10px;
    transition: all 0.2s;
    color: #4d4848;
    min-width: 62px;
    overflow: hidden;
    text-align: center;
    position: relative;
    margin-left: 1px;
    .el-icon-close {
      margin-left: 4px;
      display: inline-block;
      &:hover {
        // font-size: 14px;
      }
    }
    span {
      height: 100%;
      display: inline-block;
    }
    &::before {
      position: absolute;
      bottom: 0;
      left: 0;
      content: "";
      width: 100%;
      height: 2px;
      @include background_color("primary_color");
      opacity: 0;
      transition: all 0.2s;
    }
    &:hover,
    &.actiovTag {
      color: #000;
      // font-size: 14px;
      color: #303133;
      background-color: #f5f7fa;
      span {
        font-weight: 900;
      }
      i {
        font-weight: 900;
      }
      &::before {
        opacity: 1;
      }
    }
  }
}
</style>
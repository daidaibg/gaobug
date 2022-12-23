<script setup lang="ts">
import {
  onBeforeMount,
  onMounted,
  ref,
  reactive,
  nextTick,
  markRaw,
} from "vue";
import monacoEditor from "@/components/monaco-editor";
import CodeFormatCommon from "./common/code-format-common.vue";
import { InfoIcon } from "@/components/icons";
import { CustomMouseMenu } from "@/components/contextmenu";
import type { Options, Theme } from "@/components/monaco-editor";
import type Node from "element-plus/es/components/tree/src/model/node";
import { Logo } from "@/components/header/logo";
import { languageList, catalogueListDefault } from "./code-format-config";
import { languageIcons } from "@/config/languageIcons";
import { ElMessage, ElMessageBox } from "element-plus";
import { setLocalStorage, getLocalStorage } from "@/utils/modules/storage";
import { guid } from "@/utils/current";
import { seachTreeData } from "@/utils/tree";
import type { FileItemType } from "./type";
import { CommonEnums } from "./type";
import { userThemeStore } from "@/store";

const themeStore = userThemeStore();
const settingConfig = reactive({
  menuActive: "file",
});
//右键对象
let contextMenuCtx: any = null;

//设置弹窗ref
const settingRef = ref();
//侧边栏目录列表
const catalogueList = ref<FileItemType[]>([]);
//侧边栏目录ref
const catalogueRef = ref();
//当前选中的
const currentActiveFile = ref();
//编辑器配置
const editorOption = reactive<{
  theme: Theme;
  commonKeyword: string;
  editValue: string;
  languageModel: string;
  hightChange: boolean;
  options: {
    minimap: {
      enabled: boolean; //显示小地图
    };
  };
}>({
  theme: "vs",
  commonKeyword: "",
  editValue: "",
  languageModel: "json",
  hightChange: false,
  options: {
    minimap: {
      enabled: true,
    },
  },
});

//头部文件列表
const navFileList = ref<FileItemType[]>([]);

//editor实例加载完成
const editorMounted = (editor: any) => {
  console.log("%ceditor实例加载完成", "color: #229453");
  // console.log("editor实例加载完成", editor);
};
//点击每一项
const onCommonClick = (commonItem: any) => {
  editorOption.theme = commonItem.value;
  themeStore.changeThemeNoLocal(commonItem.type);
  setLocalStorage("codeFormatEditConfig", {
    theme: commonItem,
  });
  // console.log(editorOption,commonItem);
};

/**
 * @description: nav选中文件改变
 * @param {any} fileNavItem  文件的config
 * @return {*}
 */
const navActiveChange = (fileNavItem: any): any => {
  const currentFileData = seachTreeData(catalogueList.value, fileNavItem.id);
  saveCurrentCatalogue();
  setCurrentCheckCatalogue(fileNavItem.id);
  switchEditData(currentFileData.content, fileNavItem.language);
};

//右键
const onCataloguetNodecontextmenu = (
  event: any,
  data: FileItemType,
  node: Node,
  nodeTemplate: any
) => {
  // console.log({ event, data, node, nodeTemplate });
  // console.log(catalogueList.value.length);
  event.preventDefault();
  contextMenuCtx = CustomMouseMenu({
    el: nodeTemplate.vnode.el,
    menuList: [
      {
        label: "重命名",
        tips: "Edit",
        fn: () => rename(node, data),
      },
      {
        label: "删除",
        tips: "Delete",
        disabled: catalogueList.value.length == 1,
        fn: () => deleteFile(node, data),
      },
    ],
  });
  const { x, y } = event;
  contextMenuCtx.show(x, y);
};
//重命名
const rename = (node: Node, nodeData: FileItemType) => {
  // console.log("rename", node, nodeData);
  //不自动获取焦点问题，点击右键菜单触发点击时间，input焦点消失
  const timer = setTimeout(() => {
    clearTimeout(timer);
    ElMessageBox.prompt("请编辑文件名称", {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      inputErrorMessage: "",
      inputValue: nodeData.title,
      draggable: true,
      type: "info",
      icon: markRaw(InfoIcon),
    })
      .then(({ value }) => {
        const laguageData = getLaguageData(value);
        nodeData.title = value;
        nodeData.language = laguageData.fileLanguage;
        nodeData.icon = laguageData.fileIconData.icon.name;
        //修改编辑器语言
        editorOption.languageModel= laguageData.fileLanguage
        //处理nav导航数据
        const navIndex = navFileList.value.findIndex(
          (d: any) => d.id === nodeData.id
        );
        if (navIndex > -1) {
          navFileList.value[navIndex].title = value;
          navFileList.value[navIndex].icon = laguageData.fileIconData.icon.name;
          navFileList.value[navIndex].language = laguageData.fileLanguage;
        }
      })
      .catch((err) => {
        if (err.indexOf("cancel") == -1) {
          throw new Error(err);
        } else {
          console.info("取消");
        }
      });
  }, 20);
};
//删除
const deleteFile = (node: Node, nodeData: FileItemType) => {
  const parent = node.parent;
  const children: any = parent.data.children || parent.data;
  const index = children.findIndex((d: any) => d.id === nodeData.id);
  children.splice(index, 1);
  catalogueList.value = [...catalogueList.value];
  //处理nav数据
  const navIndex = navFileList.value.findIndex(
    (d: any) => d.id === nodeData.id
  );
  onRemoveNav(nodeData, navIndex);
};
//点击新增文件
const onAddfile = () => {
  ElMessageBox.prompt("请输入文件名称", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    inputErrorMessage: "",
    draggable: true,
    autofocus: true,
  })
    .then(({ value }) => {
      addFile(value);
    })
    .catch((err) => {
      console.error(err);
    });
};
//新增文件
const addFile = (data: string) => {
  const laguageData = getLaguageData(data);
  const newFileData = {
    title: data,
    language: laguageData.fileLanguage,
    content: "",
    id: guid(),
    icon: laguageData.fileIconData.icon.name,
  };
  catalogueList.value.push(newFileData);
  saveCurrentCatalogue();
  setCurrentCheckCatalogue(newFileData.id);
  switchEditData("", laguageData.fileLanguage);
  addNavData(newFileData);
};
//获取语言数据信息
const getLaguageData = (data: string): any => {
  const fileNameSplit = data.split(".");
  let fileLanguage: any = fileNameSplit[fileNameSplit.length - 1];
  fileLanguage = languageList.filter((item) => {
    return item.label == fileLanguage;
  });
  fileLanguage = fileLanguage.length >= 1 ? fileLanguage[0].value : "plaintext";
  let fileIconData: any = { icon: { name: "document" } };
  for (let index = 0; index < languageIcons.length; index++) {
    const element = languageIcons[index];
    if (element.ids.includes(fileLanguage)) {
      fileIconData = element;
      break;
    }
  }
  return {
    fileIconData,
    fileLanguage,
  };
};
//新增文件导航栏列表
const addNavData = (fileData: FileItemType) => {
  const isAddData = navFileList.value.find((item) => item.id == fileData.id);
  if (isAddData) return;
  navFileList.value.push(fileData);
};
//移除nav文件
const onRemoveNav = (fileNavItem: FileItemType, index: number) => {
  if (index >= 0) {
    navFileList.value.splice(index, 1);
  }
  if (navFileList.value.length == 0) {
    addNavData(catalogueList.value[0]);
  }
  if (index >= 1) {
    navActiveChange(navFileList.value[index - 1]);
  } else {
    navActiveChange(navFileList.value[0]);
  }
};

//获取文件icon
const getFileSvg = (iconname: string) => {
  return new URL(`../../../assets/file-icon/${iconname}.svg`, import.meta.url)
    .href;
};
//切换编辑器内容
const switchEditData = (content: string, language: string = "txt") => {
  editorOption.editValue = content;
  editorOption.languageModel = language;
};

//目录列表node点击事件
const catalogueNodeClick = (data: FileItemType) => {
  // console.log("catalogueNodeClick",data);
  saveCurrentCatalogue();
  currentActiveFile.value = data.id;
  addNavData({
    ...data,
  });
  switchEditData(data.content, data.language);
};
//选择切换主题
const onSelectTheme = () => {
  settingRef.value.hide();
  editorOption.commonKeyword = CommonEnums.theme;
};

const updateCatalogueNode = (treeList: any, id: any, obj: any) => {
  if (!treeList || !treeList.length) {
    return;
  }
  for (let i = 0; i < treeList.length; i++) {
    if (treeList[i].id == id) {
      treeList[i] = obj;
      break;
    }
    updateCatalogueNode(treeList[i].children, id, obj);
  }
};
//打开设置
const onSetting = () => {};
//设置当前选中的值
const setCurrentCheckCatalogue = async (id: string) => {
  currentActiveFile.value = id;
  await nextTick();
  catalogueRef.value.setCurrentKey(id);
};
//保存当前的数据
const saveCurrentCatalogue = () => {
  let currentFileData = seachTreeData(
    catalogueList.value,
    currentActiveFile.value
  );
  if (currentFileData) {
    currentFileData.content = editorOption.editValue;
  }
};

//即将离开当前页面（刷新或关闭）时触发
const pageBeforeunload = () => {
  console.log("pageBeforeunload");
  saveCurrentCatalogue();
  setFilesLocalStorage();
};
//本地存储
const setFilesLocalStorage = () => {
  setLocalStorage("codeFormatFileData", {
    catalogueList: catalogueList.value,
    navList: navFileList.value,
    active: currentActiveFile.value,
  });
};
//初始化编辑器配置
const initEditConfig = () => {
  const codeFormatEditConfig = getLocalStorage("codeFormatEditConfig");
  if (!codeFormatEditConfig) {
    return;
  }
  if (codeFormatEditConfig.theme) {
    themeStore.changeThemeNoLocal(codeFormatEditConfig.theme.type);
    editorOption.theme = codeFormatEditConfig.theme.value;
  }
};
// 初始化列表数据
const initCatalogList = async () => {
  const codeFormatFileData = getLocalStorage("codeFormatFileData");
  //如果本地存储没有得话直接附默认值
  if (!codeFormatFileData) {
    catalogueList.value = [{ ...catalogueListDefault }];
    addNavData(catalogueListDefault);
    setCurrentCheckCatalogue(catalogueListDefault.id);
    switchEditData(catalogueListDefault.content, catalogueListDefault.language);
    return;
  }
  catalogueList.value = codeFormatFileData.catalogueList;
  navFileList.value = codeFormatFileData.navList;
  currentActiveFile.value =
    codeFormatFileData.active || navFileList.value[0].id;
  const currentFileData = seachTreeData(
    catalogueList.value,
    currentActiveFile.value
  );
  if (currentFileData) {
    await nextTick();
    switchEditData(currentFileData.content, currentFileData.language);
    catalogueRef.value.setCurrentKey(currentFileData.id);
  }
};
//初始化页面
const init = () => {
  initEditConfig();
  initCatalogList()
};

init();

onMounted(() => {
  window.addEventListener("beforeunload", pageBeforeunload);
});

onBeforeMount(() => {
  window.removeEventListener("beforeunload", pageBeforeunload);
});
</script>

<template>
  <div class="json_format edit-tool-var" :class="editorOption.theme">
    <CodeFormatCommon
      v-model="editorOption.commonKeyword"
      :editorOption="editorOption"
      @clickItem="onCommonClick"
    >
    </CodeFormatCommon>
    <div class="code_format_setting">
      <div class="menubar-menu-button">
        <i class="dd-icon-mulu"></i>
      </div>
      <ul class="setting_menu">
        <li
          :class="{ menuActive: settingConfig.menuActive === 'file' }"
          class="setting_menu_item"
        >
          <i class="dd-icon-file"></i>
        </li>
      </ul>
      <ul class="setting_action">
        <li class="setting_menu_item">
          <el-popover
            placement="right"
            :width="200"
            :hide-after="0"
            trigger="click"
            :show-arrow="false"
            popper-class="setting_action_setting "
            ref="settingRef"
          >
            <template #reference>
              <i class="dd-icon-shezhi"> </i>
            </template>
            <ul class="setting_action_action">
              <li class="setting_action_action_item" @click="onSelectTheme()">
                颜色主题
              </li>
            </ul>
          </el-popover>
        </li>
      </ul>
    </div>
    <div class="json_format_nav">
      <div class="logo_wrap">
        <Logo class="tools-layout_logo" />
        <span>格式化工具</span>
      </div>
      <div class="nav_title">
        <div class="nav_title_front">
          <i class="yh-icons-arrow-down"></i>
        </div>
        <div class="nav_title_text">
          目录列表
          <el-tooltip content="数据只会存储到本地，不会进行上传，请妥善处理。">
            <i class="dd-icon-tishi"></i>
          </el-tooltip>
        </div>
        <div class="nav_title_action">
          <div class="nav_title_action_icon" @click="onAddfile">
            <i class="dd-icon-tianjiawenjian"></i>
          </div>
        </div>
      </div>
      <div class="catalogue—list">
        <el-tree
          :data="catalogueList"
          :props="{ children: 'children', label: 'title' }"
          @node-click="catalogueNodeClick"
          node-key="id"
          ref="catalogueRef"
          highlight-current
          @node-contextmenu="onCataloguetNodecontextmenu"
        >
          <template #default="{ node, data }">
            <span class="catalogue-list-tree-node">
              <div class="catalogue-list-file-icon">
                <img :src="getFileSvg(data.icon)" :alt="data.language" />
              </div>
              <span>{{ node.label }}</span>
            </span>
          </template>
        </el-tree>
      </div>
    </div>
    <div class="json_format_content">
      <nav class="file_nav_wrap">
        <div
          v-for="(item, i) in navFileList"
          @click="navActiveChange(item)"
          class="file_nav_item"
          :class="{ currentActiveFile: currentActiveFile === item.id }"
          :key="item.id"
        >
          <div class="file_nav_img">
            <img :src="getFileSvg(item.icon)" :alt="item.language" />
          </div>
          <span class="file_nav_title">{{ item.title }}</span>
          <div class="file_nav_close">
            <div
              class="file_nav_close_inner"
              @click.stop="onRemoveNav(item, i)"
              v-if="navFileList.length > 1"
            >
              <svg
                viewBox="0 0 1024 1024"
                xmlns="http://www.w3.org/2000/svg"
                width="14"
                height="14"
              >
                <path
                  fill="currentColor"
                  d="M764.288 214.592 512 466.88 259.712 214.592a31.936 31.936 0 0 0-45.12 45.12L466.752 512 214.528 764.224a31.936 31.936 0 1 0 45.12 45.184L512 557.184l252.288 252.288a31.936 31.936 0 0 0 45.12-45.12L557.12 512.064l252.288-252.352a31.936 31.936 0 1 0-45.12-45.184z"
                ></path>
              </svg>
            </div>
          </div>
        </div>
      </nav>
      <monacoEditor
        v-model="editorOption.editValue"
        :language="editorOption.languageModel"
        :hight-change="editorOption.hightChange"
        :options="(editorOption.options as any)"
        :theme="editorOption.theme"
        :read-only="false"
        @editor-mounted="editorMounted"
        class="json_format_editor"
      />
    </div>
  </div>
</template>
<style lang="scss">
.el-popover.setting_action_setting {
  padding: 8px 0;
}
</style>
<style scoped lang="scss">
@import "./code-format-var.scss";
@import "./code-format-dark.scss";
.json_format {
  width: 100%;
  height: 100vh;
  //   padding-top: 70px;
  position: relative;
  box-sizing: border-box;
  font-size: 14px;
  // padding-top: var(--header-height);
  // height: 100vh;
  display: flex;
  background-color: var(--format-bg-clolor);
  color: var(--format-text-color);

  .json_format_nav {
    width: 210px;
    flex-shrink: 0;
    box-sizing: border-box;
    outline-color: var(--format-nav-close-color);
  }
  .nav_title {
    height: 22px;
    display: flex;
    align-items: center;
    margin-bottom: 12px;
    cursor: pointer;
  }

  .nav_title_front {
    font-size: 14px;
    margin: 0 2px;
    padding: 0 2px;
    line-height: 1;
  }

  .nav_title_text {
    font-weight: 900;
    line-height: 1;

    i {
      font-weight: normal;
      font-size: 14px;
      line-height: 1;
      margin-left: 4px;

      &:hover {
        color: var(--yh-text-color-brand);
      }
    }
  }
  .nav_title_action {
    margin-left: auto;
  }

  .nav_title_action_icon {
    width: 20px;
    box-sizing: border-box;
    border-radius: 5px;
    text-align: center;
    margin-right: 4px;

    i {
      font-size: 14px;
      line-height: 1;
    }

    &:hover {
      background-color: var(--format-nav-close-color);
    }
  }
}

//   目录列表
.catalogue—list {
  :deep(.el-tree) {
    background: transparent;
    color: var(--format-text-color);
    .el-tree-node > .el-tree-node__content {
      padding-left: 4px;

      &:hover {
        background-color: var(--common-palete-hover-color);
      }
    }

    .el-tree-node.is-current > .el-tree-node__content {
      background-color: var(--formart-catalogue-current-bg);
      color: var(--format-text-color-anti);
    }
  }

  .catalogue-list-tree-node {
    display: flex;
    align-items: center;
  }

  .catalogue-list-file-icon {
    width: 22px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding-right: 4px;

    img {
      width: 16px;
    }
  }
}

//设置
.code_format_setting {
  width: 48px;
  flex-shrink: 0;
  background-color: var(--format-setting-bg-color);
  display: flex;
  flex-direction: column;

  .menubar-menu-button {
    color: rgba(255, 255, 255, 0.4);
    text-align: center;
    height: 35px;
    cursor: pointer;

    i {
      line-height: 35px;
      font-size: 16px;
    }

    &:hover {
      color: var(--format-setting-text-active-color);
    }
  }

  .setting_menu {
    margin-bottom: auto;
  }

  .setting_menu_item {
    height: 48px;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    color: var(--format-setting-text-color);
    cursor: pointer;

    &:hover {
      color: var(--format-setting-text-active-color);
    }

    &.menuActive {
      color: var(--format-setting-text-active-color);

      &::before {
        content: "";
        position: absolute;
        width: 2px;
        height: 48px;
        left: 0;
        top: 0;
        background-color: var(--format-setting-text-active-color);
      }
    }

    i {
      font-size: 22px;
    }
  }
}

//设置列表
.setting_action_action {
  .setting_action_action_item {
    cursor: pointer;
    padding: 0 26px;
    height: 26px;
    line-height: 26px;
    &:hover {
      background-color: var(--yh-brand-color);
      color: var(--yh-text-color-anti);
    }
  }
}

.logo_wrap {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;

  span {
    color: var(--format-color-brand);
    margin-left: 12px;
    font-weight: 900;
  }
}

.form_item {
}

.json_format_content {
  flex: 1;
  display: flex;
  flex-direction: column;

  .file_nav_wrap {
    height: 35px;
    flex-shrink: 0;
    display: flex;
    overflow-x: auto;
    width: 100%;
  }
}

.file_nav_item {
  display: flex;
  align-items: center;
  color: var(--format-nav-text-color);
  background-color: var(--format-nav-bg);
  cursor: pointer;
  box-sizing: border-box;
  padding-left: 10px;
  flex: 0 0 auto;
  border-right: 1px solid var(--format-bg-clolor);

  .file_nav_img {
    width: 22px;
    padding-right: 6px;

    img {
      width: 100%;
    }
  }

  .file_nav_close {
    margin-top: auto;
    margin-bottom: auto;
    width: 28px;
    opacity: 0;
    transition: opacity 0.24s;

    .file_nav_close_inner {
      width: 18px;
      height: 18px;
      margin: auto;
      border-radius: 5px;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: background-color 0.24s;
    }

    &:hover {
      .file_nav_close_inner {
        background-color: var(--format-nav-close-color);
      }
    }
  }

  &.currentActiveFile {
    background-color: var(--format-nav-bg-active);
    color: var(--format-nav-text-color-active);

    .file_nav_close {
      opacity: 1;
    }
  }

  &:hover {
    .file_nav_close {
      opacity: 1;
    }
  }
}

.json_format_editor {
  flex: 1;
}
</style>

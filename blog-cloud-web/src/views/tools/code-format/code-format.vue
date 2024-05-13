<script setup lang="ts">
import { onBeforeMount, onMounted, ref, reactive, nextTick, markRaw, defineAsyncComponent } from "vue";
import Loading from "@/components/loading";
import CodeFormatCommon from "./common/code-format-common.vue";
import Actionbar from "./common/actionbar.vue";
import Menubar from "./common/menubar.vue";
import CompositeBar from "./common/composite-bar.vue"
import { InfoIcon, CloseIcon } from "@/components/icons";
import { ArrowRight } from "@element-plus/icons-vue";
import { CustomMouseMenu } from "@/components/contextmenu";
import { Logo } from "@/components/header/logo";
import { catalogueListDefault } from "./code-format-config";
import { ElMessage, ElMessageBox, ElNotification } from "element-plus";
import { setLocalStorage, getLocalStorage } from "@/utils/modules/storage";
import { seachTreeData, seachTreeParentData } from "@/utils/tree";
import { CommonEnums } from "./type";
import { userThemeStore } from "@/store";
import { saveJSON } from "@/utils/modules/files";
import { getLaguageData, createFileData, createFolderData, timerPromise } from "./code-format";

import type Node from "element-plus/es/components/tree/src/model/node";
import type { FileItemType, EditorOptionType } from "./type";

const MonacoEditor = defineAsyncComponent({
  loader: () => import("@/components/monaco-editor"),
  loadingComponent: Loading,
});

const themeStore = userThemeStore();

//右键对象
let contextMenuCtx: any = null;
//通知
let notification: any = null;

//侧边栏目录列表
const catalogueList = ref<FileItemType[]>([]);
//侧边栏目录ref
const catalogueRef = ref();
//当前选中的
const currentActiveFile = ref();
//头部文件列表
const navFileList = ref<FileItemType[]>([]);
//编辑器配置
const editorOption = reactive<EditorOptionType>({
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
  if (!currentFileData) {
    ElMessage.error("此文件已被删除,已自动删除！");
    onRemoveNav(fileNavItem);
    return;
  }
  saveCurrentCatalogue();
  setCurrentCheckCatalogue(fileNavItem.id);
  switchEditData(currentFileData.content, fileNavItem.language);
};
//tree树数据外侧点击时候
const catalogueListContextmenu = (e: any) => {
  e.preventDefault();
  contextMenuCtx = CustomMouseMenu({
    el: e.target,
    menuList: [
      {
        label: "新建文件",
        tips: "",
        fn: () => onAddfile("1", "root"),
      },
      {
        label: "新建文件夹",
        tips: "",
        fn: () => onAddfile("2", "root"),
      },
    ],
  });
  const { x, y } = e;
  contextMenuCtx.show(x, y);
};
//右键
const onCataloguetNodecontextmenu = (event: any, data: FileItemType, node: Node, nodeTemplate: any) => {
  // console.log({ event, data, node, nodeTemplate });
  // console.log(catalogueList.value.length);
  event.preventDefault();
  contextMenuCtx = CustomMouseMenu({
    // el: nodeTemplate.vnode.el,
    el: event.target,
    menuList: [
      {
        label: "新建文件",
        tips: "",
        hidden: data.type != "2",
        fn: () => onAddfile("1", data),
      },
      {
        label: "新建文件夹",
        tips: "",
        hidden: data.type != "2",
        fn: () => onAddfile("2", data),
      },
      {
        label: "重命名",
        tips: "Edit",
        fn: () => rename(node, data),
      },
      {
        label: "删除",
        tips: "Delete",
        // disabled: catalogueList.value.length == 1,
        fn: () => deleteFile(node, data),
      },
    ],
  });
  const { x, y } = event;
  contextMenuCtx.show(x, y);
};

//重命名
const rename = async (node: Node, nodeData: FileItemType) => {
  // console.log("rename", node, nodeData);
  //不自动获取焦点问题，点击右键菜单触发点击时间，input焦点消失
  await timerPromise(20);
  ElMessageBox.prompt("请编辑文件名称", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    inputErrorMessage: "",
    inputValue: nodeData.title,
    draggable: true,
    type: "info",
  })
    .then(({ value }) => {
      nodeData.title = value;
      if (nodeData.type == "2") {
        return;
      }
      const laguageData = getLaguageData(value);
      nodeData.language = laguageData.fileLanguage;
      nodeData.icon = laguageData.fileIconData.icon.name;
      //修改编辑器语言
      editorOption.languageModel = laguageData.fileLanguage;
      //处理nav导航数据
      const navIndex = navFileList.value.findIndex((d: any) => d.id === nodeData.id);
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
};
//下载文件
const downFile = () => {
  const codeFormatFileData = setFilesLocalStorage();
  saveJSON(codeFormatFileData, "格式化工具数据.json")
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.error(err);
      ElMessage.error("保存失败！" + err);
    });
};
//删除
const deleteFile = (node: Node, nodeData: FileItemType) => {
  const parent = node.parent;
  const children: any = parent.data.children || parent.data;
  const index = children.findIndex((d: any) => d.id === nodeData.id);
  children.splice(index, 1);
  // catalogueList.value = [...catalogueList.value];
  onRemoveNav(nodeData);
};

//点击新增文件
const onAddfile = async (type: string, createParentData?: any) => {
  console.log(type, createParentData);
  let str = type == "2" ? "请输入文件夹名称" : "请输入文件名称";
  await timerPromise(20);
  ElMessageBox.prompt(str, "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    inputErrorMessage: "",
    draggable: true,
    autofocus: true,
  })
    .then(({ value }) => {
      if (type == "2") {
        addFolder(value, createParentData);
      } else {
        addFile(value, createParentData);
      }
    })
    .catch((err) => {
      console.error(err);
    });
};

//新增文件
const addFile = (data: string, createParentData: any) => {
  const { newFileData, laguageData } = createFileData(data);
  addCatalogFile(newFileData, createParentData);
  saveCurrentCatalogue();
  setCurrentCheckCatalogue(newFileData.id);
  switchEditData("", laguageData.fileLanguage);
  addNavData(newFileData);
};

//新增文件夹
const addFolder = (val: string, createParentData?: any) => {
  const newFileData = createFolderData(val);
  addCatalogFile(newFileData, createParentData);
};

//向目录中添加文件
const addCatalogFile = (fileOrFolder: any, createParentData?: any) => {
  // console.log(fileOrFolder,createParentData);
  if (createParentData) {
    // createParentData=='root' 时添加到根目录
    if (createParentData == "root") {
      catalogueList.value.push(fileOrFolder);
      return;
    }
    if (!createParentData.children) {
      createParentData.children = [];
    }
    createParentData.children.push(fileOrFolder);
    return;
  }
  const currentFileData = catalogueRef.value.getCurrentNode();
  if (!currentFileData) {
    addCatalogFile(fileOrFolder, "root");
    return;
  }
  //本身是文件夹的话向子级直接插入
  if (currentFileData.type == "2") {
    if (!currentFileData.children) {
      currentFileData.children = [];
    }
    currentFileData.children.push(fileOrFolder);
    return;
  }
  const currentParentData = seachTreeParentData(catalogueList.value, currentActiveFile.value);
  // console.log("addCatalogFile-currentParentData",currentParentData);
  //父级是文件夹的话插入
  if (currentParentData) {
    currentParentData.push(fileOrFolder);
    return;
  }
  catalogueList.value.push(fileOrFolder);
};

//新增文件导航栏列表
const addNavData = (fileData: FileItemType) => {
  const isAddData = navFileList.value.find((item) => item.id == fileData.id);
  if (isAddData) return;
  navFileList.value.push(fileData);
};

//移除nav文件
const onRemoveNav = (fileNavItem: FileItemType, index?: number) => {
  if (!index) {
    index = navFileList.value.findIndex((d: any) => d.id === fileNavItem.id);
  }
  if (index == -1) {
    return;
  }
  if (index >= 0) {
    navFileList.value.splice(index, 1);
  }
  if (navFileList.value.length == 0) {
    setCurrentCheckCatalogue(null);
    return;
  }
  if (index >= 1) {
    navActiveChange(navFileList.value[index - 1]);
  } else {
    navActiveChange(navFileList.value[0]);
  }
};

//获取文件icon
const getFileSvg = (iconname: string) => {
  // return new URL(`../../../assets/file-icon/${iconname}.svg`, import.meta.url)
  //   .href;
  // console.log(import.meta.env.VITE_PREFIX,import.meta.env);
  //
  return `${
    import.meta.env.BASE_URL.length == 1 ? "" : import.meta.env.BASE_URL
  }/static-files/code-format/code-format-icon/${iconname}.svg`;
};

//切换编辑器内容
const switchEditData = (content: string, language: string = "txt") => {
  editorOption.editValue = content;
  editorOption.languageModel = language;
};

//目录列表node点击事件
const catalogueNodeClick = (data: FileItemType) => {
  // console.log("catalogueNodeClick",data);
  if (data.type == "2") {
    return;
  }
  saveCurrentCatalogue();
  currentActiveFile.value = data.id;
  addNavData({
    ...data,
  });
  switchEditData(data.content, data.language);
};
//选择切换主题
const onSelectTheme = () => {
  editorOption.commonKeyword = CommonEnums.theme;
};
// 更新目录node
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
const setCurrentCheckCatalogue = async (id: string | null) => {
  currentActiveFile.value = id;
  await nextTick();
  catalogueRef.value.setCurrentKey(id);
};
//保存
const saveFiles = () => {
  saveCurrentCatalogue();
  setFilesLocalStorage();
};
//保存当前的数据
const saveCurrentCatalogue = () => {
  let currentFileData = seachTreeData(catalogueList.value, currentActiveFile.value);
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
  const codeFormatFileData = {
    catalogueList: catalogueList.value,
    navList: navFileList.value,
    active: currentActiveFile.value,
  };
  setLocalStorage("codeFormatFileData", codeFormatFileData);
  notification && notification.close();
  notification = ElNotification({
    title: "通知",
    message: "保存成功!",
    type: "success",
    showClose: false,
    duration: 3000,
    position: "bottom-left",
  });
  return codeFormatFileData;
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
  if (codeFormatFileData.active) {
    currentActiveFile.value = codeFormatFileData.active;
  } else if (navFileList.value.length >= 1) {
    currentActiveFile.value = navFileList.value[0].id;
  }
  if (!currentActiveFile.value) {
    return;
  }
  const currentFileData = seachTreeData(catalogueList.value, currentActiveFile.value);
  if (currentFileData) {
    await nextTick();
    switchEditData(currentFileData.content, currentFileData.language);
    catalogueRef.value.setCurrentKey(currentFileData.id);
  }
};

//初始化页面
const init = () => {
  initEditConfig();
  initCatalogList();
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
    <CodeFormatCommon v-model="editorOption.commonKeyword" :editorOption="editorOption" @clickItem="onCommonClick">
    </CodeFormatCommon>
    <div class="activitybar-left">
      <menubar @downFile="downFile"></menubar>
      <composite-bar></composite-bar>
      <actionbar @selectTheme="onSelectTheme"></actionbar>
    </div>
    <div class="json_format_nav flex flex-col">
      <div class="logo_wrap flex-shrink-0">
        <Logo class="tools-layout_logo" target="_blank" />
        <span>格式化工具</span>
      </div>
      <div class="nav_title flex-shrink-0">
        <div class="nav_title_front">
          <i class="yh-icons-arrow-down"></i>
        </div>
        <div class="nav_title_text">
          目录列表
          <el-tooltip content="数据只会存储到本地，不会进行上传，请妥善处理。">
            <i class="dd-icon-tishi"></i>
          </el-tooltip>
        </div>
        <div class="nav_title_action flex overflow-y-auto">
          <div class="nav_title_action_icon" @click="onAddfile('1')" title="新建文件...">
            <i class="dd-icon-file1"></i>
          </div>
          <div class="nav_title_action_icon" @click="onAddfile('2')" title="新建文件夹...">
            <i class="dd-icon-folder"></i>
          </div>
        </div>
      </div>
      <div class="catalogue—list flex-auto" @contextmenu.stop="catalogueListContextmenu">
        <el-tree
          :data="catalogueList"
          :props="{ children: 'children', label: 'title' }"
          :icon="ArrowRight"
          @node-click="catalogueNodeClick"
          node-key="id"
          ref="catalogueRef"
          highlight-current
          :draggable="false"
          @node-contextmenu="onCataloguetNodecontextmenu">
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
    <div class="json_format_content" v-if="navFileList.length >= 1">
      <nav class="file_nav_wrap">
        <div
          v-for="(item, i) in navFileList"
          @click="navActiveChange(item)"
          class="file_nav_item"
          :class="{ currentActiveFile: currentActiveFile === item.id }"
          :key="item.id">
          <div class="file_nav_img">
            <img :src="getFileSvg(item.icon)" :alt="item.language" />
          </div>
          <span class="file_nav_title">{{ item.title }}</span>
          <div class="file_nav_close">
            <div class="file_nav_close_inner" @click.stop="onRemoveNav(item, i)">
              <CloseIcon />
            </div>
          </div>
        </div>
      </nav>
      <MonacoEditor
        v-model="editorOption.editValue"
        :language="editorOption.languageModel"
        :hight-change="editorOption.hightChange"
        :options="(editorOption.options as any)"
        :theme="editorOption.theme"
        :read-only="false"
        @editor-mounted="editorMounted"
        @save="saveFiles"
        class="json_format_editor" />
    </div>
    <div class="json_format_content empty" v-else>
      <p>请选择左侧目录列表中的文件，或者新建文件。</p>
    </div>
  </div>
</template>
<style lang="scss">
.el-popover.setting_action_setting {
  padding: 8px 0;
}
</style>
<style scoped lang="scss">
@import "./style/code-format-var.scss";
@import "./style/code-format-dark.scss";
@import "./style/left-action.scss";
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
    .el-tree-node__expand-icon {
      color: var(--format-text-color);
      &.is-leaf {
        color: transparent;
      }
    }
    .el-tree-node.is-current > .el-tree-node__content {
      background-color: var(--formart-catalogue-current-bg);
      color: var(--format-text-color-anti);
      .el-tree-node__expand-icon {
        color: var(--format-text-color-anti);
        &.is-leaf {
          color: transparent;
        }
      }
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
.activitybar-left {
  width: 48px;
  flex-shrink: 0;
  background-color: var(--format-setting-bg-color);
  display: flex;
  flex-direction: column;
 
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
  &.empty {
    background: var(--format-nav-bg-active);
    font-size: 16px;
    text-align: center;
    padding-top: 30%;
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
      svg {
        width: 14px;
        height: 14px;
      }
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

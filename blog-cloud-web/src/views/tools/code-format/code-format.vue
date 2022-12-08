<script setup lang="ts">
import { onBeforeMount, onMounted, ref, reactive, nextTick } from "vue";
import monacoEditor from "@/components/monaco-editor";
import CodeFormatCommon from "./code-format-common.vue";
import { CustomMouseMenu } from "@/components/contextmenu";
import type { Options, Theme } from "@/components/monaco-editor";
import type Node from 'element-plus/es/components/tree/src/model/node'
import { Logo } from "@/components/header/logo";
import { languageList, catalogueListDefault } from "./code-format-config";
import { languageIcons } from "@/config/languageIcons";
import fileSvg from "@/assets/file-icon/file.svg";
import { ElMessage, ElMessageBox } from "element-plus";
import { setLocalStorage, getLocalStorage } from "@/utils/modules/storage";
import { guid } from "@/utils/current";
import { seachTreeData } from "@/utils/tree";
import type { FileItemType } from "./type";

const settingConfig = reactive({
  menuActive: "file",
});
//右键对象
let contextMenuCtx: any = null;
const editValue = ref("");
const languageModel = ref("json");
//设置弹窗ref
const settingRef = ref();
//侧边栏目录列表
const catalogueList = ref<FileItemType[]>([]);
//侧边栏目录ref
const catalogueRef = ref();
//当前选中的
const nav_active = ref();
//编辑器配置
const editorOption = reactive<{
  theme: Theme;
  commonKeyword: string;
}>({
  theme: "vs",
  commonKeyword: "",
});
const options = ref<any>({
  minimap: {
    enabled: true, //显示小地图
  },
});
//头部文件列表
const navFileList = ref<FileItemType[]>([]);

const hightChange = ref<any>(false);
//editor实例加载完成
const editorMounted = (editor: any) => {
  console.log("%ceditor实例加载完成", "color: #229453");
  // console.log("editor实例加载完成", editor);
};
//点击每一项
const onCommonClick = (commonItem: any) => {
  editorOption.theme = commonItem.value;
  console.log(editorOption);
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
  data: any,
  node: Node,
  nodeTemplate: any
) => {
  console.log({ event, data, node, nodeTemplate });
  console.log(catalogueList.value.length);
  event.preventDefault();
  contextMenuCtx = CustomMouseMenu({
    el: nodeTemplate.vnode.el,
    menuList: [
      {
        label: "重命名",
        tips: "Edit",
        fn: (
          params: any,
          currentEl: HTMLElement,
          bindingEl: HTMLElement,
          e: MouseEvent
        ) => {
          // console.log("delete", params, currentEl, bindingEl, e);
        },
      },
      {
        label: "删除",
        tips: "Delete",
        disabled:catalogueList.value.length==1,
        fn:()=> deleteFile(node,data),
      },
    ],
  });
  const { x, y } = event;
  contextMenuCtx.show(x, y);
};
//重命名
const  rename =()=>{

}
//删除
const deleteFile = (node:Node,nodeData:any)=>{
  const parent = node.parent
  const children: any = parent.data.children || parent.data
  const index = children.findIndex((d:any) => d.id === nodeData.id)
  children.splice(index, 1)
  catalogueList.value = [...catalogueList.value]

  const navIndex= navFileList.value.findIndex((d:any) => d.id === nodeData.id)
  navFileList.value.splice(navIndex, 1)

  if(nav_active.value==nodeData.id){
    navActiveChange(navFileList.value[0])
  }

}
//点击新增文件
const onAddfile = () => {
  ElMessageBox.prompt("请输入文件名称", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    inputErrorMessage: "",
    draggable: true,
  })
    .then(({ value }) => {
      addFile(value);
    })
    .catch((err) => {
      console.error(err);
    });
};
//新增文件
const addFile = (data: any) => {
  const fileNameSplit = data.split(".");
  let fileLanguage = fileNameSplit[fileNameSplit.length - 1];
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
  const newFileData = {
    title: data,
    language: fileLanguage,
    content: "",
    id: guid(),
    icon: fileIconData.icon.name,
  };
  catalogueList.value.push(newFileData);
  saveCurrentCatalogue();
  setCurrentCheckCatalogue(newFileData.id);
  switchEditData("", fileLanguage);
  addNavData(newFileData);
};

//新增文件导航栏列表
const addNavData = (fileData: any) => {
  const isAddData = navFileList.value.find((item) => item.id == fileData.id);
  if (isAddData) return;
  navFileList.value.push(fileData);
};
//移除文件
const onRemoveNav = (fileNavItem: any, index: number) => {
  navFileList.value.splice(index, 1);
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
  editValue.value = content;
  languageModel.value = language;
};

//目录列表node点击事件
const catalogueNodeClick = (data: any) => {
  // console.log("catalogueNodeClick",data);
  saveCurrentCatalogue();
  nav_active.value = data.id;
  addNavData({
    ...data,
  });
  switchEditData(data.content, data.language);
};
//选择切换主题
const onSelectTheme = () => {
  settingRef.value.hide();
  editorOption.commonKeyword = "theme";
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
const onSetting = () => { };
//设置当前选中的值
const setCurrentCheckCatalogue = async (id: string) => {
  nav_active.value = id;
  await nextTick();
  catalogueRef.value.setCurrentKey(id);
};
//保存当前的数据
const saveCurrentCatalogue = () => {
  let currentFileData = seachTreeData(catalogueList.value, nav_active.value);
  if (currentFileData) {
    currentFileData.content = editValue.value;
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
  setLocalStorage("code-format-catalogue", catalogueList.value);
  setLocalStorage("code-format-files-active", nav_active.value);
  setLocalStorage("code-format-files", navFileList.value);
};
//初始化页面
const init = async () => {
  const newCatalogueList = getLocalStorage("code-format-catalogue");
  if (!newCatalogueList) {
    catalogueList.value = [{ ...catalogueListDefault }];
    addNavData(catalogueListDefault);
    setCurrentCheckCatalogue(catalogueListDefault.id);
    switchEditData(catalogueListDefault.content, catalogueListDefault.language);
    return;
  }
  catalogueList.value = newCatalogueList;
  const filesList = getLocalStorage("code-format-files");
  if (!filesList) {
    return;
  }
  navFileList.value = filesList;
  nav_active.value =
    getLocalStorage("code-format-files-active") || filesList[0].id;
  const currentFileData = seachTreeData(catalogueList.value, nav_active.value);
  if (currentFileData) {
    await nextTick();
    switchEditData(currentFileData.content, currentFileData.language);
    catalogueRef.value.setCurrentKey(currentFileData.id);
  }
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
  <div class="json_format edit-tool-var">
    <CodeFormatCommon v-model="editorOption.commonKeyword" :editorOption="editorOption" @clickItem="onCommonClick">
    </CodeFormatCommon>
    <div class="code_format_setting">
      <div class="menubar-menu-button">
        <i class="dd-icon-mulu"></i>
      </div>
      <ul class="setting_menu">
        <li :class="{ menuActive: settingConfig.menuActive === 'file' }" class="setting_menu_item">
          <i class="dd-icon-file"></i>
        </li>
      </ul>
      <ul class="setting_action">
        <li class="setting_menu_item">
          <el-popover placement="right" :width="200" :hide-after="0" trigger="click" :show-arrow="false"
            popper-class="setting_action_setting" ref="settingRef">
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
        <el-tree :data="catalogueList" :props="{ children: 'children', label: 'title' }"
          @node-click="catalogueNodeClick" node-key="id" ref="catalogueRef" highlight-current
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
    <div class="json_format_content">
      <nav class="file_nav_wrap">
        <div v-for="(item, i) in navFileList" @click="navActiveChange(item)" class="file_nav_item"
          :class="{ nav_active: nav_active === item.id }" :key="item.id">
          <div class="file_nav_img">
            <img :src="getFileSvg(item.icon)" :alt="item.language" />
          </div>
          <span class="file_nav_title">{{ item.title }}</span>
          <div class="file_nav_close">
            <div class="file_nav_close_inner" @click.stop="onRemoveNav(item, i)" v-if="navFileList.length > 1">
              <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" width="14" height="14">
                <path fill="currentColor"
                  d="M764.288 214.592 512 466.88 259.712 214.592a31.936 31.936 0 0 0-45.12 45.12L466.752 512 214.528 764.224a31.936 31.936 0 1 0 45.12 45.184L512 557.184l252.288 252.288a31.936 31.936 0 0 0 45.12-45.12L557.12 512.064l252.288-252.352a31.936 31.936 0 1 0-45.12-45.184z">
                </path>
              </svg>
            </div>
          </div>
        </div>
      </nav>
      <monacoEditor v-model="editValue" :language="languageModel" :hight-change="hightChange" :options="options"
        :theme="editorOption.theme" :read-only="false" @editor-mounted="editorMounted" class="json_format_editor" />
    </div>
  </div>
</template>
<style lang="scss">
.el-popover.setting_action_setting {
  padding: 8px 0;
}
</style>
<style scoped lang="scss">
.light .edit-tool-var {
  --format-bg-clolor: rgb(243, 243, 243);
  --format-nav-bg: rgb(236, 236, 236);
  --format-nav-bg-active: rgb(255, 255, 255);
  --format-nav-text-color: rgba(51, 51, 51, 0.7);
  --format-nav-text-color-active: rgb(51, 51, 51);

  --format-nav-close-color: rgba(184, 184, 184, 0.31);

  //设置栏
  --format-setting-bg-color: rgb(44, 44, 44);
  --format-setting-text-color: rgba(255, 255, 255, 0.4);
  --format-setting-text-active-color: rgb(255, 255, 255);
  //命令栏
  --common-palete-bg-color: rgb(243, 243, 243);
  --common-palete-color: rgb(97, 97, 97);
  --common-palete-box-shadow: rgb(0 0 0 / 16%) 0px 0px 8px 2px;
  --common-palete-border-color: #cccedb;
  --common-palete-hover-color: #e8e8e8;
}

.dark .edit-tool-var {
  --format-bg-clolor: rgb(37, 37, 38);
  --format-nav-bg: rgb(45, 45, 45);
  --format-nav-bg-active: rgb(30, 30, 30);
  --format-nav-text-color: rgba(255, 255, 255, 0.5);
  --format-nav-text-color-active: rgb(255, 255, 255);
  --format-nav-close-color: rgba(90, 93, 94, 0.31);
  //设置栏
  --format-setting-bg-color: rgb(51, 51, 51);
  --format-setting-text-color: rgba(255, 255, 255, 0.4);
  --format-setting-text-active-color: rgb(255, 255, 255);
  //命令栏
  --common-palete-bg-color: rgb(243, 243, 243);
  --common-palete-color: rgb(97, 97, 97);
  --common-palete-box-shadow: rgb(0 0 0 / 16%) 0px 0px 8px 2px;
  --common-palete-border-color: #cccedb;
  --common-palete-hover-color: #e8e8e8;
}

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

  .json_format_nav {
    width: 210px;
    flex-shrink: 0;
    box-shadow: var(var(--yh-shadow-inset-left));
    box-sizing: border-box;
    outline-color: rgba(38, 119, 203, 0.18);
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

    .el-tree-node>.el-tree-node__content {
      padding-left: 4px;

      &:hover {
        background-color: var(--common-palete-hover-color);
      }
    }

    .el-tree-node.is-current>.el-tree-node__content {
      background-color: var(--yh-brand-color);
      color: var(--yh-text-color-anti);
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
      background-color: var(--yh-text-color-brand);
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
    color: var(--yh-text-color-brand);
    margin-left: 12px;
    font-weight: 900;
  }
}

.form_item {}

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

  &.nav_active {
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

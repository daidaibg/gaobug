<script setup lang="ts">
import { onBeforeMount, onMounted, ref, reactive } from "vue";
import monacoEditor from "@/components/monaco-editor";
import type { Options, Theme } from "@/components/monaco-editor";
import { Logo } from "@/components/header/logo";
import { languageList } from "./code-format-config";
import { languageIcons } from "@/config/languageIcons";
import fileSvg from "@/assets/file-icon/file.svg";
import { ElMessage, ElMessageBox } from "element-plus";
import { setLocalStorage, getLocalStorage } from "@/utils/modules/storage";
const settingConfig = reactive({
  menuActive: "file",
});
const editValue = ref("");
const languageModel = ref("json");
const theme = ref<Theme>("vs");
const options = ref<any>({
  minimap: {
    enabled: true, //显示小地图
  },
});
const nav_active = ref(0);
const fileList = ref([
  {
    title: "default",
    language: "json",
    content: ``,
    id: 1,
  },
]);

const themeOptions = [
  {
    value: "vs",
  },
  {
    value: "hc-black",
  },
  {
    value: "vs-dark",
  },
  {
    value: "hc-light",
  },
  // {
  //   value: "vs-light-plus",
  // },
  // { value: "default-dark", label: "Dark+ (default dark)" },
];
const hightChange = ref<any>(false);
const editorMounted = (editor: any) => {
  console.log("%ceditor实例加载完成", "color: #229453");
  // console.log("editor实例加载完成", editor);
};
/**
 * @description: 本地存储历史记录
 */
const setFilesLocalStorage = () => {
  if (fileList.value.length === 1 && fileList.value[0].content == "") {
    return;
  }
  setLocalStorage("code-format-files", fileList.value);
  setLocalStorage("code-format-files-active", nav_active.value);
};
/**
 * @description: 选中文件改变
 * @param {any} fileNavItem  文件的config
 * @param {number} index  新的文件索引
 * @param {boolean} isNoSaveEditvalue 是否不需要存储上个文件 删除的时候就不需要
 * @return {*}
 */
const selectNav = (
  fileNavItem: any,
  index: number,
  isNoSaveEditvalue?: boolean
) => {
  if (!isNoSaveEditvalue) {
    fileList.value[nav_active.value].content = editValue.value;
  }
  nav_active.value = index;
  editValue.value = fileNavItem.content;
  languageModel.value = fileNavItem.language;
  setFilesLocalStorage();
};
//新增文件
const addfile = () => {
  ElMessageBox.prompt("请输入文件名称", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    inputErrorMessage: "",
    draggable: true,
  })
    .then(({ value }) => {
      const newFileData = {
        title: value,
        language: languageModel.value,
        content: "",
        id: fileList.value[fileList.value.length - 1].id + 1,
      };
      fileList.value.push(newFileData);
      selectNav(newFileData, fileList.value.length - 1);
    })
    .catch(() => {});
};
//移除文件
const onRemoveNav = (fileNavItem: any, index: number) => {
  fileList.value.splice(index, 1);
  selectNav(fileList.value[index - 1], index - 1, true);
};
// 语言选择框发生变化
const onLanguageModelChange = (val: string) => {
  fileList.value[nav_active.value].language = val;
};
//获取文件icon
const getFileSvg = (fileNavItem: any) => {
  let fileIconData: any;
  for (let index = 0; index < languageIcons.length; index++) {
    const element = languageIcons[index];
    if (element.ids.includes(fileNavItem.language)) {
      fileIconData = element;
      break;
    }
  }
  if (fileIconData) {
    return new URL(
      `../../../assets/file-icon/${fileIconData.icon.name}.svg`,
      import.meta.url
    ).href;
  }
  return fileSvg;
};

const onSelectTheme = ()=>{
  
}

//初始化页面
const init = () => {
  let filesList = getLocalStorage("code-format-files");
  let fileActive = getLocalStorage("code-format-files-active");
  if (fileActive) {
    nav_active.value = fileActive;
  }
  if (filesList) {
    fileList.value = filesList;
    selectNav(filesList[nav_active.value], nav_active.value, true);
  }
};
//打开设置
const onSetting = () => {};
//即将离开当前页面（刷新或关闭）时触发
const pageBeforeunload = () => {
  fileList.value[nav_active.value].content = editValue.value;
  setFilesLocalStorage();
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
    <div class="code_format_setting">
      <ul class="setting_menu">
        <li
          :class="{ menuActive: settingConfig.menuActive === 'file' }"
          class="setting_menu_item"
        >
          <i class="dd-icon-a-bianzu92"></i>
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
            popper-class="setting_action_setting"
          >
            <template #reference>
              <i class="dd-icon-shezhi" @click="onSetting"> </i>
            </template>
            <ul class="setting_action_action">
              <li class="setting_action_action_item" @click="onSelectTheme()">颜色主题</li>
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
          配置工具
          <el-tooltip content="数据只会存储到本地，不会进行上传，请妥善处理。">
            <i class="dd-icon-tishi"></i>
          </el-tooltip>
        </div>
        <div class="nav_title_action">
          <div class="nav_title_action_icon" @click="addfile">
            <i class="dd-icon-tianjiawenjian"></i>
          </div>
        </div>
      </div>
      <div class="form_item">
        <label for="">语言:</label>
        <el-select
          v-model="languageModel"
          filterable
          class="language_select"
          placeholder="Select"
          style="width: 90px"
          @change="onLanguageModelChange"
          size="small"
        >
          <el-option
            v-for="item in languageList"
            :key="item.value"
            :label="item.value"
            :value="item.value"
          />
        </el-select>
      </div>
      <div class="form_item">
        <label for="">皮肤:</label>
        <el-select
          v-model="theme"
          class="language_select"
          placeholder="Select"
          style="width: 90px"
          size="small"
        >
          <el-option
            v-for="item in themeOptions"
            :key="item.value"
            :label="item.value"
            :value="item.value"
          />
        </el-select>
      </div>
    </div>
    <div class="json_format_content">
      <nav class="file_nav_wrap">
        <div
          v-for="(item, i) in fileList"
          @click="selectNav(item, i)"
          class="file_nav_item"
          :class="{ nav_active: nav_active === i }"
          :key="item.id"
        >
          <div class="file_nav_img">
            <!-- <div v-html="getFileSvg(item)"></div> -->
            <img :src="getFileSvg(item)" :alt="item.language" />
          </div>
          <span class="file_nav_title"
            >{{ item.title }}.{{ item.language }}</span
          >
          <div class="file_nav_close">
            <div
              class="file_nav_close_inner"
              v-if="fileList.length > 1"
              @click.stop="onRemoveNav(item, i)"
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
        v-model="editValue"
        :language="languageModel"
        :hight-change="hightChange"
        :options="options"
        :theme="theme"
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

  //
  // left: auto;
  //   border-right: 1px solid rgb(243, 243, 243);
  //   background-color: rgb(236, 236, 236);
  //   color: rgba(51, 51, 51, 0.7);
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
    padding: 0 8px;
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
//设置
.code_format_setting {
  width: 48px;
  flex-shrink: 0;
  background-color: var(--format-setting-bg-color);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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
.form_item {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12px;
  label {
    margin-right: 6px;
  }
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

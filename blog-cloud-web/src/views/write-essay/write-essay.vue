<script setup lang="ts">

import User from "@/components/header/user";
import Loading from "@/components/loading";

import { ref, computed, reactive, toRefs, defineAsyncComponent } from "vue";
import { tagsList} from "./write-essay";
import {beforeAvatarUpload,onUploadCover} from "@/utils/upload"
import { ElMessage, ElDialog } from "element-plus";
import { currentPOST, currentGET } from "@/api";
import { useRouter, useRoute } from "vue-router";
import { StateType, FormDataType } from "./write-essay-type";
import { mdEditorConfig, generateId } from "@/config";
import { userThemeStore } from "@/store";

import type { FormInstance, FormRules, UploadRequestOptions } from "element-plus";

const GbMdEditor = defineAsyncComponent({
  loader: () => import("@/components/md-edits/md-edits/index.vue"),
  loadingComponent: Loading,
});

const themeStore = userThemeStore();
const router = useRouter();
const route = useRoute();

const ruleFormRef = ref<FormInstance>();
const state: StateType = reactive({
  title: "",
  content: ``,
  previewTheme: "github",
  dialogVisible: false,
  classificatio: [],
  id: null, //文章id
});
const formData: FormDataType = reactive({
  categoryId: "", //分类
  coverUrl: "",
  tag: "", //标签
  articlesPart: "", //文章出处
  summary: "",
  openComment: 1, //是否开启评论0：否 1：是
});
const blogDetails: any = ref({}); //详情

const { title, content, previewTheme, classificatio, dialogVisible } = toRefs(state);

const rules = reactive<FormRules>({
  categoryId: [{ required: true, message: "请选择分类！", trigger: "blur" }],
  tag: [{ required: true, message: "请选择标签！", trigger: "blur" }],
});

// 发布
const fabu = () => {
  if (title.value == "") {
    ElMessage.warning("请输入标题!");
    return;
  } else if (content.value.length < 10) {
    ElMessage.warning("文章内容太短！");
    return;
  }
  dialogVisible.value = true;
};

// 发布文章
const publish = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid)=> {
    if (valid) {
      saveOrUpdate(1, "发布成功").then((res) => {
        if (res) {
          router.push("/blogs/manage/article");
        }
      });
    } 
  });
};

//保存草稿
const save = () => {
  if (title.value == "") {
    title.value = "【无标题】";
  }
  saveOrUpdate(0, "保存草稿成功");
};

//提交或则新增 处理参数并且提交调接口
const saveOrUpdate = async (publish: Number, successMsg: string) => {
  let type = "addBlog"; //新增
  let param: any = {
    title: title.value,
    content: content.value,
    ...formData,
    publish: publish, //是否发布0：否，1：是
  };
  if (state.id) {
    param.id = state.id;
    type = "updataBlog"; //更新
  }
  if (!param.categoryId) {
    delete param.categoryId;
  }
  const res = await currentPOST(type, param);
  if (res.code === 200) {
    //保存草稿时需要存储id 后端暂无返回id
    if (publish === 0) {
    }
    ElMessage.success(successMsg);
    return true;
  } else {
    ElMessage.error({ message: res.msg });
    return false;
  }
};

//获取分类列表
const getCategory = () => {
  currentGET("category", { size: 20 }).then((res: any) => {
    // console.log(res);
    if (res.code == 200) {
      classificatio.value = res.data.records;
    } else {
      ElMessage.error(res.msg);
    }
  });
};

// 选择表情
const onEmojiChange = (emoji: any) => {
  content.value = emoji;
};

//封面上传
const coverUrlRequest = (options: UploadRequestOptions): any => {
  return onUploadCover(options);
};

// 弹窗关闭前回调
const saveHandleClose = (done: Function) => {
  dialogVisible.value = false;
  done();
};

// 返回上一页
const goback = () => {
  // router.back();
  router.push("/blogs/manage/article");
};

// 上传失败
const onError = (error: Error | any) => {
  if (error.msg) {
    ElMessage.error(error.msg);
  } else {
    ElMessage.error("未知异常，图片上传失败");
  }
};

// 上传成功
const handleAvatarSuccess = (response: any, uploadFile: any) => {
  if (response.code == 200) {
    formData.coverUrl = response.data.url;
  } else {
    onError(response);
  }
};

const deleteCover = () => {
  formData.coverUrl = "";
};

//获取详情
const getDetail = () => {
  currentGET("blogDetail", {}, state.id).then((res: any) => {
    console.log(res);
    if (res.code == 200) {
      blogDetails.value = res.data;
      content.value = res.data.content;
      title.value = res.data.title;
      state.id = res.data.id;
      formData.summary = res.data.summary;
      formData.articlesPart = res.data.articlesPart;
      formData.tag = res.data.tag;
      formData.categoryId = res.data.categoryId;
      formData.coverUrl = res.data.coverUrl;
      // console.log(state);
    } else {
      ElMessage.error(res.msg);
    }
  });
};

// 初始化
const init = () => {
  getCategory();
  mdEditorConfig();
  if (route.query.articleId) {
    state.id = route.query.articleId;
    getDetail();
  }
};
init();
</script>

<template>
  <div class="write-essay">
    <header class="header flex items-center">
      <div class="flex items-center cursor-pointer ml-10 goback font-black" @click="goback">
        <i class="yh-icons-arrow-left"></i>
        <span style="width: 80px" class="ml-1">文章管理</span>
      </div>
      <el-input v-model="title" placeholder="请输入文章标题" class="mr-2 ml-1" />
      <yh-button theme="default" size="medium" class="mx-2" shape="round" @click="save">保存草稿</yh-button>
      <yh-button theme="primary" size="medium" class="mx-2" shape="round" @click="fabu">发布 </yh-button>
      <user></user>
    </header>
    <div class="flex-1 relative content">
      <gb-md-editor
        v-model="content"
        showCodeRowNumber
        :previewTheme="previewTheme"
        @onSave="save"
      >
      </gb-md-editor>
    </div>
  </div>
  <div class="dialog_wrap">
    <el-dialog
      title="发布文章"
      :model-value="state.dialogVisible"
      width="600px"
      :before-close="saveHandleClose"
      class="publish_dialog"
    >
      <el-form
        label-position="right"
        label-width="96px"
        :model="formData"
        style="max-width: 460px"
        :rules="rules"
        ref="ruleFormRef"
      >
        <el-form-item label="文章分类：" prop="categoryId">
          <el-radio-group v-model="formData.categoryId" class="cla_categoryId">
            <el-radio-button :label="item.id" v-for="item in classificatio" :key="item.id">
              {{ item.categoryName }}</el-radio-button
            >>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="添加标签：" prop="tag">
          <el-select v-model="formData.tag" placeholder="请选择标签" filterable>
            <el-option v-for="item in tagsList" :key="item.name" :label="item.name" :value="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面：" prop="coverUrl">
          <el-upload
            class="avatar-uploader"
            action=""
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="(rawFile: any) => beforeAvatarUpload(rawFile, ElMessage)"
            :on-error="onError"
            :http-request="coverUrlRequest"
            accept="image/png,image/jpg,image/jpeg,image/gif"
          >
            <div v-if="formData.coverUrl || formData.coverUrl !== ''" class="cover_img">
              <img :src="formData.coverUrl" class="avatar" />
              <i class="yh-icons-delete" @click.stop="deleteCover"></i>
            </div>
            <i class="yh-icons-plus" v-else></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="简介：" prop="summary">
          <el-input v-model="formData.summary" :rows="6" type="textarea" maxlength="100" show-word-limit />
        </el-form-item>
      </el-form>
      <template #footer>
        <yh-button @click="state.dialogVisible = false" shape="round">取 消</yh-button>
        <yh-button type="primary" @click="publish(ruleFormRef)" theme="primary" class="ml-4" shape="round"
          >发布文章
        </yh-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
@import "./write-essay.scss";
</style>

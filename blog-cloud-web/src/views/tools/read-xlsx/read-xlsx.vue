<script setup lang="ts">
import { ref, reactive, nextTick } from "vue";
import { ElMessage, genFileId, ElLoading, vLoading } from "element-plus";
import { readFileToJson } from "@/utils/modules/xlsx";
import type {
  UploadInstance,
  UploadProps,
  UploadRawFile,
  TabsPaneContext,
} from "element-plus";
const upload = ref<UploadInstance>();
const uploadWrapTarget = ref();
let xlsxData: any = {};
const xlsxDataSheetList = ref<any>([]);
const activeName = ref("");
const tableHeaderList = ref<any>([]);
const filters = ref<any>({});
const tabClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event);
};

const tableData = reactive<any>({
  page: 1,
  pageSize: 150,
  data: [],
  allNum: 0,
});
//超出限制时候, 选中时自动替换上一个文件。
const handleExceed: UploadProps["onExceed"] = (files) => {
  upload.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  upload.value!.handleStart(file);
};
const onCurrentPageChange = (currentPage: any) => {
  handlePageData();
};

//处理表格数据
const handlePageData = () => {
  //   console.log(tableData);
  let currentList = xlsxData[activeName.value];
  if (currentList.length > tableData.pageSize) {
    tableData.data = currentList.slice(
      (tableData.page - 1) * tableData.pageSize,
      tableData.page * tableData.pageSize
    );
  } else {
    tableData.data = currentList;
  }
};
// 切换tab
const switchTableData = () => {
  let currentList = xlsxData[activeName.value];
  tableData.allNum = currentList.length;
  tableData.page = 1;
  if (currentList) {
    tableHeaderList.value = Object.keys(currentList[0]).map((item) => {
      return {
        key: item,
        dataKey: item,
        title: item,
      };
    });

    let tableFiltersFlag: any = {};
    currentList.forEach((element: any) => {
      //@ts-ignore
      tableHeaderList.value.forEach(({ key }) => {
        let keyData = element[key];
        if (tableFiltersFlag[key]) {
          if (!tableFiltersFlag[key].includes(keyData)) {
            tableFiltersFlag[key].push(keyData);
          }
        } else {
          tableFiltersFlag[key] = [keyData];
        }
      });
      //   console.log(element);
    });
    filters.value = tableFiltersFlag;
    // console.log(tableFiltersFlag);
  }

  handlePageData();
};
const uploadChange = async (file: any) => {
  if (!/\.(xls|xlsx)$/.test(file.name.toLowerCase())) {
    ElMessage.warning("上传格式不正确，请上传xls或者xlsx格式");
    return false;
  }
  console.log("file", file);
  const loading = ElLoading.service({
    target: uploadWrapTarget.value,
    lock: true,
    text: "解析中",
  });
  await delay();
  readFileToJson(file.raw)
    .then((res: any) => {
      console.log(res);
      xlsxData = res;
      xlsxDataSheetList.value = Object.keys(res);
      activeName.value = xlsxDataSheetList.value[0];
      switchTableData();
      loading.close();
    })
    .catch((err) => {
      console.log(err);
    });
};
const delay = () => {
  return new Promise<boolean>((resolve, reject) => {
    const timer = setTimeout(() => {
      resolve(true);
      clearTimeout(timer);
    }, 300);
  });
};
</script>

<template>
  <div class="read_xlsx">
    <div ref="uploadWrapTarget">
      <el-upload
        ref="upload"
        class="read_xlsx-upload"
        action=""
        drag
        :auto-upload="false"
        :show-file-list="false"
        :on-change="uploadChange"
        :on-exceed="handleExceed"
        :limit="1"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </div>
    <el-tabs
      v-model="activeName"
      class="read_xlsx-tabs"
      size="small"
      @tab-click="tabClick"
    >
      <el-tab-pane
        :label="item"
        :name="item"
        v-for="item in xlsxDataSheetList"
        :key="item"
      >
        <el-table
          :data="tableData.data"
          border
          style="width: 100%"
          height="680"
          size="small"
        >
          <el-table-column type="index" width="50" align="center" />
          <el-table-column
            :prop="headerItem.title"
            :label="headerItem.title"
            v-for="headerItem in tableHeaderList"
            :key="headerItem.key"
            :min-width="100"
          />
        </el-table>
        <div class="pagination_wrap">
          <el-pagination
            background
            layout="total,prev, pager, next"
            :total="tableData.allNum"
            hide-on-single-page
            :page-size="tableData.pageSize"
            v-model:current-page="tableData.page"
            @current-change="onCurrentPageChange"
          />
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style scoped lang="scss">
.read_xlsx {
  width: 100%;
  min-height: 100vh;
}
.read_xlsx-upload {
  width: 90%;
  margin: 0px auto;
  padding: 24px 0;
}
.read_xlsx-tabs {
  width: 96%;
  margin: 0px auto;
}
.pagination_wrap {
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>

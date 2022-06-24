<template>
  <transition name="yh-zoom-in-right">
    <div v-show="visible" class="edit_line">
      <h1>
        <i class="el-icon-back" @click="visible = false">返回</i> / {{ title }}
      </h1>
      <span class="dialog-footer">
        <el-button @click="visible = false" size="mini">取 消</el-button>
        <el-button
          type="primary"
          @click="subItem"
          size="mini"
          :loading="loading"
          >保 存</el-button
        >
      </span>
      <LineForm ref="form" @close="close" />
      <h2 class="left_shu_h2">
        杆塔列表
      </h2>
      <div class="myTable-top-action">
        <div class="left">
          <el-button
            @click="addorUpdate('add')"
            v-if="isAuth('biz:line:save')"
            type="primary"
            plain
            >添加</el-button
          >
          <el-button
            @click="delets('all')"
            v-if="isAuth('biz:line:delete')"
            type="primary"
            plain
            >批量删除</el-button
          >
        </div>
      </div>
      <yh-table-pag
        :data="tableData"
        @getData="getPole"
        :total="total"
        style="width: 100%"
        class="my_table"
        :row-class-name="rowClassName"
        ref="myTable"
        :loading="loading"
        @selection-change="handleSelectionChange"
        loading-background="rgba(255, 255, 255, 0.4)"
        size="small"
        :height="heights"
        :currentPage="currentPage"
        :inPageGetData="false"
      >
        <el-table-column type="selection" align="center" width="55">
        </el-table-column>
        <el-table-column prop="number" label="序号" width="56" align="center">
          <template slot-scope="scope">
            <div>
              {{ scope.$index + 1 + (currentPage - 1) * pageSize }}
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="杆塔名称/号别"
          align="center"
          min-width="100"
        ></el-table-column>
        <el-table-column
          prop="type"
          label="结构形式"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="model"
          label="型号规格"
          align="center"
        ></el-table-column> 
         <el-table-column
          prop="runtime"
          label="投运时间"
          align="center"
        ></el-table-column> 
              <el-table-column
          prop="sort"
          label="排序"
          align="center"
        ></el-table-column> 
        <el-table-column
          prop="operation"
          label="操作"
          align="center"
          min-width="100px"
          v-if="isAuth('biz:line:update') || isAuth('biz:line:delete')"
        >
          <template slot-scope="{ row }">
            <div class="my_table_innerOperation">
         
                <span
                  @click="addorUpdate('edit', row)"
                  class="edit"
                  v-if="isAuth('biz:line:update')"
                >编辑</span>
                  <span
                  v-if="isAuth('biz:line:delete')"
                  @click="delets('one', row)"
                  class="del"
                >删除</span>
            </div>
          </template>
        </el-table-column>
      </yh-table-pag>

      <PoleAddOrUpdate ref="addUpdate" />
    </div>
  </transition>
</template>

<script>
import { currentPage } from "api/modules/tzgl";
import LineForm from "./line-form.vue";
import PoleAddOrUpdate from "./pole-add-or-update.vue";
export default {
  components: { LineForm, PoleAddOrUpdate },
  data() {
    return {
      visible: false,
      title: "编辑",
      loading: false,
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 10,
      multipleSelection: [],
      row: {},
      heights: null,
    };
  },
  mounted() {
  },
  methods: {
    init(row) {
      this.visible = true;
      // console.log(row);
      this.title = row.name + "-线路编辑";
      this.row = { ...row };
      this.$nextTick(() => {
        this.$refs.form.init("edit", { ...row });
      });
      this.getPole();
    },
    //获取杆塔列表
    getPole(pageData) {
      this.multipleSelection = [];
      if (pageData) {
        this.pageSize = pageData.pageSize;
        this.currentPage = pageData.currentPage;
      }
      currentPage(6,{
        size: this.pageSize,
        current: this.currentPage,
        lineId: this.row.id,
      }).then((res) => {
        if (res.success) {
          this.tableData = res.data.list;
          if (!this.heights) {
            this.$nextTick(() => {
              this.heights = this.$tableHeight(this.$refs.myTable.$el);
            });
          }
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    addorUpdate(type, row) {
      this.$nextTick(() => {
        this.$refs.addUpdate.init(type, row);
      });
    },
    handleSelectionChange(val) {
      // console.log(val);
      this.multipleSelection = val;
    },
    close() {
      this.$parent.getData();
      this.visible = false;
    },
    subItem() {
      this.$nextTick(() => {
        this.$refs.form.subItem();
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.edit_line {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: #fff;
  z-index: $default-zindex;
  box-sizing: border-box;
  margin-top: 10px;
  h1 {
    position: absolute;
    font-size: 15px;
    top: -32px;
    left: 0;
    background: $content-background;
    height: 36px;
    width: 100%;
    line-height: 36px;
    font-size: 15px;
    font-weight: 900;
    i {
      font-weight: 900;
      cursor: pointer;
      color: rgb(138, 138, 138);
    }
    &:hover {
      i {
        color: $primary-color;;
      }
    }
  }
  h2 {
    background: $content-background;
  }
  .dialog-footer {
    // padding-left: 20px;
    margin: 10px 10px;
    display: flex;
  }
  .myTable-top-action {
    box-sizing: border-box;
    padding: 0 10px;
  }
}
</style>
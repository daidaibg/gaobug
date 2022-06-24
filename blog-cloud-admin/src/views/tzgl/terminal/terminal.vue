

<!--
 * @Author: daidai
 * @Date: 2021-12-13 16:35:02
 * @LastEditors: daidai
 * @LastEditTime: 2022-02-23 14:45:55
-->
<template>
  <el-drawer
    title=""
    :visible.sync="visible"
    :before-close="handleClose"
    direction="rtl"
    size="400px"
    custom-class="yh-custom"
  >
    <template slot="title">
      <div>
        {{ title }}
      </div>
    </template>
    <div class="myTable-top-action" v-if="isAuth('biz:terminal:save')">
      <div class="left" style="margin-left: 10px">
        <el-button
          @click="add('add')"
          v-if="isAuth('biz:terminal:save')"
          type="primary"
          plain
          >添加</el-button
        >
        <!-- <el-button
          @click="delets('all')"
          v-if="isAuth('biz:terminal:delete')"
          type="primary"
          plain
          >批量删除</el-button
        > -->
      </div>
    </div>
    <ul
      class="terminal_list"
      v-loading="loading"
      element-loading-spinner="el-icon-loading"
    >
      <li v-for="(item, i) in tableData" :key="item.id">
        <div class="first">
          <div>{{ i + 1 }}</div>
        </div>
        <div style="width: 27%">
          <h4>终端ID</h4>
          <p>{{ item.nameno }}</p>
        </div>
        <div style="width: 27%;">
          <h4>在线状态</h4>
          <p >
            <el-tag
              size="mini"
              style="margin-left:6px"
              :type="item.onlinestate == 1 ? 'success' : 'danger'"
              >{{ item.onlinestate == 1 ? "在线" : "离线" }}</el-tag
            >
          </p>
        </div>
          <div style="width: 26%">
          <h4>照片</h4>
        </div>
        <div style="width: 20%">
          <h4>相线</h4>
          <p>{{ item.phase }}</p>
        </div>

        <div style="width: 50%">
          <h4>创建时间</h4>
          <p>{{ item.createtime }}</p>
        </div>
       <div style="width: 30%" class="flex">
          <!-- <h4>照片</h4> -->
          <el-image
             v-if="item.imgUrlList.length > 0"
            :src="item.imgUrlList[0].filePath"
            :preview-src-list="srclist(item.imgUrlList)"
            fit="cover"
            class="imggs"
          >
          </el-image>
        </div>
        <div style="width: 20%">
          <h4>操作</h4>
          <div class="my_table_innerOperation">
            <span
              @click="add('edit', item)"
              class="edit"
              v-if="isAuth('biz:terminal:update')"
              >编辑</span
            >
            <span
              v-if="isAuth('biz:terminal:delete')"
              @click="delets('one', item)"
              class="del"
              >删除</span
            >
          </div>
        </div>
      </li>
    </ul>
    <AddOrUpDate ref="AddOrUpDate" :id="id" @getdata="getdataedit" />
  </el-drawer>
</template>

<script>
import { currentList, currentDelete } from "api/modules";
import AddOrUpDate from "./terminal-add-or-update.vue";
export default {
  components: { AddOrUpDate },
  data() {
    return {
      loading: false,
      total: 0,
      tableData: [],
      visible: false,
      title: "",
      id: "",
    };
  },
  created() {},

  mounted() {},
  methods: {
    init(row) {
      this.visible = true;
      //   console.log(row);
      this.id = row.id;
      this.title = row.gatewayno + "-终端列表";
      this.getdata();
    },
    getdataedit() {
      this.$parent.getdata();
      this.getdata();
    },
    srclist(){

    },
    getdata() {
      this.loading = true;
      currentList(3, {
        gatewayId: this.id,
      }).then((res) => {
        this.loading = false;
        // console.log(res);
        if (res.success) {
          this.tableData = res.data;
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    add(type, row) {
      this.$refs.AddOrUpDate.init(type, row);
    },
    search() {
      this.currentPage = 1;
      this.getdata();
    },
    handleClose(done) {
      done();
      //   this.$confirm("确认关闭？")
      //     .then((_) => {
      //       done();
      //     })
      //     .catch((_) => {});
    },
    //删除
    delets(type, row) {
      // console.log(row)
      let str = "",
        appnum = [];
      if (type == "one") {
        str = row.nameno;
        appnum.push(row.id);
      } else {
        if (this.multipleSelection.length == 0) {
          this.$message.warning("请选择最少一条数据!");
          return;
        }
        this.multipleSelection.map((item) => {
          str += item.nameno + "、";
          appnum.push(item.id);
        });
        str = str.substr(0, str.length - 1);
      }
      this.$confirm(`确认永久删除终端${str}, 是否继续?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          currentDelete(3, appnum).then((data) => {
            // console.log(data.data);
            if (data.success) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.getdataedit();
            } else {
              this.$message({
                type: "error",
                message: data.msg,
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
       srclist(imgUrlList) {
      let arr = [];
      imgUrlList.map((item) => {
        arr.push(item.filePath);
      });
      return arr;
    },
    reset() {
      for (let i in this.dataForm) {
        this.dataForm[i] = "";
      }
      this.currentPage = 1;
      this.getdata();
    },
  },
};
</script>
<style lang='scss' scoped>
/deep/.el-drawer__body {
  padding: 0;
}
.terminal_list {
  box-sizing: border-box;
  padding: 0px 0 0 8px;
  li {
    border-bottom: solid 2px #e9f1f9;
    padding: 6px 6px 0px 20px;
    font-size: 14px;
    display: flex;
    flex-wrap: wrap;
    position: relative;
    h4 {
      color: #505050;
      height: 24px;
      line-height: 24px;
      color: #000000;
    }
    p {
      color: #606266;
      font-size: 13px;
    }
    > div {
      margin-bottom: 6px;
    }
    .first {
      position: absolute;
      background: rgba($color: $primary-color, $alpha: 0.4);
      left: -2px;
      top: 8px;
      width: 10px;
      height: 10px;
      border-radius: 50%;
      padding: 4px;
      text-align: center;
      line-height: 10px;
      color: #252525;
    }
    .imggs{
      width: 60px;
      height: 60px;
      cursor: pointer;
      margin-top: -16px;
    }
    .flex{
      h4{
        line-height: 44px;
      }
    }
  }
}
</style>
<!--
 * @Author: daidai
 * @Date: 2021-12-22 16:12:43
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 15:33:37
 * @FilePath: \web-pc\src\views\sys\device-alarm\device-alarm.vue
-->
<template>
  <div class="contents alarm flex flex-wrap justify-around">
    <!-- <div class="sbgjcs alarm-item"> -->
    <!-- <h2 class="left_shu_h2">{{ sbgjcsname }}</h2> -->
    <el-table
      :data="sbgjcsData"
      style="width: 100%"
      class="my_table"
      size="mini"
    >
      <el-table-column prop="alertname" label="告警类型" align="center">
      </el-table-column>

      <el-table-column prop="maxvalue" label="最大值" align="center">
      </el-table-column>
      <el-table-column prop="minvalue" label="最小值" align="center">
      </el-table-column>
      <el-table-column
        prop="isautocancel"
        label="是否开启自动取消"
        align="center"
      >
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.isautocancel == 1 ? 'success' : 'danger'"
            >{{ row.isautocancel == 1 ? "是" : "否" }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="isactive" label="是否开启自动取消" align="center">
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.isactive == 1 ? 'success' : 'danger'"
            >{{ row.isactive == 1 ? "是" : "否" }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        prop="autocanceltime"
        label="自动取消时间大小(天)"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="" label="操作" align="center">
        <template slot-scope="{ row }">
          <div class="my_table_innerOperation">
            <span
              @click="update(row, 'sbgjcs', sbgjcsname)"
              class="edit"
              v-if="isAuth('biz:alertconfig:update')"
              >编辑</span
            >
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- </div> -->
    <!-- <div class="sbgjzdqxpz alarm-item">
      <h2 class="left_shu_h2">{{ sbgjzdqxpzname }}</h2>
      <el-table
        :data="sbgjzdqxpzData"
        style="width: 100%"
        class="my_table"
        size="mini"
      >
        <el-table-column prop="name" label="告警类型" align="center">
        </el-table-column>
        <el-table-column prop="isOpen" label="是否开启" align="center">
          <template slot-scope="{ row }">
            <el-tag
              size="mini"
              :type="row.isOpen == 1 ? 'success' : 'danger'"
              >{{ row.isOpen == 1 ? "是" : "否" }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column prop="time" label="时间大小(天)" align="center">
        </el-table-column>
        <el-table-column prop="" label="操作" align="center">
          <template slot-scope="{ row }">
            <div class="my_table_innerOperation">
              <span
                @click="update(row, 'sbgjzdqxpz', sbgjzdqxpzname)"
                class="edit"
                v-if="isAuth('biz:gateway:update')"
                >编辑</span
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div> -->
    <Update ref="update" :type="type" />
  </div>
</template>

<script>
import Update from "./device-alarm-update.vue";
import { currentList } from "api/modules";
export default {
  components: { Update },
  data() {
    return {
      sbgjcsname: "设备告警参数",
      sbgjzdqxpzname: "设备告警自动取消配置",
      sbgjcsData: [
        // {
        //   alertname: "雷击",
        //   maxvalue: 66,
        //   minvalue: 10,
        //   username: "未知",
        //   isautocancel: 0,
        //   isactive: 1,
        //   autocanceltime: 7,
        //   id:1
        // },
        // {
        //   alertname: "闪络",
        //   maxvalue: 6,
        //   minvalue: 2,
        //   username: "未知",
        //   isautocancel: 1,
        //   isactive: 0,
        //   autocanceltime: 5,
        //    id:2
        // },
      ],
      sbgjzdqxpzData: [],
      type: "",
    };
  },
  created() {
    this.getData();
  },

  mounted() {},
  methods: {
    getData() {
      currentList(4).then((res) => {
        // console.log("告警管理", res);
        // console.log(res)
        if (res.success) {
          this.sbgjcsData = res.data;
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      })
    },
    update(row, type, name) {
      this.type = type;
      this.$nextTick(() => {
        this.$refs.update.init(row, name);
      });
    },
  },
};
</script>
<style lang='scss' scoped>
.alarm {
  &-item {
    width: 46%;
    height: 50%;
    box-shadow: $primary-shadow;
    border-radius: 10px;
    box-sizing: border-box;
    padding: 10px 20px;
    margin-top: 20px;
  }
  .sbgjcs {
  }
  .sbgjzdqxpz {
  }
}
</style>
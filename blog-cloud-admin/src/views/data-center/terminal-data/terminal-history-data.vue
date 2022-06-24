<template>
  <div class="contents left-tree-content">
    <div class="search-form">
      <div class="search-form-item">
        <label>网关ID:</label>
        <el-input
          size="small"
          placeholder="请输入网关ID"
          v-model="search.gatewayno"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>终端ID:</label>
        <el-input
          size="small"
          placeholder="请输入终端ID"
          v-model="search.terminalno"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item">
        <label>线路名称:</label>
        <el-input
          size="small"
          placeholder="请输入线路名称"
          v-model="search.lineName"
          clearable
        ></el-input>
      </div>
      <div class="search-form-item time">
        <label>时间选择:</label>
        <el-date-picker
          size="small"
          v-model="search.date"
          type="datetimerange"
          range-separator="至"
          unlink-panels
          :default-value="val.defaultDate"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="changeDate"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="endDatePicker"
        >
        </el-date-picker>
      </div>
      <div class="flex align-center rdx-p-l-20">
        <el-button
          size="mini"
          clearable
          :loading="loading"
          type="primary"
          @click="getdata('search')"
          >查询</el-button
        >
        <el-button size="mini" @click="reset"> 重置</el-button>
      </div>
    </div>
    <div class="myTable-top-action" v-if="isAuth('biz:terminaldata:export')">
      <div class="left">
        <!-- <el-button type="primary">删 除</el-button> -->
        <el-button
          @click="dc()"
          type="dc"
          :loading="loadings.dc"
          v-if="isAuth('biz:terminaldata:export')"
          >导出
        </el-button>
      </div>
    </div>
    <yh-table-pag
      :data="getData.tableList.list"
      style="width: 100%"
      class="my_table"
      @getData="getdata"
      :row-class-name="rowClassName"
      ref="myTable"
      size="small"
      :loading="loading"
      :total="getData.tableList.totalCount"
      :currentPage="pagData.currentPage"
      :height="heights"
      @selection-change="changeSelection"
    >
      <el-table-column type="selection" align="center" width="50">
      </el-table-column>
      <el-table-column prop="number" label="序号" width="50" align="center">
        <template slot-scope="scope">
          <div>
            {{
              scope.$index + 1 + (pagData.currentPage - 1) * pagData.pageSize
            }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gatewayno" label="网关ID" align="center">
      </el-table-column>
      <el-table-column prop="terminalno" label="终端ID" align="center">
      </el-table-column>
      <el-table-column prop="onlinestate" label="在线状态" align="center">
        <template slot-scope="{ row }">
          <el-tag
            size="mini"
            :type="row.onlinestate == 1 ? 'success' : 'danger'"
          >
            {{ row.onlinestate == 1 ? "在线" : "离线" }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="phase" label="相线" align="center">
      </el-table-column>
      <template v-for="item in zdlabelList">
        <el-table-column
          prop=""
          :label="item.itemname"
          align="center"
          :key="item.itemcode"
        >
          <template slot-scope="{ row }">
            <div>
              {{ row.data && row.data[item.itemcode] | montionFilter }}
            </div>
          </template>
        </el-table-column>
      </template>
      <!-- <el-table-column prop="uploadtime" label="入库时间" align="center">
			</el-table-column> -->
    </yh-table-pag>
  </div>
</template>

<script>
import { historyDataApi } from "api/modules/warningData";
import DDmoment from "@/lib/dd-moment";
import { shortcuts } from "@/lib/currency";
import { currencyExport } from "api/modules";
import { monitoritemList } from "api/modules";

export default {
  data() {
    return {
      loading: false,
      total: 0,
      heights: null,
      getData: {
        tableList: [],
      },
      zdlabelList: [],
      loadings: {
        dc: false,
      },
      search: {
        gatewayno: "", //网关id
        terminalno: "", //终端id
        date: [
          DDmoment().subtract(1, "weeks").format("yyyy-MM-dd HH:mm:ss"),
          DDmoment().format("yyyy-MM-dd HH:mm:ss"),
        ], //时间
      },
      pagData: {
        pageSize: 10,
        currentPage: 1,
      },
      val: {
        defaultDate: "",
        selectionArr: [], //表格选中
      },
      endDatePicker: {
        shortcuts: shortcuts,
        disabledDate: function (time) {
          return (
            time.getTime() > Date.now() ||
            time.getTime() < Date.now() - 1000 * 3600 * 24 * 90
          );
        },
      },
    };
  },
  created() {
    if (this.$route.query.terminalno != undefined) {
      this.search.terminalno = this.$route.query.terminalno;
    }
    monitoritemList({
      deviceType: 2,
    }).then((res) => {
      if (res.success) {
        this.zdlabelList = res.data;
      } else {
        this.$message({
          message: res.msg,
          type: "error",
        });
      }
    });
  },
  mounted() {
    this.val.defaultDate = new Date();
    this.val.defaultDate.setTime(
      this.val.defaultDate.getTime() - 3600 * 1000 * 24 * 30
    );
  },
  methods: {
    //删除选中
    onDel() {
      if (this.val.selectionArr != "") {
        this.$confirm(`确认永久删除, 是否继续?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            console.log("12", this.val.selectionArr);
            // terminalDelApi({
            // 	ids:this.val.selectionArr
            // }).then((res) => {
            // 	console.log("res",ids,res)
            // 	if (res.success) {
            // 		this.$message.success('删除成功')
            // 		this.getdata()
            // 	} else {
            // 		this.$message(res.msg)
            // 	}
            // })
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.$message.warning("请选择最少一条数据!");
      }
    },
    dc() {
      let param = {
        ...this.search,
      };
      if (param.date) {
        param.startTime = param.date[0];
        param.endTime = param.date[1];
        let cha =
          DDmoment(param.endTime).time.getTime() -
          DDmoment(param.startTime).time.getTime();
        let chaDay = cha / 1000 / 60 / 60 / 24;
        if (chaDay > 30) {
          this.$message.warning("导出日期相差不能超过30天！");
          return;
        }
      } else {
        this.$message.warning("请先选择日期范围，相差不能超过30天！");
        return;
      }
      delete param.date;
      this.loadings.dc = true;

      let str = "";
      if (param.gatewayno) {
        str += param.gatewayno + "(网关)-";
      }
      if (param.terminalno) {
        str += param.terminalno + "-";
      }
      // if( param.startTime){
      //   str+= param.startTime+'-'+ param.endTime+'-'
      // }
      currencyExport(8, param, str + "终端历史数据").then((data) => {
        this.loadings.dc = false;
        if (!data.success) {
          this.$message.error(data.msg);
        }
      });
    },
    //表格选中
    changeSelection(row) {
      this.val.selectionArr = [];
      row.forEach((item) => {
        this.val.selectionArr.push(item.deviceId);
      });
    },
    //获取表格数据
    getdata(pageData) {
      if (pageData == "search") {
        this.pagData.currentPage = 1;
      }
      if (pageData && pageData != "search") {
        this.pagData = {
          currentPage: pageData.currentPage,
          pageSize: pageData.pageSize,
        };
      }
      this.loading = true;
      historyDataApi({
        size: this.pagData.pageSize,
        current: this.pagData.currentPage,
        gatewayno: this.search.gatewayno,
        startTime: this.search.date[0],
        endTime: this.search.date[1],
        terminalno: this.search.terminalno,
      }).then((res) => {
        this.loading = false;
        if (res.success) {
          this.getData.tableList = res.data;
          if (!this.heights) {
            this.heights = this.$tableHeight(this.$refs.myTable.$el);
          }
        } else {
          this.$message(res.msg);
        }
      });
    },
    //重置
    reset() {
      (this.pagData.pageSize = 10),
        (this.pagData.currentPage = 1),
        (this.search.sender = ""),
        (this.search.gatewayno = ""),
        (this.search.lineName = "");
      this.search.terminalno = "";
      this.search.date = [
        DDmoment().subtract(7, "days").format("yyyy-MM-dd HH:mm:ss"),
        DDmoment().format("yyyy-MM-dd HH:mm:ss"),
      ];
      this.getdata();
    },
    //获取时间
    changeDate(e) {
      this.search.date = e;
    },
  },
};
</script>

<style lang="scss" scoped>
.search-form .search-form-item label {
  text-align: right;
  padding-right: 6px;
}
</style>

<!--
 * @Author: daidai
 * @Date: 2021-12-06 11:19:35
 * @LastEditors: daidai
 * @LastEditTime: 2022-03-30 17:10:16
 * @FilePath: \web-pc\src\views\manage-home\manage-header.vue
-->
<template>
  <div class="headers">
    <div class="left">
      <div class="suofang flex-center" @click="suofang">
        <!-- <i class="el-icon-s-fold"></i> -->
        <img src="../../assets/img/sousuo.png" alt="" />
      </div>
      <h1>{{ sysName }}</h1>
    </div>
    <div class="right">
      <ul class="action">
        <li @click="gotoBig">
          <el-tooltip class="item" effect="dark" content="大屏">
            <img
              src="../../assets/img/header/bigscreen.png"
              alt="大屏"
              style="margin-right: 10px"
            />
          </el-tooltip>
        </li>
      </ul>
      <el-dropdown @command="handleCommand">
        <div class="user flex-center">
          <el-avatar :size="36" @error="errorHandler" fit="cover" :src="url">
          </el-avatar>
          <span>{{ userData.realName || "" }}</span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logoOut">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { sysName } from "@/constants";
import { clearLoginInfo } from "@/lib/currency";
import { getUserInfo } from "api/modules/sys";
export default {
  data() {
    return {
      sysName: sysName,
      url: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      username: "管理员",
    };
  },
  computed: {
    userData() {
      let userdata = this.$store.state.userData;
      return userdata;
    },
  },
  created() {
    this.userInfo();
  },
  mounted() {},
  methods: {
    handleCommand(type) {
      console.log(type);
      switch (type) {
        case "logoOut":
          clearLoginInfo();
          this.$router.push("/login");
          break;

        default:
          break;
      }
    },
    gotoBig(){
      // window.location.href="bigScreen"
      window.open("bigScreen.html")
    },
    errorHandler() {},
    suofang() {
      this.$store.commit("setCollapsed");
    },
    userInfo() {
      getUserInfo().then((res) => {
        if (res.success) {
          this.$store.commit("setUserData", res.data);
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
  },
};
</script>
<style lang='scss' scoped>
.headers {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .left {
    color: #fefeff;
    display: flex;
    align-items: center;
    height: 100%;
    .suofang {
      width: 65px;
      height: 100%;
      background: #264663;
      cursor: pointer;
      i {
        font-size: 26px;
      }
    }

    h1 {
      font-size: 20px;
      // font-weight: 900;
      text-indent: 10px;
      // letter-spacing: 3px;
      color: #fefeff;
    }
  }
  .right {
    display: flex;
    align-items: center;
    .user {
      cursor: pointer;
      span {
        margin-left: 10px;
        font-size: 14px;
        color: #fff;
      }
    }
    .action {
      display: flex;
      align-items: center;
      li {
        cursor: pointer;
        img {
          width: 14px;
        }
      }
    }
  }
}
</style>
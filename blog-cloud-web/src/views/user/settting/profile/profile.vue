<script setup lang='ts'>
import { ElInput } from "element-plus"
import { reactive } from "vue"
import { genders } from "./profile"
import { useUserStore } from '@/store'
const userStore = useUserStore()

const getUserDataKey = (key: string) => {
  return userStore.getUserData[key]
}
const forData = reactive({
  nickName: getUserDataKey('nickName'),
  gender: getUserDataKey('gender'),
  birthday: getUserDataKey('birthday'),
  username: getUserDataKey('username'),
  summary: getUserDataKey('summary'),
  avatar: getUserDataKey('avatar'),

})

</script>

<template>
  <div class='user_setting_profile_wrap'>
    <h2 class="titles">基本信息</h2>
    <ul class="form-wrap">
      <li  class="form-wrap_item" style="align-items: center;">
        <label class="labels"> 我的头像</label>

        <div class="form-wrap_item_contents avatars">
          <img :src="forData.avatar" alt="">
        </div>
      </li>
      <li class="form-wrap_item">
        <label class="labels"> 用户昵称</label>
        <div class="form-wrap_item_contents">
          <el-input v-model="forData.nickName"></el-input>

        </div>
      </li>
      <li class="form-wrap_item">
        <label class="labels"> 登录账号</label>
        <div class="form-wrap_item_contents">
          <el-input v-model="forData.username" disabled></el-input>

        </div>
      </li>
      <li class="form-wrap_item">
        <label class="labels"  style="letter-spacing:28px;"> 性别</label>
        <div class="form-wrap_item_contents">
          <el-select v-model="forData.gender" placeholder="请选择性别" class="select">
            <el-option v-for="item in genders" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>

        </div>
      </li>
      <li class="form-wrap_item">
        <label class="labels">出生年月</label>
        <div class="form-wrap_item_contents">
          <el-input v-model="forData.birthday"></el-input>

        </div>
      </li>
      <li class="form-wrap_item">
        <label class="labels" style="letter-spacing:28px;">简介</label>
        <div class="form-wrap_item_contents">
          <el-input v-model="forData.summary" maxlength="100" show-word-limit placeholder="请输入个人简介" type="textarea"
            :rows="3"></el-input>
        </div>
      </li>
          <div class="submit_wrap">
      <yh-button theme="primary" size="medium" >保存修改</yh-button>
      
    </div>
    </ul>

  </div>
</template>

<style scoped lang='scss'>
.user_setting_profile_wrap {
  .titles {
    height: 48px;
    line-height: 48px;
    font-size: 18px;
    font-weight: 600;
    color: var(--yh-text-color-primary);
    padding-left: 16px;
    border-bottom: 1px solid var(--yh-border-level-1-color);
  }

  .form-wrap {
    padding: 0 20px;
  }

  .form-wrap_item {
    display: flex;
    align-items: flex-start;
    padding-top: 22px;
    font-size: 14px;

    .labels {
      width: 120px;
    color: var(--yh-text-color-primary);

    }

    .form-wrap_item_contents {
      width: 400px;
      flex-shrink: 0;
    }
    .avatars{
      border-radius: 50%;
      overflow: hidden;
      width: 80px;
      height: 80px;
    }

    .select {
      width: 100%;
    }
  }
  .submit_wrap{
    margin-top: 32px;
    padding-left: 120px;
  }
}
</style>
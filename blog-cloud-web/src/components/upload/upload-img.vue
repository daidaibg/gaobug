<script setup lang='ts'>
import {  ElMessage,ElUpload } from "element-plus";
import type {  UploadRequestOptions} from 'element-plus'
import {  beforeAvatarUpload,onUploadCover } from "./upload";
import {ref} from "vue"
const imgValue = ref()
// 上传失败
const onError = (error: Error|any)=>{
    if(error.msg){
        ElMessage.error(error.msg)
    }else{
        ElMessage.error('未知异常，图片上传失败')
    }
}
// 上传成功
const handleAvatarSuccess = (response: any, uploadFile: any) => {
    if(response.code ==200){
        imgValue.value= response.data.url
    }else{
        onError(response)
    }
}

//封面上传 
const coverUrlRequest =(options:UploadRequestOptions):any=>{
    return onUploadCover(options)
}
</script>

<template>
 <el-upload class="avatar-uploader" action=""
    :show-file-list="false" :on-success="handleAvatarSuccess" 
    :before-upload="(rawFile:any) => beforeAvatarUpload(rawFile, ElMessage)"
    :on-error="onError"
    :http-request="coverUrlRequest"
    accept="image/png,image/jpg,image/jpeg,image/gif">
    <img v-if="imgValue" :src="imgValue" class="avatar" />
    <i class="yh-icons-plus"></i>
</el-upload>
</template>

<style scoped lang='scss'>

</style>
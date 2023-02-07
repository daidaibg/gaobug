/*
 * @Author: wei
 * @description:
 * @LastEditTime: 2023-02-07 11:45:41
 */

import { currentPOST } from "@/api";
import { ElMessage } from "element-plus";
import { ReqCodeEnum } from "@/enums";
import { isShowLoginDislog, showLoginDislog } from "@/hook";

export interface BlogLikeParamType {
    targetId: string | string[] | undefined; //文章id
    targetType: 1 | 2; //目标类型  1 对应文章点赞 2对应评论点赞
    likeFlag: 1 | 0; // 点击标识 likeFlag 1是点赞 0是取消
}
export interface BlogLikeActionType {
    success?: Function; //请求成功回调
    error?: Function; //请求失败回调
    Unauthorized?: Function; //未登录回调
}
export const useBlogAction = () => {
    let flag = false;
    /**
     * @description: 点赞
     * @param {BlogLikeParamType} param
     * @param {BlogLikeActionType} option
     * @return {*}
     */
    const blogLike = (param: BlogLikeParamType, option: BlogLikeActionType): any => {
        let loginFlag = isShowLoginDislog();
        if (!loginFlag || flag) return;
        flag = true;
        currentPOST("blogLike", param).then((res) => {
            flag = false;
            handleReqBody(res, option);
        });
    };

    return {
        blogLike
    }
};

/**
 * @description: 处理请求返回的值
 * @param {any} res
 * @param {BlogLikeActionType} option
 * @return {*}
 */
export const handleReqBody = (res: any, option: BlogLikeActionType) => {
    console.log(res);
    if (res.code === 200) {
        option.success && option.success(res);
    } else if (res.code === ReqCodeEnum.Unauthorized) {
        ElMessage.info("请先登录!");
        showLoginDislog();
        option.Unauthorized && option.Unauthorized(res);
    } else {
        ElMessage.error(res.msg);
        option.error && option.error(res);
    }
};

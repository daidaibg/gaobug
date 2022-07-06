import { currentPOST } from "@/api"
import { ElMessage } from "element-plus"
import { ReqCodeEnum } from "@/enums"
import { isLoginShowDislog, showLoginDislog } from "../current"

let flag = false
export interface BlogLikeParamType {
    "targetId": string | string[] | undefined,  //文章id
    "targetType": 1 | 2,//目标类型  1 对应文章点赞 2对应评论点赞
    "likeFlag": 1 | 0,// 点击标识 likeFlag 1是点赞 0是取消

}
export interface BlogLikeOptionType {
    "success"?: Function,  //请求成功回调
    "error"?:Function,//请求失败回调 
    "Unauthorized"?:Function,//未登录回调
}
export const blogLike = (param: BlogLikeParamType, option: BlogLikeOptionType) => {
    let loginFlag = isLoginShowDislog()
    if (!loginFlag || flag) return
    flag = true
    currentPOST("blogLike", param).then(res => {
        flag = false
        handleReqBody(res, option)
    })
}
//处理请求返回的值
export const handleReqBody = (res: any, option: BlogLikeOptionType) => {
    console.log(res);

    if (res.code === 200) {
        option.success && option.success(res)
    } else if (res.code === ReqCodeEnum.Unauthorized) {
        ElMessage.info("请登陆后操作!")
        showLoginDislog()
        option.Unauthorized && option.Unauthorized(res)

    } else {
        ElMessage.error(res.msg)
        option.error && option.error(res)
    }
}


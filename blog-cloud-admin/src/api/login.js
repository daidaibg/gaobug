/*
 * @Author: daidai
 * @Date: 2021-07-13 08:51:12
 * @LastEditTime: 2022-05-09 16:16:32
 */
/**
 * @author liuxiaodai
 * @content 一些文件的接口
 */

import * as API from "./api";

/**
 * 登录
 */
 export const Login = (param) => {
    return API.POST("/auth/login/system/login/"+param.id, param)
}


/**
 * 获取用户信息
 * @param {*} param 
 */

export const userInfo = (param) => {
    return API.GET("/info", param)
}
/**
 * 退出账号 
 */
export const logout = (param) => {
    return API.GET("/logout", param)
}

/**
 * 发送验证码
 */
export const sendVerifyCode = (param) => {
    return API.POST("/login/sendCode", param)
}
/**
 * 获取图形验证码
 * @param {Object} param 
 */
export const captcha = (param) => {
    return API.GET("/auth/login/captcha/"+ param)
}



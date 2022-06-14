/*
 * @Author: daidai
 * @Date: 2022-01-18 16:02:35
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 16:02:51
 * @FilePath: \web-pc\src\api\modules\device-msg.js
 */


import * as API from "../api";

/**
 * 开启或关闭设备报警
 * @param {*} param 
 */
 export const gatewayOpenOrCloseAlert= (param) => {
    return API.POST("/biz/gateway/openOrCloseAlert", param)
}
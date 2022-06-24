/*
 * @Author: daidai
 * @Date: 2021-12-22 11:38:18
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 16:07:02
 * @FilePath: \web-pc\src\api\modules\sjzx.js
 */
import * as API from "../api";

/**
 * 查询实时数据
 * @param {*} param 
 */
 export const getMonitordatagateway =  (param)=> {
    return API.GET("/biz/monitordatagateway/queryDataList", param)
}



/**
 * 网关查询实时数据
 * @param {*} param 
 */
 export const kvlatesTqueryDataList =  (param)=> {
    return API.GET("/biz/kvlatest/queryDataList", param)
}


/**
 * 网关查询实时数据
 * @param {*} param 
 */
 export const GetMonitordatagatewayhistory =  (param)=> {
    return API.GET("/biz/monitordatagatewayhistory/queryHisDataList", param)
}

/**
 * 查询网关曲线数据
 * @param {*} param 
 */
 export const GetyGatewayCurveDataList =  (param)=> {
    return API.GET("/biz/countanalyze/gateway/queryGatewayCurveDataList", param)
}

/**
 * 查询终端曲线数据
 * @param {*} param 
 */
 export const QueryTerminalCurveDataList =  (param)=> {
    return API.GET("/biz/countanalyze/terminal/queryTerminalCurveDataList", param)
}

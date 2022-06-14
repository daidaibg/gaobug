/*
 * @Author: daidai
 * @Date: 2021-12-23 11:18:37
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-05-10 17:58:57
 * @FilePath: \web-pc\src\api\modules\index.js
 */
import * as API from "../api";
import {exportFile} from '@/lib/currency'
/**
 * 查询数据项列表
 * @param {Number} deviceType 设备类型，1：网关，2：终端
 */
 export const monitoritemList =(param)=>{
    return API.GET("/biz/monitoritem/list",param)
} 

export const paramType ={
    1:"/biz/gateway", //设备
    2:"/biz/line",//线路
    3:"/biz/terminal",//终端
    4:"/biz/alertconfig", //告警类型
    5:"/biz/upgradepackage", //升级包
    6:"/biz/pole", //杆塔
    7:"/biz/monitordatagatewayhistory", //网关历史数据
    8:"/biz/monitordataterminalhistory",//终端历史数据
    9:"/sys/sysdict",// 字典
    10:"/biz/company",//公司
    12:'/biz/monitoritem',//查询数据项列表
    13:'/biz/alertdata',//告警数据
    14:'/system/menu/nav',//菜单
}
/******************      通用增删改查       ********************* */
/**
 * 通用列表
 * @param {*} param 
 */
 export const currentList =  (key,param)=> {
    return API.GET(paramType[key]+"/list", param)
}
export const currentPage =  (key,param)=> {
    return API.GET(paramType[key]+"/page", param)
}
/**
 * 查询可选择的列表
 * @param {*} param 
 */
 export const currentSelectList=  (key,param)=> {
    return API.GET(paramType[key]+"/selectList", param)
}


/**
 * 通用新增
 * @param {*} param 
 */
 export const currentSave= (key,param)=> {
    return API.POST(paramType[key]+"/save", param)
}
/**
 * 通用修改
 * @param {*} param 
 */
 export const currentUpdate=  (key,param) => {
    return API.POST(paramType[key]+"/update", param)
}

/**
 * 通用删除
 * @param {*} param 
 */
 export const currentDelete= (key,param) => {
    return API.POST(paramType[key]+"/delete", param)
}

/**
 * 通用获取所有不分页
 * @param {*} param 
 */
 export const currentSelect=  (key,param)=> {
    return API.GET(paramType[key]+"/select", param)
}
/**
 * 通用导出文档流 未处理数据 需要再处理数据进行导出
 * @param {*} param 
 */
 export const currentExport=  (key,param)=> {
    return API.FILE({url:paramType[key]+"/export"},{}, param)
}
/**
 * 通用导出文档流 （处理过数据可以直接导出）
 * @param {*} param
 */
 export const currencyExport=  async (key,param,exportName)=> {
    const res = await API.FILE({ url: paramType[key] + "/export" }, {}, param);
    return exportFile(res, exportName);
}
/**
 * 通用GET
 * @param {*} param 
 */
 export const currentGET=  (key,param)=> {
    return API.GET(paramType[key], param)
}
/**
 * 通用POST
 * @param {*} param 
 */
 export const currentPOST=  (key,param)=> {
    return API.POST(paramType[key], param)
}
// 通用接口集合
export const currentApi={
    currentList,
    currentPage,
    currentSave,
    currentUpdate,
    currentDelete,
    currentSelect,
    currentSelectList,
    currentExport,
    currencyExport,
    currentPOST,
    currentGET
}
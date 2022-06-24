/*
 * @Author: daidai
 * @Date: 2021-12-20 17:33:37
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 16:19:59
 * @FilePath: \web-pc\src\api\modules\upload.js
 */

import * as API from "../api";
import { baseUrl } from '../api'
import { getToken } from '@/lib/currency'
const uploadType = {
    1: 'gateway', //设备图片、（网关）
    2: 'terminal', //终端
}
//上传图片地址
export const uploadUrl = (type, id) => {
    return baseUrl + `/biz/file/upload?tableId=${id}&tableName=${uploadType[type]}`
}
// 删除图片
export const delFile = (id) => {
    return API.GET("/biz/file/delete/" + id)
}

//上传升级包
export const updatapageData = {
    url: baseUrl + "/biz/upgradepackage/save",
    header: () => {
        return {
            token: getToken()
        }
    }
}

// 导入杆塔
export const uploadPoleUrl = (id) => {
    return `/biz/pole/import?lineId=${id}`
}

// 导入线路
export const uploadLineUrl = () => {
    return `/biz/line/import`
}
// 导入网关和终端
export const uploadgatewayUrl = (param) => {
    return `/biz/gateway/import`


}

// 导入
export const uploadXlsx = (param) => {
    // console.log(param)    
    var formData = new FormData()
    if (param.data) {
        Object.keys(param.data).forEach(key => {
            formData.append(key, param.data[key]);
        });
    }
    formData.append(param.filename, param.file)
    return API.FILESubmit(param.action, formData, {
        onProgress: param.onProgress,
    })
}
//上传图片
export const currentFILEImg = (key, id, param) => {
    var formData = new FormData()
    formData.append(param.filename, param.file)

    if (param.data) {
        Object.keys(param.data).forEach(key => {
            formData.append(key, param.data[key]);
        });
    }

    return API.FILESubmit(`/biz/file/upload?tableId=${id}&tableName=${uploadType[key]}`, formData, {
        onProgress: param.onProgress,
        setCancel: param.setCancel,
    })
}
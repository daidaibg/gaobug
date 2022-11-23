
/*
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-11-23 15:46:53
 */
import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { useUserStore } from '@/store'

import UtilVar from "../config/UtilVar";
import router from "../router/index"
import { RequestEnum } from "@/enums"
import signMd5Utils from "@/utils/modules/signMd5Utils"
import { getToken } from "@/utils/auth"

import type {fileconfigs} from "./index.d"
// console.log(router);
const baseUrl = UtilVar.baseUrl
const CancelToken = axios.CancelToken;
const service = axios.create({
    // 超时
    timeout: 10000
})
//统一拦截
const isEncryptionParam = (params: Params) => {
    return params
}
//签名参数
const getSign = (params: Params) => {
    let timestamp = Date.now()
    params = isEncryptionParam(params)
    let sign = signMd5Utils.getSign(params, timestamp);
    let headers = {
        'enc': UtilVar.ENC,//是否加密
        [RequestEnum.GB_SIGN_KEY]: sign,
        [RequestEnum.GB_TIMESTAMP_KEY]: timestamp
    }
    return { headers, encParams: params }
}
// @ts-ignore
service.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// axios.defaults.withCredentials = true;
// 添加请求拦截器
service.interceptors.request.use(function (config: AxiosRequestConfig) {
    let token = getToken();
    // @ts-ignore
    config.headers.common[RequestEnum.GB_APP_ID_KEY] = "blog-platform";//根据自己实际情况
    if (token) {
        // @ts-ignore
        config.headers.common[RequestEnum.GB_TOKEN_KEY] = token;
    }
    return config;
}, function (error: any) {
    // 对请求错误做些什么
    console.log(error)
    return Promise.reject(error);
});

/**
 * @响应拦截
 */
service.interceptors.response.use((response: AxiosResponse) => {
    if (response.request.responseType === 'blob' || response.request.responseType === 'arraybuffer') {
        return response.data
    }
    if (response.status !== 200) {
        return Promise.reject(response)
    }
    if (response.data.code == UtilVar.code) {
        const { userOffline } = useUserStore()
        userOffline()//下线 
        return Promise.resolve(response.data)
    }
    return Promise.resolve(response.data)
}, (error: any) => {
    let err = {
        success: false,
        msg: "未知异常，请联系管理员！"
    }
    if (JSON.stringify(error).indexOf('Network Error') != -1) {
        err.msg = "网络错误或服务错误！"
    }
    return Promise.reject(err)
})
export type Params = { [key: string]: string | number };
export type FileConfig = {
    setCancel?: Function;
    onProgress?: Function;
    [key: string]: any;
};

/**
 * @description: get 请求方法
 * @param {string} url 请求地址
 * @param {Params} params 请求参数
 * @return {*}
 */
export const GET = async (url: string, params: Params): Promise<any> => {
    try {
        const { headers, encParams } = getSign(params)
        const data = await service.get(`${baseUrl}${url}`, {
            params: encParams,
            headers,
        });
        return data;
    } catch (error) {
        return error;
    }
}
/**
 * @description: post请求方法
 * @param {string} url
 * @param {Params} params
 * @return {Promise} 
 */
export const POST = async (url: string, params: Params): Promise<any> => {
    try {
        const { headers, encParams } = getSign(params)
        const data = await service.post(`${baseUrl}${url}`, encParams,
            {
                headers
            }
        );
        return data;
    } catch (error) {
        return error;
    }
}


const configs: fileconfigs = {
    "headers": { 'Content-Type': 'multipart/form-data' },
}
/**
 * @description: @文件类型提交方法
 * @param {string} url
 * @param {Params} params
 * @param {FileConfig} config
 * @return {*}
 */
export const FILEPOST = async (url: string, params: Params, config: FileConfig = {}): Promise<any> => {
    try {
        const data = await service.post(`${baseUrl}${url}`, params, {
            ...configs,
            cancelToken: new CancelToken(function executor(c: any) {
                config.setCancel && config.setCancel(c)
            }),
            // 上传进度
            onUploadProgress: (e: any) => {
                if (e.total > 0) {
                    e.percent = e.loaded / e.total * 100;
                }
                config.onProgress && config.onProgress(e)
            },

        });
        return data;
    } catch (err) {
        return err;
    }
}

/**
 * 下载文档流
 * @param {config.responseType} 下载文件流根据后端 配置   arraybuffer || blod
 */
export const FILE = async (config: FileConfig = {}) => {

    try {
        const data = await service({
            method: config.method || 'get',
            url: `${baseUrl}${config.url}`,
            data: config.body || {},
            params: config.param || {},
            responseType: config.responseType || 'blod',
            onDownloadProgress: (e: any) => {
                // console.log(e,e.currentTarget)
                // if (e.currentTarget.response.size > 0) {
                //     e.percent = e.loaded / e.currentTarget.response.size * 100;
                // }
                // event.srcElement.getResponseHeader('content-length')
                config.onProgress && config.onProgress(e)
            },
        });
        return data;
    } catch (err) {
        return err;
    }
}
/**
 * @description: 可以直接使用对象方式，此处为了不区分大小写
 * @return {*}
 */
export const request = new Proxy({
    GET,
    POST
}, {
    get(target: any, p: any) {
        if (target[p.toUpperCase()]) {
            return target[p.toUpperCase()]
        }
        return Promise.reject("请求方法错误")
    }
})

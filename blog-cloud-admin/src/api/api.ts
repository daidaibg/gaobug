
/*
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-06-15 10:48:13
 */
import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { useUserStore } from '@/store'

import UtilVar from "../config/UtilVar";
import router from "../router/index"
import { getLocalStorage } from "@/utils"
import { StorageEnum, RequestEnum } from "@/enums"
import signMd5Utils from "./signMd5Utils"
// console.log(router);
let baseUrl = UtilVar.baseUrl
const CancelToken = axios.CancelToken;

export { baseUrl };
// axios.defaults.withCredentials = true;
// 添加请求拦截器
axios.interceptors.request.use(function (config: AxiosRequestConfig) {
    // console.log(config);
    // 在发送请求之前做些什么 传token
    let token = getLocalStorage(StorageEnum.GB_TOKEN_STORE);
    config.headers = {
        ...config.headers,
        'Content-Type': "application/json;charset=utf-8",
        [RequestEnum.GB_APP_ID_KEY]: "blog-platform",
        [RequestEnum.GB_TOKEN_KEY]: token
    }
    // config.headers.common['Content-Type'] = "application/json;charset=utf-8";
    // config.headers.common['app-id'] = "blog-platform";
    // config.headers.common[RequestEnum.GB_TOKEN_KEY] = token;
    // console.log(config)
    return config;
}, function (error: any) {
    // 对请求错误做些什么
    console.log(error)
    return Promise.reject(error);
});

export type Params = { [key: string]: string | number };
export type FileConfig = {
    setCancel?: Function;
    onProgress?: Function;
    [key: string]: any;
};
/**
 * @响应拦截
 */
axios.interceptors.response.use((response: AxiosResponse) => {
    /**
     * @code 登录过期 token验证失败 根据后端调 
     */
    // console.log(response)
    if (response.status !== 200) {
        return Promise.reject(response)
    }
    if (response.data.code == UtilVar.code) {
        // router.push("/login")
        const {userOffline} = useUserStore()
        userOffline()//下线 
        return response.data
    }
    return response.data
}, (error: any) => {
    // console.log(error);
    let err = {
        success: false,
        msg: "未知异常，请联系管理员！"
    }
    if (JSON.stringify(error).indexOf('Network Error') != -1) {
        err.msg = "网络错误或服务错误！"
    }
    // console.log(err);
    return Promise.reject(err)
})

//是否加密，请求头
let configs_ENC: any = {
    headers: { 'enc': UtilVar.ENC }
}
//判断是否是加密参数，是的话处理
let isEncryptionParam = (params: Params) => {
    return params
}
/**
 * @description: get 请求方法
 * @param {string} url 请求地址
 * @param {Params} params 请求参数
 * @return {*}
 */
export const GET = async (url: string, params: Params): Promise<any> => {
    try {
        let timestamp = Date.now()
        params = isEncryptionParam(params)
        let sign = signMd5Utils.getSign(params, timestamp);
        // console.log(sign);
        const data = await axios.get(`${baseUrl}${url}`, {
            params: params,
            headers: {
                [RequestEnum.GB_SIGN_KEY]: sign,
                [RequestEnum.GB_TIMESTAMP_KEY]: timestamp
            }
        });
        return data;
    } catch (error) {
        return error;
    }
}
/**
 * @description: post请求方法
 * @param {any} url
 * @param {any} params
 * @return {any} 
 */
export const POST = async (url: string, params: Params): Promise<any> => {
    try {
        let timestamp = Date.now()
        params = isEncryptionParam(params)
        let sign = signMd5Utils.getSign(params, timestamp);
        const data = await axios.post(`${baseUrl}${url}`, params,
            {
                headers: {
                    [RequestEnum.GB_SIGN_KEY]: sign,
                    [RequestEnum.GB_TIMESTAMP_KEY]: timestamp
                }
            }
        );
        return data;
    } catch (error) {
        return error;
    }
}
/**
 * @description: 没有基地址 访问根目录下文件
 * @param {string} url
 * @param {Params} params
 * @return {*}
 */
export const GETNOBASE = async (url: string, params: Params): Promise<any> => {
    try {
        const data = await axios.get(url, {
            params: params,
        });
        return data;
    } catch (error) {
        return error;
    }
}


// 定义文件类型提交方法
interface fileconfigs {
    [headers: string]: {
        'Content-Type': string
    }
}
let configs: fileconfigs = {
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
        const data = await axios.post(`${baseUrl}${url}`, params, {
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
        const data = await axios({
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


export const PUT = async (url: string, params: Params) => {
    try {
        params = isEncryptionParam(params)
        const data = await axios.put(`${baseUrl}${url}`, params);
        return data;
    } catch (error) {
        return error;
    }
}
export const DELETE = async (url: string, params: Params) => {
    // console.log(params)
    try {
        params = isEncryptionParam(params)
        const data = await axios.delete(`${baseUrl}${url}`, { data: params });
        return data;
    } catch (error) {
        return error;
    }
}


// switch (error.response?.status) {
//     case 400:
//       error.message = '请求错误(400)';
//       break;
//     case 401:
//       error.message = '未授权(401)';
//       break;
//     case 403:
//       error.message = '拒绝访问(403)';
//       break;
//     case 404:
//       error.message = '请求出错(404)';
//       break;
//     case 408:
//       error.message = '请求超时(408)';
//       break;
//     case 500:
//       error.message = '服务器错误(500)';
//       break;
//     case 501:
//       error.message = '服务未实现(501)';
//       break;
//     case 502:
//       error.message = '网络错误(502)';
//       break;
//     case 503:
//       error.message = '服务不可用(503)';
//       break;
//     case 504:
//       error.message = '网络超时(504)';
//       break;
//     case 505:
//       error.message = 'HTTP版本不受支持(505)';
//       break;
//     default:
//       error.message = `连接出错(${error.response?.status})!`;
//   }
import axios, { AxiosRequestConfig, AxiosResponse, Method, AxiosInstance, AxiosError } from 'axios';
import { useUserStore } from '@/store'
import UtilVar from "../config/UtilVar";
import router from "../router/index"
import { RequestEnum } from "@/enums"
import signMd5Utils from "@/utils/modules/signMd5Utils"
import { getToken } from "@/utils/auth"
import sm4 from "@/utils/sm4/sm4"
import type { fileconfigs ,Config} from "./index.d"
// console.log(router);
const baseUrl = UtilVar.baseUrl
const CancelToken = axios.CancelToken;

const service: AxiosInstance = axios.create({
    // 超时
    timeout: 10000,
    withCredentials: false, // 禁用 Cookie 等信息
    headers: {
        "Content-Type": "application/json;chartset=utf-8"
    }
})

//统一拦截
const isEncryptionParam = <T = Params>(params: T,config:Config={}) => {
    if(config.enc){
        params=  sm4.encrypt(JSON.stringify(params), UtilVar.ENC_key)
    }
    return params
}

//签名参数
const getSign = <T = Params>(params: T,config?:Config) => {
    let timestamp = Date.now()
    params = isEncryptionParam<T>(params,config)
    let sign = signMd5Utils.getSign(params, timestamp);
    let headers:{
        "enc":boolean,
        [RequestEnum.GB_SIGN_KEY]: string,
        [RequestEnum.GB_TIMESTAMP_KEY]: string|number,
        'Content-Type'?:string
    } = {
        'enc': UtilVar.ENC,
        [RequestEnum.GB_SIGN_KEY]: sign,
        [RequestEnum.GB_TIMESTAMP_KEY]: timestamp,
    }
    if(config&& config['Content-Type']){
        headers['Content-Type']=config['Content-Type']
    }
    return { headers, encParams: params as T }
}

// 添加请求拦截器
service.interceptors.request.use(function (config: AxiosRequestConfig):any {
    let token = getToken();
    (config as Recordable).headers[RequestEnum.GB_APP_ID_KEY] = "blog-platform";//根据自己实际情况
    if (token) {
        (config as Recordable).headers[RequestEnum.GB_TOKEN_KEY]  = `${token}`;
    }
    return config;
}, function (error: AxiosError) {
    // 对请求错误做些什么
    console.log(error)
    return Promise.reject(error);
});

/**
 * @响应拦截
 */
service.interceptors.response.use((response: AxiosResponse) => {
    if (response.request.responseType === 'blob' || response.request.responseType === 'arraybuffer') {
        return Promise.resolve(response)
    }
    if (response.status !== 200) {
        return Promise.reject(response)
    }
    if (response.data.code == UtilVar.code) {
        const { userOffline } = useUserStore()
        userOffline()//下线 
    }
    return Promise.resolve(response)
}, (error: AxiosError) => {
    console.log("response",error);
    let err = {
        success: false,
        msg: "未知异常，请联系管理员！",
        contnt:error
    }
    if (JSON.stringify(error).indexOf('Network Error') != -1) {
        err.msg = "网络错误或服务错误！"
    }

    return Promise.reject(err)
})

export type Params = { msg?: string, [key: string]: string | number | undefined |any };
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
export const requestGet = async <T = Params, R = Params>(url: string, params: T): Promise<AxiosResponse<R>["data"]> => {
    const { headers, encParams } = getSign(params)
    try {
        const data = await service.get<T, AxiosResponse<R>>(`${baseUrl}${url}`, {
            params: encParams,
            headers,
        });
        return data.data;
    } catch (error) {
        return Promise.reject(error)
    }
}
//请求public 目录文件
export const getPublic = async (url: string) => {
    try {
        const data = await service.get(url);
        return data;
    } catch (error) {
        return Promise.reject(error)
    }
}
/**
 * @description: post请求方法
 * @param {string} url
 * @param {Params} params
 * @return {Promise} 
 */
export const requestPost = async <T = Params, R = Params>(url: string, params: T,config?:Config): Promise<AxiosResponse<R>["data"]> => {
    const { headers, encParams } = getSign<T>(params,config)
    try {
        const response = await service.post<T, AxiosResponse<R>>(`${baseUrl}${url}`, encParams,
            {
                headers
            });
        return response.data
    } catch (error) {
        return Promise.reject(error)
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
        return data.data;
    } catch (error) {
        return Promise.reject(error)
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
    GET: requestGet,
    POST: requestPost
}, {
    get(target: any, p: any) {
        if (target[p.toUpperCase()]) {
            return target[p.toUpperCase()]
        }
        return Promise.reject("请求方法错误")
    }
})


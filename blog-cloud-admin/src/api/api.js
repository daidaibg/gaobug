
/*
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-05-09 15:51:31
 */
import axios from 'axios';
import UtilVar from "@/config/UtilVar";
import router from '@/router'
const CancelToken = axios.CancelToken;
const { isAjax, baseUrl,ENC } = UtilVar
export { baseUrl };
// axios.defaults.withCredentials = true
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么 传token
    let token = localStorage.getItem("token");
    // config.headers.common['Content-Type'] = "application/json;charset=utf-8";
    config.headers.common['token'] = token;  //Authorization
    config.headers.common['enc'] = ENC;  //是否加密
    
    // console.log(config)
    return config;
}, function (error) {
    // 对请求错误做些什么
    console.log(error)
    return Promise.reject(error);
});

/**
 * @响应拦截
 */
axios.interceptors.response.use(response => {
    /**
     * @code 登录过期 token验证失败 根据后端调 
     */
    // console.log(response)

    if (response.success !== undefined) {
        return response
    }

    if (response.status !== 200) {
        return Promise.reject(response)
    }
    if (response.data.code == UtilVar.code) {
        router.push("/login")
        return response.data
    }
    return response.data
}, error => {
    console.log(error);
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

let isEncryptionParam = (params) => {
    return params

}
export const GET = async (url, params) => {
    try {
        params = isEncryptionParam(params)
    
        const data = await axios.get(`${baseUrl}${url}`, {
            params: params,

        });
        return data;
    } catch (error) {
        return error;
    }
}
//没有基地址 访问根目录下文件

export const GETNOBASE = async (url, params) => {
    try {
        const data = await axios.get(url, {
            params: params,
        });
        return data;
    } catch (error) {
        return error;
    }
}
export const POST = async (url, params) => {
    try {
        params = isEncryptionParam(params)
    
        const data = await axios.post(`${baseUrl}${url}`, params,);
        return data;
    } catch (error) {
        return error;
    }
}
export const PUT = async (url, params) => {
    try {
        params = isEncryptionParam(params)
        const data = await axios.put(`${baseUrl}${url}`, params,);
        return data;
    } catch (error) {
        return error;
    }
}
export const DELETE = async (url, params) => {
    // console.log(params)
    try {
        params = isEncryptionParam(params)
        const data = await axios.delete(`${baseUrl}${url}`, { data: params, });
        return data;
    } catch (error) {
        return error;
    }
}


/**
 * @文件类型提交方法
 */
let configs = {
    headers: { 'Content-Type': 'multipart/form-data' },

}
export const FILESubmit = async (url, params, config) => {
    try {
        const data = await axios.post(`${baseUrl}${url}`, params, {
            ...configs,
            cancelToken: new CancelToken(function executor(c) {
                config.setCancel && config.setCancel(c)
            }),
            onUploadProgress: (e) => {
                if (e.total > 0) {
                    e.percent = e.loaded / e.total * 100;
                }
                // console.log(config)
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
export const FILE = async (config = {}, body, params) => {
    try {
        const data = await axios({
            method: config.method || 'get',
            url: `${baseUrl}${config.url}`,
            data: body,
            params: params,
            responseType: config.responseType || 'blob',
            onDownloadProgress: (e) => {
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



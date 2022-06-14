/*
 * @Author: daidai
 * @Date: 2021-12-09 10:47:56
 * @LastEditors: daidai
 * @LastEditTime: 2022-01-18 16:17:45
 * @FilePath: \web-pc\src\api\index.js
 */
import {
    companyApi, regionApi, menuApi,
    deptApi, roleApi, userApi, dictApi
} from './modules/sys'
import {
    currentApi
} from './modules'
import * as loginApi from './login'
export {
    companyApi,
    regionApi,
    menuApi,
    deptApi,
    roleApi,
    userApi,
    dictApi
}
export {
    loginApi
}
export {
    currentApi
}

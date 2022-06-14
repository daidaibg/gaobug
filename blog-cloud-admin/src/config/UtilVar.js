/*
 * @Author: daidai
 * @Date: 2021-12-06 10:58:24
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-05-10 17:06:52
 * @FilePath: \web-pc\src\config\UtilVar.js
 */
var UtilVar = {
    ENC: false, //返回结果是否加密
    // baseUrl: `localhost:19527`,
    baseUrl: `http://www.xihuanmantou.cn:19527`,
    isAjax: false,
    code: 401,
}
const runtimeType = {

    production: () => {
    },
    sk: () => {
    },
    yh: () => {
    },
    //开发环境
    development: () => {
        UtilVar.baseUrl = "http://www.xihuanmantou.cn:19527"
    },

}
console.log(process.env)
runtimeType[process.env.VUE_APP_URL_ENV]()
export default UtilVar


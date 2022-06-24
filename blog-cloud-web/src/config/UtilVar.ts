interface UtilVarType {
    baseUrl:string,
    code:string|number,

}

const UtilVar = {
    baseUrl:"https://www.gaobug.com/blog",
    code:401, //登陆过期
    noContentCode:204, //请求成功但没有内容
    ENC:false,

}
const runtimeType:any = {

    production: () => {

    },
    //开发环境
    development: () => {
        // UtilVar.baseUrl= `http://www.xihuanmantou.cn:19527`

    },
    
}
// console.log(import.meta.env)
runtimeType[import.meta.env.MODE]()
export default UtilVar
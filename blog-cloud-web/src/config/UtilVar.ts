interface UtilVarType {
    baseUrl: string,
    code: string | number,
    noContentCode: number,
    ENC: boolean,//是否进行加密
    ENC_key: string,//加密key
    assetsBaseUrl: string,
    resourceUrl: string,//资源地址
}

const UtilVar: UtilVarType = {
    baseUrl: "https://www.gaobug.com/blog",
    code: 401, //登陆过期
    noContentCode: 204, //请求成功但没有内容
    ENC: false,
    ENC_key: "f81697720ea83de3",
    resourceUrl: "https://www.daidaibg.com",
    get assetsBaseUrl(): string {
        return this.resourceUrl
    }
}
const runtimeType: any = {

    production: () => {

    },
    //开发环境
    development: () => {
        // UtilVar.baseUrl= `http://www.xihuanmantou.cn:19527`

    },
    hash: () => {

    }

}
// console.log(import.meta.env)
runtimeType[import.meta.env.MODE] && runtimeType[import.meta.env.MODE]()
export default UtilVar
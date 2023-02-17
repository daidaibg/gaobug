import {Home} from "../modules/home"

import {requestGet} from "../api";



//支持md语法示例
export const mdGrammar = (param: any = {}) => {
    return requestGet(Home["indexBlogDetail"]+89,param )
}
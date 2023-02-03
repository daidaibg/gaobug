
import {Home} from "./home"
import {request} from "../api";

//我的博客列表
export const getUserBlogList = (param:any)=>{
    return request.get(Home.iBlogList,param)
}

//删除博客
export const deleteBlog = (id:string)=>{
    return request.post(Home.deleteBlog+id,{})
}
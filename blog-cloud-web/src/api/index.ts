/*
 * @Author: daidai
 * @Date: 2021-12-23 11:18:37
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2023-02-13 13:01:07
 * @FilePath: \web-pc-svn\src\api\modules\index.js
 */

import {requestGet,requestPost,FILEPOST,request,getPublic} from "./api";
import {Home} from "./modules/home"
import type {UrlObjType} from "./index.d"
export * from "./modules/upload"
export const urlObj:UrlObjType = {
  ...Home,
};
type Keys=string | number

export const currentGET = (key:Keys, param?: any,pathParam:string|string[]="",) => {
  
  let url = urlObj[key]+pathParam
  return requestGet(url, param||{});
};
export const currentPOST = (key:Keys, param?: any) => {
  return requestPost(urlObj[key], param||{});
};
export const currentPOSTPath = (key:Keys,type:Keys,param?: any) => {
  return requestPost(urlObj[key]+type, param||{});
};
//通用上传
export const currentFILEPOST = (key:Keys, param?: any) => {
  return FILEPOST(urlObj[key], param||{},{});
};

export {getPublic}
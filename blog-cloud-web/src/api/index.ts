/*
 * @Author: daidai
 * @Date: 2021-12-23 11:18:37
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-11-23 17:20:29
 * @FilePath: \web-pc-svn\src\api\modules\index.js
 */

import {GET,POST,FILEPOST,request} from "./api";
import {Home} from "./modules/home"
import type {UrlObjType} from "./index.d"
export * from "./modules/upload"
export const urlObj:UrlObjType = {
  ...Home,
};
type Keys=string | number

export const requestGet = (key:Keys, param?: any,pathParam:string|string[]="",) => {
  let url = urlObj[key]+pathParam
  return request.get(url, param||{});
};
export const currentPOST = (key:Keys, param?: any) => {
  return POST(urlObj[key], param||{});
};
export const currentPOSTPath = (key:Keys,type:Keys,param?: any) => {
  return POST(urlObj[key]+type, param||{});
};
//通用上传
export const currentFILEPOST = (key:Keys, param?: any) => {
  return FILEPOST(urlObj[key], param||{},{});
};
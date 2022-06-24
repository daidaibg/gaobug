/*
 * @Author: daidai
 * @Date: 2021-10-11 17:54:21
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-15 09:11:27
 * @FilePath: \web-pc\src\constants\index.js
 */
//前缀
export const BASE_PREFIX ="gd";
//密码验证
export const passwordReg = /(?=.*[a-z])(?=.*\d)(?=.*[#@!~%^&*])[a-z\d#@!~%^&*]{8,16}/i;
// 简单密码
export const passwordRegSimple = new RegExp('(?!^([0-9]+|[a-zA-Z]+|[!#*_]+)$)^[a-zA-Z0-9!#*_]{6,16}$');
//邮箱验证
export const emailReg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
//项目名称
export const sysName = "后台管理系统"
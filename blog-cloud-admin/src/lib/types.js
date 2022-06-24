/*
 * @Author: daidai
 * @Date: 2021-12-14 09:15:11
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-16 14:21:39
 * @FilePath: \web-pc\src\lib\types.js
 */
// 是否字符串
export function isString2(str) {
    return (typeof str == 'string') && str.constructor == String;
}
export function isString(obj) {
    return Object.prototype.toString.call(obj) === '[object String]';
}
export function isObject(obj) {
    return Object.prototype.toString.call(obj) === '[object Object]';
}
export function isNumber(obj) {
    return Object.prototype.toString.call(obj) === '[object Number]';
}
// 是否完整的
export function isDef(val) {
    return val !== undefined && val !== null;
}
//
export function isKorean(text) {
    const reg = /([(\uAC00-\uD7AF)|(\u3130-\u318F)])+/gi;
    return reg.test(text);
}

export function isHtmlElement(node) {
    return node && node.nodeType === Node.ELEMENT_NODE;
}
export const isUndefined = (val) => {
    return val === void 0;
};



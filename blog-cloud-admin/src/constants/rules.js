/*
 * @Author: daidai
 * @Date: 2021-12-09 15:54:05
 * @LastEditors: daidai
 * @LastEditTime: 2021-12-22 17:48:33
 * @FilePath: \web-pc\src\constants\rules.js
 */

//element 表单的验证
import { passwordReg, emailReg, passwordRegSimple } from './index'

export const numrule = /^\d{1,}$/;
// 三位小数
export const treeNumrule = /^[0-9]+(.[0-9]{1,3})?$/
const validatePhone = (rule, value, callback) => {
    const phoneReg = /^1[3456789]\d{9}$/;
    if (phoneReg.test(value) || value == "") {
        return callback();
    } else {
        callback(new Error("手机号格式不正确"));
    }
    // phoneReg.test(value);
};

const validateNum = (rule, value, callback) => {
    
    const phoneReg = /^\d{1,}$/;
    if (phoneReg.test(value)) {
        return callback();
    } else {
        callback(new Error("请输入数字"));
    }
    // phoneReg.test(value);
};
const validateEmailReg = (rule, value, callback) => {
    if (emailReg.test(value) || value == "") {
        return callback();
    } else {
        callback(new Error("邮箱格式不正确"));
    }
};
const validatePassword = (rule, value, callback) => {
    passwordReg.test(value)
        ? callback()
        : callback(
            new Error("密码请设置为8位或以上，需包含字母、数字、特殊符号")
        );
};

// 简单密码
const validatePasswordSimple = (rule, value, callback) => {
    passwordRegSimple.test(value)
        ? callback()
        : callback(
            new Error("密码请设置为6位或以上，需包含字母、大小写、数字、特殊符号其中两种")
        );
};
// 可为空
const validatePasswordOrEmpty = (rule, value, callback) => {
    // console.log(value)
    if (value == "") {
        callback();
    }
    passwordReg.test(value)
        ? callback()
        : callback(
            new Error("密码请设置为8位或以上，需包含字母、数字、特殊符号")
        );
};
// 可为空简单
const validatePasswordOrEmptySimple = (rule, value, callback) => {
    // console.log(value)
    if (value == "") {
        callback();
    }
    passwordRegSimple.test(value)
        ? callback()
        : callback(
            new Error("密码请设置为6位或以上，需包含字母、大小写、数字、特殊符号其中两种")
        );
};

// 验证小数 三位
export const validateTreeNumrule = (str="") => {
    function validateTreeNumruleinner(rule, value, callback) {
        if (value == "") {
            callback(new Error("请输入"+str+'!'));
        }
        treeNumrule.test(value)
            ? callback()
            : callback(
                new Error("请输入正确的格式,正整数、最高三位小数！")
            );
    }
    return validateTreeNumruleinner
};

// 验证正整数
export const validatenumrule = (str="") => {
    function inner(rule, value, callback) {
        if (value == "") {
            callback(new Error("请输入"+str+'!'));
        }
        numrule.test(value)
            ? callback()
            : callback(
                new Error("请输入正确的格式,正整数！")
            );
    }
    return inner
};
export {
    validatePhone, validatePassword, validatePasswordOrEmpty, validateEmailReg, validatePasswordSimple, validatePasswordOrEmptySimple,
    validateNum
}
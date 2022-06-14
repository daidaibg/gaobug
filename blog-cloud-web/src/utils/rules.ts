//邮箱验证
export const emailReg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

export const validateEmailReg = (rule:any, value:any, callback:any) => {
    if (emailReg.test(value) || value == "") {
        return callback();
    } else {
        callback(new Error("邮箱格式不正确"));
    }
};

export const validatePhone = (rule:any, value:any, callback:any) => {
    const phoneReg = /^1[3456789]\d{9}$/;
    if (phoneReg.test(value) || value == "") {
        return callback();
    } else {
        callback(new Error("手机号格式不正确"));
    }
};
//邮箱验证
export const emailReg = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

// export const emailReg =new RegExp("^(([^<>()[\\]\\\\.,;:\\s@\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") ;
// console.log(emailReg);
// console.log(emailReg2);
export const validateEmailReg = (rule: any, value: any, callback: any) => {
    if (emailReg.test(value)) {
        callback();
    } else if (value == "") {
        callback(new Error("请输入邮箱!"));
    } else {
        callback(new Error("邮箱格式不正确!"));
    }
};

export const validatePhone = (rule: any, value: any, callback: any) => {
    // const phoneReg = /^1[3456789]\d{9}$/;
    const phoneReg = new RegExp("^1[3456789]\d{9}$");

    if (phoneReg.test(value) || value == "") {
        return callback();
    } else {
        callback(new Error("手机号格式不正确!"));
    }
};

export const validatePassword = (rule: any, value: any, callback: any) => {
    // var reg = /^[@A-Za-z0-9!#$%^&*.~,]{6,20}$/;
    var reg =  new RegExp("^[@A-Za-z0-9!#$%^&*.~,]{6,20}$");


    if (reg.test(value)  ) {
         callback();
    }else if(value == ""){
        callback(new Error("请输入密码!"));
    } else {
        callback(new Error("密码格式不正确!"));
    }
}
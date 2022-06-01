package com.imantou.response.enums;

import lombok.Getter;

/**
 * @author yhht
 */

@Getter
public enum ResultEnum {
    SUCCESS(200, true, "操作成功！"),
    SUCCESS_SAVE(200, true, "保存成功！"),
    SUCCESS_UPDATE(200, true, "修改成功！"),
    SUCCESS_DELETE(200, true, "删除成功！"),
    ERROR(400, false, "操作出现异常，请重试或联系管理员！"),
    ERROR_METHOD(400, false, "错误的请求方法！"),
    ERROR_REDIS_OUT_TIME(400, false, "redis连接超时，请重试或联系管理员"),
    ERROR_PARAMS(400, false, "错误的请求参数，请重试或联系管理员！"),
    ERROR_TRANSFER(400, false, "传输出现异常，请重试或联系管理员！"),
    ERROR_SERVER(400, false, "服务器出现异常，请重试或联系管理员！"),
    LOGON_CAPTCHA_FAILED(406, false, "登录验证失败"),
    LOGON_CAPTCHA_EXPIRE(410, false, "登录验证已过期"),
    LOGON_TOKEN_EXPIRE(401, false, "令牌无效或已过期"),
    LOGON_TOKEN_OTHER(401, false, "您的账号已在别处登录"),
    ERROR_NOT_FOUND(404, false, "这里什么都没有"),
    ERROR_UNAUTHORIZED(403, false, "越权访问"),
    ERROR_USER_PASSWORD(400, false, "账号或密码错误！"),
    ERROR_LOCKED(400, false, "密码输错次数超限，账号已锁定，请N秒后再尝试登录！"),
    ERROR_PASSWORD_EXPIRE(400, false, "您的密码密码已过期，请修改后再登录！"),
    ERROR_PASSWORD_DEFAULT(400, false, "您的密码为初始密码,请修改后再登录！"),
    ERROR_PASSWORD_DECODE(400, false, "解密异常，请重试或联系管理员！"),
    ERROR_PASSWORD_SAME(400, false, "新密码不能与历史密码一致！"),
    ERROR_PASSWORD_OLD_ERROR(400, false, "旧密码错误！"),
    ERROR_PASSWORD_WRONG_TYPE(400, false, "密码至少包含两种字符类型【a-z,A-Z,0-9,_】！"),
    ERROR_PASSWORD_WRONG_LEN(400, false, "密码长度必须在 8 ~ 16个字符之间！"),
    ERROR_PASSWORD_WEAK(400, false, "弱口令密码！"),
    ERROR_PASSWORD_TRAN_COMPLETE(400, false, "传输完整性遭受破坏，请重试或联系管理员！"),
    ERROR_PASSWORD_DB_COMPLETE(400, false, "数据库完整性遭受破坏，请重试或联系管理员！"),
    ERROR_PASSWORD_MODIFY_SUCCESS(400, true, "修改密码成功！"),

    ERROR_DEVICE_NOT_FOUND(400, false, "未找到设备信息。"),
    ;

    private Integer code;
    private boolean success;
    private String msg;

    ResultEnum(Integer code, boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }
}

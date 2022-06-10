package com.gaobug.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author gaobug
 */
@Getter
@Setter
@ToString
public class PlatformLoginForm {
    /*账号密码登录*/
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /*快捷登录*/
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String code;

    private Integer loginType;
}

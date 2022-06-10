package com.gaobug.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 平台用户注册表单
 *
 * @author gaobug
 */
@Getter
@Setter
@ToString
public class PlatformRegisterDTO implements PlatformRegister{
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能低于六位")
    private String password;
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$")
    private String email;
    @NotBlank(message = "验证码不能为空")
    private String code;

    private String mobile;
}

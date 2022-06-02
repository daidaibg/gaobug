package com.imantou.api.dto;

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
public class PlatformMobileRegisterDTO implements PlatformRegister{

    private String mobile;
}

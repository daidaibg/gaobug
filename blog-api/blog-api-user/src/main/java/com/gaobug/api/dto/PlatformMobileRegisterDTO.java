package com.gaobug.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

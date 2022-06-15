package com.gaobug.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 平台用户注册表单
 *
 * @author gaobug
 */
@Getter
@Setter
@ToString
public class UserRegisterDTO {
    private String account;
    private String nickName;
    private String password;
    private String email;
    private String mobile;
    private String code;
}

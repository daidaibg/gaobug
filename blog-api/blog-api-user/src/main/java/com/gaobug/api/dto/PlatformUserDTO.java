package com.gaobug.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PlatformUserDTO {
    private String account;
    private String nickName;
    private String password;
    private String salt;
    private Integer gender;
    private String avatar;
    private String email;
    private Date birthday;
    private String mobile;
    private String summary;
}

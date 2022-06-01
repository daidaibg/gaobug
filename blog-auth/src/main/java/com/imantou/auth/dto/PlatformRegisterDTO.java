package com.imantou.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.imantou.verify.Verify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class PlatformRegisterDTO {
    @Verify(required = true, errorMsg = "用户名不能为空")
    private String username;
    private String nickName;
    @Verify(required = true, errorMsg = "密码不能为空")
    private String password;
    private Integer gender;
    @Verify(required = true, errorMsg = "请选择头像")
    private String avatar;
    @Verify(required = true, errorMsg = "邮箱不能为空")
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String mobile;
    @Verify(maxLength = 150, errorMsg = "个人简介字符不能超过150个字符")
    private String summary;
    /**
     * 注册渠道类型
     */
    private String channelType;
}

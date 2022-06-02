package com.imantou.api.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserContext {

    /**
     * 数据主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别(1:男2:女)
     */
    private Integer gender;

    /**
     * 个人头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生年月日
     */
    private Date birthday;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 自我简介最多150字
     */
    private String summary;

    /**
     * 昵称
     */
    private String nickName;
}

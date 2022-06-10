package com.imantou.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@JsonInclude
public class PlatformUserVO {
    /**
     * 数据主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

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

    /**
     * 评论状态 1:正常 0:禁言
     */
    private Integer commentStatus;

    /**
     * 是否开启邮件通知 1:开启 0:关闭
     */
    private Integer startEmailNotification;

    /**
     * 用户标签：0：普通用户，1：管理员，2：博主 等
     */
    private Integer userTag;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否通过加载校验【0 未通过，1 已通过】
     */
    private Integer loadingValid;

}

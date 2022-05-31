package com.imantou.auth.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PlatformUserContextVO {
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
     * 个人简介
     */
    private String summary;

    /**
     * 昵称
     */
    private String nickName;
    /**
     * 今日阅读数
     */
    private String readToday;
    /**
     * 昨日阅读数
     */
    private String readYesterday;
    /**
     * 今日点赞
     */
    private String likeToday;
    /**
     * 昨日点赞
     */
    private String likeYesterday;
    /**
     * 我的文章
     */
    private String myArticle;
    /**
     * 我的收藏
     */
    private String myCollection;
    /**
     * 我的关注
     */
    private String myAttention;
    /**
     * 最后查询时间
     */
    private Date lastTime;
}

package com.gaobug.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BlogPageVO {
    /**
     * 数据主键
     */
    private Long id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客简介
     */
    private String summary;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客点击数
     */
    private Integer clickCount;

    /**
     * 博客收藏数
     */
    private Integer collectCount;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 作者
     */
    private Long author;
    /**
     * 作者
     */
    private String authorName = "gaobug";
    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 是否开启评论：0：否 1：是
     */
    private Integer openComment;

    /**
     * 置顶（数字越大越靠前）
     */
    private Integer hot;
    /**
     * 距离发布时间过去多久
     */
    private String publishTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}

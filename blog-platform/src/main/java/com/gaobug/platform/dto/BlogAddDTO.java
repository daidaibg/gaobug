package com.gaobug.platform.dto;

import com.gaobug.verify.Verify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客文章
 * @author gaobug
 */
@Getter
@Setter
@ToString
public class BlogAddDTO {

    /**
     * 博客标题
     */
    @Verify(required = true, errorMsg = "博客标题不能空")
    private String title;

    /**
     * 博客简介
     */
    private String summary;

    /**
     * 博客内容
     */
    @Verify(required = true, errorMsg = "博客内容不能为空")
    private String content;

    /**
     * 标签标识
     */
    private String tag;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 文章出处
     */
    private String articlesPart;

    /**
     * 博客分类标识
     */
    private Long categoryId;

    /**
     * 是否发布：0：否，1：是
     */
    private Integer publish;

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
}
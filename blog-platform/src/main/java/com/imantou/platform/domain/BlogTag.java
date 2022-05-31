package com.imantou.platform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客标签
 * @TableName t_blog_tag
 */
@TableName(value ="t_blog_tag")
@Data
public class BlogTag implements Serializable {
    /**
     * 数据主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 分类描述
     */
    private String remark;

    /**
     * 标签状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 点击次数
     */
    private Integer clickCount;

    /**
     * 排序字段，越大越靠前
     */
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
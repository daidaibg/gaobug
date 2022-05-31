package com.imantou.platform.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客类目
 * @TableName t_blog_category
 */
@TableName(value ="t_blog_category")
@Data
public class BlogCategory implements Serializable {
    /**
     * 数据主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目等级
     */
    private Integer level;

    /**
     * 父类目标识（0：顶级类目）
     */
    private Long parentId;

    /**
     * 排序字段，越大越靠前
     */
    private Integer sort;

    /**
     * 点击数
     */
    private Integer clickCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}